// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: ShooterAutomatic.java
// Intent: Forms a command to perform shooter / intake activities based on orientation of jaws.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;
import frc.robot.subsystems.BallStorage;
import frc.robot.subsystems.Jaws;

public class ShooterAutomatic extends CommandBase
{
  // target the shooter 
  // format: 
  // 0. minimum arm angle
  // 1. maximum arm angle
  // 2. bottom target shooter speed in rpm
  // 3. bottom shooter speed tolerance in rpm
  // 4. top target shooter speed in rpm
  // 5. top shooter speed tolerance in rpm
  private static final double shooterIntakeTargets[][] =
  {
    {-10.0, 10.0, -500.0, 20.0, -500.0, 20.0}, // intake targets
    {30.0, 40.0, 1000.0, 20.0, 1000.0, 20.0}, // low ball shooter targets
    {60.0, 80.0, 1500.0, 20.0, 1500.0, 20.0}, // forward high ball shooter targets
    {110.0, 130.0, 1500.0, 20.0, 1500.0, 20.0}, // reverse high ball shooter targets
  };
  private static final double kP = 1.0;
  private static final double kI = 0.01;
  private static final double kD = 0.1;

  private Shooter shooterSubsystem;
  private BallStorage ballStorageSubsystem;
  private Jaws jawsSubsystem;
  private boolean done = false;
  private PIDController bottomPidController;
  private PIDController topPidController;

  private double bottomShooterTargetVelocityRpm = 1000;
  private double bottomShooterVelocityToleranceRpm = 10;
  private double topShooterTargetVelocityRpm = 1000;
  private double topShooterVelocityToleranceRpm = 10;

  /**
  * The two argument constructor for the shooter forward low shot
  *
  * @param ShooterSubsystem - The shooter subsystem in this robot
  * @param BallStorageSubsystem - The ball storage subsystem in this robot
  * @param JawsSubsystem - The jaws subsystem in this robot
  */
  public ShooterAutomatic(
      Shooter ShooterSubsystem,
      BallStorage BallStorageSubsystem,
      Jaws JawsSubsystem)
  {
    this.shooterSubsystem = ShooterSubsystem;
    addRequirements(ShooterSubsystem);

    this.ballStorageSubsystem = BallStorageSubsystem;
    addRequirements(BallStorageSubsystem); 

    this.jawsSubsystem = JawsSubsystem;
    addRequirements(JawsSubsystem); 

    bottomPidController = new PIDController(kP, kI, kD);
    topPidController = new PIDController(kP, kI, kD);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
      done = false;
      // determine the target 
      double currentJawsAngle = jawsSubsystem.getJawsAngle();
      for(int inx = 0; inx < shooterIntakeTargets.length; ++inx)
      {
          double lowBar = shooterIntakeTargets[inx][0];
          double highBar = shooterIntakeTargets[inx][1];
          if(currentJawsAngle >= lowBar && currentJawsAngle <= highBar)
          {
            bottomShooterTargetVelocityRpm = shooterIntakeTargets[inx][2];
            bottomShooterVelocityToleranceRpm = shooterIntakeTargets[inx][3];
            topShooterTargetVelocityRpm = shooterIntakeTargets[inx][4];
            topShooterVelocityToleranceRpm = shooterIntakeTargets[inx][5];
            break;          
          }
      }

      // determine where the arm is and then map that to the target array values
      bottomPidController.setSetpoint(this.bottomShooterTargetVelocityRpm);
      bottomPidController.setTolerance(this.bottomShooterVelocityToleranceRpm);
      topPidController.setSetpoint(this.topShooterTargetVelocityRpm);
      topPidController.setTolerance(this.topShooterVelocityToleranceRpm);
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
    else
    {
      // set the top and bottom speeds based on the PID
      shooterSubsystem.shooterManualBottom(this.getNextBottomMotorSpeed());
      shooterSubsystem.shooterManualTop(this.getNextTopMotorSpeed());

      // when the PID's say speed is at setpoint / with tolerance then call retrieve
      if(bottomPidController.atSetpoint() && topPidController.atSetpoint())
      {
        // when the ball storage store method returns true a ball has been stored
        if(ballStorageSubsystem.retrieve())
        {
            done = true;
        }
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

  private double getNextBottomMotorSpeed()
  {
    return bottomPidController.calculate(shooterSubsystem.getBottomShooterRevolutionsPerMinute()) / Constants.talonMaximumRevolutionsPerMinute;
  }

  private double getNextTopMotorSpeed()
  {
    return topPidController.calculate(shooterSubsystem.getTopShooterRevolutionsPerMinute()) / Constants.talonMaximumRevolutionsPerMinute;
  }
}
