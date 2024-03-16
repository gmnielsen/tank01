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

  public static class Auto {
    // don't forget direction on speed and rotation !!!
    public static double sign = 1;

    // just move out of the zone
    public static double kTO_Out = 2;             // time out sceonds
    public static double kSP_Out = 0.8*sign;      // percent speed
    public static double kDist_Out = 4.5;         // feet
    // out and back
    public static double kTO_OutAndBack = 4;          // time out seconds
    public static double kSP_OutAndBack = 0.8*sign;   // percent speed
    public static double kDist_OutAndBack = 4.5;      // feet
    // throw from A, out and back
    public static double kTO_throwA = 2;          // time out seconds
    public static double kSP_throwA = 0.8*sign;   // percent speed
    public static double kDist_throwAfirst = 2.0;      // feet
    public static double kDist_throwAsecond = 5.5;
    public static double kTurn_throwA = - 0.6;      // rotation value
  // throw from B, out and back
    public static double kTO_throwB = 6;          // time out seconds
    public static double kSP_throwB = 0.6*sign;   // percent speed
    public static double kDist_throwB = 3.5;      // feet
    public static double kTurn_throwB = 0.2;      // percent rotation
    // throw from C, out and back
    public static double kTO_throwC = 2;          // time out seconds
    public static double kSP_throwC = 0.8*sign;   // percent speed
    public static double kDist_throwCfirst = 2.0;      // feet
    public static double kDist_throwCsecond = 5.5;
    public static double kTurn_throwC = 0.60;      // percent rotation
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
    public static final int kSwingMotorID = 15;       // Neo
    public static final int kIntakeWheelsID = 16;     // Neo
    public static final int kThrowLeftMotorID = 17;   // Neo
    public static final int kThrowRightMotorID = 18;  // Neo

    /*// Venom
    public static final IdleMode kSwingIdle = IdleMode.kBrake;
    public static final double kSwingSpeed = 0.5;
    public static final double kSwingRampRate = 0.3;*/

    // Neo
    public static final double kIntakeWheelsRampRate = 0.5;
    public static final IdleMode kIntakeWheelsIdle = IdleMode.kBrake;
    public static final double kIntakeWheelsMax = 0.8;
    public static final double kIntakeSlowRollers = 0.6;

    public static final double kThrowLeftMotorRampRate = 0.5;
    public static final double kThrowRightMotorRampRate = 0.5;
    public static final IdleMode kThrowLeftMotorIdle = IdleMode.kBrake;
    public static final IdleMode kThrowRightMotorIdle = IdleMode.kBrake;
    public static final double kThrowLeftMotorMax = 1.0;
    public static final double kThrowRightMotorMax = 1.0;

    // PID values
    public static final double KpUp = 2.0;
    public static final double KdUp = 0.0;
    public static final double kfUp = 0.184;
    public static final double KpDown = 0.3;
    public static final double KdDown = 0.0;
    public static final double KfDown = 0.184;
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
  
  public static class myMath {
    public static double pi = 3.1415;
    public static double wheelRadius = 3;
    public static double inches_to_feet = 12;
    public static double wheelCircumference = ( 2 * wheelRadius * pi ) / inches_to_feet;
    public static double gearRatio = 8.46;
    public static double clicksToOneRoatation = 42 / gearRatio;
    public static double clicksPerFoot = clicksToOneRoatation / wheelCircumference;
  }
}
