// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import java.util.Map;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.Intake;

public class noteHandler extends SubsystemBase {

  // declare motor controllers
  private final CANSparkMax m_intakeWheels = new CANSparkMax(Intake.kIntakeWheelsID, MotorType.kBrushless);
  private final CANSparkMax m_ThrowLeftMotor = new CANSparkMax(Intake.kThrowLeftMotorID, MotorType.kBrushless);
  private final CANSparkMax m_ThrowRighttMotor = new CANSparkMax(Intake.kThrowRightMotorID, MotorType.kBrushless);
    
  private final ShuffleboardTab sbConfig = Shuffleboard.getTab("Config");
  //public final GenericEntry sbPos = sbConfig.add("pos", m_swingMotor.getPosition())
  //  .withPosition(0, 0).getEntry();
  public final GenericEntry sbKp = sbConfig.add("kP Up", Intake.KpUp)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 2.0))
    .withPosition(0, 1).getEntry();
  public final GenericEntry sbKd = sbConfig.add("kD Up", Intake.KdUp)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 2.0))
    .withPosition(2, 1).getEntry();
    public final GenericEntry sbKf = sbConfig.add("kF Up", Intake.kfUp)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 0.5))
    .withPosition(2, 1).getEntry();
  public final GenericEntry sbKpDown = sbConfig.add("kP Down", Intake.KpDown)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 2.0))
    .withPosition(0, 2).getEntry();
  public final GenericEntry sbKdDown = sbConfig.add("kD Down", Intake.KdDown)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 2.0))
    .withPosition(2, 2).getEntry();
  public final GenericEntry sbKfDown = sbConfig.add("kF Down", Intake.KfDown)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 0.5))
    .withPosition(2, 1).getEntry();
  public final GenericEntry sbBDown = sbConfig.add("B Down", Intake.KBDown)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 1.0))
    .withPosition(4, 2).getEntry();


  /** Creates a new noteHandler. */
  public noteHandler() {
    super();
  }

  public void moveOrGrabNote(){
    m_intakeWheels.set(Intake.kIntakeSlowRollers);
  }

  // * * * * * * * * * * THROWER
  public Command throwerOn(){
    return Commands.sequence (
      this.runOnce( () -> m_ThrowLeftMotor.set(-1.0) ),
      this.runOnce( () -> m_ThrowRighttMotor.set(1.0) ) 
    );
  }

  public Command throwerOff() {
    return Commands.sequence (
      this.runOnce( () -> m_ThrowLeftMotor.stopMotor() ),
      this.runOnce( () -> m_ThrowRighttMotor.stopMotor() )
    );
  }
  
  // * * * * * * * * * * THROWER and INTAKE

  public Command sendForThrow(){
    return Commands.sequence(
      this.throwerOn(),
      new WaitCommand(Intake.kThrowLeftMotorRampRate),
      this.intakeFullUp() );
  }
    


  
  // * * * * * * * * * * INTAKE
  public Command intakeOff(){
    return this.runOnce( () -> m_intakeWheels.set(0.0) );
  }
  public Command intakeSlowUp() {
    return this.runOnce(() -> m_intakeWheels.set(-Intake.kIntakeSlowRollers));
  }
  public Command intakeSlowDown(){
    return this.runOnce(() -> m_intakeWheels.set(Intake.kIntakeSlowRollers));  
  }
  public Command intakeFullUp(){
    return this.runOnce(() -> m_intakeWheels.set(-1.0) );
  }
  
  // * * * * * * * * * * PERIODIC
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
