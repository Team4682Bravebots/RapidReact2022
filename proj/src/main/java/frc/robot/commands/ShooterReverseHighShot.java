// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ShooterReverseHighShot.java
// Intent: Forms a command to shoot the ball at the high goal assuming the reverse position.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.BallStorage;
import frc.robot.subsystems.Interfaces;
import frc.robot.subsystems.Pneumatics;

public class ShooterReverseHighShot extends CommandBase {
 
  public Shooter shooterSubsystem;
  public BallStorage ballStorageSubsystem;

  public ShooterReverseHighShot(
    Shooter ShooterSubsystem, 
    BallStorage BallStorageSubsystem)
  {
    // Use addRequirements() here to declare subsystem dependencies.
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
    if(shooterSubsystem.shootHighReverse())
    {
      ballStorageSubsystem.retrieve();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}
