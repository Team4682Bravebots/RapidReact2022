// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: TelescopingArmExtendMiddle.java
// Intent: Forms a command to drive the telescoping arms to their extended position.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.TelescopingArms;


public class TelescopingArmExtendMiddle extends CommandBase
{
  public TelescopingArms telescopingArmSubsystem;
  boolean done = false;

  public TelescopingArmExtendMiddle(TelescopingArms telescopingArmSubsystem)
  {
    // Use addRequirements() here to declare subsystem dependencies.
    this.telescopingArmSubsystem = telescopingArmSubsystem;
    addRequirements(telescopingArmSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if(telescopingArmSubsystem.setTelescopingArmsHeight(Constants.telescopingArmsMediumExtendHeightInches, Constants.telescopingArmsToleranceInches))
    {
      done = true;
    }
    System.out.println("Jaws angle at: " + telescopingArmSubsystem.getTelescopingArmsHeight());
  }
 
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return done;
  }
}