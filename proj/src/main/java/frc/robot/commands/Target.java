 // ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: Target.java
// Intent: Forms a command to target balls.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ManualInputInterfaces;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.DriveTrain;

public class Target extends CommandBase {
  private Camera cameraSubsystem;
  private DriveTrain driveTrainSubsystem;
  private ManualInputInterfaces manualInputInterfaces;

    /**
  * The two argument constructor for the shooter forward low shot
  *
  * @param CameraSubsystem - The camera subsystem in this robot
  * @param DriveTrain - The drive subsystem in this robot
  */


  /** Creates a new Target. */
  public Target(Camera cameraSubsystem, DriveTrain subsystem) {
    this.cameraSubsystem = cameraSubsystem;
    addRequirements(cameraSubsystem);

    this.driveTrainSubsystem = driveTrainSubsystem;
    addRequirements(driveTrainSubsystem); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
 
    //Grabs controller x and y values and sends them to target objects 
    double x = manualInputInterfaces.getInputArcadeDriveX();
    double y = manualInputInterfaces.getInputArcadeDriveY();

    cameraSubsystem.targetObject(x, y);


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
