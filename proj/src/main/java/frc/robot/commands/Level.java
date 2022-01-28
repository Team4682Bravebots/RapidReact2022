// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Level.java
// Intent: ???.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

// TODO - Owen what is this for?  Are we going to self-level the robot?  I dobut it.
// this needs work

package frc.robot.commands;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Jaws;

public class Level extends CommandBase {
  /** Creates a new level. */
  double gyroValue;
  double gyroLevel;
  double JawsTarget;
  double degreeToEncoderTick;

  boolean done;

  private Jaws jawsSubsystem;

  AnalogGyro gyro = new AnalogGyro(0); // ANA Ch. 0


  public Level(Jaws jawsSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.jawsSubsystem = jawsSubsystem;
    addRequirements(jawsSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    done = false;

    gyro.reset();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    gyroValue = gyro.getAngle(); // TODO read gyro value
    gyroLevel = 0; // ?????
    degreeToEncoderTick = 20;

    JawsTarget = gyroValue/175  * -degreeToEncoderTick; // Change negitive if it makes it swing more

    //divied by 175 make a gyro tick equal to one degree



    jawsSubsystem.setJawsPosition(JawsTarget);

    //double slow = 0.24;

    System.out.println(Math.round(gyro.getAngle()));

  

   /* 
  if (Math.abs(gyro.getAngle() <= 3)) {
    jawsSubsystem.setJawsPosition(slow - (gyro.getAngle()) / 15);
   } else if (Math.abs(gyro.getAngle()) < 10) {
    if (gyro.getAngle() > 0) {
     jawsSubsystem.setJawsPosition(slow);
    } else if (gyro.getAngle() < 0) {
     jawsSubsystem.setJawsPosition(slow);
    }
     } */

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
