// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.XboxController;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class Drive {
    public static final int kLeftMotorIDLead = 11;
    public static final int kLeftMotorIDFollow = 12;
    public static final int kRightMotorIDLead = 13;
    public static final int kRightMotorIDFollow = 14;

    public static final double kRampRate = 0.65;
    public static final double kDeadBand = 0.15;
    public static final double kMaxSpeed = 0.8;
    public static final double kReducedSpeed = kMaxSpeed / 2;
    public static final IdleMode kIdleMode = IdleMode.kBrake; //kBrake or kCoast

  }
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;

    /* Xbox buttons
     * kA
     * kB           IN USE flip
     * kBack
     * kLeftBumper
     * kLeftStick
     * kRightBumper IN USE slow down
     * kRightStick
     * kStart
     * kX
     * kY
     * 
     */

    // flip button switches with each press
    public static int kFlipButton = XboxController.Button.kB.value;
    // slow down button needs to be held to slow down the robot
    public static int kSlowDownButton = XboxController.Button.kRightBumper.value;

  }
}
