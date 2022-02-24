// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: Shooter.java
// Intent: Forms a subsystem that controls Shooter operations.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.common.MotorUtils;

public class Shooter extends SubsystemBase implements Sendable
{
  // Talon info
  private static final double talonMaximumTicksPerSecond = Constants.talonMaximumRevolutionsPerMinute * Constants.CtreTalonFx500EncoderTicksPerRevolution / 60;
  private static final double velocitySufficientWarmupThreshold = 0.8;

  // Shooter gearing - currently 1:1
  private static final double topShooterGearRatio = 1.0;
  private static final double bottomShooterGearRatio = 1.0;
  
  private WPI_TalonFX topMotor = new WPI_TalonFX(Constants.shooterMotorTopCanId);
  private WPI_TalonFX bottomMotor = new WPI_TalonFX(Constants.shooterMotorBottomCanId);

  /**
   * Constructor
   */
  public Shooter()
  {
    bottomMotor.configFactoryDefault();
    topMotor.configFactoryDefault();
    bottomMotor.setInverted(Constants.shooterBottomMotorDefaultDirection);
    topMotor.setInverted(Constants.shooterTopMotorDefaultDirection);
    CommandScheduler.getInstance().registerSubsystem(this);
  }
  
  /**
   * Gets the most recent bottom shooter RPM
   * @return the bottom shooter RPM based on the past 100 ms
   */
  public double getBottomShooterRevolutionsPerMinute()
  {
    return (bottomMotor.getSelectedSensorVelocity() / Constants.CtreTalonFx500EncoderTicksPerRevolution) * 600.0 * Shooter.bottomShooterGearRatio;
  }

