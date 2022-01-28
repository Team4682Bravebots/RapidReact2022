// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: JawsDefault.java
// Intent: Forms a command to drive the Jaws to a default position (e.g., stored away from dammage).
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Jaws;

public class JawsDefault extends CommandBase {
  private final Jaws jawsSubsystem;
  boolean done;


  /** Creates a new JawsDefualt. */
  public JawsDefault(Jaws jawsSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.jawsSubsystem = jawsSubsystem;
    addRequirements(jawsSubsystem);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    done = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    jawsSubsystem.setJawsPosition(Constants.JawsDefualt);
    //RobotContainer.m_Jaws.setJawsPosition(0);

  //  jawsSubsystem.isFinished(done, Constants.JawsDefualt);
    System.out.println(jawsSubsystem.getPosition());
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
