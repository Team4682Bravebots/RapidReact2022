// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: DriveTrainAuto.java
// Intent: Forms model for the DriveTrain subsystem that has working autonomous aspects.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import java.io.Console;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.motorcontrol.*;

import frc.robot.Constants;
import frc.robot.OnboardInputInterfaces;
import frc.robot.common.MotorUtils;

// see ramsetecontroller
// https://github.com/wpilibsuite/allwpilib/tree/main/wpilibjExamples/src/main/java/edu/wpi/first/wpilibj/examples/ramsetecontroller

// https://docs.wpilib.org/en/stable/docs/software/kinematics-and-odometry/differential-drive-kinematics.html
// https://docs.wpilib.org/en/stable/docs/software/kinematics-and-odometry/differential-drive-odometry.html

// javadoc for below https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/package-summary.html
import edu.wpi.first.math.kinematics.*;
// javadoc for below https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/math/geometry/package-summary.html
import edu.wpi.first.math.geometry.*;

public class DriveTrainAuto extends SubsystemBase implements Sendable
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
  private DifferentialDrive drive = new DifferentialDrive(left, right);

  // TODO - get this info from Greyson / John
  private static final double robotTrackWidthInches = 22.5;
  private static final double halfRobotTrackWidthInches = robotTrackWidthInches / 2;

  private static final double wheelDiameterInches = 6.0;
  private static final double effectiveWheelMotorGearBoxRatio = (40.0 / 12.0) * (40.0 / 14.0);

  private static final int motorSettingTimeout = 0; //Constants.kTimeoutMs;

  private boolean motionMagicRunning = false;
  private boolean initalizedForMotionMagic = true;

  private DifferentialDriveOdometry currentOdometry = null;
  private Pose2d currentPosition = null;
  private OnboardInputInterfaces onboardInput = null;

  /**
  * No argument constructor for the DriveTrain subsystem.
  */
  public DriveTrainAuto(OnboardInputInterfaces interfaces)
  {
    this.forceSensorReset();
    CommandScheduler.getInstance().registerSubsystem(this);

    onboardInput = interfaces;
    currentPosition = new Pose2d(5.0, 13.5, new Rotation2d());
    currentOdometry = new DifferentialDriveOdometry(
        Rotation2d.fromDegrees(-1.0 * onboardInput.getAccumulatedYawAngle()),
        currentPosition);
  }

  /**arcade
  * A method to take in x and y stick inputs and turn them into right and left motor speeds
  * considering arcade style driving
  *
  * @param  yAxisValue - left motor speed, range -1.0 to 1.0 where positive values are forward
  * @param  xAxisValue - right motor speed, range -1.0 to 1.0 where positive values are forward
  */
  public void arcadeDrive(double yAxisValue, double xAxisValue)
  {
    drive.arcadeDrive(xAxisValue, yAxisValue);
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

  @Override
  public void periodic()
  {
    var gyroAngle = Rotation2d.fromDegrees(-1.0 * onboardInput.getAccumulatedYawAngle())
    // Update the pose
    currentPosition =  currentOdometry.update(gyroAngle, m_leftEncoder.getDistance(), m_rightEncoder.getDistance());
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

  private double getEncoderUnitsFromTrackDistanceInInches(double wheelTrackDistanceInches)
  {
    return (wheelTrackDistanceInches / (Math.PI * DriveTrainAuto.wheelDiameterInches)) * Constants.CtreTalonFx500EncoderTicksPerRevolution * DriveTrainAuto.effectiveWheelMotorGearBoxRatio;
  }

  private double getTrackDistanceInInchesFromEncoderUnits(double encoderUnits)
  {
    return (encoderUnits / Constants.CtreTalonFx500EncoderTicksPerRevolution / DriveTrainAuto.effectiveWheelMotorGearBoxRatio) * (Math.PI * DriveTrainAuto.wheelDiameterInches);
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
      leftRear.follow(leftFront);
      rightFront.configFactoryDefault();
      rightRear.configFactoryDefault();
      rightRear.follow(rightFront);
      this.initalizedForMotionMagic = false;
    }
  }

}
 