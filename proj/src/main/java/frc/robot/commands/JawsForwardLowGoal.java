// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: JawsForwardLowGoal.java
// Intent: Forms a command to drive the Jaws to the low goal forward position.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Jaws;

public class JawsForwardLowGoal extends CommandBase
{
  public Jaws jawsSubsystem;
  private boolean done = false;
  
  public JawsForwardLowGoal(Jaws JawsSubsystem)
  {
    // Use addRequirements() here to declare subsystem dependencies.
    this.jawsSubsystem = JawsSubsystem;
    addRequirements(JawsSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if(jawsSubsystem.setJawsAngle(Constants.jawsLowGoalPositionAngle, Constants.jawsPositionAngleTolerance))
    {
      jawsSubsystem.suspendJawMovement();
      done = true;
    }
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
