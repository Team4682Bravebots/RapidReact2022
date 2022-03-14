// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: AngleArm.java
// Intent: Forms a subsystem that controls the AngleArm operations.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.Constants;
import frc.robot.InstalledHardware;
import frc.robot.common.MotorUtils;

public class AngleArms extends SubsystemBase implements Sendable
{
    // update this when folks are ready for it
    private static final double talonFxMotorSpeedReductionFactor = 0.75;
    
    private final WPI_TalonFX rightMotor = new WPI_TalonFX(Constants.angleArmsMotorCanId);
    private boolean motorsNeedInit = true;

    /**
     * The constructor for AngleArms
     */
    public AngleArms()
    {
        this.forceSensorReset();
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    /**
     * A method to update the sensors on this device
     */
    public void forceSensorReset()
    {
      this.initializeMotors();
      rightMotor.setSelectedSensorPosition(Constants.angleArmsReferencePositionMotorEncoderUnits);
    }

    /**
    * A method to obtain the Jaws current angle
    *
    * @return the current jaws angle
    */
    public double getCurrentAngleArmsAngle()
    {
        return this.convertMotorEncoderPositionToAngleArmsAngle(this.getAverageMotorEncoderPosition());
    }

    /**
     * A method to return the jaws subsystem to the reference position
     */
    public double getAngleArmsReferencePositionAngle()
    {
        return Constants.angleArmsReferencePositionAngle;
    }

    @Override
    public void initSendable(SendableBuilder builder)
    {
      builder.addDoubleProperty("AngleArmsAverageMotorOutput", this::getAverageMotorOutput, null);
      builder.addDoubleProperty("AngleArmsAverageMotorEncoderPosition", this::getAverageMotorEncoderPosition, null);
      builder.addDoubleProperty("AngleArmsCurrentAngleArmsAngle", this::getCurrentAngleArmsAngle, null);
    }

    @Override
    public void setDefaultCommand(Command myCommand)
    {
        super.setDefaultCommand(myCommand);
    }

    /**
    * a method exposed to callers to set the angle arms angle
    *
    * @param  targetAngle - target angle of the angle arms measured from front limit switch position
    * @param  toleranceInDegrees - the tolerance bounds in degrees that determine if the jaws have reached the proper setting
    * @return if the angle arms have attained the target angle setpoint and are within the tolerance threashold
    */
    public boolean setAngleArmsAngle(double targetAngleInDegrees, double toleranceInDegrees)
    {
        this.initializeMotors();
        double trimmedAngle = MotorUtils.truncateValue(targetAngleInDegrees, Constants.angleArmsMinimumPositionAngle, Constants.angleArmsMaximumPositionAngle);

        // because of follower this will set both motors
        rightMotor.set(TalonFXControlMode.MotionMagic, convertAngleArmsAngleToMotorEncoderPosition(trimmedAngle));
        double currentAngle = this.getCurrentAngleArmsAngle();

        boolean result = (currentAngle >= targetAngleInDegrees - toleranceInDegrees && currentAngle <= targetAngleInDegrees + toleranceInDegrees); 
//      System.out.println("target angle = " + targetAngleInDegrees + " current angle = " + currentAngle + " tolerance degrees = " + toleranceInDegrees + " result = " + result);
        return result;
    }

    /**
    * a method to drive the jaws motors manually
    *
    * @param  angleArmsSpeed - the target jaws speed
    */
    public void setAngleArmsSpeedManual(double angleArmsSpeed)
    {
        if(this.motorsNeedInit == false)
        {
            rightMotor.configFactoryDefault(0);
            this.motorsNeedInit = true;
        }
        rightMotor.set(TalonFXControlMode.PercentOutput, MotorUtils.truncateValue(angleArmsSpeed, -1.0, 1.0));
    }

    /* *********************************************************************
    PRIVATE METHODS
    ************************************************************************/
    // a method to convert a angle arms angle into the motor encoder position for the existing setup
    private double convertAngleArmsAngleToMotorEncoderPosition(double angleArmsAngle)
    {
      // TODO - fix this to properly approximate what the mechanism that will be used on the robot
      // for instance if there is a wire and a spool this might be more of a trig type function to approximate
      // straight line movement vs on an arc - not sure what mechanical will look like
      return angleArmsAngle * Constants.CtreTalonFx500EncoderTicksPerRevolution / Constants.DegreesPerRevolution * Constants.angleArmsMotorEffectiveGearRatio;
    }

    // a method to convert the current motor encoder position for the existing setup into angle arms angle position
    private double convertMotorEncoderPositionToAngleArmsAngle(double angleArmsMotorEncoderPosition)
    {
      // TODO - fix this to properly approximate what the mechanism that will be used on the robot
      // for instance if there is a wire and a spool this might be more of a trig type function to approximate
      // straight line movement vs on an arc - not sure what mechanical will look like
      return angleArmsMotorEncoderPosition / Constants.CtreTalonFx500EncoderTicksPerRevolution * Constants.DegreesPerRevolution / Constants.angleArmsMotorEffectiveGearRatio;
    }

    private double getAverageMotorEncoderPosition()
    {
      return rightMotor.getSelectedSensorPosition();
    }

    private double getAverageMotorOutput()
    {
      return rightMotor.getMotorOutputPercent();
    }

    // a method devoted to establishing proper startup of the jaws motors
    // this method sets all of the key settings that will help in motion magic
    private void initializeMotors()
    {
      if(motorsNeedInit)
      {
        double maxVelocity = Constants.talonMaximumRevolutionsPerMinute * Constants.CtreTalonFx500EncoderTicksPerRevolution / 10.0 * AngleArms.talonFxMotorSpeedReductionFactor;

        rightMotor.configFactoryDefault();
        rightMotor.setNeutralMode(NeutralMode.Brake);
        rightMotor.setInverted(Constants.angleArmsRightMotorDefaultDirection);
        rightMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
        rightMotor.setSensorPhase(false);
        rightMotor.configNeutralDeadband(0.001, Constants.kTimeoutMs);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
        rightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
  
        /* Set the peak and nominal outputs */
        rightMotor.configNominalOutputForward(0.0, Constants.kTimeoutMs);
        rightMotor.configNominalOutputReverse(-0.0, Constants.kTimeoutMs);
        rightMotor.configPeakOutputForward(1.0, Constants.kTimeoutMs);
        rightMotor.configPeakOutputReverse(-1.0, Constants.kTimeoutMs);
  
        rightMotor.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
        rightMotor.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
        rightMotor.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
        rightMotor.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
        rightMotor.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);
        rightMotor.configMotionCruiseVelocity(maxVelocity, Constants.kTimeoutMs);
        rightMotor.configMotionAcceleration(maxVelocity, Constants.kTimeoutMs);
  
        // current limit enabled | Limit(amp) | Trigger Threshold(amp) | Trigger
        rightMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, 20, 25, 1.0));

        motorsNeedInit = false;
      }
   }

}
