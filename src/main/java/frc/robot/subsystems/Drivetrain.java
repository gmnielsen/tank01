// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.sendable.SendableRegistry;
import frc.robot.Constants.Drive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Drivetrain extends SubsystemBase {
  /** Creates a new DriveTrain. */

  // four motors
  private final CANSparkMax m_leftLeader = new CANSparkMax(Drive.kLeftMotorIDLead,MotorType.kBrushless);
  private final CANSparkMax m_rightLeader = new CANSparkMax(Drive.kRightMotorIDLead,MotorType.kBrushless);
  private final CANSparkMax m_leftFollow = new CANSparkMax(Drive.kLeftMotorIDFollow,MotorType.kBrushless);
  private final CANSparkMax m_rightFollow = new CANSparkMax(Drive.kRightMotorIDFollow,MotorType.kBrushless);

  // drivetrain pairing
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftLeader::set, m_rightLeader::set);



  public Drivetrain() {
    super();

    SendableRegistry.addChild(m_drive,m_leftLeader);
    SendableRegistry.addChild(m_drive, m_rightLeader);

    // invert the polarity of the right side of the robot
    // such the forward is positive motion on the left and negative motion on the right
    m_rightLeader.setInverted(true);
    m_rightFollow.setInverted(true);
    
    // pair the motors
    m_leftLeader.follow(m_leftFollow);
    m_rightLeader.follow(m_rightFollow);

    // ramp rate for the motors, or how fast the motor can reach max speed
    m_leftLeader.setOpenLoopRampRate(Drive.kRampRate);
    m_rightLeader.setOpenLoopRampRate(Drive.kRampRate);
    m_leftFollow.setOpenLoopRampRate(Drive.kRampRate);
    m_rightFollow.setOpenLoopRampRate(Drive.kRampRate);

    // other driving parameters
    m_drive.setDeadband(Drive.kDeadBand);
    m_drive.setMaxOutput(Drive.kMaxSpeed);





  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
