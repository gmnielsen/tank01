// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.Intake;

public class swingSubsystem extends PIDSubsystem {
  private final CANSparkMax m_swingMotor = new CANSparkMax(Intake.kSwingMotorID, MotorType.kBrushless);
  private final SparkPIDController m_PID;
  private final RelativeEncoder m_encoder;
  
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


  /** Creates a new swingSubsystem. */
  public swingSubsystem() {
    super(
      
        // The PIDController used by the subsystem
        new PIDController(0, 0, 0));

    // rampRate for motors
    //m_swingMotor.setIdleMode(Intake.kSwingIdle);
    m_swingMotor.getEncoder();
    m_swingMotor.getPIDController();
    //m_swingMotor.setOpenLoopRampRate(Intake.kSwingRampRate);

    m_PID = m_swingMotor.getPIDController();
    m_encoder = m_swingMotor.getEncoder();

    m_PID.setP(sbKp.getDouble(1.6));
    m_PID.setI(0.0);
    m_PID.setD(sbKd.getDouble(0.01));


  }

  
  public double swingPosition(){
    return 0.0; //m_swingMotor.getPosition();
  }
  public void swing() {
    m_swingMotor.getEncoder();   //set(Intake.kSwingSpeed);
  }

  public void swingOff(){
    m_swingMotor.stopMotor();
  }



  // COMMANDS
  // these methods return a Command

  
  // * * * * * * * * * * SWING
  public Command swingUp(double pos){
    return Commands.sequence(
      //this.runOnce( () -> m_swingMotor.setPID(sbKp.getDouble(1.6), 0, sbKd.getDouble(0.01), 0.184, 0.0) ),
      //this.runOnce( () -> m_swingMotor.setCommand(ControlMode.PositionControl, pos) ),
      //Commands.print(String.valueOf( m_swingMotor.getPosition() ) ),
      Commands.print("not complete"),
      Commands.print("not complete")
      //this.runOnce( () -> m_swingMotor.setBrakeCoastMode(BrakeCoastMode.Brake) )
      
    );
  }

  public Command swingDown(double pos){
    return Commands.sequence(
      //this.runOnce( () -> m_swingMotor.setPID(sbKpDown.getDouble(0.2), 0, sbKdDown.getDouble(.0), sbKfDown.getDouble(0.184), sbBDown.getDouble(0.088) ) ),
      //this.runOnce( () -> m_swingMotor.setCommand(ControlMode.PositionControl, pos) ),
      //Commands.print(String.valueOf( m_swingMotor.getPosition() ) ),
      Commands.print("not complete"),
      Commands.print("not complete")
      //this.runOnce( () -> m_swingMotor.setBrakeCoastMode(BrakeCoastMode.Brake) )
    );
  }

  public Command swingPos(){
    return  Commands.print("not complete"); //Commands.print(String.valueOf( m_swingMotor.getPosition() ) );
  }


  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return 0;
  }
}
