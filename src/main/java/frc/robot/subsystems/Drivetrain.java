// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.Drive;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  /** Creates a new DriveTrain. */

  // declare the four motors
  private final CANSparkMax m_leftLeader = new CANSparkMax(Drive.kLeftMotorIDLead,MotorType.kBrushless);
  private final CANSparkMax m_rightLeader = new CANSparkMax(Drive.kRightMotorIDFollow,MotorType.kBrushless);
  private final CANSparkMax m_leftFollow = new CANSparkMax(Drive.kLeftMotorIDFollow,MotorType.kBrushless);
  private final CANSparkMax m_rightFollow = new CANSparkMax(Drive.kRightMotorIDLead,MotorType.kBrushless);

  // declare the drivetrain
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftLeader::set, m_rightLeader::set);

  // declare the encoders for the left and right leader motors
  private final RelativeEncoder m_leftEncoder;
  private final RelativeEncoder m_rightEncoder;

  public Drivetrain() {
    super();

    SendableRegistry.addChild(m_drive,m_leftLeader);
    SendableRegistry.addChild(m_drive, m_rightLeader);

    // reset all motors to default values
    // REMOVED: motors were still resetting while new settings applied
    //m_leftLeader.restoreFactoryDefaults();
    //m_rightLeader.restoreFactoryDefaults();
    //m_leftFollow.restoreFactoryDefaults();
    //m_rightFollow.restoreFactoryDefaults();

    // invert the polarity of the right side of the robot
    // such that forward is positive motion on the left and negative motion on the right
    m_rightLeader.setInverted(true);
    //m_rightFollow.setInverted(true);
    
    // pair the motors
    // one motor - the leader - controls the other - the follow
    m_leftFollow.follow(m_leftLeader);
    m_rightFollow.follow(m_rightLeader);

    // ramp rate for the motors, or how fast the motor can reach max speed
    m_leftLeader.setOpenLoopRampRate(Drive.kRampRate);
    m_rightLeader.setOpenLoopRampRate(Drive.kRampRate);
    m_leftFollow.setOpenLoopRampRate(Drive.kRampRate);
    m_rightFollow.setOpenLoopRampRate(Drive.kRampRate);

    // make the motors brake and not coast
    m_leftLeader.setIdleMode(Drive.kIdleMode);
    m_leftFollow.setIdleMode(Drive.kIdleMode);
    m_rightLeader.setIdleMode(Drive.kIdleMode);
    m_rightFollow.setIdleMode(Drive.kIdleMode);

    // define the encoders
    // these will be the encoders inside the NEO motors,
    // NOT the encoders on the drive axle
    m_leftEncoder = m_leftLeader.getEncoder();
    m_rightEncoder = m_rightLeader.getEncoder();

    // reset each encoder to 0
    m_leftEncoder.setPosition(0.0);
    m_rightEncoder.setPosition(0.0);
    // invert the values for the right encoder
    // m_rightEncoder.setInverted(true);

    // other driving parameters
    m_drive.setDeadband(Drive.kDeadBand);
    m_drive.setMaxOutput(Drive.kMaxSpeed);
  } // end Drivetrain

  // create driving method - arcade controls
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(-fwd*Math.abs(fwd), rot);
  }

  // set speed, usually reduced and full speeds
  public void setSpeed(double speed){
    m_drive.setMaxOutput(speed);
  }

  // reverse the robot, front is back and back is front
  public void reverseOrientation(){
    // stop moving
    arcadeDrive(0.0, 0.0);
    // get the opposite of the current state
    boolean flipLeft = ! m_leftLeader.getInverted();
    boolean flipRight = ! m_rightLeader.getInverted();
    // flip each motor
    m_leftLeader.setInverted(flipLeft);
    m_rightLeader.setInverted(flipRight);
  }

  // reset the encoders
  public void resetEncoders(){
    m_leftEncoder.setPosition(0.0);
    m_rightEncoder.setPosition(0.0);
  }
  
  // get average distance travelled
  public double getAverageDistance(){
    return ( (m_leftEncoder.getPosition() + m_rightEncoder.getPosition())/2 );
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void initSendable(SendableBuilder builder) {
    super.initSendable(builder);
    // Publish encoder distances to telemetry.
    builder.addDoubleProperty("leftDistance",m_leftEncoder::getPosition,null);
    builder.addDoubleProperty("rightDistance",m_rightEncoder::getPosition, null);
  }
}
