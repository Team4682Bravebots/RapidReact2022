// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: ManualInputInterfaces.java
// Intent: Forms a class that grants access to driver controlled inputs.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.builders.ClimbCommandBuilder;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class ManualInputInterfaces
{
  // sets joystick variables to joysticks
  private XboxController driverController = new XboxController(Constants.portDriverController); 
  private XboxController coDriverController = new XboxController(Constants.portCoDriverController);
  private Joystick buttonBoard = new Joystick(Constants.buttonBoardPort);

  // subsystems needed for inputs
  private SubsystemCollection subsystemCollection = null;

  /**
   * The constructor to build this 'manual input' conduit
   */
  public ManualInputInterfaces(SubsystemCollection currentCollection)
  {
    subsystemCollection = currentCollection;
  }

  /**
   * A method to get the arcade drive X componet being input from humans
   * @return - a double value associated with the magnitude of the x componet
   */
  public double getInputArcadeDriveX()
  {
    return driverController.getLeftX();
  }

  /**
   * A method to get the arcade drive X componet being input from humans
   * @return - a double value associated with the magnitude of the x componet
   */
  public double getInputArcadeDriveY()
  {
    // need to invert the y for all xbox controllers due to xbox controler having up as negative y axis
    return driverController.getLeftY() * -1.0;
  }

  /**
   * A method to get the jaws up/down input from humans
   * @return - a double value associated with the magnitude of the jaws up/down
   */
  public double getInputJaws()
  {
    // TODO - switch this to use the coDriverController soon!!!
    // should be: coDriverController.getLeftY();
    return driverController.getRightX();
  }

  /**
   * A method to get the jaws up/down input from humans
   * @return - a double value associated with the magnitude of the jaws up/down
   */
  public double getInputShooter()
  {
    // TODO - switch this to use the coDriverController soon!!!
    XboxController theControllerToUse = driverController;
    return theControllerToUse.getLeftTriggerAxis() > theControllerToUse.getRightTriggerAxis() ? 
           -1.0 * theControllerToUse.getLeftTriggerAxis() : 
           theControllerToUse.getRightTriggerAxis();
  }

  /**
   * A method to get the telescoping arms up/down input from humans
   * @return - a double value associated with the magnitude of the telescoping arms up/down
   */
  public double getInputTelescopingArms()
  {
    // TODO - switch this to use the coDriverController soon!!!
    // should be: coDriverController.getRightY();
    // need to invert the y for all xbox controllers due to xbox controler having up as negative y axis
    return driverController.getRightY() * -1.0;
  }

  /**
   * A method to initialize various commands to the numerous buttons.
   * Need delayed bindings as some subsystems during testing won't always be there.
   */
  public void initializeButtonCommandBindings()
  {
    // Configure the button board bindings
    this.bindCommandsToButtonBoardButtons();

    // Configure the driver xbox controller bindings
    this.bindCommandsToCoDriverXboxButtons();

    // Configure the co-driver xbox controller bindings
    this.bindCommandsToDriverXboxButtons();
  }

  /**
   * Will attach commands to the Driver XBox buttons 
   */
  private void bindCommandsToDriverXboxButtons()
  {
    if(InstalledHardware.driverXboxControllerInstalled)
    {
      // *************************************************************
      // *************************************************************
      // *************************************************************
      // this is just for testing!!! RIP IT OUT LATER!!!
      JoystickButton buttonB = new JoystickButton(coDriverController, XboxController.Button.kB.value);
      JoystickButton buttonY = new JoystickButton(coDriverController, XboxController.Button.kY.value);
      JoystickButton bumperLeft = new JoystickButton(driverController, XboxController.Button.kLeftBumper.value);
      JoystickButton bumperRight = new JoystickButton(driverController, XboxController.Button.kRightBumper.value);
      JoystickButton joystickButton = new JoystickButton(driverController, XboxController.Button.kRightStick.value);

      if(subsystemCollection.getAngleArmsSubsystem() != null)
      {
        buttonB.whenPressed(new AngleArmsJawsManual(subsystemCollection.getAngleArmsSubsystem()));
        buttonY.whenPressed(new AngleArmsChassisManual(subsystemCollection.getAngleArmsSubsystem())); 
      }
      if(subsystemCollection.getBallStorageSubsystem() != null)
      {
        bumperLeft.whenPressed(new BallStorageStoreManual(subsystemCollection.getBallStorageSubsystem()));
        bumperRight.whenPressed(new BallStorageRetrieveManual(subsystemCollection.getBallStorageSubsystem()));
      }
      if(subsystemCollection.getJawsSubsystem() != null)
      {
        joystickButton.whenPressed(new JawsHoldReleaseManual(subsystemCollection.getJawsSubsystem()));
      }
      // *************************************************************
      // *************************************************************
      // *************************************************************
    }
  }

  /**
   * Will attach commands to the Co Driver XBox buttons 
   */
  private void bindCommandsToCoDriverXboxButtons()
  {
    if(InstalledHardware.coDriverXboxControllerInstalled)
    {
      JoystickButton buttonB = new JoystickButton(coDriverController, XboxController.Button.kB.value);
      JoystickButton buttonY = new JoystickButton(coDriverController, XboxController.Button.kY.value);
      JoystickButton bumperLeft = new JoystickButton(coDriverController, XboxController.Button.kLeftBumper.value);
      JoystickButton bumperRight = new JoystickButton(coDriverController, XboxController.Button.kRightBumper.value);
      JoystickButton joystickButton = new JoystickButton(coDriverController, XboxController.Button.kLeftStick.value);

      if(subsystemCollection.getAngleArmsSubsystem() != null)
      {
        buttonB.whenPressed(new AngleArmsJawsManual(subsystemCollection.getAngleArmsSubsystem()));
        buttonY.whenPressed(new AngleArmsChassisManual(subsystemCollection.getAngleArmsSubsystem())); 
      }
      if(subsystemCollection.getBallStorageSubsystem() != null)
      {
        bumperLeft.whenPressed(new BallStorageStoreManual(subsystemCollection.getBallStorageSubsystem()));
        bumperRight.whenPressed(new BallStorageRetrieveManual(subsystemCollection.getBallStorageSubsystem()));
      }
      if(subsystemCollection.getJawsSubsystem() != null)
      {
        joystickButton.whenPressed(new JawsHoldReleaseManual(subsystemCollection.getJawsSubsystem()));
      }
    }
  }

  private void bindCommandsToButtonBoardButtons()
  {
    if(InstalledHardware.buttonBoardInstalled)
    {
      JoystickButton button0 = new JoystickButton(buttonBoard, 0);
      JoystickButton button1 = new JoystickButton(buttonBoard, 1);
      JoystickButton button2 = new JoystickButton(buttonBoard, 2);
      JoystickButton button3 = new JoystickButton(buttonBoard, 3);
      JoystickButton button4 = new JoystickButton(buttonBoard, 4);
      JoystickButton button5 = new JoystickButton(buttonBoard, 5);
      JoystickButton button6 = new JoystickButton(buttonBoard, 6);
      JoystickButton button7 = new JoystickButton(buttonBoard, 7);
      JoystickButton button8 = new JoystickButton(buttonBoard, 8);
      JoystickButton button9 = new JoystickButton(buttonBoard, 9);
      JoystickButton button10 = new JoystickButton(buttonBoard, 10);
      JoystickButton button11 = new JoystickButton(buttonBoard, 11);
      JoystickButton button12 = new JoystickButton(buttonBoard, 12);

      if(subsystemCollection.getAngleArmsSubsystem() != null && subsystemCollection.getJawsSubsystem() != null)
      {
        button0.whenPressed(new AngleArmsEngageChassis(subsystemCollection.getAngleArmsSubsystem(), subsystemCollection.getJawsSubsystem()));
        button1.whenPressed(new AngleArmsEngageJaws(subsystemCollection.getAngleArmsSubsystem(), subsystemCollection.getJawsSubsystem()));
      }

      if(subsystemCollection.getTelescopingArmsSubsystem() != null && subsystemCollection.getJawsSubsystem() != null && subsystemCollection.getDriveTrainSubsystem() != null)
      {
        button2.whenPressed(ClimbCommandBuilder.buildMediumBarClimb(subsystemCollection));
        button3.whenPressed(ClimbCommandBuilder.buildHighBarClimb(subsystemCollection));
        button4.whenPressed(ClimbCommandBuilder.buildHighBarClimb(subsystemCollection));  
      }

      if(subsystemCollection.getJawsSubsystem() != null)
      {
        button5.whenPressed(new JawsForwardHighGoal(subsystemCollection.getJawsSubsystem()));
        button6.whenPressed(new JawsReverseHighGoal(subsystemCollection.getJawsSubsystem()));
        button7.whenPressed(new JawsForwardLowGoal(subsystemCollection.getJawsSubsystem()));
        button8.whenPressed(new JawsIntake(subsystemCollection.getJawsSubsystem()));
      }

      if(subsystemCollection.getShooterSubsystem() != null && subsystemCollection.getBallStorageSubsystem() != null)
      {
        button9.whenPressed(new ShooterForwardHighShot(subsystemCollection.getShooterSubsystem(), subsystemCollection.getBallStorageSubsystem()));
        button10.whenPressed(new ShooterReverseHighShot(subsystemCollection.getShooterSubsystem(), subsystemCollection.getBallStorageSubsystem()));
        button11.whenPressed(new ShooterForwardLowShot(subsystemCollection.getShooterSubsystem(), subsystemCollection.getBallStorageSubsystem()));
        button12.whenPressed(new ShooterIntake(subsystemCollection.getShooterSubsystem(), subsystemCollection.getBallStorageSubsystem()));
      }
    }
  }
}
