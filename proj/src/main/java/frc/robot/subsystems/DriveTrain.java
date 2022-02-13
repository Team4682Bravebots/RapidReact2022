// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: DriveTrain.java
// Intent: Forms model for the DriveTrain subsystem.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.motorcontrol.*;

import frc.robot.Constants;
import frc.robot.common.MotorUtils;

public class DriveTrain extends SubsystemBase
{
  // four matched motors - two for each tank drive side
  private WPI_TalonFX leftFront = new WPI_TalonFX(Constants.driveMotorLeftFrontCanId);
  private WPI_TalonFX leftRear = new WPI_TalonFX(Constants.driveMotorLeftRearCanId);
  private WPI_TalonFX rightFront = new WPI_TalonFX(Constants.driveMotorRightFrontCanId);
  private WPI_TalonFX rightRear = new WPI_TalonFX(Constants.driveMotorRightRearCanId);
  private MotorControllerGroup left = new MotorControllerGroup(leftFront, leftRear);
  private MotorControllerGroup right = new MotorControllerGroup(rightFront, rightRear);
  private DifferentialDrive drive = new DifferentialDrive(left, right);

  // TODO - get this info from Greyson / Adenine
  private static final double robotTrackWidthInches = 28.7;
  private static final double halfRobotTrackWidthInches = robotTrackWidthInches / 2;
  private static final double wheelDiameterInches = 6.0;
  private static final double effectiveWheelMotorGearBoxRatio = 100.0;
  private static final double halfRotationEncoderTicks = Constants.CtreTalonFx500EncoderTicksPerRevolution / 2;

  private boolean motionMagicRunning = false;
  private double lastMotionMagicTargetError = halfRotationEncoderTicks;

  /**
  * No argument constructor for the DriveTrain subsystem.
  */
  public DriveTrain()
  {
    this.initializeMotors();
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
    drive.arcadeDrive(yAxisValue, xAxisValue);
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
    moveWheelsDistance(leftDistanceInches, rightDistanceInches, targetTimeSeconds);
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
        leftRear.set(ControlMode.PercentOutput, 0.0);
        rightRear.set(ControlMode.PercentOutput, 0.0);
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
    double leftDeltaEncoderTicks = getEncoderUnitsFromTrackDistanceInInches(leftWheelDistanceInches);
    double rightDeltaEncoderTicks = getEncoderUnitsFromTrackDistanceInInches(rightWheelDistanceInches);   

    // TODO - with trapazoidal, this won't be fast enough - more work here for sure ...
    double leftAverageVelocityEncoderTicksPer100Milliseconds = leftDeltaEncoderTicks/(targetTimeInSeconds*1000);
    double rightAverageVelocityEncoderTicksPer100Milliseconds = rightDeltaEncoderTicks/(targetTimeInSeconds*1000);

    // Motion Magic Configurations
    leftRear.configMotionAcceleration(leftAverageVelocityEncoderTicksPer100Milliseconds, Constants.kTimeoutMs);
		leftRear.configMotionCruiseVelocity(leftAverageVelocityEncoderTicksPer100Milliseconds, Constants.kTimeoutMs);
    rightRear.configMotionAcceleration(rightAverageVelocityEncoderTicksPer100Milliseconds, Constants.kTimeoutMs);
		rightRear.configMotionCruiseVelocity(rightAverageVelocityEncoderTicksPer100Milliseconds, Constants.kTimeoutMs);

    // move it with motion magic
    leftRear.set(ControlMode.MotionMagic, leftDeltaEncoderTicks + this.getAverageLeftMotorEncoderPosition());
    rightRear.set(ControlMode.MotionMagic, rightDeltaEncoderTicks + this.getAverageRightMotorEncoderPosition());

    double onePercentError = (Math.abs(leftDeltaEncoderTicks) + Math.abs(rightDeltaEncoderTicks)) / 2 * 0.01;
    lastMotionMagicTargetError =  onePercentError > halfRotationEncoderTicks ? halfRotationEncoderTicks : onePercentError;
    motionMagicRunning = true;
  }

  private double getEncoderUnitsFromTrackDistanceInInches(double wheelTrackDistanceInches)
  {
    return (wheelTrackDistanceInches / (Math.PI * DriveTrain.wheelDiameterInches)) * Constants.CtreTalonFx500EncoderTicksPerRevolution * DriveTrain.effectiveWheelMotorGearBoxRatio;
  }

  private double getAverageLeftMotorEncoderPosition()
  {
    return (leftRear.getSelectedSensorPosition() + leftFront.getSelectedSensorPosition()) / 2;
  }

  private double getAverageRightMotorEncoderPosition()
  {
    return (rightRear.getSelectedSensorPosition() + rightFront.getSelectedSensorPosition()) / 2;
  }

  private void initializeMotors()
  {
    leftFront.configFactoryDefault();
    leftRear.configFactoryDefault();
    rightFront.configFactoryDefault();
    rightRear.configFactoryDefault();

    // setup each side with a follower
    leftRear.follow(leftFront);
    rightRear.follow(rightFront);

    // setup the inverted values for each motor
    leftFront.setInverted(Constants.driveMotorLeftFrontInverted);
    leftRear.setInverted(Constants.driveMotorLeftRearInverted);
    rightFront.setInverted(Constants.driveMotorRightFrontInverted);
    rightRear.setInverted(Constants.driveMotorRightRearInverted);
    
    // setup the various settings for the motors
    leftFront.setNeutralMode(NeutralMode.Brake);
		leftFront.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		leftFront.overrideLimitSwitchesEnable(true);
		leftFront.config_kP(0, 5, 10); // TODO - fix these magic numbers!!!
		leftFront.config_kD(0, 4000, 10); // TODO - fix these magic numbers!!!
		leftFront.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
		leftFront.configMotionCruiseVelocity(0.01, 0); // TODO - fix these magic numbers!!!
		leftFront.configMotionAcceleration(0.01, 0); // TODO - fix these magic numbers!!!
    leftFront.configStatorCurrentLimit(
      new StatorCurrentLimitConfiguration(
        true, // enabled | 
        20, // Limit(amp) |
        25, // Trigger Threshold(amp) |
        1.0)); // Trigger Threshold Time(s)
    //leftFront.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

		rightFront.setNeutralMode(NeutralMode.Brake);
		rightFront.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		rightFront.overrideLimitSwitchesEnable(true);
		rightFront.config_kP(0, 5, 10);
		rightFront.config_kD(0, 4000, 10);
		rightFront.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
		rightFront.configMotionCruiseVelocity(0.01, 0); 
		rightFront.configMotionAcceleration(0.01, 0);	
    rightFront.configStatorCurrentLimit(
      new StatorCurrentLimitConfiguration(
        true, // enabled | 
        20, // Limit(amp) |
        25, // Trigger Threshold(amp) |
        1.0)); // Trigger Threshold Time(s)
    //rightFront..configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
  }
 
}
 