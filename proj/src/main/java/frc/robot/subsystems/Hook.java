// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hook extends SubsystemBase {

	private WPI_TalonFX left = new WPI_TalonFX(Constants.FrontClimbersMotorLeft);
	private WPI_TalonFX right = new WPI_TalonFX(Constants.FrontClimbersMotorRight);
  /*
	 * Talon FX has 2048 units per revolution
	 * 
	 * https://phoenix-documentation.readthedocs.io/en/latest/ch14_MCSensor.html#sensor-resolution
	 */
	final int kUnitsPerRevolution = 2048; /* this is constant for Talon FX */

   /** Creates a new FrontClimbers. */
  public Hook() {

    left.configFactoryDefault();
		right.configFactoryDefault(); 

		right.follow(left);
		right.setNeutralMode(NeutralMode.Brake);
		right.configOpenloopRamp(0.2, 0);

		left.setNeutralMode(NeutralMode.Brake);
		left.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		left.overrideLimitSwitchesEnable(true);
		left.config_kP(0, 5, 10);
		left.config_kD(0, 4000, 10);
		left.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
		left.configMotionCruiseVelocity(0.01, 0); 
		left.configMotionAcceleration(0.01, 0);
		
		//current limit                                                       enabled | Limit(amp) | Trigger Threshold(amp) | Trigger Threshold Time(s)  */
		left.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(      true,       20,                25,                         1.0));

    //left.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);



  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public double getPosition(){
    double selSenPos = left.getSelectedSensorPosition(0);
    return selSenPos;
  }

  public double getVelocity(){
    double selSenVel = left.getSelectedSensorVelocity(0);
    return selSenVel;
  }




}
