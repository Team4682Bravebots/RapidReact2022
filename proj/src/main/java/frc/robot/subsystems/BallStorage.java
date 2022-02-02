// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ClimberS1.java
// Intent: Forms a subsystem that controls movements by the Jaws.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BallStorage extends SubsystemBase {

  private WPI_TalonSRX topMotor = new WPI_TalonSRX(Constants.telescopingArmsMotorLeftCanId);
  private WPI_TalonSRX bottomMotor = new WPI_TalonSRX(Constants.telescopingArmsMotorRightCanId);

  double input;

  @Override
  public void setDefaultCommand(Command myCommand) {
      // TODO Auto-generated method stub
      super.setDefaultCommand(myCommand);
  }
  
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

  public void store(){
    topMotor.set(Constants.storeSpeed);
  }

  public void retrieve(){
    topMotor.set(Constants.retrieveSpeed * -1.0);
  }

  public void defaultM(){ 
    topMotor.set(0.0);
  }


}
