// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: HookRelease.java
// Intent: Command/operation to release the hooks to to the arm.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Hooks;

public class HookRelease extends CommandBase
{
  private Hooks hooksSubsystem;

  // ctor
  public HookRelease(Hooks hooks)
  {
    hooksSubsystem = hooks;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(hooksSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    // TODO
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
