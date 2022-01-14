// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Interfaces extends SubsystemBase {
  /** Creates a new interfaces. */
  public Interfaces() {
  }

  public Joystick coDriverController;
  public Joystick driverController;

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
      // init hids \\
     driverController = new Joystick(Constants.portDriverController); // sets joystick varibles to joysticks
     coDriverController = new Joystick(Constants.portCoDriverController);



  }

    //gets the joystick axis value where ever you want, 
  //for y use Robot.m_robotContainer.getJoystickRawAxis(Constants.joystickY); 
  //for x use Robot.m_robotContainer.getJoystickRawAxis(Constants.joystickX);
  public double getJoystickRawAxis(int axis){
    return driverController.getRawAxis(axis);
  }
  public double getXboxRawAxis(int axis){
    return coDriverController.getRawAxis(axis);
  }

  public int getXboxPov() {
    int pov = coDriverController.getPOV();
    return pov;
  }


}
