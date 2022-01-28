// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ClimberS1.java
// Intent: Forms a subsystem that controls movements by the Jaws.
// ************************************************************

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BallStorage extends SubsystemBase {

  private WPI_TalonSRX topMotor = new WPI_TalonSRX(Constants.telescopingArmsMotorLeftCanId);
  private WPI_TalonSRX bottomMotor = new WPI_TalonSRX(Constants.telescopingArmsMotorRightCanId);

  double imput;

   /** Creates a new climberS1. */
  public BallStorage() {

    topMotor.configFactoryDefault();
		bottomMotor.configFactoryDefault(); 

		bottomMotor.follow(topMotor);
    bottomMotor.setInverted(true);
		bottomMotor.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void store(double speed){
    topMotor.set(speed);
  }

  public void retrieve(double speed){
    topMotor.set(speed * -1.0);
  }

  public void defualt(){
    topMotor.set(0.0);
  }
}
