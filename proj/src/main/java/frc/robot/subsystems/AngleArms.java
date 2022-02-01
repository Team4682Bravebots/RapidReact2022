// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: AngleArm.java
// Intent: Forms a subsystem that controls the AngleArm operations.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

public class AngleArms extends SubsystemBase {

  /** Creates a new AngleArm. */ 
  public AngleArms() {}
  
  private final DoubleSolenoid leftChassisAngleArmSolenoid = new DoubleSolenoid(null, 2,3); 
  private final DoubleSolenoid rightChassisAngleArmSolenoid = new DoubleSolenoid(null, 2,3); 
  private final DoubleSolenoid leftJawsAngleArmSolenoid = new DoubleSolenoid(null, 2,3); 
  private final DoubleSolenoid rightJawsAngleArmSolenoid = new DoubleSolenoid(null, 2,3); 

  public void engageChassis(){
    leftChassisAngleArmSolenoid.set(kForward);
    rightChassisAngleArmSolenoid.set(kForward);
  }

  public void disengageChassis(){
    leftChassisAngleArmSolenoid.set(kReverse);
    rightChassisAngleArmSolenoid.set(kReverse);
  }

  public void enguageJaws(){
    leftJawsAngleArmSolenoid.set(kForward);
    rightJawsAngleArmSolenoid.set(kForward);
  }


  public void disenguageJaws(){
    leftJawsAngleArmSolenoid.set(kReverse);
    rightJawsAngleArmSolenoid.set(kReverse);
  }

  public void setPower(double power){

  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
