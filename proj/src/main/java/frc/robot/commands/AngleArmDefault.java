// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: AngleArmDefault.java
// Intent: Forms a command to have the AngleArm attach to the chassis and disconnect from the Jaws.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AngleArms;

public class AngleArmDefault extends CommandBase {
  private AngleArms angleArmSubsystem;

  public AngleArmDefault(AngleArms angleArmSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.angleArmSubsystem = angleArmSubsystem;
    addRequirements(angleArmSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    angleArmSubsystem.solenoidPopBackward();
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
