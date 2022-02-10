// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Shooter.java
// Intent: Forms a subsystem that controls Shooter operations.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  
  private WPI_TalonFX topMotor = new WPI_TalonFX(Constants.shooterMotorTopCanId);
  private WPI_TalonFX bottomMotor = new WPI_TalonFX(Constants.shooterMotorBottomCanId);
  private double velocitySufficientWarmupThreshold = 0.8;
  private double talonMaximumTicksPerSecond = 6380 * Constants.CtreTalonFx500EncoderTicksPerRevolution / 60;

  @Override
  public void setDefaultCommand(Command myCommand)
  {
      // TODO Auto-generated method stub
      super.setDefaultCommand(myCommand);
  }
  
  /** Creates a new Shooter. */
  public Shooter()
  {
    bottomMotor.setInverted(true);
  }
  
  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
  }

  public boolean intake()
  {
    topMotor.set(Constants.topMotorIntakeSpeed);
    bottomMotor.set(Constants.bottomMotorIntakeSpeed);
    boolean rtnVal = isMotorUpToSpeed(topMotor, Constants.topMotorIntakeSpeed);
    rtnVal &= isMotorUpToSpeed(bottomMotor, Constants.bottomMotorIntakeSpeed);
    return rtnVal;
  }

  public boolean shootLow()
  {
    topMotor.set(Constants.topMotorForwardLowGoalSpeed);
    bottomMotor.set(Constants.bottomMotorForwardLowGoalSpeed);
    boolean rtnVal = isMotorUpToSpeed(topMotor, Constants.topMotorForwardLowGoalSpeed);
    rtnVal &= isMotorUpToSpeed(bottomMotor, Constants.bottomMotorForwardLowGoalSpeed);
    return rtnVal;
  }

  public boolean shootHigh()
  {
    topMotor.set(Constants.topMotorForwardHighGoalSpeed);
    bottomMotor.set(Constants.bottomMotorForwardHighGoalSpeed);
    boolean rtnVal = isMotorUpToSpeed(topMotor, Constants.topMotorForwardHighGoalSpeed);
    rtnVal &= isMotorUpToSpeed(bottomMotor, Constants.bottomMotorForwardHighGoalSpeed);
    return rtnVal;
  }

  public boolean shootHighReverse()
  {
    topMotor.set(Constants.topMotorReverseHighGoalSpeed);
    bottomMotor.set(Constants.bottomMotorReverseHighGoalSpeed);
    boolean rtnVal = isMotorUpToSpeed(topMotor, Constants.topMotorReverseHighGoalSpeed);
    rtnVal &= isMotorUpToSpeed(bottomMotor, Constants.bottomMotorReverseHighGoalSpeed);
    return rtnVal;
  }

  public void shooterManual(double speed)
  {
    topMotor.set(speed);
    bottomMotor.set(speed);
  }
  
  public void stopShooter()
  {
    topMotor.set(0.0);
    bottomMotor.set(0.0);
  }

  private boolean isMotorUpToSpeed(WPI_TalonFX motor, double targetSpeed)
  {
    double approximateMotorVelocityTicksPerSecond = motor.getSelectedSensorVelocity() * 10;
    return (approximateMotorVelocityTicksPerSecond > 
      this.talonMaximumTicksPerSecond * targetSpeed * this.velocitySufficientWarmupThreshold);
  }
}
