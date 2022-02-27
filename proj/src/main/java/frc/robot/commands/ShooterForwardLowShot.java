// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: ShooterForwardLowShot.java
// Intent: Forms a command to shoot the ball at the low goal assuming the forward position.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.BallStorage;

public class ShooterForwardLowShot extends CommandBase {
 
  private Shooter shooterSubsystem;
  private BallStorage ballStorageSubsystem;
  private boolean done = false;

  /**
  * The two argument constructor for the shooter forward low shot
  *
  * @param ShooterSubsystem - The shooter subsystem in this robot
  * @param BallStorageSubsystem - The ball storage subsystem in this robot
  */
  public ShooterForwardLowShot(Shooter ShooterSubsystem, BallStorage BallStorageSubsystem)
  {
    this.shooterSubsystem = ShooterSubsystem;
    addRequirements(ShooterSubsystem);

    this.ballStorageSubsystem = BallStorageSubsystem;
    addRequirements(BallStorageSubsystem); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
   done = false; 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    // when no balls are present ... just mark this as done
    if(ballStorageSubsystem.getOnboardBallCount() <= 0)
    {
      done = true;
    }
    // when the shot method returns true it is up to sufficient speed
    else if(shooterSubsystem.shootLow())
    {
      // when the ball storage store method returns true a ball has been stored
      if(ballStorageSubsystem.retrieve())
      {
        done = true;
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return done;
  }
}