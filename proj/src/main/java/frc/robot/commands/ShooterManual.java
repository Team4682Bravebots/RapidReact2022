// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ShooterManual.java
// Intent: Forms a command to let the shooter wind down to stopped position.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;
import frc.robot.subsystems.Interfaces;

public class ShooterManual extends CommandBase {

  private Shooter shooterSubsystem;
  private Interfaces interfacesSubsystem;
  
  public ShooterManual(
    Shooter ShooterSubsystem, 
    Interfaces InterfacesSubsystem)
  {
    this.shooterSubsystem = ShooterSubsystem;
    addRequirements(ShooterSubsystem);

    this.interfacesSubsystem = InterfacesSubsystem;
    addRequirements(InterfacesSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    shooterSubsystem.shooterManual(interfacesSubsystem.getXboxRawAxis(Constants.joystickZ));
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
