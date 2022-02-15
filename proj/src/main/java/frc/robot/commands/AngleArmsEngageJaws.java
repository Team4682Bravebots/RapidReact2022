// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: AngleArmsEngageJaws.java
// Intent: Forms a command to have the AngleArm attach to the Jaws and disconnect from the chassis.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.AngleArms;
import frc.robot.subsystems.Jaws;

public class AngleArmsEngageJaws extends CommandBase {

  private AngleArms angleArmSubsystem;
  private Jaws jawsSubsystem;
  private Timer timer = new Timer();
  private boolean done;

  /**
   * The constructor 
   * @param AngleArmSubsystem - must hand in the enabled angle arms subsystem
   * @param JawsSubsystem - must hand in the enabled jaws subsystem
   */
  public AngleArmsEngageJaws(
    AngleArms AngleArmSubsystem,
    Jaws JawsSubsystem)
  {
    // Use addRequirements() here to declare subsystem dependencies.
    this.angleArmSubsystem = AngleArmSubsystem;
    addRequirements(AngleArmSubsystem);
  }  

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    timer.reset();
    timer.start();
    done = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if(jawsSubsystem.setJawsAngle(Constants.jawsAngleArmsEnguagePositionAngle, Constants.jawsAngleArmsEnguagePositionTolerance))
    {
      angleArmSubsystem.engageJaws();
      if (timer.hasElapsed(Constants.AngleArmTimingSeconds)){
        angleArmSubsystem.disengageChassis();
        done = true;
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    // in the event that we are being interrupted and the operation is not complete, we want to revert it
    if(interrupted == true && done == false)
    {
      // revert the change - seemingly we should order in the same way just reverse the operations
      angleArmSubsystem.disengageJaws();
      angleArmSubsystem.engageChassis();
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return done;
  }
}
