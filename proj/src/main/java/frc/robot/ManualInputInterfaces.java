// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: ManualInputInterfaces.java
// Intent: Forms a class that grants access to driver controlled inputs.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.builders.AutonomousCommandBuilder;
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
    XboxController theControllerToUse = driverController;
    // need to invert the y for all xbox controllers due to xbox controler having up as negative y axis
    double input = theControllerToUse.getRightY() * -1.0;
    // avoid xbox controller shadow input drift
    return (Math.abs(input) > 0.1 ? input : 0.0);
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
      JoystickButton buttonA = new JoystickButton(driverController, XboxController.Button.kA.value);
      JoystickButton buttonB = new JoystickButton(driverController, XboxController.Button.kB.value);
      JoystickButton buttonY = new JoystickButton(driverController, XboxController.Button.kY.value);
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
        bumperLeft.whenHeld(new BallStorageStoreManual(subsystemCollection.getBallStorageSubsystem()));
        bumperRight.whenHeld(new BallStorageRetrieveManual(subsystemCollection.getBallStorageSubsystem()));
        bumperLeft.whenReleased(new BallStorageAllStopManual(subsystemCollection.getBallStorageSubsystem()));
        bumperRight.whenReleased(new BallStorageAllStopManual(subsystemCollection.getBallStorageSubsystem()));
      }

      if(subsystemCollection.getJawsSubsystem() != null)
      {
        joystickButton.whenPressed(new JawsHoldReleaseManual(subsystemCollection.getJawsSubsystem()));

        // TODO - rip the next 5 lines out as these are only for testing the jaws subsystem!!!
        JoystickButton buttonX = new JoystickButton(driverController, XboxController.Button.kX.value);
        buttonX.whenPressed(new JawsForwardLowGoal(subsystemCollection.getJawsSubsystem()));
        buttonA.whenPressed(new JawsIntake(subsystemCollection.getJawsSubsystem()));
        buttonB.whenPressed(new JawsAllStop(subsystemCollection.getJawsSubsystem()));
      }

      if(subsystemCollection.getCameraSubsystem() != null)
      {
        buttonA.whenPressed(new Target(
          subsystemCollection.getCameraSubsystem(),
          subsystemCollection.getDriveTrainSubsystem(),
          subsystemCollection.getManualInputInterfaces()));
      }
      
      // TODO - rip the next 8 lines out as these are only for testing the telescoping arms subsystem!!!
      if(subsystemCollection.getTelescopingArmsSubsystem() != null)
      {
        JoystickButton buttonX = new JoystickButton(driverController, XboxController.Button.kX.value);
        buttonX.whenPressed(new TelescopingArmExtendVariable(subsystemCollection.getTelescopingArmsSubsystem(), 25.75));
        buttonA.whenPressed(new TelescopingArmExtendVariable(subsystemCollection.getTelescopingArmsSubsystem(), 1.0));
        buttonY.whenPressed(new TelescopingArmExtendVariable(subsystemCollection.getTelescopingArmsSubsystem(), 0.0));
        buttonB.whenPressed(new TelescopingArmsAllStop(subsystemCollection.getTelescopingArmsSubsystem()));
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
        bumperLeft.whenHeld(new BallStorageStoreManual(subsystemCollection.getBallStorageSubsystem()));
        bumperRight.whenHeld(new BallStorageRetrieveManual(subsystemCollection.getBallStorageSubsystem()));
        bumperLeft.whenReleased(new BallStorageAllStopManual(subsystemCollection.getBallStorageSubsystem()));
        bumperRight.whenReleased(new BallStorageAllStopManual(subsystemCollection.getBallStorageSubsystem()));
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
      JoystickButton extendAndPair = new JoystickButton(buttonBoard, 0);
      JoystickButton midBarClimb = new JoystickButton(buttonBoard, 1);
      JoystickButton highBarClimb = new JoystickButton(buttonBoard, 2);
      JoystickButton traversalBarClimb = new JoystickButton(buttonBoard, 3);
      JoystickButton angleArmsChassisToggle = new JoystickButton(buttonBoard, 4);
      JoystickButton angleArmsJawsToggle = new JoystickButton(buttonBoard, 5);
      JoystickButton telescopingArmsUp = new JoystickButton(buttonBoard, 6);
      JoystickButton telescopingArmsDown = new JoystickButton(buttonBoard, 7);
      JoystickButton shooterShoot = new JoystickButton(buttonBoard, 8);
      JoystickButton shooterIntake = new JoystickButton(buttonBoard, 9);
      JoystickButton commandStop = new JoystickButton(buttonBoard, 10);
      JoystickButton jawsPositive = new JoystickButton(buttonBoard, 11);
      JoystickButton jawsNegative = new JoystickButton(buttonBoard, 12);
      JoystickButton jawsClutchToggle = new JoystickButton(buttonBoard, 13);
      JoystickButton jawsForwardIntake = new JoystickButton(buttonBoard, 14);
      JoystickButton jawsForwardLow = new JoystickButton(buttonBoard, 15);
      JoystickButton jawsForwardHigh = new JoystickButton(buttonBoard, 16);
      JoystickButton jawsReverseHigh = new JoystickButton(buttonBoard, 17);
      JoystickButton jawsReverseLow = new JoystickButton(buttonBoard, 18);

      if(subsystemCollection.getAngleArmsSubsystem() != null &&
        subsystemCollection.getJawsSubsystem() != null &&
        subsystemCollection.getTelescopingArmsSubsystem() != null)
      {
        extendAndPair.whenPressed(ClimbCommandBuilder.buildExtensionAndPairing(subsystemCollection));
        midBarClimb.whenPressed(ClimbCommandBuilder.buildMediumBarClimb(subsystemCollection));
        highBarClimb.whenPressed(ClimbCommandBuilder.buildHighBarClimb(subsystemCollection));
        traversalBarClimb.whenPressed(ClimbCommandBuilder.buildTraversalBarClimb(subsystemCollection));
      }

      if(subsystemCollection.getAngleArmsSubsystem() != null)
      {
        angleArmsChassisToggle.whenPressed(new AngleArmsChassisManual(subsystemCollection.getAngleArmsSubsystem()));
        angleArmsJawsToggle.whenPressed(new AngleArmsJawsManual(subsystemCollection.getAngleArmsSubsystem()));
      }

      if(subsystemCollection.getTelescopingArmsSubsystem() != null)
      {
        telescopingArmsUp.whenPressed(new TelescopingArmsManual(subsystemCollection.getTelescopingArmsSubsystem(), Constants.telescopingArmsDefaultExtendSpeed));
        telescopingArmsDown.whenPressed(new TelescopingArmsManual(subsystemCollection.getTelescopingArmsSubsystem(), Constants.telescopingArmsDefaultRetractSpeed));
        telescopingArmsUp.whenReleased(new TelescopingArmsManual(subsystemCollection.getTelescopingArmsSubsystem(), Constants.telescopingArmsStopSpeed));
        telescopingArmsDown.whenReleased(new TelescopingArmsManual(subsystemCollection.getTelescopingArmsSubsystem(), Constants.telescopingArmsStopSpeed));
      }

      commandStop.whenPressed(AutonomousCommandBuilder.buildAllStop(subsystemCollection));

      if(subsystemCollection.getJawsSubsystem() != null)
      {
        jawsPositive.whenPressed(new JawsManual(subsystemCollection.getJawsSubsystem(), Constants.jawsDefaultPositiveSpeed));
        jawsNegative.whenPressed(new JawsManual(subsystemCollection.getJawsSubsystem(), Constants.jawsDefaultNegativeSpeed));
        jawsPositive.whenReleased(new JawsAllStop(subsystemCollection.getJawsSubsystem()));
        jawsNegative.whenReleased(new JawsAllStop(subsystemCollection.getJawsSubsystem()));
        jawsClutchToggle.whenPressed(new JawsHoldReleaseManual(subsystemCollection.getJawsSubsystem()));
        jawsForwardIntake.whenPressed(new JawsIntake(subsystemCollection.getJawsSubsystem()));
        jawsForwardLow.whenPressed(new JawsForwardLowGoal(subsystemCollection.getJawsSubsystem()));
        jawsForwardHigh.whenPressed(new JawsForwardHighGoal(subsystemCollection.getJawsSubsystem()));
        jawsReverseHigh.whenPressed(new JawsReverseHighGoal(subsystemCollection.getJawsSubsystem()));
        jawsReverseLow.whenPressed(new JawsReverseLowGoal(subsystemCollection.getJawsSubsystem()));
      }
    }
  }
}
