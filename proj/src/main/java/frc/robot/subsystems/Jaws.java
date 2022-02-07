// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Jaws.java
// Intent: Forms a subsystem that controls movements by the Jaws.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.common.MotorUtils;

public class Jaws extends SubsystemBase
{

  /* *********************************************************************
  CONSTANTS
  ************************************************************************/
  private static final double jawsMotorEncoderTicksPerDegree = Constants.CtreTalonFx500EncoderTicksPerRevolution / Constants.DegreesPerRevolution;
  private static final double jawsSpeedDuringCalibration = -0.8;
  private static final double jawsMotorToArmEffectiveGearRatio = 500;

  /* *********************************************************************
  MEMBERS
  ************************************************************************/
  private final WPI_TalonFX rightMotor = new WPI_TalonFX(Constants.jawsMotorRightCanId);
  private final WPI_TalonFX leftMotor = new WPI_TalonFX(Constants.jawsMotorLeftCanId);
  private final DigitalInput jawsIntakeStopLimitSwitch = new DigitalInput(Constants.jawsIntakeStopLimitSwitchChannel);

  private double motorReferencePosition = 0.0;

  /* *********************************************************************
  CONSTRUCTORS
  ************************************************************************/

  // constructor for Jaws
  public Jaws()
  {
    initializeJawMotors();
  }

  /* *********************************************************************
  PUBLIC METHODS
  ************************************************************************/

  // this method will force the jaws to the lower limit switch position
  public void calibrateJaws()
  {
    try
    {
      // power the motors toward the intake position
      this.setJawsSpeedManual(Jaws.jawsSpeedDuringCalibration);

      // wait for the limit switch to not be in the 'open' state
      while(jawsIntakeStopLimitSwitch.get())
      {
        // TODO - how to not tight while properly?
        java.lang.Thread.sleep(10);
      }

      // stop the motors
      rightMotor.set(ControlMode.PercentOutput, 0.0); 

      // set the motor encoders to zero
      rightMotor.setSelectedSensorPosition(0);
      leftMotor.setSelectedSensorPosition(0);
    }
    catch(Exception ex)
    {
      System.out.println("Jaws::calibrateJaws failed - " + ex.toString());
    }

    // get the motor encoders reference position
    motorReferencePosition = this.getAverageMotorEncoderPosition();
  }

  // A method to obtain the Jaws current angle
  public double getJawsAngle()
  {
    return this.convertMotorEncoderPositionToJawsAngle(this.getAverageMotorEncoderPosition());
  }

  // This method will be called once per scheduler run
  @Override
  public void periodic()
  {
    // TODO - do we need anything here?
  }

  // This method helps decide the default command
  @Override
  public void setDefaultCommand(Command myCommand)
  {
      super.setDefaultCommand(myCommand);
  }

  // a method exposed to callers to set the jaws angle
  public void setJawsAngle(double targetAngle)
  {
    // because of follower this will set both motors
    rightMotor.set(TalonFXControlMode.MotionMagic, convertJawsAngleToMotorEncoderPosition(targetAngle));
  }

  // a method to drive the jaws motors manually
  public void setJawsSpeedManual(double jawsSpeed)
  {
    MotorUtils.validateMotorSpeedInput(jawsSpeed, "jawsSpeed", null);
    rightMotor.set(TalonFXControlMode.PercentOutput, jawsSpeed);
  }

  /* *********************************************************************
  PRIVATE METHODS
  ************************************************************************/
  // a method to convert a jaws angle into the motor encoder position for the existing setup
  // note this includes adding the originating motor reference position (which is hopefully 0.0)
  private double convertJawsAngleToMotorEncoderPosition(double jawsAngle)
  {
    return jawsAngle * Jaws.jawsMotorEncoderTicksPerDegree * Jaws.jawsMotorToArmEffectiveGearRatio + motorReferencePosition;
  }

  // a method to convert the current motor encoder position for the existing setup into jaws angle position
  // note this includes subtracting the originating motor reference position (which is hopefully 0.0)
  private double convertMotorEncoderPositionToJawsAngle(double jawsMotorEncoderPosition)
  {
    return (jawsMotorEncoderPosition - motorReferencePosition) / Jaws.jawsMotorEncoderTicksPerDegree / Jaws.jawsMotorToArmEffectiveGearRatio;
  }

  private double getAverageMotorEncoderPosition()
  {
    return (rightMotor.getSelectedSensorPosition() + leftMotor.getSelectedSensorPosition())/2;
  }

  // a method devoted to establishing proper startup of the jaws motors
  // this method sets all of the key settings that will help in motion magic
  private void initializeJawMotors()
  {
    rightMotor.configFactoryDefault();
    leftMotor.setInverted(true);
    rightMotor.setInverted(true);
    leftMotor.setNeutralMode(NeutralMode.Brake);
    rightMotor.setNeutralMode(NeutralMode.Brake);

    leftMotor.follow(rightMotor);

    rightMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    rightMotor.setSensorPhase(false);
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
  }
 
}