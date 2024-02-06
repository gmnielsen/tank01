// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.Drive;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.Drivetrain;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drive = new Drivetrain();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final XboxController m_driverController =
      new XboxController(OperatorConstants.kDriverControllerPort);

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  // Camera
  Thread m_visionThread;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();    

    // A split-stick arcade command, with forward/backward controlled by the left
    // hand, and turning controlled by the right.
    m_drive.setDefaultCommand(
      Commands.run(
        () ->
          m_drive.arcadeDrive(
            // -m_driverController.getLeftY(), -m_driverController.getRightX() ),
            m_driverController.getRightTriggerAxis()-m_driverController.getLeftTriggerAxis(), -m_driverController.getRightX() ),
          m_drive)
    );

    // autonomous chooser()
    // allows us to pick different auto routines from the dashboard
    m_chooser.setDefaultOption("Simple Auto", Commands.runOnce( () -> Autos.simpleAuto(m_drive) ) );
    m_chooser.addOption("Simple Auto again", Commands.runOnce( () -> Autos.simpleAuto(m_drive) ) );
    // Put the autonomous chooser on the dashboard
    Shuffleboard.getTab("Autonomous").add(m_chooser);

    // Put subsystems to dashboard.
    Shuffleboard.getTab("Drivetrain").add(m_drive);

    cameraInit();

    // Set the scheduler to log Shuffleboard events for command initialize, interrupt, finish
    CommandScheduler.getInstance()
        .onCommandInitialize(
            command ->
                Shuffleboard.addEventMarker(
                    "Command initialized", command.getName(), EventImportance.kNormal));
    CommandScheduler.getInstance()
        .onCommandInterrupt(
            command ->
                Shuffleboard.addEventMarker(
                    "Command interrupted", command.getName(), EventImportance.kNormal));
    CommandScheduler.getInstance()
        .onCommandFinish(
            command ->
                Shuffleboard.addEventMarker(
                    "Command finished", command.getName(), EventImportance.kNormal));

    
  } // end RobotContainer constructor

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //    .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    // INLINE COMMAND
    // specify the xbox button from our Constants
    // specify the condition, then the type of command,
    // then specify it as inline () ->
    // then the actual command

    // While holding right bumper, drive at reduced speed
    new JoystickButton ( m_driverController, OperatorConstants.kSlowDownButton)
      .onTrue(Commands.runOnce( () -> m_drive.setSpeed(Drive.kReducedSpeed) ) )
      .onFalse(Commands.runOnce( () -> m_drive.setSpeed(Drive.kMaxSpeed) ) );

    // switch drive orientation
    new JoystickButton ( m_driverController, OperatorConstants.kFlipButton)
      .onTrue(Commands.runOnce( () -> m_drive.reverseOrientation() ) );
  }

  private void cameraInit(){
    m_visionThread =

        new Thread(

            () -> {

              // Get the UsbCamera from CameraServer

              UsbCamera camera = CameraServer.startAutomaticCapture();

              // Set the resolution

              camera.setResolution(640, 480);


              // Get a CvSink. This will capture Mats from the camera

              CvSink cvSink = CameraServer.getVideo();

              // Setup a CvSource. This will send images back to the Dashboard

              CvSource outputStream = CameraServer.putVideo("Rectangle", 640, 480);


              // Mats are very memory expensive. Lets reuse this Mat.

              Mat mat = new Mat();


              // This cannot be 'true'. The program will never exit if it is. This

              // lets the robot stop this thread when restarting robot code or

              // deploying.

              while (!Thread.interrupted()) {

                // Tell the CvSink to grab a frame from the camera and put it

                // in the source mat.  If there is an error notify the output.

                if (cvSink.grabFrame(mat) == 0) {

                  // Send the output the error.

                  outputStream.notifyError(cvSink.getError());

                  // skip the rest of the current iteration

                  continue;

                }

                // Put a rectangle on the image

                Imgproc.rectangle(

                    mat, new Point(100, 100), new Point(400, 400), new Scalar(255, 255, 255), 5);

                // Give the output stream a new image to display

                outputStream.putFrame(mat);

              }

            });

    m_visionThread.setDaemon(true);

    m_visionThread.start();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}
