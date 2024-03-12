// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.Drive;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.noteHandler;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
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
  private final noteHandler m_NoteHandler = new noteHandler();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final XboxController m_driverController =
      new XboxController(OperatorConstants.kDriverControllerPort);


  // autonomous commands
  private final Command m_simpleAuto = Autos.simpleAuto(m_drive);
  private final Command m_simpleWall = Autos.autoWall(m_drive);
  private final Command m_OutAndBack = Autos.simpleOutAndBack (m_drive);

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  // Camera
  //Thread m_visionThread;
  //UsbCamera camera01;
  //UsbCamera camera02;
  //VideoSink camServer;


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
            m_driverController.getRightTriggerAxis()-m_driverController.getLeftTriggerAxis(), -m_driverController.getLeftX() ),
            //.getRightX() ),
          m_drive)
    );

    // autonomous chooser()


    // allows us to pick different auto routines from the dashboard
    m_chooser.setDefaultOption("Simple Auto", m_simpleAuto);

    m_chooser.addOption("Simple Auto Wall", m_simpleWall );
    m_chooser.addOption("OutAndBack", m_OutAndBack);

    // Put the autonomous chooser on the dashboard
    Shuffleboard.getTab("Autonomous").add(m_chooser);

    // Put subsystems to dashboard.
    Shuffleboard.getTab("Drivetrain").add(m_drive);

    //
    //cameraInit();

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
    /*new JoystickButton(m_driverController, OperatorConstants.kCameraButton)
      .toggleOnFalse(Commands.run( () -> camServer.setSource(camera01) ) )
      .toggleOnTrue(Commands.run( () -> camServer.setSource(camera02) ) );
    */
    // when triggered, switch to camera01

    /*
     * if (joy1.getTriggerPressed()) {
        System.out.println("Setting camera 2");
        server.setSource(camera2);
    } else if (joy1.getTriggerReleased()) {
        System.out.println("Setting camera 1");
        server.setSource(camera1);
    }
     */


    // While holding right bumper, drive at reduced speed
    new JoystickButton ( m_driverController, OperatorConstants.kSlowDownButton)
      .onTrue(Commands.runOnce( () -> m_drive.setSpeed(Drive.kReducedSpeed) ) )
      .onFalse(Commands.runOnce( () -> m_drive.setSpeed(Drive.kMaxSpeed) ) );

    // swing orientation and Venom reporting
    new JoystickButton ( m_driverController, OperatorConstants.kSwingButton)
      .onTrue(Commands.runOnce( () -> m_NoteHandler.swing() ) )
      .onTrue(Commands.print(String.valueOf( m_NoteHandler.swingPosition() ) ) ) 
      .onFalse(Commands.runOnce( () -> m_NoteHandler.swingOff() ) )
      .onFalse(Commands.print(String.valueOf( m_NoteHandler.swingPosition() ) ) ) ;

    // switch drive orientation
    new JoystickButton ( m_driverController, OperatorConstants.kFlipButton)
      .onTrue(Commands.runOnce( () -> m_drive.reverseOrientation() ) );

    // test button 
    new JoystickButton(m_driverController, OperatorConstants.kTestButton)
      .onTrue(Commands.print("autoWall is starting, average distance at: ") )
      .onFalse(Commands.print( String.valueOf( m_drive.getAverageDistance() ) ) );
  
  //    .onTrue(Commands.runOnce( () -> m_NoteHandler.throwerOn() ) )
  //    .onFalse(Commands.runOnce( () -> m_NoteHandler.throwerOff() ) ) ;

    // D Pad up, intake at set speed going up
    new POVButton(m_driverController, OperatorConstants.kDPadUp)
      .onTrue(Commands.runOnce ( () -> m_NoteHandler.moveOrGrabNote() ) )
      .onFalse(m_NoteHandler.intakeOff() );

    // D Pad left, intake at full speed goint up
    new POVButton(m_driverController, OperatorConstants.kDPadLeft)
      .onTrue(Commands.runOnce ( () -> m_NoteHandler.sendForThrow() ) )
      .onFalse(m_NoteHandler.intakeOff() ) ;

    // D Pad down, intake at set speed going down
    new POVButton(m_driverController, OperatorConstants.kDPadDown)
      .onTrue(Commands.runOnce ( () -> m_NoteHandler.sendForThrow() ) )
      .onFalse(m_NoteHandler.intakeOff() ) ;

    // D Pad right, not in use
    new POVButton(m_driverController, OperatorConstants.kDPadDown)
      .toggleOnTrue ( Commands.print("empty button pressed on") )
      .toggleOnFalse( Commands.print("empty buttoned pressed off")) ;

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
