// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
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

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

import frc.robot.Constants;
import frc.robot.common.ConsecutiveDigitalInput;
import frc.robot.common.MotorUtils;

public class Jaws extends SubsystemBase
{
    /* *********************************************************************
    CONSTANTS
    ************************************************************************/
    private static final double jawsMotorEncoderTicksPerDegree = Constants.CtreTalonFx500EncoderTicksPerRevolution / Constants.DegreesPerRevolution;
    private static final double jawsSpeedDuringCalibration = -0.8;
    private static final double jawsMotorToArmEffectiveGearRatio = 212; // according to nathan on 02/08/2022
    private static final int jawsMinimumIsCalibratedConsecutiveCount = 1;
    private static final double minmumTargetAngle = 0.0;
    private static final double maximumTargetAngle = 160.0;

    /* *********************************************************************
    MEMBERS
    ************************************************************************/
    private final WPI_TalonFX rightMotor = new WPI_TalonFX(Constants.jawsMotorRightCanId);
    private final WPI_TalonFX leftMotor = new WPI_TalonFX(Constants.jawsMotorLeftCanId);
    private final ConsecutiveDigitalInput jawsIntakeStopLimitSwitch = 
      new ConsecutiveDigitalInput(Constants.jawsIntakeStopLimitSwitchChannel);
    private final DoubleSolenoid jawsClutchSolenoid = new DoubleSolenoid(
      Constants.robotPneumaticsControlModuleType,
      Constants.jawsClutchSolenoidForwardChannel,
      Constants.jawsClutchSolenoidReverseChannel); 

    private double motorReferencePosition = 0.0;
    private boolean jawsMotionCurrentlyCalibrating = false;
    private boolean jawsMotionCalibrated = false;
    private boolean clutchEnguaged = false;
    private DoubleSolenoid.Value clutchEnguagedSetting = kForward;
    private DoubleSolenoid.Value clutchDisenguagedSetting = kReverse;

    /* *********************************************************************
    CONSTRUCTORS
    ************************************************************************/

    /**
    * constructor for Jaws subsystem
    */
    public Jaws()
    {
      initializeMotors();
      CommandScheduler.getInstance().registerSubsystem(this);
    }

    /* *********************************************************************
    PUBLIC METHODS
    ************************************************************************/

    /**
    * A method to obtain the Jaws current angle
    *
    * @return the current jaws angle
    */
    public double getJawsAngle()
    {
      return this.convertMotorEncoderPositionToJawsAngle(this.getAverageMotorEncoderPosition());
    }

    /**
    * a method to continue to watch and find out if the jaws have finished calibration
    *
    * @return true if calibration is complete, else false
    */
    public boolean isCalibrationComplete()
    {
      if(jawsMotionCalibrated == false)
      {
        // make sure the limit switch is set - switch is closed
        // also make sure it has been closed on at least two successive calls
        if(jawsIntakeStopLimitSwitch.get() &&
          jawsIntakeStopLimitSwitch.getStatusCount() >= Jaws.jawsMinimumIsCalibratedConsecutiveCount)
        {
          // stop the motors
          rightMotor.set(ControlMode.PercentOutput, 0.0); 

          // set the motor encoders to zero
          rightMotor.setSelectedSensorPosition(0);
          leftMotor.setSelectedSensorPosition(0);

          // get the motor encoders reference position
          motorReferencePosition = this.getAverageMotorEncoderPosition();
      
          // mark things as calibrated
          jawsMotionCalibrated = true;
          jawsMotionCurrentlyCalibrating = false;
        }
      }
      return jawsMotionCalibrated;
    }

    /**
    * This method will be called once per scheduler run
    */
    @Override
    public void periodic()
    {
      // TODO - do we need anything here?
    }

    /**
    * This method helps decide the default command
    *
    * @param  value - The default command
    */
    @Override
    public void setDefaultCommand(Command myCommand)
    {
        super.setDefaultCommand(myCommand);
    }

    /**
    * a method exposed to callers to set the jaws angle
    *
    * @param  targetAngle - target angle of the jaws measured from front limit switch position
    * @param  toleranceInDegrees - the tolerance bounds in degrees that determine if the jaws have reached the proper setting
    * @return if the jaws have attained the target angle setpoint and are within the tolerance threashold
    */
    public boolean setJawsAngle(double targetAngleInDegrees, double toleranceInDegrees)
    {
      this.resumeJawMovement();
      double trimmedAngle = MotorUtils.truncateValue(targetAngleInDegrees, Jaws.minmumTargetAngle, Jaws.maximumTargetAngle);

      // because of follower this will set both motors
      rightMotor.set(TalonFXControlMode.MotionMagic, convertJawsAngleToMotorEncoderPosition(trimmedAngle));
      double currentAngle = this.getJawsAngle();

      return (currentAngle - toleranceInDegrees >= targetAngleInDegrees && currentAngle + toleranceInDegrees <= targetAngleInDegrees);
    }

    /**
    * a method to drive the jaws motors manually
    *
    * @param  jawsSpeed - the target jaws speed
    */
    public void setJawsSpeedManual(double jawsSpeed)
    {
      this.resumeJawMovement();
      MotorUtils.validateMotorSpeedInput(jawsSpeed, "jawsSpeed", null);
      rightMotor.set(TalonFXControlMode.PercentOutput, jawsSpeed);
    }

    /**
    * a method to start jaws on the path toward calibration
    */
    public void startCalibration()
    {
      // make sure the clutch is disenguaged
      jawsClutchSolenoid.set(this.clutchDisenguagedSetting);
      clutchEnguaged = false;
      if(jawsMotionCurrentlyCalibrating == false && jawsMotionCalibrated == false)
      {
        // power the motors toward the intake position
        this.setJawsSpeedManual(Jaws.jawsSpeedDuringCalibration);
        jawsMotionCurrentlyCalibrating = true;
      }
    }

    /**
    * a method exposed to callers to hold/lock the current jaws angle
    */
    public void suspendJawMovement()
    {
      if(!clutchEnguaged)
      {
        jawsClutchSolenoid.set(this.clutchEnguagedSetting);
        clutchEnguaged = true;
        rightMotor.set(TalonFXControlMode.PercentOutput, 0.0);
      }      
    }
    
    /**
    * a method exposed to callers to release/unlock the current jaws angle
    */
    public void resumeJawMovement()
    {
      if(clutchEnguaged)
      {
        jawsClutchSolenoid.set(this.clutchDisenguagedSetting);
        clutchEnguaged = false;
      }      
    }

    /**
    * A method exposed to callers to toggle between locking and unlocking movement on the jaws
    */
    public void toggleJawMovement()
    {
      if(clutchEnguaged)
      {
        this.resumeJawMovement();
      }
      else
      {
        this.suspendJawMovement();
      }
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
    private void initializeMotors()
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