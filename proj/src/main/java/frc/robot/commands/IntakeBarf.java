// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ArmDefault.java
// Intent: Forms a command to drive the arm to a default position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Interfaces;
import frc.robot.subsystems.Pnuematics;

public class IntakeBarf extends CommandBase {
 
  public Intake intakeSubsystem;
  public Pnuematics pnuematicsSubsystem;
  public Interfaces interfacesSubsystem;

  public IntakeBarf(Intake intakeSubystem, Pnuematics pnuematicsSubsystem, Interfaces interfacesSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intakeSubsystem = intakeSubystem;
    addRequirements(intakeSubystem);

    this.pnuematicsSubsystem = pnuematicsSubsystem;
    addRequirements(pnuematicsSubsystem);

    this.interfacesSubsystem = interfacesSubsystem;
    addRequirements(interfacesSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intakeSubsystem.barf(interfacesSubsystem.getXboxRawAxis(2));
    System.out.println(interfacesSubsystem.getXboxRawAxis(2));
    pnuematicsSubsystem.solenoidIntakeArmForward();
    pnuematicsSubsystem.solenoidIntakeArmForward();
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
