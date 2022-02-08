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

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

import frc.robot.Constants;

public class AngleArms extends SubsystemBase
{

  private final DoubleSolenoid bothChassisAngleArmSolenoid = new DoubleSolenoid(
    Constants.robotPneumaticsControlModuleType,
    Constants.bothChassisAngleArmSolenoidForwardChannel,
    Constants.bothChassisAngleArmSolenoidReverseChannel); 
  private final DoubleSolenoid bothJawsAngleArmSolenoid = new DoubleSolenoid(
    Constants.robotPneumaticsControlModuleType,
    Constants.bothJawsAngleArmSolenoidForwardChannel,
    Constants.bothJawsAngleArmSolenoidReverseChannel); 
  
  private DoubleSolenoid.Value chassisEnguaged = kForward;
  private DoubleSolenoid.Value chassisDisenguaged = kReverse;
  private DoubleSolenoid.Value jawsEnguaged = kForward;
  private DoubleSolenoid.Value jawsDisnguaged = kReverse;

  // ctor for angle arms 
  public AngleArms() {}

  public void engageChassis()
  {
    bothChassisAngleArmSolenoid.set(this.chassisEnguaged);
  }

  public void disengageChassis()
  {
    bothChassisAngleArmSolenoid.set(this.chassisDisenguaged);
  }

  public void engageJaws()
  {
    bothJawsAngleArmSolenoid.set(this.jawsEnguaged);
  }

  public void disengageJaws()
  {
    bothJawsAngleArmSolenoid.set(this.jawsDisnguaged);
  }

  @Override
  public void setDefaultCommand(Command myCommand)
  {
      // TODO Auto-generated method stub
      super.setDefaultCommand(myCommand);
  }
}
