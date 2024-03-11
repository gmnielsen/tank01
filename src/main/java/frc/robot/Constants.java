// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.playingwithfusion.CANVenom.BrakeCoastMode;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class Auto {
    public static double perpendicularSubWoofer = 8.0;
    public static double perpendicularWall = 10;
    public static double stepsToClicks = 1000;
    public static double wallSpeed = 0.6;
    public static double distWall = perpendicularWall * stepsToClicks;
    public static double kTimeOut = 1.5; // seconds
    }

  public static class Drive {
    // identify our motors
    public static final int kLeftMotorIDLead = 11;
    public static final int kLeftMotorIDFollow = 12;
    public static final int kRightMotorIDLead = 13;
    public static final int kRightMotorIDFollow = 14;

    // tweak our acceleration, minumum motion, maximum motion
    public static final double kRampRate = 0.65;              // acceleration
    public static final double kDeadBand = 0.15;              // minimum speed
    public static final double kMaxSpeed = 0.8;               // maximum speed
    public static final double kReducedSpeed = kMaxSpeed / 2;
    public static final IdleMode kIdleMode = IdleMode.kBrake; //kBrake or kCoast
    public static final double kTurnReduction = 0.70;

  }

  public static class Intake {
    // identify our motors
    public static final int kSwingMotorID = 15;       // Venom
    public static final int kIntakeWheelsID = 16;     // Neo
    public static final int kThrowLeftMotorID = 17;   // Neo
    public static final int kThrowRightMotorID = 18;  // Neo

    // Venom
    public static final BrakeCoastMode kSwingIdle = BrakeCoastMode.Brake;
    public static final double kSwingSpeed = 0.5;
    public static final double kSwingRampRate = 0.3;

    // Neo
    public static final double kIntakeWheelsRampRate = 0.9;
    public static final IdleMode kIntakeWheelsIdle = IdleMode.kBrake;
    public static final double kIntakeWheelsMax = 0.8;
    public static final double kIntakeSlowRollers = 0.4;

    public static final double kThrowLeftMotorRampRate = 0.9;
    public static final double kThrowRightMotorRampRate = 0.9;
    public static final IdleMode kThrowLeftMotorIdle = IdleMode.kBrake;
    public static final IdleMode kThrowRightMotorIdle = IdleMode.kBrake;
    public static final double kThrowLeftMotorMax = 1.0;
    public static final double kThrowRightMotorMax = 1.0;

    // PID values
    public static final double KpUp = 2.0;
    public static final double KdUp = 0.0;
    public static final double KpDown = 0.3;
    public static final double KdDown = 0.0;
    public static final double KBDown = 0.09;           
    
  }
  public static class OperatorConstants {
    // identify our hand controllers
    public static final int kDriverControllerPort = 0;

    /* Xbox buttons
     * kA           IN USE swing intake
     * kB           IN USE flip
     * kBack
     * kLeftBumper
     * kLeftStick
     * kRightBumper IN USE slow down
     * kRightStick
     * kStart
     * kX           test
     * kY           camera
     * 
     */

    // flip button switches robot orientation with each press
    public static int kFlipButton = XboxController.Button.kB.value;
    // camera button changes between cameras
    public static int kCameraButton = XboxController.Button.kY.value;
    // slow down button needs to be held to slow down the robot
    public static int kSlowDownButton = XboxController.Button.kRightBumper.value;
    // swing the intake
    public static int kSwingButton = XboxController.Button.kA.value;
    // test button
    public static int kTestButton = XboxController.Button.kX.value;
    // intake values
    public static int kDPadUp = 0;      // slow up
    public static int kDPadLeft = 270;  // fast up
    public static int kDPadDown = 180;  // slow down
    public static int kDPadRight = 90;  // not in use

  

  }
}
