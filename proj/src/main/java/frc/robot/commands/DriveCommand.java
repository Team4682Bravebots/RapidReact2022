// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: DriveCommand.java
// Intent: Forms a command to drive the wheels according to input parameters (encoder dead reckoning and accelormeter for rotation).
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveCommand extends CommandBase
{

  private DriveTrain driveTrain;
  private double targetDistanceInInches;
  private double targetRotationInDegrees;
  private double targetTimeInSeconds;
  boolean started = false;
  boolean done = false;
  
  /** 
  * Creates a new driveCommand. 
  * 
  * @param driveTrainSubsystem - the drive train subsystem
  * @param distanceInInches - the distance in inches the centroid of the robot should move (positive is forward, negative is reverse)
  * @param rotationInDegrees - the rotation from -360.0 to +360.0 in degrees
  * @param timeInSeconds - the target time in seconds to perform the operation
  */
  public DriveCommand(
    DriveTrain driveTrainSubsystem,
    double distanceInInches,
    double rotationInDegrees,
    double timeInSeconds)
  {
    // Use addRequirements() here to declare subsystem dependencies.
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
    if(!started)
    {
      driveTrain.performCircleArcDriveInches(targetDistanceInInches, targetRotationInDegrees, targetTimeInSeconds);
    }
    done = driveTrain.isCurrentlyPerformingDriveMovement();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return done;
  }
}
