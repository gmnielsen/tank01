// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.Auto;
import frc.robot.Constants.myMath;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.noteHandler;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public final class Autos {

  public static double convert = myMath.clicksPerFoot;

  // no throw of the note, just movement
  public static Command simpleOutAndBack(Drivetrain m_drive) {
    return Commands.sequence(
      // move out of the parked zone by distance
      new FunctionalCommand(
        m_drive::resetEncoders,
        () -> m_drive.arcadeDrive(Auto.kSP_OutAndBack, 0.0),
        interrupt -> m_drive.arcadeDrive(0, 0),
        () -> Math.abs( m_drive.getAverageDistance() ) >= (convert * Auto.kDist_OutAndBack), m_drive)
        .withTimeout(Auto.kTO_OutAndBack),
      // wait a sec
      new WaitCommand(1),
      // move back by the same distance to get out of the way
      new FunctionalCommand(
        m_drive::resetEncoders,
        // opposite speed from the start
        () -> m_drive.arcadeDrive(-Auto.kSP_OutAndBack, 0.0),
        interrupt -> m_drive.arcadeDrive(0, 0),
        () -> Math.abs( m_drive.getAverageDistance() ) >= (convert * Auto.kDist_OutAndBack), m_drive)
        .withTimeout(Auto.kTO_OutAndBack) 
    );
  }

  // just move out, no getting out of the way
  public static Command simpleOut(Drivetrain m_drive) {
    return new FunctionalCommand(
      m_drive::resetEncoders,
      () -> m_drive.arcadeDrive(Auto.kSP_Out, 0.0),
      interrupt -> m_drive.arcadeDrive(0, 0),
      () -> Math.abs( m_drive.getAverageDistance() ) >= (convert * Auto.kDist_Out),
      m_drive)
      .withTimeout(Auto.kTO_Out)
    ;
  }

  public static Command throwFromA (Drivetrain m_drive, noteHandler m_NoteHandler){
    return Commands.sequence (
      m_NoteHandler.sendForThrow(),
      // wait, then turn off note system
      new WaitCommand(0.4),
      m_NoteHandler.throwerOff(),
      m_NoteHandler.intakeOff(),
      // move away
      new FunctionalCommand(
        m_drive::resetEncoders,
        () -> m_drive.arcadeDrive(Auto.kSP_throwA, 0.0),
        interrupt -> m_drive.arcadeDrive(0, 0),
        () -> Math.abs( m_drive.getAverageDistance() ) >= (convert * Auto.kDist_throwAfirst),
        m_drive)
        .withTimeout(Auto.kTO_throwA),
      // turn in place
      m_drive.arcadeMove(0.0, Auto.kTurn_throwA).withTimeout(0.5),
      // move out of the way
      new FunctionalCommand(
        m_drive::resetEncoders,
        () -> m_drive.arcadeDrive(Auto.kSP_throwA, 0.0),
        interrupt -> m_drive.arcadeDrive(0, 0),
        () -> Math.abs( m_drive.getAverageDistance() ) >= (convert * Auto.kDist_throwAsecond),
        m_drive)
        .withTimeout(Auto.kTO_throwA)
    );
  }

  
  public static Command throwFromB (Drivetrain m_drive, noteHandler m_NoteHandler){
    return Commands.sequence (
      m_NoteHandler.sendForThrow(),
      // wait, then turn off note system
      new WaitCommand(0.4),
      m_NoteHandler.throwerOff(),
      m_NoteHandler.intakeOff(),
      // move away
     new FunctionalCommand(
        m_drive::resetEncoders,
        () -> m_drive.arcadeDrive(Auto.kSP_OutAndBack, 0.0),
        interrupt -> m_drive.arcadeDrive(0, 0),
        () -> Math.abs( m_drive.getAverageDistance() ) >= (convert * Auto.kDist_OutAndBack), m_drive)
        .withTimeout(Auto.kTO_OutAndBack),
      // wait a sec
      new WaitCommand(1),
      // move back by the same distance to get out of the way
      new FunctionalCommand(
        m_drive::resetEncoders,
        // opposite speed from the start
        () -> m_drive.arcadeDrive(-Auto.kSP_OutAndBack, 0.0),
        interrupt -> m_drive.arcadeDrive(0, 0),
        () -> Math.abs( m_drive.getAverageDistance() ) >= (convert * Auto.kDist_OutAndBack), m_drive)
        .withTimeout(Auto.kTO_OutAndBack)
    );
  }
  
  public static Command throwFromC (Drivetrain m_drive, noteHandler m_NoteHandler){
    return Commands.sequence (
      m_NoteHandler.sendForThrow(),
      // wait, then turn off note system
      new WaitCommand(0.4),
      m_NoteHandler.throwerOff(),
      m_NoteHandler.intakeOff(),
      // move away
      new FunctionalCommand(
        m_drive::resetEncoders,
        () -> m_drive.arcadeDrive(Auto.kSP_throwC, 0.0),
        interrupt -> m_drive.arcadeDrive(0, 0),
        () -> Math.abs( m_drive.getAverageDistance() ) >= (convert * Auto.kDist_throwCfirst),
        m_drive)
        .withTimeout(Auto.kTO_throwC),
      // turn in place
      m_drive.arcadeMove(0.0, Auto.kTurn_throwC).withTimeout(0.5),
      // move out of the way
      new FunctionalCommand(
        m_drive::resetEncoders,
        () -> m_drive.arcadeDrive(Auto.kSP_throwC, 0.0),
        interrupt -> m_drive.arcadeDrive(0, 0),
        () -> Math.abs( m_drive.getAverageDistance() ) >= (convert * Auto.kDist_throwCsecond),
        m_drive)
        .withTimeout(Auto.kTO_throwC)
    );
  }

  public static Command moveFromA (Drivetrain m_drive, noteHandler m_NoteHandler) {
    return Commands.sequence( 
      m_drive.arcadeMove(-0.6, 0.0).withTimeout(1.5),
      m_drive.arcadeMove(0.0,-0.5).withTimeout(0.5),
      m_drive.arcadeMove(-0.6, 0.0).withTimeout(1.5)
      );
      /* ,
      new WaitCommand(2.0),
      m_drive.arcadeMove(0.0, -0.8).withTimeout(0.35),
      new WaitCommand(1.0),
      m_drive.arcadeMove(0.8,0.0).withTimeout(0.5) */
    }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
