// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Interfaces.java
// Intent: Forms model for the Interfaces subsystem.
// ************************************************************

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Interfaces extends SubsystemBase
{
  private Joystick coDriverController;
  private Joystick driverController;

  // ctor
  public Interfaces()
  {
  }

  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
    // init hids \\
    driverController = new Joystick(Constants.portDriverController); // sets joystick varibles to joysticks
    coDriverController = new Joystick(Constants.portCoDriverController);
  }

  // gets the joystick axis value where ever you want, 
  // for y use Robot.m_robotContainer.getJoystickRawAxis(Constants.joystickY); 
  // for x use Robot.m_robotContainer.getJoystickRawAxis(Constants.joystickX);
  public double getJoystickRawAxis(int axis)
  {
    return driverController.getRawAxis(axis);
  }

  public double getXboxRawAxis(int axis)
  {
    return coDriverController.getRawAxis(axis);
  }

  public int getXboxPov()
  {
    int pov = coDriverController.getPOV();
    return pov;
  }
}
