// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ShooterForwardHighShot.java
// Intent: Forms a command to shoot the ball at the high goal assuming the forward position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Interfaces;
import frc.robot.subsystems.Pneumatics;

public class ShooterForwardHighShot extends CommandBase {
 
  public Shooter ShooterSubsystem;
  public Pneumatics PneumaticsSubsystem;
  public Interfaces interfacesSubsystem;

  public ShooterForwardHighShot(Shooter ShooterSubystem, Pneumatics PneumaticsSubsystem, Interfaces interfacesSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.ShooterSubsystem = ShooterSubystem;
    addRequirements(ShooterSubystem);

    this.PneumaticsSubsystem = PneumaticsSubsystem;
    addRequirements(PneumaticsSubsystem);

    this.interfacesSubsystem = interfacesSubsystem;
    addRequirements(interfacesSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ShooterSubsystem.barf(interfacesSubsystem.getXboxRawAxis(2));
    System.out.println(interfacesSubsystem.getXboxRawAxis(2));
    PneumaticsSubsystem.solenoidShooterJawsForward();
    PneumaticsSubsystem.solenoidShooterJawsForward();
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
