// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Launcher extends SubsystemBase {
  private final CANSparkMax m_ThrowLeftMotor = new CANSparkMax(Constants.Intake.kThrowLeftMotorID, MotorType.kBrushless);
  private final CANSparkMax m_ThrowRighttMotor = new CANSparkMax(Constants.Intake.kThrowRightMotorID, MotorType.kBrushless);
  /** Creates a new Launcher. */
  public Launcher() {

  }
  
  public void setSpeed(double speed) {
    speed = speed * Constants.Intake.kThrowOrientation;
    m_ThrowLeftMotor.set(speed);
    m_ThrowRighttMotor.set(-speed);
  }
  
  public void stop(){
    m_ThrowLeftMotor.stopMotor();
    m_ThrowRighttMotor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
