// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Pneumatics.java
// Intent: Forms a subsystem that controls movements by the Jaws.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

import edu.wpi.first.wpilibj.Compressor;


public class Pneumatics extends SubsystemBase {
  //TODO add the correct pneumatics module 
  private final Compressor compressor = new Compressor(0, null);
  private final DoubleSolenoid Solenoid = new DoubleSolenoid(null, 0,1);
  private final DoubleSolenoid TelescopingArm = new DoubleSolenoid(null, 2,3);

  
  public void solenoidShooterJawsForward() {
    Solenoid.set(kForward);
  }

 
  public void solenoidShooterJawsBackward() {
    Solenoid.set(kReverse);
  }

  public void TelescopingArmLock(){
    TelescopingArm.set(kForward);
  }

  public void TelescopingArmUnlock(){
    TelescopingArm.set(kReverse);
  }

  public void JawsToTelescopingArmLock(){
    TelescopingArm.set(kForward);
  }

  public void JawsToTelescopingArmUnlock(){
    TelescopingArm.set(kReverse);
  }

  public void compressorOn() {
    compressor.enableDigital(); //maybe if no work enable analog?
  }

  public void compressorOff() {
    compressor.disable();
  }
}
