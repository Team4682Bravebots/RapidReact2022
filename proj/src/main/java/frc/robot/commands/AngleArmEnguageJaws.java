// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Level.java
// Intent: Forms a command to have the AngleArm attach to the Jaws and disconnect from the chassis.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.AngleArms;

public class AngleArmEnguageJaws extends CommandBase {
  private AngleArms angleArmSubsystem;
  private Timer timer = new Timer();
  private boolean done;

  public AngleArmEnguageJaws(AngleArms angleArmSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.angleArmSubsystem = angleArmSubsystem;
    addRequirements(angleArmSubsystem);
  }  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    done = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    angleArmSubsystem.enguageJaws();
    if (timer.hasElapsed(Constants.AngleArmTiming)){
      angleArmSubsystem.disenguageChassis();
      done = true;
    } 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
