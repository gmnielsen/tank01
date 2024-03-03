// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import com.revrobotics.CANSparkLowLevel.MotorType;
// import com.playingwithfusion.CANVenom;
// import com.playingwithfusion.CANVenom.ControlMode;
// import com.revrobotics.CANSparkMax;

// import edu.wpi.first.networktables.GenericEntry;
// import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
// import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
// import java.util.Map;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants.Intake;

// public class noteHandler extends SubsystemBase {

//   // declare motor controllers
//   private final CANVenom m_swingMotor = new CANVenom(Intake.kSwingMotorID);
//   //private final CANSparkMax m_swingMotor = new CANSparkMax(Intake.kSwingMotorID, MotorType.kBrushless);
//   private final CANSparkMax m_intakeWheels = new CANSparkMax(Intake.kIntakeWheelsID, MotorType.kBrushless);
//   private final CANSparkMax m_ThrowLeftMotor = new CANSparkMax(Intake.kThrowLeftMotorID, MotorType.kBrushless);
//   private final CANSparkMax m_ThrowRighttMotor = new CANSparkMax(Intake.kThrowRightMotorID, MotorType.kBrushless);
    
//   private final ShuffleboardTab sbConfig = Shuffleboard.getTab("Config");
//   public final GenericEntry sbPos = sbConfig.add("pos", m_swingMotor.getPosition())
//     .withPosition(0, 0).getEntry();
//   public final GenericEntry sbKp = sbConfig.add("kP Up", Intake.KpUp)
//     .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 2.0))
//     .withPosition(0, 1).getEntry();
//   public final GenericEntry sbKd = sbConfig.add("kD Up", Intake.KdUp)
//     .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 2.0))
//     .withPosition(2, 1).getEntry();
//   public final GenericEntry sbKpDown = sbConfig.add("kP Down", Intake.KpDown)
//     .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 2.0))
//     .withPosition(0, 2).getEntry();
//   public final GenericEntry sbKdDown = sbConfig.add("kD Down", Intake.KdDown)
//     .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 2.0))
//     .withPosition(2, 2).getEntry();
//   public final GenericEntry sbBDown = sbConfig.add("B Down", Intake.KBDown)
//     .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 1.0))
//     .withPosition(4, 2).getEntry();


//   /** Creates a new noteHandler. */
//   // public noteHandler() {
//   //   super();
//   //   // setup swing motor
//   //   // coast or brake, set to brake
//   //   m_swingMotor.setBrakeCoastMode(Intake.kSwingIdle);
//   //   // not controlled
//   //   m_swingMotor.setControlMode(ControlMode.Disabled);
//   //   m_swingMotor.setMaxSpeed(Intake.kSwingSpeed);
//   // }

//   // public void swing() {
//   //   m_swingMotor.set(Intake.kSwingSpeed);
//   // }

//   // public void swingOff(){
//   //   m_swingMotor.stopMotor();
//   // }

//   // public void resetSwingPostion(){
//   //   m_swingMotor.setControlMode(ControlMode.Disabled);
//   //   m_swingMotor.resetPosition();
//   //   m_swingMotor.setCommand(ControlMode.Proportional, 0.0);
//   // }

//   // public void intakeUpOn(double speed) {
//   //   speed = speed * Intake.kIntakeOrientation;
//   //   m_intakeWheels.set(speed);
//   // }
//   // public void intakeDownOn(double speed) {
//   //   m_intakeWheels.set(-speed);
//   // }
//   // public void intakeOff(){
//   //   m_intakeWheels.stopMotor();
//   // }

//   // public void throwOutOn(double speed) {
//   //   speed = speed * Intake.kThrowOrientation;
//   //   m_ThrowLeftMotor.set(speed);
//   //   m_ThrowRighttMotor.set(-speed);
//   // }
//   // public void throwInOn(double speed){
//   //   speed = speed * (-1) * Intake.kThrowOrientation;
//   //   m_ThrowLeftMotor.set(speed);
//   //   m_ThrowRighttMotor.set(-speed);
//   // }
//   // public void throwOff(){
//   //   m_ThrowLeftMotor.stopMotor();
//   //   m_ThrowRighttMotor.stopMotor();
//   // }


//   @Override
//   public void periodic() {
//     // This method will be called once per scheduler run
//   }
// }
