// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: TelescopingArmRetract.java
// Intent: Forms a command to drive the telescoping arms to their retracted position.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.TelescopingArms;

public class TelescopingArmRetract extends CommandBase
{
  public TelescopingArms telescopingArmSubsystem;
  boolean done = false;

  public TelescopingArmRetract(TelescopingArms telescopingArmSubsystem)
  {
    // Use addRequirements() here to declare subsystem dependencies.
    this.telescopingArmSubsystem = telescopingArmSubsystem;
    addRequirements(telescopingArmSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if(telescopingArmSubsystem.setTelescopingArmsHeight(Constants.telescopingArmsRetractHeightInches, Constants.telescopingArmsToleranceInches))
    {
      done = true;
    }
    System.out.println("Telescoping arms height: " + telescopingArmSubsystem.getTelescopingArmsHeight());
  }
 
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return done;
  }
}