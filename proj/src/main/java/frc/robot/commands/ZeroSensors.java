// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Level.java
// Intent: Forms a command to reset all sensors?
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Jaws;
import frc.robot.subsystems.BallStorage;
import frc.robot.subsystems.TelescopingArms;

public class ZeroSensors extends CommandBase {
  /** Creates a new zeroSensors. 
 * @param m_climbers2
 * @param m_climbers1
 * @param m_Jaws*/

  public Jaws m_Jaws;
  public BallStorage m_climbers1;
  public TelescopingArms m_climbers2;

  public ZeroSensors(Jaws m_Jaws, BallStorage m_climbers1, TelescopingArms m_climbers2) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.m_Jaws = m_Jaws;
    addRequirements(m_Jaws);

    this.m_climbers1 = m_climbers1;
    addRequirements(m_climbers1);

    this.m_climbers2 = m_climbers2;
    addRequirements(m_climbers2);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Jaws.zeroSensors();
    //m_climbers1.zeroSensors();
    //m_climbers2.zeroSensors();
    //TODO add anyother sensors 

    System.out.println(m_Jaws.getPosition());
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
