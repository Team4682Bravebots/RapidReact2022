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
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

public class AngleArms extends SubsystemBase {

  /** Creates a new AngleArm. */ 
  public AngleArms() {}

  @Override
  public void setDefaultCommand(Command myCommand) {
      // TODO Auto-generated method stub
      super.setDefaultCommand(myCommand);
  }
  
  //TODO only ports 1-8 are acceptable for solenoids
  
  private final DoubleSolenoid leftChassisAngleArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 4,5); 
  private final DoubleSolenoid rightChassisAngleArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 6,7); 
 // private final DoubleSolenoid leftJawsAngleArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 8,9); 
 // private final DoubleSolenoid rightJawsAngleArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 10,11); 

  public void engageChassis(){
    leftChassisAngleArmSolenoid.set(kForward);
    rightChassisAngleArmSolenoid.set(kForward);
  }

  public void disengageChassis(){
    leftChassisAngleArmSolenoid.set(kReverse);
    rightChassisAngleArmSolenoid.set(kReverse);
  }


  public void setPower(double power){

  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
