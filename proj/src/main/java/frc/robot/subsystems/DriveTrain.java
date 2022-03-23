// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: DriveTrain.java
// Intent: Forms model for the DriveTrain subsystem.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import java.io.Console;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.motorcontrol.*;

import frc.robot.Constants;
import frc.robot.common.MotorUtils;

public class DriveTrain extends SubsystemBase implements Sendable
{
  // update this when folks are ready for it
  private static final double talonFxMotorSpeedReductionFactor = 0.50;

  // four matched motors - two for each tank drive side
  private WPI_TalonFX leftFront = new WPI_TalonFX(Constants.driveMotorLeftFrontCanId);
  private WPI_TalonFX leftRear = new WPI_TalonFX(Constants.driveMotorLeftRearCanId);
  private WPI_TalonFX rightFront = new WPI_TalonFX(Constants.driveMotorRightFrontCanId);
  private WPI_TalonFX rightRear = new WPI_TalonFX(Constants.driveMotorRightRearCanId);
  private MotorControllerGroup left = new MotorControllerGroup(leftFront, leftRear);
  private MotorControllerGroup right = new MotorControllerGroup(rightFront, rightRear);
  private DifferentialDrive forwardDrive = new DifferentialDrive(left, right);
  private DifferentialDrive reverseDrive = new DifferentialDrive(right, left);

  private DifferentialDrive currentDrive = forwardDrive;
  private boolean isCurrentDriveForward = true;

  // TODO - get this info from Greyson / John
  private static final double robotTrackWidthInches = 21.7;
  private static final double halfRobotTrackWidthInches = robotTrackWidthInches / 2;

  private static final double wheelDiameterInches = 6.0;
  private static final double effectiveWheelMotorGearBoxRatio = (40.0 / 12.0) * (40.0 / 14.0);

  private static final int motorSettingTimeout = 0; //Constants.kTimeoutMs;

  private boolean motionMagicRunning = false;
  private boolean initalizedForMotionMagic = true;

  private double leftTargetEncoderTicks = 0;
  private double rightTargetEncoderTicks = 0;
  private double leftTargetEncoderTicksError = 0;
  private double rightTargetEncoderTicksError = 0;

  private double arcadeMotorPowerReductionFactor = 0.7;

  /**
  * No argument constructor for the DriveTrain subsystem.
  */
  public DriveTrain()
  {
    this.forceSensorReset();
    CommandScheduler.getInstance().registerSubsystem(this);
  }

  /**arcade
  * A method to take in x and y stick inputs and turn them into right and left motor speeds
  * considering arcade style driving
  *
  * @param  powerValue - power forward backward speed, range -1.0 to 1.0 where positive values are forward
  * @param  spinValue - spin componet, range -1.0 to 1.0 where positive values are forward
  */
  public void arcadeDrive(double powerValue, double spinValue)
  {
    currentDrive.arcadeDrive(powerValue * this.arcadeMotorPowerReductionFactor, spinValue);
  }

  /**
   * Set the manual power fraction that will be used in arcade
   * @param arcadePowerFraction
   */
  public void setArcadePowerFactor(double arcadePowerFraction)
  {
    this.arcadeMotorPowerReductionFactor = MotorUtils.truncateValue(arcadePowerFraction, 0.1, 1.0);
  }
  /**
   * Get the manual power fraction
   * @return
   */
  public double getArcadePowerFactor()
  {
    return this.arcadeMotorPowerReductionFactor;
  }

  /**
   * Get the manual power fraction
   * @return
   */
  public void toggleDriveDirection()
  {
    if(this.isCurrentDriveForward == true)
    {
      this.currentDrive = this.reverseDrive;
      this.isCurrentDriveForward = false;
    }
    else
    {
      this.currentDrive = this.forwardDrive;
      this.isCurrentDriveForward = true;
    }
  }

  /**
   * A method to update the sensors on this device
   */
  public void forceSensorReset()
  {
    this.initializeMotorsManual();
    leftFront.setSelectedSensorPosition(0.0);
    leftRear.setSelectedSensorPosition(0.0);
    rightFront.setSelectedSensorPosition(0.0);
    rightRear.setSelectedSensorPosition(0.0);
  }

