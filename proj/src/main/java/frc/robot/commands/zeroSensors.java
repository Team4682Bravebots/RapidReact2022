// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.arm;
import frc.robot.subsystems.climberS1;
import frc.robot.subsystems.graber;

public class ZeroSensors extends CommandBase {
  /** Creates a new zeroSensors. 
 * @param m_climbers2
 * @param m_climbers1
 * @param m_arm*/

  public arm m_arm;
  public climberS1 m_climbers1;
  public graber m_climbers2;

  public ZeroSensors(arm m_arm, climberS1 m_climbers1, graber m_climbers2) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.m_arm = m_arm;
    addRequirements(m_arm);

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
    m_arm.zeroSensors();
    //m_climbers1.zeroSensors();
    //m_climbers2.zeroSensors();
    //TODO add anyother sensors 

    System.out.println(m_arm.getPosition());
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
