// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: Shooter.java
// Intent: Forms a subsystem that controls Shooter operations.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ShooterAutomatic;
import frc.robot.common.Gains;
import frc.robot.common.MotorUtils;

public class Shooter extends SubsystemBase implements Sendable
{
  // Talon info
  private static final double talonMaximumTicksPerSecond = Constants.talonMaximumRevolutionsPerMinute * Constants.CtreTalonFx500EncoderTicksPerRevolution / 60;
  private static final double velocitySufficientWarmupThreshold = 0.8;

  // Shooter gearing - currently 1:1
  private static final double topShooterGearRatio = 1.0;
  private static final double bottomShooterGearRatio = 1.0;
  
  private static final double kMinDeadband = 0.001;
  private static final int kPIDLoopIdx = 0;
  private static final int kTimeoutMs = 30;

  private WPI_TalonFX topMotor = new WPI_TalonFX(Constants.shooterMotorTopCanId);
  private WPI_TalonFX bottomMotor = new WPI_TalonFX(Constants.shooterMotorBottomCanId);

  private Gains topMotorGains = new Gains(0.1, 0.001, 5, 1023/20660.0, 300, 1.00);
  private Gains bottomMotorGains = new Gains(0.1, 0.001, 5, 1023/20660.0, 300, 1.00);

  /**
   * Constructor
   */
  public Shooter()
  {
    bottomMotor.configFactoryDefault();
    topMotor.configFactoryDefault();

    // TODO - not sure we need a neutral mode ... not sure
    bottomMotor.setNeutralMode(NeutralMode.Coast);
    topMotor.setNeutralMode(NeutralMode.Coast);

    // based on content found at:
    // https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java%20General/VelocityClosedLoop/src/main/java/frc/robot/Robot.java

    bottomMotor.setInverted(Constants.shooterBottomMotorDefaultDirection);
    topMotor.setInverted(Constants.shooterTopMotorDefaultDirection);

    bottomMotor.configNeutralDeadband(Shooter.kMinDeadband);
    topMotor.configNeutralDeadband(Shooter.kMinDeadband);

    bottomMotor.configSelectedFeedbackSensor(
      TalonFXFeedbackDevice.IntegratedSensor,
      Shooter.kPIDLoopIdx,
      Shooter.kTimeoutMs);
    topMotor.configSelectedFeedbackSensor(
      TalonFXFeedbackDevice.IntegratedSensor,
      Shooter.kPIDLoopIdx,
      Shooter.kTimeoutMs);

    bottomMotor.configNominalOutputForward(0, Shooter.kTimeoutMs);
    bottomMotor.configNominalOutputReverse(0, Shooter.kTimeoutMs);
    bottomMotor.configPeakOutputForward(1.0, Shooter.kTimeoutMs);
    bottomMotor.configPeakOutputReverse(-1.0, Shooter.kTimeoutMs);

    topMotor.configNominalOutputForward(0, Shooter.kTimeoutMs);
    topMotor.configNominalOutputReverse(0, Shooter.kTimeoutMs);
    topMotor.configPeakOutputForward(1.0, Shooter.kTimeoutMs);
    topMotor.configPeakOutputReverse(-1.0, Shooter.kTimeoutMs);

    bottomMotor.config_kF(Shooter.kPIDLoopIdx, this.bottomMotorGains.kF, Shooter.kTimeoutMs);
    bottomMotor.config_kP(Shooter.kPIDLoopIdx, this.bottomMotorGains.kP, Shooter.kTimeoutMs);
    bottomMotor.config_kI(Shooter.kPIDLoopIdx, this.bottomMotorGains.kI, Shooter.kTimeoutMs);
    bottomMotor.config_kD(Shooter.kPIDLoopIdx, this.bottomMotorGains.kD, Shooter.kTimeoutMs);

    topMotor.config_kF(Shooter.kPIDLoopIdx, this.topMotorGains.kF, Shooter.kTimeoutMs);
    topMotor.config_kP(Shooter.kPIDLoopIdx, this.topMotorGains.kP, Shooter.kTimeoutMs);
    topMotor.config_kI(Shooter.kPIDLoopIdx, this.topMotorGains.kI, Shooter.kTimeoutMs);
    topMotor.config_kD(Shooter.kPIDLoopIdx, this.topMotorGains.kD, Shooter.kTimeoutMs);

    CommandScheduler.getInstance().registerSubsystem(this);
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
    topMotor.set(ControlMode.PercentOutput, Constants.topMotorIntakeSpeed);
    bottomMotor.set(ControlMode.PercentOutput, Constants.bottomMotorIntakeSpeed);
    boolean rtnVal = isMotorUpToSpeed(topMotor, Constants.topMotorIntakeSpeed);
    rtnVal &= isMotorUpToSpeed(bottomMotor, Constants.bottomMotorIntakeSpeed);
    return rtnVal;
  }

  /**
   * Determine if the the bottom shooter motor velocity 
   * @param targetToleranceRpm - the tolerance in rpm 
   * @return true if the error in RPM is within the tolerance else false
   */
  public boolean isShooterVelocityUpToSpeedBottom(double targetToleranceRpm)
  {
    return Math.abs(bottomMotor.getClosedLoopError(Shooter.kPIDLoopIdx)) < 
      this.convertShooterRpmToMotorUnitsPer100Ms(Math.abs(targetToleranceRpm), Shooter.bottomShooterGearRatio);
  }

