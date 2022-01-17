// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Pnuematics.java
// Intent: Forms model for the Pnuematics subsystem.
// ************************************************************

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.*;

public class Pnuematics extends SubsystemBase
{
  private final Compressor compressor = new Compressor(0);
  private final DoubleSolenoid solenoid = new DoubleSolenoid(0,1);

  public void hookEngage()
  {
    solenoid.set(Constants.hookSolenoidEnguage);
  }

  public void hookRelease()
  {
    solenoid.set(Constants.hookSolenoidRelease);
  }

  public void compressorOn()
  {
    compressor.start(); 
  }

  public void compressorOff()
  {
    compressor.stop();
  }
}
