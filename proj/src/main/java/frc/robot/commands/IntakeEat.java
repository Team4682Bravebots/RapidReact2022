// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: IntakeEat.java
// Intent: Forms a command to drive the arm to a default position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pneumatics;

public class IntakeEat extends CommandBase {
  private Pneumatics PneumaticsSubsystem;
  private Intake intakeSubsystem;

public IntakeEat(Intake intakeSubsystem, Pneumatics PneumaticsSubsystem) {
  this.intakeSubsystem = intakeSubsystem;
  addRequirements(intakeSubsystem);

  this.PneumaticsSubsystem = PneumaticsSubsystem;
  addRequirements(PneumaticsSubsystem);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    PneumaticsSubsystem.solenoidIntakeArmForward(); 
    //run intake morons too
    intakeSubsystem.eat(0.1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    PneumaticsSubsystem.solenoidIntakeArmBackward();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