  @Override
  public void initSendable(SendableBuilder builder)
  {
    builder.addDoubleProperty("DriveTrainAverageLeftMotorOutput", this::getAverageLeftMotorOutputSpeed, null);
    builder.addDoubleProperty("DriveTrainAverageRightMotorOutput", this::getAverageRightMotorOutputSpeed, null);
    builder.addDoubleProperty("DriveTrainAverageLeftMotorEncoderPosition", this::getAverageLeftMotorEncoderPosition, null);
    builder.addDoubleProperty("DriveTrainAverageRightMotorEncoderPosition", this::getAverageRightMotorEncoderPosition, null);
    builder.addDoubleProperty("DriveTrainApproximateLeftTravelDistanceInches", this::getApproximateLeftWheelDistance, null);
    builder.addDoubleProperty("DriveTrainApproximateRightTravelDistanceInches", this::getApproximateRightWheelDistance, null);
    builder.addStringProperty("DriveTrainMovementDescription", this::describeDriveTrainMovement, null);
  }
  
  /**
   * Determines if the motors are performing drive movement
   * @return true if an automated drive movement is currently happening
   */
  public boolean isCurrentlyPerformingDriveMovement()
  {
    if(motionMagicRunning)
    {
      System.out.println("Motion magic running.");
      leftFront.set(ControlMode.MotionMagic, leftTargetEncoderTicks);
      rightFront.set(ControlMode.MotionMagic, rightTargetEncoderTicks);
        double leftError = Math.abs(leftFront.getClosedLoopError());
      double rightError = Math.abs(rightFront.getClosedLoopError());
      double leftTicks = leftFront.getSelectedSensorPosition();
      double rightTicks = rightFront.getSelectedSensorPosition();
      if(leftError < leftTargetEncoderTicksError &&
         rightError < rightTargetEncoderTicksError && 
         leftTicks >= leftTargetEncoderTicks - leftTargetEncoderTicksError && leftTicks <= leftTargetEncoderTicks + leftTargetEncoderTicksError &&
         rightTicks >= rightTargetEncoderTicks - rightTargetEncoderTicksError && rightTicks <= rightTargetEncoderTicks + rightTargetEncoderTicksError)
      {
        leftFront.set(ControlMode.PercentOutput, 0.0);
        rightFront.set(ControlMode.PercentOutput, 0.0);
        motionMagicRunning = false;
        System.out.println("Stopping motition magic.");
      }
    }
    else
    {
      motionMagicRunning = false;
      System.out.println("forcing motion magic to false.");
    }
    return motionMagicRunning;
  }

  /**
   * stops performing drive movement if the motion magic is running
   */
  public void stopPerformingDriveMovement()
  {
    if(motionMagicRunning)
    {
      System.out.println("stopPerformingDriveMovement being called ... ");
      leftFront.set(ControlMode.PercentOutput, 0.0);
      rightFront.set(ControlMode.PercentOutput, 0.0);
      motionMagicRunning = false;
    }
  }

  /**
  * Move wheels such that centroid of robot follows a circular arc with a defined distance 
  *
  * @param  leftDistanceInches - robot centroid movement distance, range includes negative
  * @param  rotationDegrees - relative rotation from current position with forward robot heading is at 0.0 (must be between -360.0 and 360.0), positive values are clockwise rotation
  * @param  targetTimeSeconds - the target time to run this operation in seconds
  */
  public void performCircleArcDriveInches(double distanceInches, double rotationDegrees, double targetTimeSeconds)
  {
    this.initializeMotorsMotionMagic();
    double leftDistanceInches = distanceInches;
    double rightDistanceInches = distanceInches; 

    double radiusInches = this.radiusFromArcDistance(distanceInches, rotationDegrees);
    if(rotationDegrees > 0.5)
    {
      leftDistanceInches = this.distanceFromArcRadius(radiusInches + DriveTrain.halfRobotTrackWidthInches, rotationDegrees);
      rightDistanceInches = this.distanceFromArcRadius(radiusInches - DriveTrain.halfRobotTrackWidthInches, rotationDegrees);
    }
    else if (rotationDegrees < -0.5)
    {
      leftDistanceInches = this.distanceFromArcRadius(radiusInches - DriveTrain.halfRobotTrackWidthInches, rotationDegrees);
      rightDistanceInches = this.distanceFromArcRadius(radiusInches + DriveTrain.halfRobotTrackWidthInches, rotationDegrees);
    }
    else
    {
      leftDistanceInches = distanceInches;
      rightDistanceInches = distanceInches;         
    }

    // sets motors to input 
    this.moveWheelsDistance(leftDistanceInches, rightDistanceInches, targetTimeSeconds);
  }

  @Override
  public void periodic()
  {
  }

  @Override
  public void setDefaultCommand(Command myCommand)
  {
      // TODO Auto-generated method stub
      super.setDefaultCommand(myCommand);
  }

