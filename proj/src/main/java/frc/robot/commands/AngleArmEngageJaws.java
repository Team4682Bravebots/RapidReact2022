// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Level.java
// Intent: Forms a command to have the AngleArm attach to the Jaws and disconnect from the chassis.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.AngleArms;
import frc.robot.subsystems.Interfaces;
import frc.robot.subsystems.Jaws;

public class AngleArmEngageJaws extends CommandBase {

  private AngleArms angleArmSubsystem;
  private Interfaces interfacesSubsystem;
  private Jaws jawsSubsystem;
  private Timer timer = new Timer();
  private boolean done;

   // TODO - why is it that we need the jaws and the interface here?
  // cotr
  public AngleArmEngageJaws(
    AngleArms AngleArmSubsystem,
    Interfaces InterfacesSubsystem,
    Jaws JawsSubsystem
  ) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.angleArmSubsystem = AngleArmSubsystem;
    addRequirements(AngleArmSubsystem);

    this.interfacesSubsystem = InterfacesSubsystem;
    addRequirements(InterfacesSubsystem);

    this.jawsSubsystem = JawsSubsystem;
    addRequirements(JawsSubsystem);
  }  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    done = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //angleArmSubsystem.engageJaws();
    if (timer.hasElapsed(Constants.AngleArmTiming)){
      angleArmSubsystem.disengageChassis();
      done = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
