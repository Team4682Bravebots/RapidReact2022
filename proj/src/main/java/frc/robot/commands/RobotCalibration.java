// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: RobotCalibration.java
// Intent: Forms a command to calibrate subsystems within the robot
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Jaws;
import frc.robot.subsystems.TelescopingArms;

public class RobotCalibration extends CommandBase {

  private Jaws jaws;
  private TelescopingArms telescopingArms;
  private boolean sensorCalibrationComplete = false;

  // constructor
  public RobotCalibration(Jaws Jaws, TelescopingArms TelescopingArms) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.jaws = Jaws;
    addRequirements(Jaws);

    this.telescopingArms = TelescopingArms;
    addRequirements(TelescopingArms);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    boolean allCalibrationComplete = true;

    // determine if jaws are calibrated cleanly
    jaws.startCalibration();
    allCalibrationComplete &= jaws.isCalibrationComplete();

    // determine if jaws are calibrated cleanly
    telescopingArms.zeroSensors();

    // TODO - add more subsystems to become calibrated here!!!

    // only do the set after all calibration steps have been progressed through
    sensorCalibrationComplete = allCalibrationComplete;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return sensorCalibrationComplete;
  }
}
