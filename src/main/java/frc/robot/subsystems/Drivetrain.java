// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.sendable.SendableRegistry;
import frc.robot.Constants.Drive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Drivetrain extends SubsystemBase {
  /** Creates a new DriveTrain. */

  // four motors
  private final PWMSparkMax m_leftLeader = new PWMSparkMax(Drive.kLeftMotorIDLead);
  private final PWMSparkMax m_rightLeader = new PWMSparkMax(Drive.kRightMotorIDLead);
  private final PWMSparkMax m_leftFollow = new PWMSparkMax(Drive.kLeftMotorIDFollow);
  private final PWMSparkMax m_rightFollow = new PWMSparkMax(Drive.kRightMotorIDFollow);
  
  // drivetrain pairing
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftLeader::set, m_rightLeader::set);



  public Drivetrain() {
    super();

    SendableRegistry.addChild(m_drive,m_leftLeader);
    SendableRegistry.addChild(m_drive, m_rightLeader);

    m_leftLeader.addFollower(m_leftFollow);
    m_rightLeader.addFollower(m_rightFollow);

    // invert the polarity of the right side of the robot
    // such the forward is positive motion on the left and negative motion on the right
    m_rightLeader.setInverted(isInverted:true);
    

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
