// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Arm.java
// Intent: Forms model for the Arm subsystem.
// ************************************************************

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.fasterxml.jackson.databind.node.BooleanNode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase
{
  // two matched motors - one for each climber side
  private WPI_TalonFX left = new WPI_TalonFX(Constants.frontClimbersMotorLeftCanId);
  private WPI_TalonFX right = new WPI_TalonFX(Constants.frontClimbersMotorRightCanId);

  // ctr
  public Arm()
  {
    left.configFactoryDefault();  
    left.configSelectedFeedbackSensor(
      TalonFXFeedbackDevice.IntegratedSensor,
      Constants.kPIDLoopIdx,
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

    left.configStatorCurrentLimit(
      new StatorCurrentLimitConfiguration(
        true, // enabled | 
        20, // Limit(amp) |
        25, // Trigger Threshold(amp) |
        1.0)); // Trigger Threshold Time(s)
    // left.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
  }

  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
  }

  public double getPosition()
  {
    double selSenPos = left.getSelectedSensorPosition();
    return selSenPos;
  }

  public double getVelocity()
  {
    double selSenVel = left.getSelectedSensorVelocity();
    return selSenVel;
  }

  public void setArmPosition(double targetPos)
  {
    left.set(TalonFXControlMode.MotionMagic, targetPos);
  }
  
  //public void isFinished(boolean done, double targetPos){
   //if(left.getSelectedSensorPosition() >= targetPos + 100 || left.getSelectedSensorPosition() <=targetPos - 100){
     //done = true;
    //} else { done = false; }
  //}

  public void setInverted()
  {
    left.setInverted(true);
  }

  public void zeroSensors()
  {
    left.setSelectedSensorPosition(0);
  }
}