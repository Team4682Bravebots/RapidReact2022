// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: TelescopingArm.java
// Intent: Forms a subsystem that controls TelescopingArm operations.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMax.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TelescopingArms extends SubsystemBase
{
  // two matched motors - one for each climber side
  private CANSparkMax left = new CANSparkMax(Constants.telescopingArmsMotorLeftCanId, MotorType.kBrushless);
  private CANSparkMax right = new CANSparkMax(Constants.telescopingArmsMotorRightCanId, MotorType.kBrushless);
  private SparkMaxPIDController rightPidController;
  private SparkMaxPIDController leftPidController;
  private RelativeEncoder rightEncoder;
  private RelativeEncoder leftEncoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
  
  /*
	 * Talon FX has 2048 units per revolution
	 * 
	 * https://phoenix-documentation.readthedocs.io/en/latest/ch14_MCSensor.html#sensor-resolution
	 */
	final int kUnitsPerRevolution = 2048; /* this is constant for Talon FX */

   /** Creates a new FrontClimbers. */
  public TelescopingArms()
  {
    left.restoreFactoryDefaults();
		right.restoreFactoryDefaults(); 

		right.follow(left);
		right.setIdleMode(IdleMode.kBrake);

    // initialze PID controller and encoder objects
    leftPidController = left.getPIDController();
    rightPidController = right.getPIDController();
    leftEncoder = left.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 1);
    rightEncoder = left.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 1);
    leftEncoder.setPosition(0.0);
    rightEncoder.setPosition(0.0);

    // PID coefficients
    kP = 5e-5; 
    kI = 1e-6;
    kD = 0; 
    kIz = 0; 
    kFF = 0.000156; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 5700;

    // Smart Motion Coefficients
    maxVel = 2000; // rpm
    maxAcc = 1500;

    // set PID coefficients
    leftPidController.setP(kP);
    leftPidController.setI(kI);
    leftPidController.setD(kD);
    leftPidController.setIZone(kIz);
    leftPidController.setFF(kFF);
    leftPidController.setOutputRange(kMinOutput, kMaxOutput);

    int smartMotionSlot = 0;
    leftPidController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
    leftPidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
    leftPidController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
    leftPidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
    
    // set PID coefficients
    rightPidController.setP(kP);
    rightPidController.setI(kI);
    rightPidController.setD(kD);
    rightPidController.setIZone(kIz);
    rightPidController.setFF(kFF);
    rightPidController.setOutputRange(kMinOutput, kMaxOutput);

    rightPidController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
    rightPidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
    rightPidController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
    rightPidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
  }

  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
  }

  public double getPosition()
  {
    double selSenPos = leftEncoder.getPosition();
    // TODO
    return selSenPos;
  }

  public double getVelocity()
  {
    double selSenVel = leftEncoder.getVelocity();
    // TODO
    return selSenVel;
  }

  public void setClimberPostion(double targetPos)
  {
    leftPidController.setReference(targetPos, ControlType.kSmartMotion);
    // TODO
  }

  public void setInverted()
  {
    left.setInverted(true);
    // TODO
  }
}
