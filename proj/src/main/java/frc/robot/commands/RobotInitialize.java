// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: RobotInitalize.java
// Intent: Resets the robot to reference settings.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.*;

public class RobotInitialize  extends CommandBase {
  /** Creates a new zeroSensors. 
 * @param frontClimbers
 * @param hooks
 * @param arm*/

  private Arm armSubsystem;
  private FrontClimbers frontClimbersSubsystem;
  private Hooks hooksSubsystem;

  public RobotInitialize(Arm arm, FrontClimbers frontClimbers, Hooks hooks) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.armSubsystem = arm;
    addRequirements(this.armSubsystem);

    this.frontClimbersSubsystem = frontClimbers;
    addRequirements(this.frontClimbersSubsystem);

    this.hooksSubsystem = hooks;
    addRequirements(this.hooksSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    armSubsystem.zeroSensors();
    //m_climbers1.zeroSensors();
    //m_climbers2.zeroSensors();
    //TODO add anyother sensors 
    System.out.println(armSubsystem.getPosition());
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
