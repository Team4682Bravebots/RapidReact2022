// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: RobotCalibration.java
// Intent: Forms a command to calibrate subsystems within the robot
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Jaws;
import frc.robot.subsystems.TelescopingArms;

/**
* A Command class devoted to getting the robot in a place where all systems are ready for a match.
* Includes work to move prepare subsystems such as moving an arm into a reference position or adjusting
* the ball subsystem such that the ball is ready to shoot.
*/
public class RobotCalibration extends CommandBase {

  private Jaws jaws;
  private TelescopingArms telescopingArms;
  private boolean sensorCalibrationComplete = false;

  /**
  * Constructor for RobotCalibration
  *
  * @param  Jaws - the current Jaws subsystem object
  * @param  TelescopingArms - the current TelescopingArms subsystem object
  */
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
    telescopingArms.startCalibration();
    allCalibrationComplete &= telescopingArms.isCalibrationComplete();

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
