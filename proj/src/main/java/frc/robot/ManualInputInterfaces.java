// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: ManualInputInterfaces.java
// Intent: Forms a class that grants access to driver controlled inputs.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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
    return this.getInputArcadeDriveX() * 0.75;
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
    double inputValue = driverController.getRightX();
    return ((inputValue > 0.070 || inputValue < -0.070) ? inputValue : 0.0);
  }

  /**
   * A method to get the telescoping arms up/down input from humans
   * @return - a double value associated with the magnitude of the telescoping arms up/down
   */
  public double getInputTelescopingArms()
  {
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
    if(InstalledHardware.highLevelButtonBoardInstalled)
    {
      this.bindHighLevelCommandsToButtonBoardButtons();
    }
    if(InstalledHardware.lowLevelButtonBoardInstalled)
    {
      this.bindLowLevelCommandsToButtonBoardButtons();
    }

    // Configure the driver xbox controller bindings
    if(InstalledHardware.driverXboxControllerInstalled)
    {
      this.bindCommandsToDriverXboxButtons();
    }

    // Configure the co-driver xbox controller bindings
    if(InstalledHardware.coDriverXboxControllerInstalled)
    {
      this.bindCommandsToCoDriverXboxButtons();
    }
  }

  /**
   * Will attach commands to the Driver XBox buttons 
   */
  private void bindCommandsToDriverXboxButtons()
  {
    // *************************************************************
    // TODO - REMOVE THIS WHOLE IF STATEMENT AFTER HOME TESTING!!!!!
    // *************************************************************
    /*
    if(InstalledHardware.driverXboxControllerInstalled)
    {
      JoystickButton buttonA = new JoystickButton(driverController, XboxController.Button.kA.value);
      JoystickButton buttonB = new JoystickButton(driverController, XboxController.Button.kB.value);
      JoystickButton buttonX = new JoystickButton(driverController, XboxController.Button.kX.value);
      JoystickButton buttonY = new JoystickButton(driverController, XboxController.Button.kY.value);
      JoystickButton bumperLeft = new JoystickButton(driverController, XboxController.Button.kLeftBumper.value);
      JoystickButton bumperRight = new JoystickButton(driverController, XboxController.Button.kRightBumper.value);
      JoystickButton buttonBack = new JoystickButton(driverController, XboxController.Button.kBack.value);
      JoystickButton buttonStart = new JoystickButton(driverController, XboxController.Button.kStart.value);

      if(subsystemCollection.getBallStorageSubsystem() != null && subsystemCollection.getShooterSubsystem() != null)
      {
        buttonA.whenPressed(
          new ParallelCommandGroup(
            new ShooterAutomatic(
              subsystemCollection.getShooterSubsystem(),
              subsystemCollection.getBallStorageSubsystem(),
              false,
              Constants.jawsIntakePositionAngle),
            new ButtonPress("driverController.kA", "buttonA.whenPressed")));
        buttonB.whenPressed(
          new ParallelCommandGroup(
            new ShooterAutomatic(
              subsystemCollection.getShooterSubsystem(),
              subsystemCollection.getBallStorageSubsystem(),
              true,
              Constants.jawsLowGoalPositionAngle),
              new ButtonPress("driverController.kB", "buttonB.whenPressed")));
        buttonX.whenPressed(
          new ParallelCommandGroup(
            new ShooterAutomatic(
              subsystemCollection.getShooterSubsystem(),
              subsystemCollection.getBallStorageSubsystem(),
              true,
              Constants.jawsHighGoalPositionAngle),
            new ButtonPress("driverController.kX", "buttonX.whenPressed")));
        buttonY.whenPressed(
          new ParallelCommandGroup(
            new ShooterAutomatic(
              subsystemCollection.getShooterSubsystem(),
              subsystemCollection.getBallStorageSubsystem(),
              true,
              Constants.jawsReverseHighGoalPositionAngle),
            new ButtonPress("driverController.kY", "buttonY.whenPressed")));
      }

      if(subsystemCollection.getTelescopingArmsSubsystem() != null)
      {
        bumperLeft.whenPressed(
          new ParallelCommandGroup(
            new TelescopingArmRetract(
              subsystemCollection.getTelescopingArmsSubsystem()),
            new ButtonPress("driverController.kLeftBumper", "bumperLeft.whenPressed")));
        bumperRight.whenPressed(
          new ParallelCommandGroup(
            new TelescopingArmExtendMiddle(
              subsystemCollection.getTelescopingArmsSubsystem()),
            new ButtonPress("driverController.kRightBumper", "bumperRight.whenPressed")));
      }

      if(subsystemCollection.getJawsSubsystem() != null)
      {
        buttonBack.whenPressed(
          new ParallelCommandGroup(
            new JawsIntake(
              subsystemCollection.getJawsSubsystem()),
            new ButtonPress("driverController.kBack", "buttonBack.whenPressed")));
        buttonStart.whenPressed(
          new ParallelCommandGroup(
            new JawsReverseLowGoal(
              subsystemCollection.getJawsSubsystem()),
            new ButtonPress("driverController.kStart", "buttonStart.whenPressed")));
      }

      if(subsystemCollection.getAngleArmsSubsystem() != null)
      {
        buttonB.whenPressed(
          new ParallelCommandGroup(
            new AngleArmsManual(subsystemCollection.getAngleArmsSubsystem(), Constants.angleArmsManualMotorReverseSpeed),
            new ButtonPress("driverController.kB", "buttonB.whenPressed")));
        buttonY.whenPressed(
          new ParallelCommandGroup(
            new AngleArmsManual(subsystemCollection.getAngleArmsSubsystem(), Constants.angleArmsManualMotorForwardSpeed),
            new ButtonPress("driverController.kY", "buttonY.whenPressed")));
        buttonB.whenReleased(
          new ParallelCommandGroup(
            new AngleArmsAllStop(subsystemCollection.getAngleArmsSubsystem()),
            new ButtonPress("driverController.kB", "buttonB.whenReleased")));
        buttonY.whenReleased(
          new ParallelCommandGroup(
            new AngleArmsAllStop(subsystemCollection.getAngleArmsSubsystem()),
            new ButtonPress("driverController.kY", "buttonY.whenReleased")));
        buttonX.whenPressed(
          new ParallelCommandGroup(
            new AngleArmsAngleVariable(subsystemCollection.getAngleArmsSubsystem(), Constants.angleArmsBarPositionAngle),
            new ButtonPress("driverController.kX", "buttonX.whenPressed")));
        buttonA.whenPressed(
          new ParallelCommandGroup(
            new AngleArmsAngleVariable(subsystemCollection.getAngleArmsSubsystem(), Constants.angleArmsMaximumPositionAngle),
            new ButtonPress("driverController.kA", "buttonA.whenPressed")));
      }
    }
    */
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

      if(subsystemCollection.getAngleArmsSubsystem() != null)
      {
        buttonB.whenPressed(
          new ParallelCommandGroup(
            new AngleArmsManual(subsystemCollection.getAngleArmsSubsystem(), Constants.angleArmsManualMotorReverseSpeed),
            new ButtonPress("coDriverController", "kB.whenPressed")));
        buttonY.whenPressed(
          new ParallelCommandGroup(
            new AngleArmsManual(subsystemCollection.getAngleArmsSubsystem(), Constants.angleArmsManualMotorForwardSpeed),
            new ButtonPress("coDriverController", "kY.whenPressed")));
        buttonB.whenReleased(
          new ParallelCommandGroup(
            new AngleArmsAllStop(subsystemCollection.getAngleArmsSubsystem()),
            new ButtonPress("coDriverController", "kB.whenPressed")));
        buttonY.whenReleased(
          new ParallelCommandGroup(
            new AngleArmsAllStop(subsystemCollection.getAngleArmsSubsystem()),
            new ButtonPress("coDriverController", "kY.whenPressed")));
      }
      if(subsystemCollection.getBallStorageSubsystem() != null)
      {
        bumperLeft.whenHeld(
          new ParallelCommandGroup(
            new BallStorageStoreManual(subsystemCollection.getBallStorageSubsystem()),
            new ButtonPress("coDriverController", "kLeftBumper.whenHeld")));
        bumperRight.whenHeld(
          new ParallelCommandGroup(
            new BallStorageRetrieveManual(subsystemCollection.getBallStorageSubsystem()),
            new ButtonPress("coDriverController", "kRightBumper.whenHeld")));
        bumperLeft.whenReleased(
          new ParallelCommandGroup(
            new BallStorageAllStopManual(subsystemCollection.getBallStorageSubsystem()),
            new ButtonPress("coDriverController", "kLeftBumper.whenPressed")));
        bumperRight.whenReleased(
          new ParallelCommandGroup(
            new BallStorageAllStopManual(subsystemCollection.getBallStorageSubsystem()),
            new ButtonPress("coDriverController", "kRightBumper.whenPressed")));
      }

      JoystickButton buttonA = new JoystickButton(coDriverController, XboxController.Button.kA.value);
      JoystickButton buttonX = new JoystickButton(coDriverController, XboxController.Button.kX.value);

      if(subsystemCollection.getTelescopingArmsSubsystem() != null)
      {
        buttonX.whenPressed(
          new ParallelCommandGroup(
            new TelescopingArmExtendMiddle(subsystemCollection.getTelescopingArmsSubsystem()),
            new ButtonPress("coDriverController", "kX.whenPressed")));
        buttonA.whenPressed(
          new ParallelCommandGroup(
            new TelescopingArmRetract(subsystemCollection.getTelescopingArmsSubsystem()),
            new ButtonPress("coDriverController", "kA.whenPressed")));
      }
    }
  }

  private void bindHighLevelCommandsToButtonBoardButtons()
  {
    if(InstalledHardware.highLevelButtonBoardInstalled)
    {
      JoystickButton extendAndReady = new JoystickButton(highLevelButtonBoard, 1);
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
        extendAndReady.whenPressed(
          new ParallelCommandGroup(
            ClimbCommandBuilder.buildExtensionAndReadyPosition(subsystemCollection),
            new ButtonPress("buttonBoardHigh.1", "extendAndReady.whenPressed")).withTimeout(Constants.maximumClimbTimeOperationSeconds));
        midBarClimb.whenPressed(
          new ParallelCommandGroup(
            ClimbCommandBuilder.buildMediumBarClimb(subsystemCollection),
            new ButtonPress("buttonBoardHigh.2", "midBarClimb.whenPressed")).withTimeout(Constants.maximumClimbTimeOperationSeconds));
        highBarClimb.whenPressed(
          new ParallelCommandGroup(
            ClimbCommandBuilder.buildHighBarClimb(subsystemCollection),
            new ButtonPress("buttonBoardHigh.3", "highBarClimb.whenPressed")).withTimeout(Constants.maximumClimbTimeOperationSeconds));
        traversalBarClimb.whenPressed(
          new ParallelCommandGroup(
            ClimbCommandBuilder.buildTraversalBarClimb(subsystemCollection),
            new ButtonPress("buttonBoardHigh.4", "traversalBarClimb.whenPressed")).withTimeout(Constants.maximumClimbTimeOperationSeconds));
      }

      if(subsystemCollection.getShooterSubsystem() != null && 
         subsystemCollection.getBallStorageSubsystem() != null /*&&
         subsystemCollection.getJawsSubsystem() != null */)
      {
        shooterShoot.whenPressed(
          new ParallelCommandGroup(
            new ShooterAutomatic(
              subsystemCollection.getShooterSubsystem(),
              subsystemCollection.getBallStorageSubsystem(),
              true,
              Constants.jawsHighGoalPositionAngle),
            new ButtonPress("buttonBoardHigh.5", "shooterShoot.whenPressed")).withTimeout(Constants.maximumShooterTimeOperationSeconds));
        /*
        shooterShoot.whenPressed(
          new ParallelCommandGroup(           
            new ShooterAutomatic(
              subsystemCollection.getShooterSubsystem(),
              subsystemCollection.getBallStorageSubsystem(),
              subsystemCollection.getJawsSubsystem(),
              true),
            new ButtonPress("buttonBoardHigh.5", "shooterShoot.whenPressed")).withTimeout(Constants.maximumShooterTimeOperationSeconds));
          */
        shooterIntake.whileHeld(
          new ParallelCommandGroup(
            new ShooterManual(
              subsystemCollection.getShooterSubsystem(),
              Constants.topMotorIntakeSpeedRpm,
              Constants.bottomMotorForwardHighGoalSpeedRpm),
            new BallStorageStoreManual(
              subsystemCollection.getBallStorageSubsystem()),
            new ButtonPress("buttonBoardHigh.6", "shooterIntake.whenPressed")).withTimeout(Constants.maximumShooterTimeOperationSeconds));
        shooterIntake.whenReleased(
          new ParallelCommandGroup(
            new ShooterAllStop(
              subsystemCollection.getShooterSubsystem()),
            new BallStorageAllStopManual(
              subsystemCollection.getBallStorageSubsystem()),
            new ButtonPress("buttonBoardHigh.6", "shooterIntake.whenPressed")).withTimeout(Constants.maximumShooterTimeOperationSeconds));
      }

      commandStop.whenPressed(
        new ParallelCommandGroup(
          AutonomousCommandBuilder.buildAllStop(subsystemCollection),
          new ButtonPress("buttonBoardHigh.7", "commandStop.whenPressed")));

      if(subsystemCollection.getJawsSubsystem() != null)
      {
        jawsForwardIntake.whenPressed(
          new ParallelCommandGroup(
            new JawsIntake(subsystemCollection.getJawsSubsystem()),
            new ButtonPress("buttonBoardHigh.8", "jawsForwardIntake.whenPressed")).withTimeout(Constants.maximumJawsTimeOperationSeconds));
        jawsForwardLow.whenPressed(
          new ParallelCommandGroup(
            new JawsForwardLowGoal(subsystemCollection.getJawsSubsystem()),
            new ButtonPress("buttonBoardHigh.9", "jawsForwardLow.whenPressed")).withTimeout(Constants.maximumJawsTimeOperationSeconds));
        jawsForwardHigh.whenPressed(
          new ParallelCommandGroup(
            new JawsForwardHighGoal(subsystemCollection.getJawsSubsystem()),
            new ButtonPress("buttonBoardHigh.10", "jawsForwardHigh.whenPressed")).withTimeout(Constants.maximumJawsTimeOperationSeconds));
        jawsReverseHigh.whenPressed(
          new ParallelCommandGroup(
            new JawsReverseHighGoal(subsystemCollection.getJawsSubsystem()),
            new ButtonPress("buttonBoardHigh.11", "jawsReverseHigh.whenPressed")).withTimeout(Constants.maximumJawsTimeOperationSeconds));
        jawsReverseLow.whenPressed(
          new ParallelCommandGroup(
            new JawsReverseLowGoal(subsystemCollection.getJawsSubsystem()),
            new ButtonPress("buttonBoardHigh.12", "jawsReverseLow.whenPressed")).withTimeout(Constants.maximumJawsTimeOperationSeconds));
      }
    }
  }

  private void bindLowLevelCommandsToButtonBoardButtons()
  {
    if(InstalledHardware.lowLevelButtonBoardInstalled)
    {
      JoystickButton angleArmsForward = new JoystickButton(lowLevelButtonBoard, 1);
      JoystickButton angleArmsReverse = new JoystickButton(lowLevelButtonBoard, 2);
      JoystickButton telescopingArmsUp = new JoystickButton(lowLevelButtonBoard, 3);
      JoystickButton telescopingArmsDown = new JoystickButton(lowLevelButtonBoard, 4);
      JoystickButton jawsPositive = new JoystickButton(lowLevelButtonBoard, 5);
      JoystickButton jawsNegative = new JoystickButton(lowLevelButtonBoard, 6);
      JoystickButton jawsReferencePosition = new JoystickButton(lowLevelButtonBoard, 7);

      if(subsystemCollection.getAngleArmsSubsystem() != null)
      {
        angleArmsForward.whenPressed(
          new ParallelCommandGroup(
            new AngleArmsManual(subsystemCollection.getAngleArmsSubsystem(), Constants.angleArmsManualMotorReverseSpeed),
            new ButtonPress("buttonBoardLow.1", "angleArmsForward.whenPressed")));
        angleArmsReverse.whenPressed(
          new ParallelCommandGroup(
            new AngleArmsManual(subsystemCollection.getAngleArmsSubsystem(), Constants.angleArmsManualMotorForwardSpeed),
            new ButtonPress("buttonBoardLow.2", "angleArmsReverse.whenPressed")));
        angleArmsForward.whenReleased(
          new ParallelCommandGroup(
            new AngleArmsAllStop(subsystemCollection.getAngleArmsSubsystem()),
            new ButtonPress("buttonBoardLow.1", "angleArmsForward.whenReleased")));
        angleArmsReverse.whenReleased(
          new ParallelCommandGroup(
            new AngleArmsAllStop(subsystemCollection.getAngleArmsSubsystem()),
            new ButtonPress("buttonBoardLow.2", "angleArmsReverse.whenReleased")));
      }

      if(subsystemCollection.getTelescopingArmsSubsystem() != null)
      {
        telescopingArmsUp.whileHeld(
          new ParallelCommandGroup(
            new TelescopingArmsManual(subsystemCollection.getTelescopingArmsSubsystem(), Constants.telescopingArmsDefaultExtendSpeed),
            new ButtonPress("buttonBoardLow.3", "3.whileHeld")));
        telescopingArmsDown.whileHeld(
          new ParallelCommandGroup(
            new TelescopingArmsManual(subsystemCollection.getTelescopingArmsSubsystem(), Constants.telescopingArmsDefaultRetractSpeed),
            new ButtonPress("buttonBoardLow.4", "4.whileHeld")));
        telescopingArmsUp.whenReleased(
          new ParallelCommandGroup(
            new TelescopingArmsManual(subsystemCollection.getTelescopingArmsSubsystem(), Constants.telescopingArmsStopSpeed),
            new ButtonPress("buttonBoardLow.3", "3.whenReleased")));
        telescopingArmsDown.whenReleased(
          new ParallelCommandGroup(
            new TelescopingArmsManual(subsystemCollection.getTelescopingArmsSubsystem(), Constants.telescopingArmsStopSpeed),
            new ButtonPress("buttonBoardLow.4", "4.whenReleased")));
      }

      if(subsystemCollection.getJawsSubsystem() != null)
      {
        jawsPositive.whenPressed(
          new ParallelCommandGroup(
            new JawsManual(subsystemCollection.getJawsSubsystem(), Constants.jawsDefaultPositiveSpeed),
            new ButtonPress("buttonBoardLow.5", "jawsPositive.whenPressed")));
        jawsNegative.whenPressed(
          new ParallelCommandGroup(
            new JawsManual(subsystemCollection.getJawsSubsystem(), Constants.jawsDefaultNegativeSpeed),
            new ButtonPress("buttonBoardLow.6", "jawsNegative.whenPressed")));
        jawsPositive.whenReleased(
          new ParallelCommandGroup(
            new JawsAllStop(subsystemCollection.getJawsSubsystem()),
            new ButtonPress("buttonBoardLow.5", "jawsPositive.whenReleased")));
        jawsNegative.whenReleased(
          new ParallelCommandGroup(
            new JawsAllStop(subsystemCollection.getJawsSubsystem()),
            new ButtonPress("buttonBoardLow.6", "jawsNegative.whenReleased")));
        jawsReferencePosition.whenPressed(
          new ParallelCommandGroup(
            new JawsAngleVariable(subsystemCollection.getJawsSubsystem(), subsystemCollection.getJawsSubsystem().getJawsReferencePositionAngle()),
            new ButtonPress("buttonBoardLow.7", "jawsReferencePosition.whenPressed")).withTimeout(Constants.maximumJawsTimeOperationSeconds));
      }
    }
  }
}
