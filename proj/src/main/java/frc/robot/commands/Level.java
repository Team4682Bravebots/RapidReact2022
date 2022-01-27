// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ArmDefault.java
// Intent: Forms a command to drive the arm to a default position.
// ************************************************************

package frc.robot.commands;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class Level extends CommandBase {
  /** Creates a new level. */
  double gyroValue;
  double gyroLevel;
  double armTarget;
  double degreeToEncoderTick;

  boolean done;

  private Arm armSubsystem;

  AnalogGyro gyro = new AnalogGyro(0); // ANA Ch. 0


  public Level(Arm armSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.armSubsystem = armSubsystem;
    addRequirements(armSubsystem);
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

    armTarget = gyroValue/175  * -degreeToEncoderTick; // Change negitive if it makes it swing more

    //divied by 175 make a gyro tick equal to one degree



    armSubsystem.setArmPosition(armTarget);

    //double slow = 0.24;

    System.out.println(Math.round(gyro.getAngle()));

  

   /* 
  if (Math.abs(gyro.getAngle() <= 3)) {
    armSubsystem.setArmPosition(slow - (gyro.getAngle()) / 15);
   } else if (Math.abs(gyro.getAngle()) < 10) {
    if (gyro.getAngle() > 0) {
     armSubsystem.setArmPosition(slow);
    } else if (gyro.getAngle() < 0) {
     armSubsystem.setArmPosition(slow);
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
