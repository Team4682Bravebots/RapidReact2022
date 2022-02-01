// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: JawsDefault.java
// Intent: Forms a command to drive the Jaws to a default position (e.g., stored away from dammage).
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Interfaces;
import frc.robot.subsystems.Jaws;

public class JawsDefault extends CommandBase {

  private final Jaws jawsSubsystem;
  private final Interfaces interfacesSubsystem;
  boolean done;

  /** Creates a new JawsDefault. */
  public JawsDefault(
    Jaws JawsSubsystem,
    Interfaces InterfacesSubsystem
    ) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.jawsSubsystem = JawsSubsystem;
    addRequirements(JawsSubsystem);

    this.interfacesSubsystem = InterfacesSubsystem;
    addRequirements(InterfacesSubsystem);

    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    done = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // TODO - Owen, seems like JawsDefault should be a default position of the jaws
    // not manual use of the jaws.  Does the 'default' name make you think of manual movement?
    jawsSubsystem.jawsManual(interfacesSubsystem.getXboxRawAxis(Constants.joystickY));
    //TODO set this to the right axis 
    //RobotContainer.m_Jaws.setJawsPosition(0); 

  //  jawsSubsystem.isFinished(done, Constants.JawsDefualt);
    System.out.println(jawsSubsystem.getPosition());
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
