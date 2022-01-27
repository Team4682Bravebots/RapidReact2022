// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: IntakeDefault.java
// Intent: Forms a command to drive the arm to a default position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pneumatics;

public class IntakeDefault extends CommandBase {
  /** Creates a new solenoidOne. 
 * @param m_intake*/
    private Pneumatics PneumaticsSubsystem;
    private Intake intakeSubsystem;
  
  public IntakeDefault(Intake intakeSubsystem, Pneumatics PneumaticsSubsystem) {
    this.intakeSubsystem = intakeSubsystem;
    addRequirements(intakeSubsystem);

    this.PneumaticsSubsystem = PneumaticsSubsystem;
    addRequirements(PneumaticsSubsystem);

  }
    // Use addRequirements() here to declare subsystem dependencies.

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    PneumaticsSubsystem.solenoidIntakeArmBackward();
    //any motors that need to be turned off
    intakeSubsystem.defualt();
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
