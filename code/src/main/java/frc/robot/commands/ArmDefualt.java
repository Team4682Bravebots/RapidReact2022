// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ArmDefault.java
// Intent: Return arm to its default position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class ArmDefualt extends CommandBase
{

  private Arm armSubsystem;
  boolean done;

  // ctor
  public ArmDefualt(Arm arm)
  {
    // Use addRequirements() here to declare subsystem dependencies.
    this.armSubsystem = arm;
    addRequirements(armSubsystem);
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
    armSubsystem.setArmPosition(Constants.armDefualt);
    //RobotContainer.m_arm.setArmPosition(0);

    //  armSubsystem.isFinished(done, Constants.armDefualt);
    System.out.println(armSubsystem.getPosition());
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
    return false;
  }
}
