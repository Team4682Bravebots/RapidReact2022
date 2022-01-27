// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: FrontClimbersDefault.java
// Intent: Return the climbers to their default position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.FrontClimbers;

public class FrontClimbersDefault extends CommandBase
{
 
  private FrontClimbers frontClimbersSubsystem;

  // ctor
  public FrontClimbersDefault(FrontClimbers frontClimbers)
  {
    frontClimbersSubsystem = frontClimbers;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    frontClimbersSubsystem.setClimberPostion(0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}