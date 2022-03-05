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
  private Joystick highLevelButtonBoard = new Joystick(Constants.highLevelButtonBoardPort);
  private Joystick lowLevelButtonBoard = new Joystick(Constants.lowLevelButtonBoardPort);

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
   * A method to get the arcade drive X componet being input from humans
   * @return - a double value associated with the magnitude of the x componet
   */
  public double getGtaInputArcadeDriveX()
  { 
    // cubic to achieve a yaw throttle curve
    return Math.pow(this.getInputArcadeDriveX(), 3.0);
  } 

  /**
   * A method to get the arcade drive X componet being input from humans
   * @return - a double value associated with the magnitude of the x componet
   */
  public double getGtaInputArcadeDriveY()
  {
    if(driverController.getLeftTriggerAxis() > driverController.getRightTriggerAxis())
    {
      return driverController.getLeftTriggerAxis();
    }
    else
    {
      return driverController.getRightTriggerAxis() * -1.0;
    }
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
    XboxController theControllerToUse = coDriverController;
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
    this.bindHighLevelCommandsToButtonBoardButtons();
    this.bindLowLevelCommandsToButtonBoardButtons();

    // TODO - remove this when we are no longer needing to confirm the button board is working properly
    this.testBindCommandsToHighLevelButtonBoard();
    this.testBindCommandsToLowLevelButtonBoard();

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
      JoystickButton buttonX = new JoystickButton(driverController, XboxController.Button.kX.value);
      buttonX.whenPressed(AutonomousCommandBuilder.buildAllStop(subsystemCollection));
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

  private void bindHighLevelCommandsToButtonBoardButtons()
  {
    if(InstalledHardware.highLevelButtonBoardInstalled)
    {
      JoystickButton extendAndPair = new JoystickButton(highLevelButtonBoard, 1);
      JoystickButton midBarClimb = new JoystickButton(highLevelButtonBoard, 2);
      JoystickButton highBarClimb = new JoystickButton(highLevelButtonBoard, 3);
      JoystickButton traversalBarClimb = new JoystickButton(highLevelButtonBoard, 4);
      JoystickButton shooterShoot = new JoystickButton(highLevelButtonBoard, 5);
      JoystickButton shooterIntake = new JoystickButton(highLevelButtonBoard, 6);
      JoystickButton commandStop = new JoystickButton(highLevelButtonBoard, 7);
      JoystickButton jawsForwardIntake = new JoystickButton(highLevelButtonBoard, 8);
      JoystickButton jawsForwardLow = new JoystickButton(highLevelButtonBoard, 9);
      JoystickButton jawsForwardHigh = new JoystickButton(highLevelButtonBoard, 10);
      JoystickButton jawsReverseHigh = new JoystickButton(highLevelButtonBoard, 11);
      JoystickButton jawsReverseLow = new JoystickButton(highLevelButtonBoard, 12);

      if(subsystemCollection.getAngleArmsSubsystem() != null &&
        subsystemCollection.getJawsSubsystem() != null &&
        subsystemCollection.getTelescopingArmsSubsystem() != null)
      {
        extendAndPair.whenPressed(ClimbCommandBuilder.buildExtensionAndPairing(subsystemCollection));
        midBarClimb.whenPressed(ClimbCommandBuilder.buildMediumBarClimb(subsystemCollection));
        highBarClimb.whenPressed(ClimbCommandBuilder.buildHighBarClimb(subsystemCollection));
        traversalBarClimb.whenPressed(ClimbCommandBuilder.buildTraversalBarClimb(subsystemCollection));
      }

      if(subsystemCollection.getShooterSubsystem() != null && 
         subsystemCollection.getBallStorageSubsystem() != null &&
         subsystemCollection.getJawsSubsystem() != null)
      {
        shooterShoot.whenPressed(
          new ShooterAutomatic(
            subsystemCollection.getShooterSubsystem(),
            subsystemCollection.getBallStorageSubsystem(),
            subsystemCollection.getJawsSubsystem(),
            true,
            (InstalledHardware.rearBallStorageBeamBreakSensorInstalled && InstalledHardware.forwardBallStorageBeamBreakSensorInstalled)));
        shooterIntake.whenPressed(
          new ShooterAutomatic(
            subsystemCollection.getShooterSubsystem(),
            subsystemCollection.getBallStorageSubsystem(),
            subsystemCollection.getJawsSubsystem(),
            false,
            (InstalledHardware.rearBallStorageBeamBreakSensorInstalled && InstalledHardware.forwardBallStorageBeamBreakSensorInstalled)));
      }

      commandStop.whenPressed(AutonomousCommandBuilder.buildAllStop(subsystemCollection));

      if(subsystemCollection.getJawsSubsystem() != null)
      {
        jawsForwardIntake.whenPressed(new JawsIntake(subsystemCollection.getJawsSubsystem()));
        jawsForwardLow.whenPressed(new JawsForwardLowGoal(subsystemCollection.getJawsSubsystem()));
        jawsForwardHigh.whenPressed(new JawsForwardHighGoal(subsystemCollection.getJawsSubsystem()));
        jawsReverseHigh.whenPressed(new JawsReverseHighGoal(subsystemCollection.getJawsSubsystem()));
        jawsReverseLow.whenPressed(new JawsReverseLowGoal(subsystemCollection.getJawsSubsystem()));
      }
    }
  }

  private void bindLowLevelCommandsToButtonBoardButtons()
  {
    if(InstalledHardware.lowLevelButtonBoardInstalled)
    {
      JoystickButton angleArmsChassisToggle = new JoystickButton(lowLevelButtonBoard, 1);
      JoystickButton angleArmsJawsToggle = new JoystickButton(lowLevelButtonBoard, 2);
      JoystickButton telescopingArmsUp = new JoystickButton(lowLevelButtonBoard, 3);
      JoystickButton telescopingArmsDown = new JoystickButton(lowLevelButtonBoard, 4);
      JoystickButton jawsPositive = new JoystickButton(lowLevelButtonBoard, 5);
      JoystickButton jawsNegative = new JoystickButton(lowLevelButtonBoard, 6);
      JoystickButton jawsClutchToggle = new JoystickButton(lowLevelButtonBoard, 7);

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

      if(subsystemCollection.getJawsSubsystem() != null)
      {
        jawsPositive.whenPressed(new JawsManual(subsystemCollection.getJawsSubsystem(), Constants.jawsDefaultPositiveSpeed));
        jawsNegative.whenPressed(new JawsManual(subsystemCollection.getJawsSubsystem(), Constants.jawsDefaultNegativeSpeed));
        jawsPositive.whenReleased(new JawsAllStop(subsystemCollection.getJawsSubsystem()));
        jawsNegative.whenReleased(new JawsAllStop(subsystemCollection.getJawsSubsystem()));
        jawsClutchToggle.whenPressed(new JawsHoldReleaseManual(subsystemCollection.getJawsSubsystem()));
      }
    }
  }

  private void testBindCommandsToHighLevelButtonBoard()
  {
    if(InstalledHardware.testHighLevelButtonBoardInstalled)
    {
      JoystickButton extendAndPair = new JoystickButton(highLevelButtonBoard, 1);
      JoystickButton midBarClimb = new JoystickButton(highLevelButtonBoard, 2);
      JoystickButton highBarClimb = new JoystickButton(highLevelButtonBoard, 3);
      JoystickButton traversalBarClimb = new JoystickButton(highLevelButtonBoard, 4);
      JoystickButton shooterShoot = new JoystickButton(highLevelButtonBoard, 5);
      JoystickButton shooterIntake = new JoystickButton(highLevelButtonBoard, 6);
      JoystickButton commandStop = new JoystickButton(highLevelButtonBoard, 7);
      JoystickButton jawsForwardIntake = new JoystickButton(highLevelButtonBoard, 8);
      JoystickButton jawsForwardLow = new JoystickButton(highLevelButtonBoard, 9);
      JoystickButton jawsForwardHigh = new JoystickButton(highLevelButtonBoard, 10);
      JoystickButton jawsReverseHigh = new JoystickButton(highLevelButtonBoard, 11);
      JoystickButton jawsReverseLow = new JoystickButton(highLevelButtonBoard, 12);

      extendAndPair.whenPressed(new ButtonBoardButtonPress("highLevelButtonBoard", 1));
      midBarClimb.whenPressed(new ButtonBoardButtonPress("highLevelButtonBoard", 2));
      highBarClimb.whenPressed(new ButtonBoardButtonPress("highLevelButtonBoard", 3));
      traversalBarClimb.whenPressed(new ButtonBoardButtonPress("highLevelButtonBoard", 4));
      shooterShoot.whenPressed(new ButtonBoardButtonPress("highLevelButtonBoard", 5));
      shooterIntake.whenPressed(new ButtonBoardButtonPress("highLevelButtonBoard", 6));
      commandStop.whenPressed(new ButtonBoardButtonPress("highLevelButtonBoard", 7));
      jawsForwardIntake.whenPressed(new ButtonBoardButtonPress("highLevelButtonBoard", 8));
      jawsForwardLow.whenPressed(new ButtonBoardButtonPress("highLevelButtonBoard", 9));
      jawsForwardHigh.whenPressed(new ButtonBoardButtonPress("highLevelButtonBoard", 10));
      jawsReverseHigh.whenPressed(new ButtonBoardButtonPress("highLevelButtonBoard", 11));
      jawsReverseLow.whenPressed(new ButtonBoardButtonPress("highLevelButtonBoard", 12));
    }
  }

  private void testBindCommandsToLowLevelButtonBoard()
  {
    if(InstalledHardware.testLowLevelButtonBoardInstalled)
    {
      JoystickButton angleArmsChassisToggle = new JoystickButton(lowLevelButtonBoard, 1);
      JoystickButton angleArmsJawsToggle = new JoystickButton(lowLevelButtonBoard, 2);
      JoystickButton telescopingArmsUp = new JoystickButton(lowLevelButtonBoard, 3);
      JoystickButton telescopingArmsDown = new JoystickButton(lowLevelButtonBoard, 4);
      JoystickButton jawsPositive = new JoystickButton(lowLevelButtonBoard, 5);
      JoystickButton jawsNegative = new JoystickButton(lowLevelButtonBoard, 6);
      JoystickButton jawsClutchToggle = new JoystickButton(lowLevelButtonBoard, 7);

      angleArmsChassisToggle.whenPressed(new ButtonBoardButtonPress("lowLevelButtonBoard", 1));
      angleArmsJawsToggle.whenPressed(new ButtonBoardButtonPress("lowLevelButtonBoard", 2));
      telescopingArmsUp.whenPressed(new ButtonBoardButtonPress("lowLevelButtonBoard", 3));
      telescopingArmsDown.whenPressed(new ButtonBoardButtonPress("lowLevelButtonBoard", 4));
      jawsPositive.whenPressed(new ButtonBoardButtonPress("lowLevelButtonBoard", 5));
      jawsNegative.whenPressed(new ButtonBoardButtonPress("lowLevelButtonBoard", 6));
      jawsClutchToggle.whenPressed(new ButtonBoardButtonPress("lowLevelButtonBoard", 7));
    }
  }
}
