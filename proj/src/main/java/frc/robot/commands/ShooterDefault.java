// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ShooterDefault.java
// Intent: Forms a command to let the shooter wind down to stopped position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Pneumatics;

public class ShooterDefault extends CommandBase {
  /** Creates a new solenoidOne. 
 * @param m_Shooter*/
    private Pneumatics PneumaticsSubsystem;
    private Shooter ShooterSubsystem;
  
  public ShooterDefault(Shooter ShooterSubsystem, Pneumatics PneumaticsSubsystem) {
    this.ShooterSubsystem = ShooterSubsystem;
    addRequirements(ShooterSubsystem);

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
    PneumaticsSubsystem.solenoidShooterJawsBackward();
    //any motors that need to be turned off
    ShooterSubsystem.defualt();
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
