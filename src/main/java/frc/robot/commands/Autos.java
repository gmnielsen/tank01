// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static Command simpleAuto(Drivetrain m_drive) {
    return new FunctionalCommand(
      m_drive::resetEncoders,
      () -> m_drive.arcadeDrive(0.0, 0.0),
      interrupt -> m_drive.arcadeDrive(0, 0),
      () -> m_drive.getAverageDistance() >= 20,
      m_drive);
  }

  public static Command autoWall(Drivetrain m_drive) {
    return new FunctionalCommand (
      m_drive::resetEncoders,
      () -> m_drive.arcadeDrive(1.0, 0.0),//Auto.wallSpeed, 0.0),
      interrupt -> m_drive.arcadeDrive(1.0, 0.0),
      () -> m_drive.getAverageDistance() >= 10000, //Auto.distWall,
      m_drive)
      .beforeStarting(Commands.print("autoWall is starting" ) )
      .withTimeout(10); //Auto.kTimeOut);
  }

  public static Command autoWall2(Drivetrain m_drive) {
    return Commands.sequence(
      Commands.print("in auto2"),
      new FunctionalCommand(
        m_drive :: resetEncoders,
        () -> m_drive.arcadeDrive(1.0, 0),
        interrupt -> m_drive.arcadeDrive(.0, 0.0),
        () -> m_drive.getAverageDistance() >= 1000
      ),
      Commands.waitSeconds(2.0),
      Commands.runOnce ( () -> m_drive.arcadeDrive(0, 0) )
    );


  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
