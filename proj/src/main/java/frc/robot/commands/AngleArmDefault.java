// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Level.java
// Intent: Forms a command to drive the Jaws to a default position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AngleArms;

public class AngleArmDefault extends CommandBase {
  private AngleArms AngleArmSubsystem;

  public AngleArmDefault(AngleArms AngleArmSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.AngleArmSubsystem = AngleArmSubsystem;
    addRequirements(AngleArmSubsystem);
  }

  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    AngleArmSubsystem.solenoidPopBackward();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
   
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