  private String describeDriveTrainMovement()
  {
    double leftMovement = this.getAverageLeftMotorOutputSpeed();
    double rightMovement = this.getAverageRightMotorOutputSpeed();
    if(leftMovement == 0.0 && rightMovement == 0.0)
    {
      return "Stopped";
    }
    else if(leftMovement > 0.0 && leftMovement == rightMovement)
    {
      return "Forward";
    }
    else if(leftMovement < 0.0 && leftMovement == rightMovement)
    {
      return "Reverse";
    }
    else if(leftMovement > 0.0 && rightMovement > 0.0)
    {
      if(leftMovement > rightMovement)
      {
        return "Forward Right Arc";
      }
      else
      {
        return "Forward Left Arc";
      }
    }
    else if(leftMovement < 0.0 && rightMovement < 0.0)
    {
      if(leftMovement < rightMovement)
      {
        return "Reverse Right Arc";
      }
      else
      {
        return "Reverse Left Arc";
      }
    }
    else if(leftMovement > 0.0 && leftMovement > rightMovement)
    {
      return "Right Spin";
    }
    else if(rightMovement > 0.0 && rightMovement > leftMovement)
    {
      return "Left Spin";
    }
    else
    {
      return "Undefined";
    }
  }

  private double radiusFromArcDistance(double distance, double rotationDegrees)
  {
    double absDistance = Math.abs(distance);
    double absDegrees = Math.abs(rotationDegrees);
    return (180.0 * absDistance) / (Math.PI * absDegrees);
  }

  private double distanceFromArcRadius(double radius, double rotationDegrees)
  {
    double radDistance = Math.abs(radius);
    double absDegrees = Math.abs(rotationDegrees);
    return (radDistance * Math.PI * absDegrees) / 180.0;
  }

  private void moveWheelsDistance(double leftWheelDistanceInches, double rightWheelDistanceInches, double targetTimeInSeconds)
  {
    double leftDeltaEncoderTicks = this.getEncoderUnitsFromTrackDistanceInInches(leftWheelDistanceInches);
    double rightDeltaEncoderTicks = this.getEncoderUnitsFromTrackDistanceInInches(rightWheelDistanceInches);   

    // set the targets
    leftTargetEncoderTicks = leftDeltaEncoderTicks + this.getAverageLeftMotorEncoderPosition();
    leftTargetEncoderTicksError = Math.abs(leftDeltaEncoderTicks) * 0.01;
    rightTargetEncoderTicks = rightDeltaEncoderTicks + this.getAverageRightMotorEncoderPosition();
    rightTargetEncoderTicksError = Math.abs(rightDeltaEncoderTicks) * 0.01;

    // move it with motion magic
    leftFront.set(ControlMode.MotionMagic, leftTargetEncoderTicks);
    rightFront.set(ControlMode.MotionMagic, rightTargetEncoderTicks);

    motionMagicRunning = true;
    System.out.println("END moveWheelsDistance!");
  }

  private double getEncoderUnitsFromTrackDistanceInInches(double wheelTrackDistanceInches)
  {
    return (wheelTrackDistanceInches / (Math.PI * DriveTrain.wheelDiameterInches)) * Constants.CtreTalonFx500EncoderTicksPerRevolution * DriveTrain.effectiveWheelMotorGearBoxRatio;
  }

  private double getTrackDistanceInInchesFromEncoderUnits(double encoderUnits)
  {
    return (encoderUnits / Constants.CtreTalonFx500EncoderTicksPerRevolution / DriveTrain.effectiveWheelMotorGearBoxRatio) * (Math.PI * DriveTrain.wheelDiameterInches);
  }

  private double getApproximateLeftWheelDistance()
  {
    return this.getTrackDistanceInInchesFromEncoderUnits(this.getAverageLeftMotorEncoderPosition());
  }

  private double getApproximateRightWheelDistance()
  {
    return this.getTrackDistanceInInchesFromEncoderUnits(this.getAverageRightMotorEncoderPosition());
  }

  private double getAverageLeftMotorEncoderPosition()
  {
    return leftFront.getSelectedSensorPosition();
  }

  private double getAverageLeftMotorOutputSpeed()
  {
    return leftFront.getMotorOutputPercent() / 100.0;
  }

  private double getAverageRightMotorEncoderPosition()
  {
    return rightFront.getSelectedSensorPosition();
  }

  private double getAverageRightMotorOutputSpeed()
  {
    return rightFront.getMotorOutputPercent() / 100.0;
  }

