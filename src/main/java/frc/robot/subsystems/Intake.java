// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private final CANSparkMax m_intakeWheels = new CANSparkMax(Constants.Intake.kIntakeWheelsID, MotorType.kBrushless);

/** Creates a new Intake. */
  public Intake() {
    
  }
  public void setSpeed(double speed) {
    speed = speed * Constants.Intake.kIntakeOrientation;
    m_intakeWheels.set(speed);
  }

  public void stop(){
    m_intakeWheels.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
