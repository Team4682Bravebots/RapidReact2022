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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

public class AngleArms extends SubsystemBase {

 
  private final DoubleSolenoid bothChassisAngleArmSolenoid = new DoubleSolenoid(
    Constants.robotPneumaticsControlModuleType,
    Constants.bothChassisAngleArmSolenoidForwardChannel,
    Constants.bothChassisAngleArmSolenoidReverseChannel); 
  private final DoubleSolenoid bothJawsAngleArmSolenoid = new DoubleSolenoid(
    Constants.robotPneumaticsControlModuleType,
    Constants.bothJawsAngleArmSolenoidForwardChannel,
    Constants.bothJawsAngleArmSolenoidReverseChannel); 

 /** Creates a new AngleArm. */ 
  public AngleArms() {}
 
  public void engageChassis()
  {
    bothChassisAngleArmSolenoid.set(kForward);
  }

  public void disengageChassis()
  {
    bothChassisAngleArmSolenoid.set(kReverse);
  }

  public void engageJaws()
  {
    bothJawsAngleArmSolenoid.set(kForward);
  }

  public void disengageJaws()
  {
    bothJawsAngleArmSolenoid.set(kReverse);
  }

  @Override
  public void setDefaultCommand(Command myCommand)
  {
      // TODO Auto-generated method stub
      super.setDefaultCommand(myCommand);
  }
}