  private void initializeMotorsManual()
  {
    if(this.initalizedForMotionMagic == true)
    {
      leftFront.configFactoryDefault();
      leftRear.configFactoryDefault();
      leftFront.setInverted(Constants.driveMotorLeftFrontDefaultDirection);
      leftRear.setInverted(Constants.driveMotorLeftRearDefaultDirection);
      leftFront.setNeutralMode(NeutralMode.Brake);
      leftRear.setNeutralMode(NeutralMode.Brake);
      leftRear.follow(leftFront);
      rightFront.configFactoryDefault();
      rightRear.configFactoryDefault();
      rightFront.setInverted(Constants.driveMotorRightFrontDefaultDirection);
      rightRear.setInverted(Constants.driveMotorRightRearDefaultDirection);
      rightFront.setNeutralMode(NeutralMode.Brake);
      rightRear.setNeutralMode(NeutralMode.Brake);
      rightRear.follow(rightFront);
      this.initalizedForMotionMagic = false;
    }
  }

  private void initializeMotorsMotionMagic()
  {
    if(this.initalizedForMotionMagic == false)
    {
      double maxVelocity = Constants.talonMaximumRevolutionsPerMinute * Constants.CtreTalonFx500EncoderTicksPerRevolution / 10.0 * DriveTrain.talonFxMotorSpeedReductionFactor;

      // LEFT MOTORS!
      // FRONT
      leftFront.configFactoryDefault();
      leftFront.setNeutralMode(NeutralMode.Coast);
      leftFront.setInverted(Constants.driveMotorLeftFrontDefaultDirection);
      leftFront.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, DriveTrain.motorSettingTimeout);
      leftFront.setSensorPhase(false);
      leftFront.configNeutralDeadband(0.001, DriveTrain.motorSettingTimeout);
      leftFront.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, DriveTrain.motorSettingTimeout);
      leftFront.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, DriveTrain.motorSettingTimeout);

      /* Set the peak and nominal outputs */
      leftFront.configNominalOutputForward(0.0, DriveTrain.motorSettingTimeout);
      leftFront.configNominalOutputReverse(-0.0, DriveTrain.motorSettingTimeout);
      leftFront.configPeakOutputForward(1.0, DriveTrain.motorSettingTimeout);
      leftFront.configPeakOutputReverse(-1.0, DriveTrain.motorSettingTimeout);

      leftFront.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
      leftFront.config_kF(Constants.kSlotIdx, Constants.kGains.kF, DriveTrain.motorSettingTimeout);
      leftFront.config_kP(Constants.kSlotIdx, Constants.kGains.kP, DriveTrain.motorSettingTimeout);
      leftFront.config_kI(Constants.kSlotIdx, Constants.kGains.kI, DriveTrain.motorSettingTimeout);
      leftFront.config_kD(Constants.kSlotIdx, Constants.kGains.kD, DriveTrain.motorSettingTimeout);
      leftFront.configMotionCruiseVelocity(maxVelocity, DriveTrain.motorSettingTimeout);
      leftFront.configMotionAcceleration(maxVelocity, DriveTrain.motorSettingTimeout);

      // current limit enabled | Limit(amp) | Trigger Threshold(amp) | Trigger
      leftFront.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, 20, 25, 1.0));
