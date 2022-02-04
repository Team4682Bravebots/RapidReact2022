// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Shooter.java
// Intent: Forms a subsystem that controls Shooter operations.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  
  private CANSparkMax topMotor = new CANSparkMax(Constants.shooterMotorLeftCanId, MotorType.kBrushless);
  private CANSparkMax bottomMotor = new CANSparkMax(Constants.shooterMotorRightCanId, MotorType.kBrushless);

  @Override
  public void setDefaultCommand(Command myCommand) {
      // TODO Auto-generated method stub
      super.setDefaultCommand(myCommand);
  }
  
  /** Creates a new Shooter. */
  public Shooter() {
    topMotor.restoreFactoryDefaults();
		bottomMotor.restoreFactoryDefaults(); 
		topMotor.setIdleMode(IdleMode.kCoast);
		bottomMotor.setIdleMode(IdleMode.kCoast);

		bottomMotor.follow(topMotor);
    bottomMotor.setInverted(true);
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void eat(){
    topMotor.set(Constants.eatSpeed);
  }

  public void barf(){
    topMotor.set(Constants.barfSpeed);
  }

  public void shootLow(){
    topMotor.set(Constants.highGoalSpeed);
  }

  public void shootHigh(){
    topMotor.set(Constants.lowGoalSpeed);
  }

  public void shooterManual(double speed){
    topMotor.set(speed);
  }
  
  public void defaultM(){
    topMotor.set(0.0);
  }
}
