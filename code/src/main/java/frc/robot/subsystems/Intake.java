// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Intake.java
// Intent: Forms model for the Intake subsystem.
// ************************************************************

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase
{
  private WPI_TalonFX leftMotor = new WPI_TalonFX(Constants.intakeMotorLeftCanId);
  private WPI_TalonFX rightMotor = new WPI_TalonFX(Constants.intakeMotorRightCanId);

	final int kUnitsPerRevolution = 2048; /* this is constant for Talon FX */

  // ctor
  public Intake()
  {
    leftMotor.configFactoryDefault();
    rightMotor.configFactoryDefault();
    rightMotor.follow(leftMotor);
    rightMotor.setInverted(true);
    rightMotor.setNeutralMode(NeutralMode.Coast);
    
    leftMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx,
    Constants.kTimeoutMs);

    leftMotor.setSensorPhase(false);
    leftMotor.setInverted(false);
    leftMotor.configNeutralDeadband(0.001, Constants.kTimeoutMs);   
    leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
    leftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

    /* Set the peak and nominal outputs */
		leftMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
		leftMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
		leftMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
		leftMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    leftMotor.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    leftMotor.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
    leftMotor.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
    leftMotor.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    leftMotor.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);

    leftMotor.setNeutralMode(NeutralMode.Coast);

    //leftMotor.configMotionCruiseVelocity(30000, Constants.kTimeoutMs);
    //leftMotor.configMotionAcceleration(30000, Constants.kTimeoutMs);

    // current limit enabled | Limit(amp) | Trigger Threshold(amp) | Trigger
    // Threshold Time(s) */
    leftMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, 20, 25, 1.0));
    // leftMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
  }
  

  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
  }

  public void eat(double speed)
  {
    leftMotor.set(TalonFXControlMode.PercentOutput, speed);
  }

  public void barf(double speed)
  {
    leftMotor.set(TalonFXControlMode.PercentOutput, speed * -1.0);
  }

  public void defualt()
  {
    leftMotor.set(TalonFXControlMode.PercentOutput, Constants.intakeDefualt);
  }
}
