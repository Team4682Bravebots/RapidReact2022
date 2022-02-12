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
import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class ManualInputInterfaces
{
  // sets joystick variables to joysticks
  private XboxController driverController = new XboxController(Constants.portDriverController); 
  private XboxController coDriverController = new XboxController(Constants.portCoDriverController);
  private Joystick buttonBoard = new Joystick(Constants.buttonBoardPort);

  // subsystems needed for inputs
  private AngleArms angleArms = null;
  private BallStorage ballStorage = null;
  private DriveTrain driveTrain = null;
  private Jaws jaws = null;
  private Shooter shooter = null;
  private TelescopingArms telescopingArms = null;

  /**
   * The constructor to build this s
   */
  public ManualInputInterfaces()
  {
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
    return driverController.getLeftY();
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
    // should be: coDriverController.getRightX();
    return driverController.getRightX();
  }
  /**
   * A method to initialize various commands to the numerous buttons
   * @param angleArmsSubsystem - the current angle arms subsystem
   * @param ballStorageSubsystem - the current ball storage subsystem
   * @param driveTrainSubsystem - the current drive train subsystem
   * @param jawsSubsystem - the current jaws subsystem
   * @param shooterSubsystem - the current shooter subsystem
   * @param telescopingArmsSubsystem - the current telescoping arms subsystem
   */
  public void initializeButtonCommandBindings(
    AngleArms angleArmsSubsystem,
    BallStorage ballStorageSubsystem,
    DriveTrain driveTrainSubsystem,
    Jaws jawsSubsystem,
    Shooter shooterSubsystem,
    TelescopingArms telescopingArmsSubsystem)
  {
    angleArms = angleArmsSubsystem;
    ballStorage = ballStorageSubsystem;
    driveTrain = driveTrainSubsystem;
    jaws = jawsSubsystem;
    shooter = shooterSubsystem;
    telescopingArms = telescopingArmsSubsystem;
    
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

      if(angleArms != null)
      {
        buttonB.whenPressed(new AngleArmsJawsManual(angleArms));
        buttonY.whenPressed(new AngleArmsChassisManual(angleArms)); 
      }
      if(ballStorage != null)
      {
        bumperLeft.whenPressed(new BallStorageStoreManual(ballStorage));
        bumperRight.whenPressed(new BallStorageRetrieveManual(ballStorage));
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

      if(angleArms != null)
      {
        buttonB.whenPressed(new AngleArmsJawsManual(angleArms));
        buttonY.whenPressed(new AngleArmsChassisManual(angleArms)); 
      }
      if(ballStorage != null)
      {
        bumperLeft.whenPressed(new BallStorageStoreManual(ballStorage));
        bumperRight.whenPressed(new BallStorageRetrieveManual(ballStorage));
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

      if(angleArms != null && jaws != null)
      {
        button0.whenPressed(new AngleArmsEngageChassis(angleArms, jaws));
        button1.whenPressed(new AngleArmsEngageJaws(angleArms, jaws));
      }

      if(telescopingArms != null)
      {
        button2.whenPressed(new TelescopingArmRetract(telescopingArms));
        button3.whenPressed(new TelescopingArmExtendMiddle(telescopingArms));
        button4.whenPressed(new TelescopingArmExtendHigh(telescopingArms));  
      }

      if(jaws != null)
      {
        button5.whenPressed(new JawsForwardHighGoal(jaws));
        button6.whenPressed(new JawsReverseHighGoal(jaws));
        button7.whenPressed(new JawsForwardLowGoal(jaws));
        button8.whenPressed(new JawsIntake(jaws));
      }

      if(shooter != null && ballStorage != null)
      {
        button9.whenPressed(new ShooterForwardHighShot(shooter, ballStorage));
        button10.whenPressed(new ShooterReverseHighShot(shooter, ballStorage));
        button11.whenPressed(new ShooterForwardLowShot(shooter, ballStorage));
        button12.whenPressed(new ShooterIntake(shooter, ballStorage));
      }
    }
  }
}
