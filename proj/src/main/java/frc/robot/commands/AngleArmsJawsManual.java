// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: AngleArmsJawsManual.java
// Intent: Forms a manual command to have the AngleArm attach/detatch from the Jaws.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AngleArms;

public class AngleArmsJawsManual extends CommandBase {

  private AngleArms angleArmSubsystem;
  private boolean done = false;

  /**
   * The constructor 
   * @param AngleArmSubsystem - must hand in the enabled angle arms subsystem
   */
  public AngleArmsJawsManual(AngleArms AngleArmSubsystem)
  {
    // Use addRequirements() here to declare subsystem dependencies.
    this.angleArmSubsystem = AngleArmSubsystem;
    addRequirements(AngleArmSubsystem);
  }  

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    angleArmSubsystem.toggleJawsConnection();
    done = true;
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
    return done;
  }
}
