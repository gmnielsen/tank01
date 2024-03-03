// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.playingwithfusion.CANVenom;
import com.playingwithfusion.CANVenom.ControlMode;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Swing extends SubsystemBase {
  private final CANVenom m_swingMotor = new CANVenom(Constants.Intake.kSwingMotorID);
  /** Creates a new Swing. */
  public Swing() {
     m_swingMotor.setBrakeCoastMode(Constants.Intake.kSwingIdle);
    // not controlled
    m_swingMotor.setControlMode(ControlMode.Disabled);
    m_swingMotor.setMaxSpeed(Constants.Intake.kSwingSpeed);
    resetSwingPostion();
  }

  public void swing() {
    m_swingMotor.set(Constants.Intake.kSwingSpeed);
  }

  public void swingOff(){
    m_swingMotor.stopMotor();
  }

  public void resetSwingPostion(){
    m_swingMotor.setControlMode(ControlMode.Disabled);
    m_swingMotor.resetPosition();
    m_swingMotor.setCommand(ControlMode.Proportional, 0.0);
  }

  public void setSpeed(double speed) {
    m_swingMotor.set(speed);
  }

  private final ShuffleboardTab sbConfig = Shuffleboard.getTab("Config");
  public final GenericEntry sbPos = sbConfig.add("pos", m_swingMotor.getPosition())
    .withPosition(0, 0).getEntry();
  public final GenericEntry sbKp = sbConfig.add("kP Up", Constants.Intake.KpUp)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 2.0))
    .withPosition(0, 1).getEntry();
  public final GenericEntry sbKd = sbConfig.add("kD Up", Constants.Intake.KdUp)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 2.0))
    .withPosition(2, 1).getEntry();
  public final GenericEntry sbKpDown = sbConfig.add("kP Down", Constants.Intake.KpDown)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 2.0))
    .withPosition(0, 2).getEntry();
  public final GenericEntry sbKdDown = sbConfig.add("kD Down", Constants.Intake.KdDown)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 2.0))
    .withPosition(2, 2).getEntry();
  public final GenericEntry sbBDown = sbConfig.add("B Down", Constants.Intake.KBDown)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.0, "max", 1.0))
    .withPosition(4, 2).getEntry();

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
