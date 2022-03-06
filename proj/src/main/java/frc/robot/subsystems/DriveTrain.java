// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: DriveTrain.java
// Intent: Forms model for the DriveTrain subsystem.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

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
  // four matched motors - two for each tank drive side
  private WPI_TalonFX leftFront = new WPI_TalonFX(Constants.driveMotorLeftFrontCanId);
  private WPI_TalonFX leftRear = new WPI_TalonFX(Constants.driveMotorLeftRearCanId);
  private WPI_TalonFX rightFront = new WPI_TalonFX(Constants.driveMotorRightFrontCanId);
  private WPI_TalonFX rightRear = new WPI_TalonFX(Constants.driveMotorRightRearCanId);
  private MotorControllerGroup left = new MotorControllerGroup(leftFront, leftRear);
  private MotorControllerGroup right = new MotorControllerGroup(rightFront, rightRear);
  private DifferentialDrive drive = new DifferentialDrive(left, right);

  // TODO - get this info from Greyson / John
  private static final double robotTrackWidthInches = 21.7;
  private static final double halfRobotTrackWidthInches = robotTrackWidthInches / 2;

  private static final double wheelDiameterInches = 6.0;
  private static final double effectiveWheelMotorGearBoxRatio = (40.0 / 12.0) * (40.0 / 14.0);

  private static final int motorSettingTimeout = 0; //Constants.kTimeoutMs;
  private static final double halfRotationEncoderTicks = Constants.CtreTalonFx500EncoderTicksPerRevolution / 2;

  private boolean motionMagicRunning = false;
  private double lastMotionMagicTargetError = halfRotationEncoderTicks;
  private boolean initalizedForMotionMagic = false;

  /**
  * No argument constructor for the DriveTrain subsystem.
  */
  public DriveTrain()
  {
    this.forceSensorReset();
    CommandScheduler.getInstance().registerSubsystem(this);
  }

  /**
  * A method to take in x and y stick inputs and turn them into right and left motor speeds
  * considering arcade style driving
  *
  * @param  yAxisValue - left motor speed, range -1.0 to 1.0 where positive values are forward
  * @param  xAxisValue - right motor speed, range -1.0 to 1.0 where positive values are forward
  */
  public void arcadeDrive(double yAxisValue, double xAxisValue)
  {
    this.initializeMotorsDirectDrive();
    drive.arcadeDrive(yAxisValue, xAxisValue);
  }

  /**
   * A method to update the sensors on this device
   */
  public void forceSensorReset()
  {
    this.initializeMotorsMotionMagic();
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
   * @return
   */
  public boolean isCurrentlyPerformingDriveMovement()
  {
    return motionMagicRunning;
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

    // sets motors to input 
    this.moveWheelsDistance(leftDistanceInches, rightDistanceInches, targetTimeSeconds);
  }

  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
    if(leftRear.getControlMode() == ControlMode.MotionMagic && rightRear.getControlMode() == ControlMode.MotionMagic)
    {
      if(Math.abs(leftRear.getClosedLoopError()) < lastMotionMagicTargetError &&
        Math.abs(rightRear.getClosedLoopError()) < lastMotionMagicTargetError)
      {
        this.initializeMotorsDirectDrive();
        leftFront.set(ControlMode.PercentOutput, 0.0);
        rightFront.set(ControlMode.PercentOutput, 0.0);
        motionMagicRunning = false;
      }
    }
    else
    {
      motionMagicRunning = false;
    }
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
    System.out.println("START moveWheelsDistance!");
    double leftDeltaEncoderTicks = this.getEncoderUnitsFromTrackDistanceInInches(leftWheelDistanceInches);
    double rightDeltaEncoderTicks = this.getEncoderUnitsFromTrackDistanceInInches(rightWheelDistanceInches);   

    /*
    // TODO - with trapazoidal, this won't be fast enough - more work here for sure ...
    double leftAverageVelocityEncoderTicksPer100Milliseconds = leftDeltaEncoderTicks/(targetTimeInSeconds*1000);
    double rightAverageVelocityEncoderTicksPer100Milliseconds = rightDeltaEncoderTicks/(targetTimeInSeconds*1000);

    // Motion Magic Configurations
    leftFront.configMotionAcceleration(leftAverageVelocityEncoderTicksPer100Milliseconds, Constants.kTimeoutMs);
		leftFront.configMotionCruiseVelocity(leftAverageVelocityEncoderTicksPer100Milliseconds, Constants.kTimeoutMs);
    rightFront.configMotionAcceleration(rightAverageVelocityEncoderTicksPer100Milliseconds, Constants.kTimeoutMs);
		rightFront.configMotionCruiseVelocity(rightAverageVelocityEncoderTicksPer100Milliseconds, Constants.kTimeoutMs);
    */

    // get ready for error calc
    double onePercentError = (Math.abs(leftDeltaEncoderTicks) + Math.abs(rightDeltaEncoderTicks)) / 2 * 0.01;
    double futureLastMotitionMagicError = onePercentError > halfRotationEncoderTicks ? halfRotationEncoderTicks : onePercentError;

    // move it with motion magic
    leftFront.set(ControlMode.MotionMagic, leftDeltaEncoderTicks + this.getAverageLeftMotorEncoderPosition());
    rightFront.set(ControlMode.MotionMagic, rightDeltaEncoderTicks + this.getAverageRightMotorEncoderPosition());
    lastMotionMagicTargetError =  futureLastMotitionMagicError;
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

  private void initializeMotorsMotionMagic()
  {
    /*
    if(this.initalizedForMotionMagic == false)
    {
      leftFront.configFactoryDefault();
      leftRear.configFactoryDefault();
      rightFront.configFactoryDefault();
      rightRear.configFactoryDefault();
        
      // setup each side with a follower
      leftRear.follow(leftFront);
      rightRear.follow(rightFront);

      // LEFT FRONT LEADER
      // setup the inverted values for each motor
      leftFront.setInverted(Constants.driveMotorLeftFrontDefaultDirection);
      leftFront.setNeutralMode(NeutralMode.Brake);

      leftFront.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, DriveTrain.motorSettingTimeout);
      leftFront.setSensorPhase(false);
      leftFront.configNeutralDeadband(0.001, DriveTrain.motorSettingTimeout);
      leftFront.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, DriveTrain.motorSettingTimeout);
      leftFront.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, DriveTrain.motorSettingTimeout);

      // Set the peak and nominal outputs
      leftFront.configNominalOutputForward(0.0, DriveTrain.motorSettingTimeout);
      leftFront.configNominalOutputReverse(-0.0, DriveTrain.motorSettingTimeout);
      leftFront.configPeakOutputForward(1.0, DriveTrain.motorSettingTimeout);
      leftFront.configPeakOutputReverse(-1.0, DriveTrain.motorSettingTimeout);

      leftFront.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
      leftFront.config_kF(Constants.kSlotIdx, Constants.kGains.kF, DriveTrain.motorSettingTimeout);
      leftFront.config_kP(Constants.kSlotIdx, Constants.kGains.kP, DriveTrain.motorSettingTimeout);
      leftFront.config_kI(Constants.kSlotIdx, Constants.kGains.kI, DriveTrain.motorSettingTimeout);
      leftFront.config_kD(Constants.kSlotIdx, Constants.kGains.kD, DriveTrain.motorSettingTimeout);

      leftFront.configMotionCruiseVelocity(25000, DriveTrain.motorSettingTimeout);
      leftFront.configMotionAcceleration(10000, DriveTrain.motorSettingTimeout);

      // current limit enabled | Limit(amp) | Trigger Threshold(amp) | Trigger
      leftFront.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, 20, 25, 1.0));

      // RIGHT FRONT LEADER
      // setup the inverted values for each motor
      rightFront.setInverted(Constants.driveMotorRightFrontDefaultDirection);
      rightFront.setNeutralMode(NeutralMode.Brake);

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

      rightFront.configMotionCruiseVelocity(25000, DriveTrain.motorSettingTimeout);
      rightFront.configMotionAcceleration(10000, DriveTrain.motorSettingTimeout);

      this.initalizedForMotionMagic = true;
      System.out.println("initalizedForMotionMagic = true");
    }
    */
  }

  private void initializeMotorsDirectDrive()
  {
    if(this.initalizedForMotionMagic == true)
    {
      leftFront.configFactoryDefault();
      leftRear.configFactoryDefault();
      rightFront.configFactoryDefault();
      rightRear.configFactoryDefault();
  
      // setup the inverted values for each motor
      leftFront.setInverted(Constants.driveMotorLeftFrontDefaultDirection);
      leftRear.setInverted(Constants.driveMotorLeftRearDefaultDirection);
      rightFront.setInverted(Constants.driveMotorRightFrontDefaultDirection);
      rightRear.setInverted(Constants.driveMotorRightRearDefaultDirection);

      // setup each side with a follower
      leftRear.follow(leftFront);
      rightRear.follow(rightFront);
  
      this.initalizedForMotionMagic = false;
      System.out.println("initalizedForMotionMagic = false");
    }
  }
 
}
 