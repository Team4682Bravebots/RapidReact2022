// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.pnuematics;

public class intakeEat extends CommandBase {
  private pnuematics pnuematicsSubsystem;
  private intake intakeSubsystem;

public intakeEat(intake intakeSubsystem, pnuematics pnuematicsSubsystem) {
  this.intakeSubsystem = intakeSubsystem;
  addRequirements(intakeSubsystem);

  this.pnuematicsSubsystem = pnuematicsSubsystem;
  addRequirements(pnuematicsSubsystem);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pnuematicsSubsystem.solenoidIntakeArmForward(); 
    //run intake morons too
    intakeSubsystem.eat(0.1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    pnuematicsSubsystem.solenoidIntakeArmBackward();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
