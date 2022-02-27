// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: AngleArmEngageChassis.java
// Intent: Forms a command to have the AngleArm attach to the chassis and disconnect from the Jaws.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.AngleArms;
import frc.robot.subsystems.Jaws;

// Ideally this command needs to be scheduled as NOT being interruptible (e.g., uninterruptible)
public class AngleArmsEngageChassis extends CommandBase {

  private AngleArms angleArmSubsystem;
  private Jaws jawsSubsystem;
  private Timer timer = new Timer();
  private boolean done;  

  /**
   * The constructor 
   * @param AngleArmSubsystem - must hand in the enabled angle arms subsystem
   * @param JawsSubsystem - must hand in the enabled jaws subsystem
   */
  public AngleArmsEngageChassis(
    AngleArms AngleArmSubsystem,
    Jaws JawsSubsystem)
  {
    // Use addRequirements() here to declare subsystem dependencies.
    this.angleArmSubsystem = AngleArmSubsystem;
    addRequirements(AngleArmSubsystem);

    this.jawsSubsystem = JawsSubsystem;
    addRequirements(JawsSubsystem);
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
    if(jawsSubsystem.setJawsAngle(Constants.jawsAngleArmsEngagePositionAngle, Constants.jawsAngleArmsEngagePositionTolerance))
    {
      angleArmSubsystem.engageChassis();
      if (timer.hasElapsed(Constants.angleArmTimingSeconds)){
        angleArmSubsystem.disengageJaws();
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
      angleArmSubsystem.disengageChassis();
      angleArmSubsystem.engageJaws();
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return done;
  }

}