  /**
   * Determine if the the top shooter motor velocity 
   * @param targetToleranceRpm - the tolerance in rpm 
   * @return true if the error in RPM is within the tolerance else false
   */
  public boolean isShooterVelocityUpToSpeedTop(double targetToleranceRpm)
  {
    return Math.abs(topMotor.getClosedLoopError(Shooter.kPIDLoopIdx)) <
      this.convertShooterRpmToMotorUnitsPer100Ms(Math.abs(targetToleranceRpm), Shooter.topShooterGearRatio);
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
    topMotor.set(ControlMode.PercentOutput, Constants.topMotorForwardLowGoalSpeed);
    bottomMotor.set(ControlMode.PercentOutput, Constants.bottomMotorForwardLowGoalSpeed);
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
    topMotor.set(ControlMode.PercentOutput, Constants.topMotorForwardHighGoalSpeed);
    bottomMotor.set(ControlMode.PercentOutput, Constants.bottomMotorForwardHighGoalSpeed);
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
    topMotor.set(ControlMode.PercentOutput, Constants.topMotorReverseHighGoalSpeed);
    bottomMotor.set(ControlMode.PercentOutput, Constants.bottomMotorReverseHighGoalSpeed);
    boolean rtnVal = isMotorUpToSpeed(topMotor, Constants.topMotorReverseHighGoalSpeed);
    rtnVal &= isMotorUpToSpeed(bottomMotor, Constants.bottomMotorReverseHighGoalSpeed);
    return rtnVal;
  }

  /**
   * Set both motor speeds to to the same value 
   * @param speed - Range -1.0 to 1.0 where negative values imply intake and positive imply shooting
   */
  public void setShooterManual(double speed)
  {
    double cleanSpeed = MotorUtils.truncateValue(speed, -1.0, 1.0);
    this.setShooterManualBottom(cleanSpeed);
    this.setShooterManualTop(cleanSpeed);
  }

  /**
   * Set the bottom shooter motor to a specific speed 
   * @param speed - Set the bottom motor speed, -1.0 to 1.0 where negative values imply intake and positive imply shooting
   */
  public void setShooterManualBottom(double speed)
  {
    bottomMotor.set(ControlMode.PercentOutput, MotorUtils.truncateValue(speed, -1.0, 1.0));
  }

  /**
   * Set the top shooter motor to a specific speed 
   * @param speed - Set the top motor speed, -1.0 to 1.0 where negative values imply intake and positive imply shooting
   */
  public void setShooterManualTop(double speed)
  {
    topMotor.set(ControlMode.PercentOutput, MotorUtils.truncateValue(speed, -1.0, 1.0));
  }

  /**
   * Set the bottom shooter motor to a specific velocity using the in-built PID controller
   * @param revolutionsPerMinute - the RPM that the bottom motor should spin
   */
  public void setShooterVelocityBottom(double revolutionsPerMinute)
  {
    bottomMotor.set(
      ControlMode.Velocity,
      this.convertShooterRpmToMotorUnitsPer100Ms(revolutionsPerMinute, Shooter.bottomShooterGearRatio));
  }

  /**
   * Set the top shooter motor to a specific velocity using the in-built PID controller
   * @param revolutionsPerMinute - the RPM that the top motor should spin
   */
  public void setShooterVelocityTop(double revolutionsPerMinute)
  {
    topMotor.set(
      ControlMode.Velocity,
      this.convertShooterRpmToMotorUnitsPer100Ms(revolutionsPerMinute, Shooter.topShooterGearRatio));      
  }

  /**
   * Method to stop the shooter motors
   */
  public void stopShooter()
  {
    topMotor.set(ControlMode.PercentOutput, 0.0);
    bottomMotor.set(ControlMode.PercentOutput, 0.0);
  }

  private double convertShooterRpmToMotorUnitsPer100Ms(double targetRpm, double targetGearRatio)
  {
    double targetUnitsPer100ms = 
      MotorUtils.truncateValue(
        targetRpm,
        Constants.talonMaximumRevolutionsPerMinute * -1.0,
        Constants.talonMaximumRevolutionsPerMinute) *
      Constants.CtreTalonFx500EncoderTicksPerRevolution *
      targetGearRatio / 600.0;
    return targetUnitsPer100ms;
  }

  private boolean isMotorUpToSpeed(WPI_TalonFX motor, double targetSpeed)
  {
    double approximateMotorVelocityTicksPerSecond = motor.getSelectedSensorVelocity() * 10;
    return (approximateMotorVelocityTicksPerSecond > 
      Shooter.talonMaximumTicksPerSecond * targetSpeed * Shooter.velocitySufficientWarmupThreshold);
  }

  /**
   * Gets the most recent bottom shooter RPM
   * @return the bottom shooter RPM based on the past 100 ms
   */
  private double getBottomShooterRevolutionsPerMinute()
  {
    return (bottomMotor.getSelectedSensorVelocity() / Constants.CtreTalonFx500EncoderTicksPerRevolution) * 600.0 * Shooter.bottomShooterGearRatio;
  }

  /**
   * Gets the most recent top shooter RPM
   * @return the top shooter RPM based on the past 100 ms
   */
  private double getTopShooterRevolutionsPerMinute()
  {
    return (topMotor.getSelectedSensorVelocity() / Constants.CtreTalonFx500EncoderTicksPerRevolution) * 600.0 * Shooter.topShooterGearRatio;
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
    else if (topMotorSpeed < 0.0 && bottomMotorSpeed < 0.0)
    {
      return "Intaking";
    }
    else
    {
      return "Undefined";
    }
  }
}
