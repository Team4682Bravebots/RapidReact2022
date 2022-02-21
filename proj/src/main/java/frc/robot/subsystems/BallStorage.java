// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: ClimberS1.java
// Intent: Forms a subsystem that controls movements by the Jaws.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.subsystems;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.common.*;

public class BallStorage extends SubsystemBase implements Sendable
{
  private static final int minimumBallPresentSamples = 3;

  private WPI_TalonSRX bottomMotor = new WPI_TalonSRX(Constants.ballStorageMotorBottomCanId);
  private WPI_TalonSRX topMotor = new WPI_TalonSRX(Constants.ballStorageMotorTopCanId);
  private ConsecutiveDigitalInput frontBeamBreakSensor = new ConsecutiveDigitalInput(Constants.ballStorageFrontBeamBreakSensorChannel);
  private ConsecutiveDigitalInput rearBeamBreakSensor = new ConsecutiveDigitalInput(Constants.ballStorageRearBeamBreakSensorChannel);

  private int onboardBallCount = 0;
  private int historicOnboardBallCount = 0;
  
  /**
  * No argument constructor for the BallStorage subsystem.
  */
  public BallStorage()
  {  
    topMotor.configFactoryDefault();
		bottomMotor.configFactoryDefault(); 

		bottomMotor.follow(topMotor);
    bottomMotor.setInverted(true);
		bottomMotor.setNeutralMode(NeutralMode.Brake);
    CommandScheduler.getInstance().registerSubsystem(this);
  }

  @Override
  public void periodic()
  {
    // establish the next count of onboard balls
    int nextOnboardBallCount = 0;
    if(this.isBallInFrontPosition())
    {
      ++nextOnboardBallCount;
    }
    if(this.isBallInRearPosition())
    {
      ++nextOnboardBallCount;
    }

    // atomic assignment assumed on this roborio here
    onboardBallCount = nextOnboardBallCount; 
  }

  /**
  * A method exposed to callers to retrieve the count of balls in storage
  *
  * @return Will return the count of balls that are onboard
  */
  public int getOnboardBallCount()
  {
    this.periodic();
    return onboardBallCount;
  }

  @Override
  public void initSendable(SendableBuilder builder)
  {
    builder.addDoubleProperty("OnboardBallCount", this::getOnboardBallCount, null);
    builder.addBooleanProperty("BallInFrontPosition", this::isBallInFrontPosition, null);
    builder.addBooleanProperty("BallInRearPosition", this::isBallInRearPosition, null);
    builder.addDoubleProperty("TopMotorSpeedSetting", this::getTopMotorSpeed, null);
    builder.addDoubleProperty("BottomMotorSpeedSetting", this::getBottomMotorSpeed, null);
    builder.addStringProperty("BallStorageDirection", this::getBallStorageDirection, null);
  }

  /**
  * A method exposed to callers to retrieve one ball from storage
  *
  * @return Will return true once a ball has been retrieved enough to have been ejected from the shooter
  */
  public boolean retrieve()
  {
    boolean rtnVal = false;
    this.periodic();

    // when the onboard ball count is now lower than our old 
    // onboard ball count that means we subtracted one or more
    // this implies that the retrieve operation is complete and we
    // can tell the caller that we have indeed retrieved a ball
    if(onboardBallCount < historicOnboardBallCount || onboardBallCount <= 0)
    {
      historicOnboardBallCount = onboardBallCount;
      topMotor.set(0.0);
      rtnVal = true;
    }
    else
    {
      // since motors are followers ok to just set one
      topMotor.set(Constants.ballRetrieveSpeed);
    }

    return rtnVal;
  }

  /**
  * A method exposed to callers to store one ball into storage
  *
  * @return Will return true once a ball has been consumed into storage otherwise the motors will keep attempting to store
  */
  public boolean store()
  {
    boolean rtnVal = false;
    this.periodic();

    // when the onboard ball count is now higher than our old 
    // onboard ball count that means we added one or more
    // this implies that the storeage operation is complete and we
    // can tell the caller that we have indeed stored a ball
    if(onboardBallCount > historicOnboardBallCount || onboardBallCount >= Constants.maximumStoredBallCount)
    {
      historicOnboardBallCount = onboardBallCount;
      topMotor.set(0.0);
      rtnVal = true;
    }
    else
    {
      // since motors are followers ok to just set one
      topMotor.set(Constants.ballStoreSpeed);
    }

    return rtnVal;
  }

  @Override
  public void setDefaultCommand(Command myCommand)
  {
      // TODO Auto-generated method stub
      super.setDefaultCommand(myCommand);
  }

  /**
  * A method exposed to callers to cause motors to drive belts in storage direction
  */
  public void storeBallManual()
  {
      // since motors are followers ok to just set one
      topMotor.set(Constants.ballStoreSpeed);
  }

  /**
  * A method exposed to callers to cause motors to stop
  */
  public void stopBallManual()
  {
      // since motors are followers ok to just set one
      topMotor.set(0.0);
  }

  /**
  * A method exposed to callers to cause motors to drive belts in retrieve direction
  */
  public void retrieveBallManual()
  {
      // since motors are followers ok to just set one
      topMotor.set(Constants.ballStoreSpeed);
  }

  /**
   * Gets the top motor speed setting
   * @return the top motor controller output as decmil fraction
   */
  private double getTopMotorSpeed()
  {
    return topMotor.getMotorOutputPercent() / 100.0;
  }

  /**
   * Gets the bottom motor speed setting
   * @return the bottom motor controller output as decmil fraction
   */
  private double getBottomMotorSpeed()
  {
    return bottomMotor.getMotorOutputPercent() / 100.0;
  }

  /**
   * To get a string that represents what direction the ball is moving in
   * @return a string representing the ball storage movement direction
   */
  private String getBallStorageDirection()
  {
    double topMotorSpeed = this.getTopMotorSpeed();
    double bottomMotorSpeed = this.getTopMotorSpeed();
    if(topMotorSpeed == 0.0 && bottomMotorSpeed == 0.0)
    {
      return "Stopped";
    }
    else if((Constants.ballRetrieveSpeed > 0.0 && topMotorSpeed > 0.0) || 
      (Constants.ballRetrieveSpeed < 0.0 && topMotorSpeed < 0.0))
    {
      return "Retrieving";
    }
    else if((Constants.ballStoreSpeed > 0.0 && topMotorSpeed > 0.0) || 
      (Constants.ballStoreSpeed < 0.0 && topMotorSpeed < 0.0))
    {
      return "Storing";
    }
    else
    {
      if(topMotorSpeed > 0.1 && bottomMotorSpeed > 0.1 ||
        topMotorSpeed < -0.1 && bottomMotorSpeed < -0.1)
      {
        return "VERY INVALID";
      }
      else
      {
        return "INVALID";
      }
    }
  }

  private boolean isBallInFrontPosition()
  {
    // Gets the value of the digital input.  Returns true if the circuit is open.
    return (frontBeamBreakSensor.get() &&
      frontBeamBreakSensor.getStatusCount() >= BallStorage.minimumBallPresentSamples);
  }

  private boolean isBallInRearPosition()
  {
    // Gets the value of the digital input.  Returns true if the circuit is open.
    return (rearBeamBreakSensor.get() &&
      rearBeamBreakSensor.getStatusCount() >= BallStorage.minimumBallPresentSamples);
  }

}
