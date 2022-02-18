// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: AngleArm.java
// Intent: Forms a subsystem that controls the AngleArm operations.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

import frc.robot.Constants;
import frc.robot.InstalledHardware;

public class AngleArms extends SubsystemBase
{
  private DoubleSolenoid bothChassisAngleArmSolenoid = null;
  private DoubleSolenoid bothJawsAngleArmSolenoid = null; 
  
  private DoubleSolenoid.Value chassisEnguaged = kForward;
  private DoubleSolenoid.Value chassisDisenguaged = kReverse;
  private DoubleSolenoid.Value jawsEnguaged = kForward;
  private DoubleSolenoid.Value jawsDisnguaged = kReverse;

  private DoubleSolenoid.Value currentChassisSetting = kReverse;
  private DoubleSolenoid.Value currentJawsSetting = kReverse;

  /**
   * The constructor for AngleArms
   */
  public AngleArms()
  {
    bothChassisAngleArmSolenoid = new DoubleSolenoid(
      Constants.robotPneumaticsControlModuleType,
      Constants.bothChassisAngleArmSolenoidForwardChannel,
      Constants.bothChassisAngleArmSolenoidReverseChannel);
    this.engageChassis();
    bothJawsAngleArmSolenoid = new DoubleSolenoid(
      Constants.robotPneumaticsControlModuleType,
      Constants.bothJawsAngleArmSolenoidForwardChannel,
      Constants.bothJawsAngleArmSolenoidReverseChannel);
    this.disengageJaws();
  CommandScheduler.getInstance().registerSubsystem(this);
  }

  /**
   * A method to tell the the solenoid to engage the chassis
   */
  public void engageChassis()
  {
    if(currentChassisSetting != this.chassisEnguaged)
    {
      this.manualChassisConnection(this.chassisEnguaged);
    }
  }

  /**
   * A method to tell the the solenoid to disengage the chassis
   */
  public void disengageChassis()
  {
    if(currentChassisSetting != this.chassisDisenguaged)
    {
      this.manualChassisConnection(this.chassisDisenguaged);
    }
  }

  /**
   * A method to tell the the solenoid to engage the chassis
   */
  public void engageJaws()
  {
    if(this.currentJawsSetting != this.jawsEnguaged)
    {
      this.manualJawsConnection(this.jawsEnguaged);
    }
  }

  /**
   * A method to tell the the solenoid to disengage the chassis
   */
  public void disengageJaws()
  {
    if(this.currentJawsSetting != this.jawsDisnguaged)
    {
      this.manualJawsConnection(this.jawsDisnguaged);
    }
  }

  /**
   * A method to tell tell the chassis connection to do the opposite of its current setting
   * If currently set to engaged it will disengage
   * If currently set to disengage it will engage
   */
  public void toggleChassisConnection()
  {
    this.manualChassisConnection(
      currentJawsSetting == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward
    );
  }

  /**
   * A method to tell tell the jaws connection to do the opposite of its current setting
   * If currently set to engaged it will disengage
   * If currently set to disengage it will engage
   */
  public void toggleJawsConnection()
  {
    this.manualJawsConnection(
      currentJawsSetting == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward
    );
  }

  @Override
  public void setDefaultCommand(Command myCommand)
  {
      // TODO Auto-generated method stub
      super.setDefaultCommand(myCommand);
  }

  private void manualChassisConnection(DoubleSolenoid.Value setting)
  {
    if(this.currentChassisSetting != setting)
    {
      bothChassisAngleArmSolenoid.set(setting);
      this.currentChassisSetting = setting;
      System.out.println("Setting bothChassisAngleArmSolenoid to: " + currentJawsSetting.toString());
    }
  }

  private void manualJawsConnection(DoubleSolenoid.Value setting)
  {
    if(this.currentJawsSetting != setting)
    {
      bothJawsAngleArmSolenoid.set(setting);
      this.currentJawsSetting = setting;
      System.out.println("Setting bothJawsAngleArmSolenoid to: " + currentJawsSetting.toString());
    }
  }

}
