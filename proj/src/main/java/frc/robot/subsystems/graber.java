// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class graber extends SubsystemBase {


  private WPI_TalonFX left = new WPI_TalonFX(Constants.climberS1MotorLeft);
  private WPI_TalonFX right = new WPI_TalonFX(Constants.climberS1MotorRight);

  double imput;
  /*
	 * Talon FX has 2048 units per revolution
	 * 
	 * https://phoenix-documentation.readthedocs.io/en/latest/ch14_MCSensor.html#sensor-resolution
	 */
	final int kUnitsPerRevolution = 2048; /* this is constant for Talon FX */

   /** Creates a new climberS1. */
  public graber() {

    left.configFactoryDefault();
    right.follow(left);
    
    left.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx,
    Constants.kTimeoutMs);

    left.setSensorPhase(false);
    left.setInverted(false);

    left.configNeutralDeadband(0.001, Constants.kTimeoutMs);

    left.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
    left.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

    		/* Set the peak and nominal outputs */
		left.configNominalOutputForward(0, Constants.kTimeoutMs);
		left.configNominalOutputReverse(0, Constants.kTimeoutMs);
		left.configPeakOutputForward(1, Constants.kTimeoutMs);
		left.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    left.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    left.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
    left.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
    left.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    left.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);

    left.setNeutralMode(NeutralMode.Brake);

    left.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
    left.configMotionAcceleration(6000, Constants.kTimeoutMs);

    // current limit enabled | Limit(amp) | Trigger Threshold(amp) | Trigger
    // Threshold Time(s) */
    left.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, 20, 25, 1.0));

    // left.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public double getPosition(){
    double selSenPos = left.getSelectedSensorPosition(0);
    return selSenPos;
  }

  public double getVelocity(){
    double selSenVel = left.getSelectedSensorVelocity(0);
    return selSenVel;
  }

  public void setClimberPostion(double targetPos, int smoothing) {

    left.set(ControlMode.MotionMagic, targetPos);
    left.configMotionSCurveStrength(smoothing);
  }

  public void setInverted() {
    left.setInverted(true);
  }

  public void zeroSensors() {
    left.setSelectedSensorPosition(0);
  }

  public void setSmoothing(int smoothing){
    left.configMotionSCurveStrength(smoothing);

  }

}
