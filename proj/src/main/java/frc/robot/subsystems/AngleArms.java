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

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.Constants;
import frc.robot.InstalledHardware;

public class AngleArms extends SubsystemBase implements Sendable
{
  private DoubleSolenoid bothChassisAngleArmSolenoid = null;
  private DoubleSolenoid bothJawsAngleArmSolenoid = null; 
  
  private DoubleSolenoid.Value chassisEngaged = kForward;
  private DoubleSolenoid.Value chassisDisengaged = kReverse;
  private DoubleSolenoid.Value jawsEngaged = kForward;
  private DoubleSolenoid.Value jawsDisengaged = kReverse;

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
    if(currentChassisSetting != this.chassisEngaged)
    {
      this.manualChassisConnection(this.chassisEngaged);
    }
  }

  /**
   * A method to tell the the solenoid to disengage the chassis
   */
  public void disengageChassis()
  {
    if(currentChassisSetting != this.chassisDisengaged)
    {
      this.manualChassisConnection(this.chassisDisengaged);
    }
  }

  /**
   * A method to tell the the solenoid to engage the chassis
   */
  public void engageJaws()
  {
    if(this.currentJawsSetting != this.jawsEngaged)
    {
      this.manualJawsConnection(this.jawsEngaged);
    }
  }

  /**
   * A method to tell the the solenoid to disengage the chassis
   */
  public void disengageJaws()
  {
    if(this.currentJawsSetting != this.jawsDisengaged)
    {
      this.manualJawsConnection(this.jawsDisengaged);
    }
  }

  @Override
  public void initSendable(SendableBuilder builder)
  {
    builder.addBooleanProperty("AngleArmsAndChassisEngaged", this::getAngleArmsAndChassisEngaged, null);
    builder.addBooleanProperty("AngleArmsAndJawsEngaged", this::getAngleArmsAndJawsEngaged, null);
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

  /**
   * Obtain if the current subsystem has the angle arms engagued with the chassis
   * @return True if the angle arms are engaged with the chassis, else false (implying disengaged)
   */
  private boolean getAngleArmsAndChassisEngaged()
  {
    return this.currentChassisSetting == this.chassisEngaged;
  }

  /**
   * Obtain if the current subsystem has the angle arms engagued with the jaws
   * @return True if the angle arms are engaged with the jaws, else false (implying disengaged)
   */
  private boolean getAngleArmsAndJawsEngaged()
  {
    return this.currentJawsSetting == this.jawsEngaged;
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
