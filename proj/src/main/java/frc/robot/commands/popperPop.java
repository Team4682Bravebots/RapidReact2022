// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Level.java
// Intent: Forms a command to drive the arm to a default position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Popper;

public class PopperPop extends CommandBase {
  private Popper popperSubsystem;
  private Timer timer = new Timer();

  private boolean done;

  public PopperPop(Popper popperSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.popperSubsystem = popperSubsystem;
    addRequirements(popperSubsystem);
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
    popperSubsystem.solenoidPopForward();
    if (timer.hasElapsed(Constants.popperTiming)){
      popperSubsystem.solenoidPopBackward();
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
