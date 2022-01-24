// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: IntakeDefault.java
// Intent: Return intake motors and belts to a default position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeEat extends CommandBase
{
  private Intake intakeSubsystem;

  /**
   * Command that will move the intake to its default position.
   * @param intake the currently active intake subsystem
   */
  public IntakeEat(Intake intake)
  {
    this.intakeSubsystem = intake;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    // TODO
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    //any motors that need to be turned off
    intakeSubsystem.defualt();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    // TODO
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}
