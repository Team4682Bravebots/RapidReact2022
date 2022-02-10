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

  private DoubleSolenoid.Value currentChassisSetting = kReverse;
  private DoubleSolenoid.Value currentJawsSetting = kReverse;

  // ctor for angle arms 
  public AngleArms() {}

  public void engageChassis()
  {
    if(currentChassisSetting != this.chassisEnguaged)
    {
      bothChassisAngleArmSolenoid.set(this.chassisEnguaged);
    }
  }

  public void disengageChassis()
  {
    if(currentChassisSetting != this.chassisDisenguaged)
    {
      bothChassisAngleArmSolenoid.set(this.chassisDisenguaged);
    }
  }

  public void engageJaws()
  {
    if(this.currentJawsSetting != this.jawsEnguaged)
    {
      bothJawsAngleArmSolenoid.set(this.jawsEnguaged);
    }
  }

  public void disengageJaws()
  {
    if(this.currentJawsSetting != this.jawsDisnguaged)
    {
      bothJawsAngleArmSolenoid.set(this.jawsDisnguaged);
    }
  }

  public void manualChassisConnection(DoubleSolenoid.Value setting)
  {
    if(this.currentChassisSetting != setting)
    {
      bothChassisAngleArmSolenoid.set(setting);
      this.currentChassisSetting = setting;
      System.out.println("Setting bothChassisAngleArmSolenoid to: " + currentJawsSetting.toString());
    }
  }

  public void manualJawsConnection(DoubleSolenoid.Value setting)
  {
    if(this.currentJawsSetting != setting)
    {
      bothJawsAngleArmSolenoid.set(setting);
      this.currentJawsSetting = setting;
      System.out.println("Setting bothJawsAngleArmSolenoid to: " + currentJawsSetting.toString());
    }
  }

  @Override
  public void setDefaultCommand(Command myCommand)
  {
      // TODO Auto-generated method stub
      super.setDefaultCommand(myCommand);
  }
}
