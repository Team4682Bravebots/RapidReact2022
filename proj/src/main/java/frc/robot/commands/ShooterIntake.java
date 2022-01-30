// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ShooterIntake.java
// Intent: Forms a command to intake the ball when the Jaws are at the floor/intake position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.BallStorage;
import frc.robot.subsystems.Pneumatics;

public class ShooterIntake extends CommandBase {
  private Pneumatics PneumaticsSubsystem;
  private Shooter ShooterSubsystem;
  private BallStorage BallStorageSubsystem;

public ShooterIntake(
  Shooter ShooterSubsystem,
  Pneumatics PneumaticsSubsystem,
  BallStorage BallStorageSubsystem
  ) {
  this.ShooterSubsystem = ShooterSubsystem;
  addRequirements(ShooterSubsystem);

  this.PneumaticsSubsystem = PneumaticsSubsystem;
  addRequirements(PneumaticsSubsystem);

  this.BallStorageSubsystem = BallStorageSubsystem;
  addRequirements(BallStorageSubsystem);
  
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ShooterSubsystem.eat();
    BallStorageSubsystem.store();

    //PneumaticsSubsystem.solenoidShooterJawsForward(); 
    //run Shooter morons too
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    PneumaticsSubsystem.solenoidShooterJawsBackward();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
