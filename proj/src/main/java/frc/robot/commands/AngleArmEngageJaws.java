// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Level.java
// Intent: Forms a command to have the AngleArm attach to the Jaws and disconnect from the chassis.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.AngleArms;
import frc.robot.subsystems.Interfaces;
import frc.robot.subsystems.Jaws;

public class AngleArmEngageJaws extends CommandBase {
  private AngleArms angleArmSubsystem;
  private Interfaces InterfacesSubsystem;
  private Jaws JawsSubsystem;
  private Timer timer = new Timer();
  private boolean done;

  
  public AngleArmEngageJaws(
    AngleArms angleArmSubsystem,
    Interfaces InterfacesSubsystem,
    Jaws JawsSubsystem
  ) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.angleArmSubsystem = angleArmSubsystem;
    addRequirements(angleArmSubsystem);

    this.InterfacesSubsystem = InterfacesSubsystem;
    addRequirements(InterfacesSubsystem);

    this.JawsSubsystem = JawsSubsystem;
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
    angleArmSubsystem.engageArm();
    angleArmSubsystem.disengageChassis();

    JawsSubsystem.setJawsPosition(InterfacesSubsystem.getXboxRawAxis(Constants.joystickZ));
    //TODO set this to the right joystick


    /*
    if (timer.hasElapsed(Constants.AngleArmTiming)){
      angleArmSubsystem.disenguageChassis();
      done = true;
    } */

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
