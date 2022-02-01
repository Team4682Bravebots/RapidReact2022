// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Jaws.java
// Intent: Forms a subsystem that controls movements by the Jaws.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Jaws extends SubsystemBase {


  private final WPI_TalonFX rightMotor = new WPI_TalonFX(Constants.jawsMotorRightCanId);
  private final WPI_TalonFX leftMotor = new WPI_TalonFX(Constants.jawsMotorRightCanId);

  /*
   * Talon FX has 2048 units per revolution
   * 
   * https://phoenix-documentation.readthedocs.io/en/latest/ch14_MCSensor.html#
   * sensor-resolution
   */
  final int kUnitsPerRevolution = 2048; /* this is constant for Talon FX */

  /** Creates a new climberS1. */
  public Jaws() {

    rightMotor.configFactoryDefault();
    leftMotor.setInverted(true);
    leftMotor.follow(rightMotor);
    
    rightMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx,
    Constants.kTimeoutMs);

    rightMotor.setSensorPhase(false);
    rightMotor.setInverted(false);

    rightMotor.configNeutralDeadband(0.001, Constants.kTimeoutMs);

    
    rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
    rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

    		/* Set the peak and nominal outputs */
		rightMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
		rightMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
		rightMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
		rightMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    rightMotor.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    rightMotor.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
    rightMotor.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
    rightMotor.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    rightMotor.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);

    rightMotor.setNeutralMode(NeutralMode.Brake);

    rightMotor.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
    rightMotor.configMotionAcceleration(6000, Constants.kTimeoutMs);

    // current limit enabled | Limit(amp) | Trigger Threshold(amp) | Trigger
    // Threshold Time(s) */
    rightMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, 20, 25, 1.0));

    // left.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public double getPosition() {
    double selSenPos = rightMotor.getSelectedSensorPosition();
    return selSenPos;
  }

  public double getVelocity() {
    double selSenVel = rightMotor.getSelectedSensorVelocity();
    return selSenVel;
  }

  public void setJawsPosition(double targetPos) {
    rightMotor.set(TalonFXControlMode.MotionMagic, targetPos);
  }
  
  public void jawsManual(double speed){
    rightMotor.set(TalonFXControlMode.PercentOutput, speed);
  }

  //public void isFinished(boolean done, double targetPos){
   //if(rightMotor.getSelectedSensorPosition() >= targetPos + 100 || rightMotor.getSelectedSensorPosition() <=targetPos - 100){
     //done = true;
    //} else { done = false; }
  //}

  public void setInverted() {
    rightMotor.setInverted(true);
  }

  public void zeroSensors() {
    rightMotor.setSelectedSensorPosition(0);
  }
 
}