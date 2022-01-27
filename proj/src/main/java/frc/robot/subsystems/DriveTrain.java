// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: DriveTrain.java
// Intent: Forms model for the DriveTrain subsystem.
// ************************************************************

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase
{
  // four matched motors - two for each tank drive side
  private WPI_TalonFX leftFront = new WPI_TalonFX(Constants.driveMotorLeftFrontCanId);
  private WPI_TalonFX leftRear = new WPI_TalonFX(Constants.driveMotorLeftRearCanId);
  private WPI_TalonFX rightFront = new WPI_TalonFX(Constants.driveMotorRightFrontCanId);
  private WPI_TalonFX rightRear = new WPI_TalonFX(Constants.driveMotorRightRearCanId);

  // ctor
  public DriveTrain()
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

  public void driveControll(double leftSpeed, double rightSpeed)
  {
    //sets motors to imput speeds (sets to conroll motor and conquencly follower motor)
    leftRear.set(leftSpeed);
    rightRear.set(rightSpeed);
  }

  public void defualt(){
    leftRear.set(0);
    rightRear.set(0);
  }
  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
  }
}
 