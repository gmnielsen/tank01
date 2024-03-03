// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

  Thread m_visionThread;
  private UsbCamera camera01;
  private UsbCamera camera02;
  private VideoSink camServer;

  /** Creates a new Vision. */
  public Vision() {
    super();

    m_visionThread = new Thread(
      () -> {

        // Get the UsbCamera from CameraServer
        camera01 = CameraServer.startAutomaticCapture(0);
        camera02 = CameraServer.startAutomaticCapture(1);

        // Set the resolution
        camera01.setResolution(640, 480);
        camera02.setResolution(640, 480);
        camServer = CameraServer.getServer();

      });
    m_visionThread.setDaemon(true);
    m_visionThread.start();
  }

  public void source01() {
    camServer.setSource(camera01);
  }

  public void source02() {
    camServer.setSource(camera02);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
