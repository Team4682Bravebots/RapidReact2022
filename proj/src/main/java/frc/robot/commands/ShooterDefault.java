// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ShooterDefault.java
// Intent: Forms a command to let the shooter wind down to stopped position.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;
import frc.robot.subsystems.Interfaces;
import frc.robot.subsystems.Pneumatics;

public class ShooterDefault extends CommandBase {
  /** Creates a new solenoidOne. 
 * @param m_Shooter*/

    private Pneumatics pneumaticsSubsystem;
    private Shooter shooterSubsystem;
    private Interfaces interfacesSubsystem;
  
  public ShooterDefault(
    Shooter ShooterSubsystem, 
    Pneumatics PneumaticsSubsystem, 
    Interfaces InterfacesSubsystem
    ) {
    this.shooterSubsystem = ShooterSubsystem;
    addRequirements(ShooterSubsystem);

    this.pneumaticsSubsystem = PneumaticsSubsystem;
    addRequirements(PneumaticsSubsystem);

    this.interfacesSubsystem = InterfacesSubsystem;
    addRequirements(InterfacesSubsystem);

  }
    // Use addRequirements() here to declare subsystem dependencies.

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pneumaticsSubsystem.solenoidShooterJawsBackward();
    shooterSubsystem.shooterManual(interfacesSubsystem.getXboxRawAxis(Constants.joystickZ));
    //TODO check this is the right axis 

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
