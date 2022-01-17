// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ArmDefault.java
// Intent: Return arm to its default position.
// ************************************************************

package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class intakeHome extends CommandBase {
  /** Creates a new solenoidOne. */
  public intakeHome() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.m_pnuematics);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.m_pnuematics.solenoidIntakeArmBackward();
    //any motors that need to be turned off
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
