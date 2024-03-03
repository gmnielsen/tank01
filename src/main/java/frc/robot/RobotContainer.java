// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.Drive;
import frc.robot.commands.Autos;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Swing;
import frc.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
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
  // private final noteHandler m_NoteHandler = new noteHandler();
  private final Intake m_intake = new Intake();
  private final Launcher m_launch = new Launcher();
  private final Swing m_swing = new Swing();
  private final Vision m_vision = new Vision();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(ControllerConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController =
        new CommandXboxController(ControllerConstants.kOperatorControllerPort);
  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  // Camera
  /* 
  Thread m_visionThread;
  UsbCamera camera01;
  UsbCamera camera02;
  VideoSink camServer;
  */

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    
    // Configure the trigger bindi
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

    // cameraInit();

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

    // when triggered, switch to camera02
    //new JoystickButton(m_driverController, OperatorConstants.kCameraButton)
    //  .toggleOnTrue(() -> camServer.setSource(camera01));
      //.toggleOnFalse(() -> camServer.set (camera02));
    //new JoystickButton(m_driverController, ControllerConstants.kCameraButton)
    m_driverController.y() 
      .toggleOnFalse(Commands.run( () -> m_vision.source01() ) )
      .toggleOnTrue(Commands.run( () -> m_vision.source02() ) );

    // While holding right bumper, drive at reduced speed
    //new JoystickButton (m_driverController, ControllerConstants.kSlowDownButton)
    m_driverController.rightBumper()
      .onTrue(Commands.runOnce( () -> m_drive.setSpeed(Drive.kReducedSpeed) ) )
      .onFalse(Commands.runOnce( () -> m_drive.setSpeed(Drive.kMaxSpeed) ) );

    // switch drive orientation
    //new JoystickButton ( m_driverController, ControllerConstants.kSwingButton)
    m_driverController.a()
      .onTrue(Commands.runOnce( () -> m_swing.swing(), m_swing))
      .onFalse(Commands.runOnce( () -> m_swing.swingOff(), m_swing));

    // switch drive orientation
    //new JoystickButton ( m_driverController, ControllerConstants.kFlipButton)
    m_driverController.b()
      .onTrue(Commands.runOnce( () -> m_drive.reverseOrientation() ) );

    // Operator/Tester Controls
    m_operatorController.povUp().whileTrue(new StartEndCommand(() -> m_swing.setSpeed(0.1), () -> m_swing.setSpeed(0), m_swing));
    m_operatorController.povDown().whileTrue(new StartEndCommand(() -> m_swing.setSpeed(-0.1), () -> m_swing.setSpeed(0), m_swing));


    // Default commands
    m_intake.setDefaultCommand(new RunCommand(() -> m_intake.setSpeed(m_operatorController.getLeftY()), m_intake));
    m_launch.setDefaultCommand(new RunCommand(() -> m_launch.setSpeed(m_operatorController.getRightY()), m_launch));
  }

  /*
   private void cameraInit(){
      m_visionThread = new Thread(
        () -> {
  
          // Get the UsbCamera from CameraServer
          camera01 = CameraServer.startAutomaticCapture(0);
          camera02 = CameraServer.startAutomaticCapture(1);
  
          // Set the resolution
          camera01.setResolution(640, 480);
          camera02.setResolution(640, 480);
          camServer = CameraServer.getServer();
  
        });
      m_visionThread.setDaemon(true);
      m_visionThread.start();
    }
   */

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }

}
