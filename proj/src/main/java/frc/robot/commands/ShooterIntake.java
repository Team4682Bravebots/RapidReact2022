// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ShooterIntake.java
// Intent: Forms a command to intake the ball when the Jaws are at the floor/intake position.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.BallStorage;

public class ShooterIntake extends CommandBase {

  private Shooter shooterSubsystem;
  private BallStorage ballStorageSubsystem;

public ShooterIntake(
  Shooter ShooterSubsystem,
  BallStorage BallStorageSubsystem
  ) {
  this.shooterSubsystem = ShooterSubsystem;
  addRequirements(ShooterSubsystem);

  this.ballStorageSubsystem = BallStorageSubsystem;
  addRequirements(BallStorageSubsystem);
  
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    // when the shot method returns true it is up to sufficient speed
    if(shooterSubsystem.intake())
    {
      ballStorageSubsystem.store();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