/*     
      // REAR
      leftRear.configFactoryDefault();
      leftRear.setNeutralMode(NeutralMode.Coast);
      leftRear.setInverted(Constants.driveMotorLeftRearDefaultDirection);
      leftRear.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, DriveTrain.motorSettingTimeout);
      leftRear.setSensorPhase(false);
      leftRear.configNeutralDeadband(0.001, DriveTrain.motorSettingTimeout);
      leftRear.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, DriveTrain.motorSettingTimeout);
      leftRear.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, DriveTrain.motorSettingTimeout);

      // Set the peak and nominal outputs
      leftRear.configNominalOutputForward(0.0, DriveTrain.motorSettingTimeout);
      leftRear.configNominalOutputReverse(-0.0, DriveTrain.motorSettingTimeout);
      leftRear.configPeakOutputForward(1.0, DriveTrain.motorSettingTimeout);
      leftRear.configPeakOutputReverse(-1.0, DriveTrain.motorSettingTimeout);

      leftRear.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
      leftRear.config_kF(Constants.kSlotIdx, Constants.kGains.kF, DriveTrain.motorSettingTimeout);
      leftRear.config_kP(Constants.kSlotIdx, Constants.kGains.kP, DriveTrain.motorSettingTimeout);
      leftRear.config_kI(Constants.kSlotIdx, Constants.kGains.kI, DriveTrain.motorSettingTimeout);
      leftRear.config_kD(Constants.kSlotIdx, Constants.kGains.kD, DriveTrain.motorSettingTimeout);
      leftRear.configMotionCruiseVelocity(maxVelocity, DriveTrain.motorSettingTimeout);
      leftRear.configMotionAcceleration(maxVelocity, DriveTrain.motorSettingTimeout);

      // current limit enabled | Limit(amp) | Trigger Threshold(amp) | Trigger
      leftRear.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, 20, 25, 1.0));

      // make rear a follower of front
      leftRear.follow(leftFront);
      */

      // RIGHT MOTORS!
      // FRONT
      rightFront.configFactoryDefault();
      rightFront.setNeutralMode(NeutralMode.Coast);
      rightFront.setInverted(Constants.driveMotorRightFrontDefaultDirection);
      rightFront.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, DriveTrain.motorSettingTimeout);
      rightFront.setSensorPhase(false);
      rightFront.configNeutralDeadband(0.001, DriveTrain.motorSettingTimeout);
      rightFront.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, DriveTrain.motorSettingTimeout);
      rightFront.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, DriveTrain.motorSettingTimeout);

      // Set the peak and nominal outputs
      rightFront.configNominalOutputForward(0.0, DriveTrain.motorSettingTimeout);
      rightFront.configNominalOutputReverse(-0.0, DriveTrain.motorSettingTimeout);
      rightFront.configPeakOutputForward(1.0, DriveTrain.motorSettingTimeout);
      rightFront.configPeakOutputReverse(-1.0, DriveTrain.motorSettingTimeout);

      rightFront.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
      rightFront.config_kF(Constants.kSlotIdx, Constants.kGains.kF, DriveTrain.motorSettingTimeout);
      rightFront.config_kP(Constants.kSlotIdx, Constants.kGains.kP, DriveTrain.motorSettingTimeout);
      rightFront.config_kI(Constants.kSlotIdx, Constants.kGains.kI, DriveTrain.motorSettingTimeout);
      rightFront.config_kD(Constants.kSlotIdx, Constants.kGains.kD, DriveTrain.motorSettingTimeout);
      rightFront.configMotionCruiseVelocity(maxVelocity, DriveTrain.motorSettingTimeout);
      rightFront.configMotionAcceleration(maxVelocity, DriveTrain.motorSettingTimeout);

      // current limit enabled | Limit(amp) | Trigger Threshold(amp) | Trigger
      rightFront.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, 20, 25, 1.0));
/*     
      // FRONT
      rightRear.configFactoryDefault();
      rightRear.setNeutralMode(NeutralMode.Coast);
      rightRear.setInverted(Constants.driveMotorRightRearDefaultDirection);
      rightRear.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, DriveTrain.motorSettingTimeout);
      rightRear.setSensorPhase(false);
      rightRear.configNeutralDeadband(0.001, DriveTrain.motorSettingTimeout);
      rightRear.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, DriveTrain.motorSettingTimeout);
      rightRear.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, DriveTrain.motorSettingTimeout);

      // Set the peak and nominal outputs
      rightRear.configNominalOutputForward(0.0, DriveTrain.motorSettingTimeout);
      rightRear.configNominalOutputReverse(-0.0, DriveTrain.motorSettingTimeout);
      rightRear.configPeakOutputForward(1.0, DriveTrain.motorSettingTimeout);
      rightRear.configPeakOutputReverse(-1.0, DriveTrain.motorSettingTimeout);

      rightRear.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
      rightRear.config_kF(Constants.kSlotIdx, Constants.kGains.kF, DriveTrain.motorSettingTimeout);
      rightRear.config_kP(Constants.kSlotIdx, Constants.kGains.kP, DriveTrain.motorSettingTimeout);
      rightRear.config_kI(Constants.kSlotIdx, Constants.kGains.kI, DriveTrain.motorSettingTimeout);
      rightRear.config_kD(Constants.kSlotIdx, Constants.kGains.kD, DriveTrain.motorSettingTimeout);
      rightRear.configMotionCruiseVelocity(maxVelocity, DriveTrain.motorSettingTimeout);
      rightRear.configMotionAcceleration(maxVelocity, DriveTrain.motorSettingTimeout);

      // current limit enabled | Limit(amp) | Trigger Threshold(amp) | Trigger
      rightRear.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, 20, 25, 1.0));

      // make rear a follower of front
      rightRear.follow(rightFront);
*/
      this.initalizedForMotionMagic = true;
      System.out.println("initalizedForMotionMagic = true");
    }
  } 
}
 