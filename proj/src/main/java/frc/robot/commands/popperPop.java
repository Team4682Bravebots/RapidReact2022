// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.popper;

public class popperPop extends CommandBase {
  private popper popperSubsystem;
  private Timer timer = new Timer();

  private boolean done;

  public popperPop(popper popperSubsystem) {
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