  /**
   * Gets the most recent top shooter RPM
   * @return the top shooter RPM based on the past 100 ms
   */
  public double getTopShooterRevolutionsPerMinute()
  {
    return (topMotor.getSelectedSensorVelocity() / Constants.CtreTalonFx500EncoderTicksPerRevolution) * 600.0 * Shooter.topShooterGearRatio;
  }

  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
  }

  @Override
  public void initSendable(SendableBuilder builder)
  {
    builder.addDoubleProperty("ShooterBottomMotorSpeed", this::getBottomMotorSpeed, null);
    builder.addDoubleProperty("ShooterBottomRevolutionsPerMinute", this::getBottomShooterRevolutionsPerMinute, null);
    builder.addDoubleProperty("ShooterTopMotorSpeed", this::getTopMotorSpeed, null);
    builder.addDoubleProperty("ShooterTopRevolutionsPerMinute", this::getTopShooterRevolutionsPerMinute, null);
    builder.addStringProperty("ShooterIntakeDescription", this::getShooterIntakeDescription, null);
  }

  /**
   * A non-blocking call to spin up the shooter motors to a preset value for the intake
   * @return True when the motor is sufficiently up to speed, else false
   */
  public boolean intake()
  {
    topMotor.set(Constants.topMotorIntakeSpeed);
    bottomMotor.set(Constants.bottomMotorIntakeSpeed);
    boolean rtnVal = isMotorUpToSpeed(topMotor, Constants.topMotorIntakeSpeed);
    rtnVal &= isMotorUpToSpeed(bottomMotor, Constants.bottomMotorIntakeSpeed);
    return rtnVal;
  }

  @Override
  public void setDefaultCommand(Command myCommand)
  {
      // TODO Auto-generated method stub
      super.setDefaultCommand(myCommand);
  }
  
  /**
   * A non-blocking call to spin up the shooter motors to a preset value for the shoot low
   * @return True when the motor is sufficiently up to speed, else false
   */
  public boolean shootLow()
  {
    topMotor.set(Constants.topMotorForwardLowGoalSpeed);
    bottomMotor.set(Constants.bottomMotorForwardLowGoalSpeed);
    boolean rtnVal = isMotorUpToSpeed(topMotor, Constants.topMotorForwardLowGoalSpeed);
    rtnVal &= isMotorUpToSpeed(bottomMotor, Constants.bottomMotorForwardLowGoalSpeed);
    return rtnVal;
  }

  /**
   * A non-blocking call to spin up the shooter motors to a preset value for the shoot high
   * @return True when the motor is sufficiently up to speed, else false
   */
  public boolean shootHigh()
  {
    topMotor.set(Constants.topMotorForwardHighGoalSpeed);
    bottomMotor.set(Constants.bottomMotorForwardHighGoalSpeed);
    boolean rtnVal = isMotorUpToSpeed(topMotor, Constants.topMotorForwardHighGoalSpeed);
    rtnVal &= isMotorUpToSpeed(bottomMotor, Constants.bottomMotorForwardHighGoalSpeed);
    return rtnVal;
  }

  /**
   * A non-blocking call to spin up the shooter motors to a preset value for the shoot high reverse
   * @return True when the motor is sufficiently up to speed, else false
   */
  public boolean shootHighReverse()
  {
    topMotor.set(Constants.topMotorReverseHighGoalSpeed);
    bottomMotor.set(Constants.bottomMotorReverseHighGoalSpeed);
    boolean rtnVal = isMotorUpToSpeed(topMotor, Constants.topMotorReverseHighGoalSpeed);
    rtnVal &= isMotorUpToSpeed(bottomMotor, Constants.bottomMotorReverseHighGoalSpeed);
    return rtnVal;
  }

  /**
   * Set both motor speeds to to the same value 
   * @param speed - Range -1.0 to 1.0 where negative values imply intake and positive imply shooting
   */
  public void shooterManual(double speed)
  {
    double cleanSpeed = MotorUtils.truncateValue(speed, -1.0, 1.0);
    this.shooterManualBottom(cleanSpeed);
    this.shooterManualTop(cleanSpeed);
  }

  /**
   * Set the bottom shooter motor to a specific speed 
   * @param speed - Set the bottom motor speed, -1.0 to 1.0 where negative values imply intake and positive imply shooting
   */
  public void shooterManualBottom(double speed)
  {
    bottomMotor.set(MotorUtils.truncateValue(speed, -1.0, 1.0));
  }

  /**
   * Set the top shooter motor to a specific speed 
   * @param speed - Set the top motor speed, -1.0 to 1.0 where negative values imply intake and positive imply shooting
   */
  public void shooterManualTop(double speed)
  {
    topMotor.set(MotorUtils.truncateValue(speed, -1.0, 1.0));
  }

  /**
   * Method to stop the shooter motors
   */
  public void stopShooter()
  {
    topMotor.set(0.0);
    bottomMotor.set(0.0);
  }

  private boolean isMotorUpToSpeed(WPI_TalonFX motor, double targetSpeed)
  {
    double approximateMotorVelocityTicksPerSecond = motor.getSelectedSensorVelocity() * 10;
    return (approximateMotorVelocityTicksPerSecond > 
      Shooter.talonMaximumTicksPerSecond * targetSpeed * Shooter.velocitySufficientWarmupThreshold);
  }

  /**
   * Gets the bottom motor speed setting
   * @return the bottom motor controller output as decmil fraction
   */
  private double getBottomMotorSpeed()
  {
    return bottomMotor.getMotorOutputPercent() / 100.0;
  }

  /**
   * Gets the top motor speed setting
   * @return the top motor controller output as decmil fraction
   */
  private double getTopMotorSpeed()
  {
    return topMotor.getMotorOutputPercent() / 100.0;
  }

  private String getShooterIntakeDescription()
  {
    double topMotorSpeed = this.getTopMotorSpeed();
    double bottomMotorSpeed = this.getBottomMotorSpeed();
    if(topMotorSpeed == 0.0 && bottomMotorSpeed == 0.0)
    {
      return "Stopped";
    }
    else if (topMotorSpeed > 0.0 && bottomMotorSpeed > 0.0)
    {
      return "Shooting";
    }
    else if (topMotorSpeed > 0.0 && bottomMotorSpeed > 0.0)
    {
      return "Intaking";
    }
    else
    {
      return "Undefined";
    }
  }
}
