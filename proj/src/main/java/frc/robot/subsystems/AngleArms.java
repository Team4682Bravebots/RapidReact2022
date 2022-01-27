// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: AngleArm.java
// Intent: Forms a subsystem that controls the AngleArm operations.
// ************************************************************

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

public class AngleArms extends SubsystemBase {
  /** Creates a new AngleArm. */
  
  public AngleArms() {}
  
  private final DoubleSolenoid AngleArmSolenoid = new DoubleSolenoid(null, 2,3); 

  public void solenoidPopForward(){
    AngleArmSolenoid.set(kForward);
  }
  public void solenoidPopBackward(){
    AngleArmSolenoid.set(kReverse);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
