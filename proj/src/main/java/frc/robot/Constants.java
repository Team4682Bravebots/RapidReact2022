// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: Constants.java
// Intent: Forms all constants for the robot.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot;

import frc.robot.*;
import frc.robot.common.Gains;
import frc.robot.common.RoboRioOrientation;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{
  public static final double DegreesPerRevolution = 360.0;
  // Talon maximum RPM 
  public static final double neoMaximumRevolutionsPerMinute = 6380;
  // Talon maximum RPM 
  public static final double talonMaximumRevolutionsPerMinute = 6380;
  // this uses the on-motor quadratrue encoder
  // see: https://www.vexrobotics.com/pro/falcon-500 where it says "2048 CPR encoder"
  public static final double CtreTalonFx500EncoderTicksPerRevolution = 2048; 
  // this uses the halls effect sensor when plugged into the spark max
  // see: https://www.revrobotics.com/rev-21-1650/ where it says "42 counts per rev."
  public static final double RevNeoEncoderTicksPerRevolution = 42; 

  // shooter 
  // inverts - see: https://docs.ctre-phoenix.com/en/stable/ch13_MC.html#talon-fx-specific-inverts
  public static TalonFXInvertType shooterBottomMotorDefaultDirection = TalonFXInvertType.CounterClockwise;
  public static TalonFXInvertType shooterTopMotorDefaultDirection = TalonFXInvertType.CounterClockwise;

  // Motor magic numbers
  public static final double bottomMotorForwardLowGoalSpeed = 0.8;
  public static final double topMotorForwardLowGoalSpeed = 0.7;
  public static final double bottomMotorForwardHighGoalSpeed = 1.0;
  public static final double topMotorForwardHighGoalSpeed = 0.95;
  public static final double bottomMotorReverseHighGoalSpeed = 1.0;
  public static final double topMotorReverseHighGoalSpeed = 0.95;
  public static final double bottomMotorIntakeSpeed = -0.5;
  public static final double topMotorIntakeSpeed = -0.4;

  // Jaws reach points \\
  public static final double jawsReverseHighGoalPositionAngle = 115.0;
  public static final double jawsIntakePositionAngle = 0.0;
  public static final double jawsLowGoalPositionAngle = 40.0;
  public static final double jawsHighGoalPositionAngle = 65.0; 
  public static final double jawsPositionAngleTolerance = 1.2;
  public static final double jawsAngleArmsEngagePositionAngle = 81.0;
  public static final double jawsAngleArmsEngagePositionTolerance = 0.9;

  // telescoping arms reach points \\
  public static final double telescopingArmsRetractHeightInches = 0.33;
  public static final double telescopingArmsMediumExtendHeightInches = 30.0;
  public static final double telescopingArmsHighExtendHeightInches = 25.0;
  public static final double telescopingArmsToleranceInches = 0.10;

  // Ball storage \\
  public static final double ballStoreSpeed = -0.4;
  public static final double ballRetrieveSpeed = 0.4;
  public static final int maximumStoredBallCount = 2;

  // CAN BUS NUMBERS \\
  //TODO - P0 - fill in proper CAN bus index for Jaws motors
  public static int jawsMotorRightCanId = 9;
  public static int jawsMotorLeftCanId = 10;

  //TODO - P0 - fill in proper CAN bus index for telescoping arm motors
  public static int telescopingArmsMotorLeftCanId = 13;
  public static int telescopingArmsMotorRightCanId = 14;

  //TODO - P0 - fill in proper CAN bus index for shooting motors
  public static final int shooterMotorBottomCanId = 9;
  public static final int shooterMotorTopCanId = 10;

  //TODO - P0 - fill in proper CAN bus index for ball storage motors
  public static int ballStorageMotorTopCanId = 15;
  public static int ballStorageMotorBottomCanId = 16;

  //TODO - P0 - fill in proper CAN bus index for drive motors
  public static int driveMotorLeftFrontCanId = 7;
  public static int driveMotorLeftRearCanId = 8;
  public static int driveMotorRightFrontCanId = 11;
  public static int driveMotorRightRearCanId = 12;

  // Pneumatics Control Module
  public static final PneumaticsModuleType robotPneumaticsControlModuleType = PneumaticsModuleType.CTREPCM;
  public static final int ctrePneumaticsControlModuleCanId = 0;
  public static final int revPneumaticsControlModuleCanId = 1;
  public static final int robotPneumaticsControlModuleCanId = 
    robotPneumaticsControlModuleType == PneumaticsModuleType.CTREPCM ? ctrePneumaticsControlModuleCanId : revPneumaticsControlModuleCanId;
  public static final int bothChassisAngleArmSolenoidForwardChannel = 0;
  public static final int bothChassisAngleArmSolenoidReverseChannel = 1;
  public static final int bothJawsAngleArmSolenoidForwardChannel = 4;
  public static final int bothJawsAngleArmSolenoidReverseChannel = 5;
  public static final int jawsClutchSolenoidForwardChannel = 6;
  public static final int jawsClutchSolenoidReverseChannel = 7;

  // Digital Input Channel Numbers
  public static final int jawsIntakeStopLimitSwitchChannel = 0;
  public static final int ballStorageFrontBeamBreakSensorChannel = 1;
  public static final int ballStorageRearBeamBreakSensorChannel = 2;

  // MOTOR SETTINGS \\

  // Drive motor magic numbers
  // inverts - see: https://docs.ctre-phoenix.com/en/stable/ch13_MC.html#talon-fx-specific-inverts
  public static TalonFXInvertType driveMotorLeftFrontDefaultDirection = TalonFXInvertType.CounterClockwise;
  public static TalonFXInvertType driveMotorLeftRearDefaultDirection = TalonFXInvertType.FollowMaster;
  public static TalonFXInvertType driveMotorRightFrontDefaultDirection = TalonFXInvertType.Clockwise;
  public static TalonFXInvertType driveMotorRightRearDefaultDirection = TalonFXInvertType.FollowMaster;

  // jaws arm motors clockwise is elevate
  public static TalonFXInvertType jawsLeftMotorDefaultDirection = TalonFXInvertType.FollowMaster;
  public static TalonFXInvertType jawsRightMotorDefaultDirection = TalonFXInvertType.Clockwise;

  // TIMING AND SPEEDS \\
  // AngleArm timing \\
  public static final double AngleArmTimingSeconds = 0.3;

  // BallStorage timing \\
  public static final double BallStorageStoreTimingSeconds = 1.0;
  public static final double BallStorageRetrieveTimingSeconds = 0.3;

  // HIDS \\
  // hid ports \\ 
  public static int portDriverController = 0;
  public static int portCoDriverController = 1;

  // util \\ //TODO if needed 
  public static final int kPIDLoopIdx = 0;
  public static final int kTimeoutMs = 30;
  public static final int kSlotIdx = 0;

  // gains \\
  public static final Gains kGains = new Gains(0.2, 0.0, 0.0, 0.2, 0, 1.0);
  public static final int countPerRevHallSensor = 42;
  public static final int buttonBoardPort = 0;

  // orentation
  public static final RoboRioOrientation roboRioOrientation = RoboRioOrientation.RelayForward;

}
