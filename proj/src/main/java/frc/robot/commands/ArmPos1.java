// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ArmDefault.java
// Intent: Forms a command to drive the arm to a default position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;

public class ArmPos1 extends CommandBase {
  public Arm armSubsystem;
  boolean done;

  public ArmPos1(Arm armSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.armSubsystem = armSubsystem;
    addRequirements(armSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    armSubsystem.setArmPosition(Constants.armPos1);

   // armSubsystem.isFinished(done, Constants.armDefualt);
   System.out.println(armSubsystem.getPosition());


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