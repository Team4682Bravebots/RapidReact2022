// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Shooter.java
// Intent: Forms a subsystem that controls Shooter operations.
// ************************************************************

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  
  private CANSparkMax topMotor = new CANSparkMax(Constants.telescopingArmsMotorLeftCanId, MotorType.kBrushless);
  private CANSparkMax bottomMotor = new CANSparkMax(Constants.telescopingArmsMotorRightCanId, MotorType.kBrushless);

  /** Creates a new Shooter. */
  public Shooter() {
    topMotor.restoreFactoryDefaults();
		bottomMotor.restoreFactoryDefaults(); 

		bottomMotor.follow(topMotor);
    bottomMotor.setInverted(true);
		bottomMotor.setIdleMode(IdleMode.kCoast);
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void eat(double speed){
    topMotor.set(speed);
  }

  public void barf(double speed){
    topMotor.set(speed * -1.0);
  }

  public void defualt(){
    topMotor.set(0.0);
  }
}
