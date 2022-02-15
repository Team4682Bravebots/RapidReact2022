// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: RobotContainer.java
// Intent: Forms the key command initiation logic of the robot.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.builders.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
  // declaring input classes
  private ManualInputInterfaces m_manualInput = null;
  private OnboardInputInterfaces m_onboardInput = null;

  // declaring and init subsystems  
  private AngleArms m_angleArms = null;
  private BallStorage m_ballStorage = null;
  private DriveTrain m_driveTrain = null;
  private Jaws m_jaws = null;
  private Pneumatics m_pneumatics  = null;
  private Shooter m_shooter = null;
  private TelescopingArms m_telescopingArms = null;

  private SubsystemCollection m_collection = new SubsystemCollection();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer()
  {   
    // create the manual input interface
    this.initalizeManualInputInterfaces();

    // create the onboard input interface
    this.initalizeOnboardInputInterfaces();

    // initalize the Angle Arms
    this.initalizeAngleArms();

    // initialize the ball storage
    this.initalizeBallStorage();

    // initialize the drive train
    this.initalizeDriveTrain();

    // initialize the jaws
    this.initializeJaws();

    // initialize the pneumatics - mostly just turn the compressor on ...
    this.initializePneumatics();

    // initialize the telescoping arms
    this.initalizeTelescopingArms();
    
    // assemble all of the constructed content and insert the references into the subsystem collection
    m_collection.setAngleArmsSubsystem(m_angleArms);
    m_collection.setBallStorageSubsystem(m_ballStorage);
    m_collection.setDriveTrainSubsystem(m_driveTrain);
    m_collection.setJawsSubsystem(m_jaws);
    m_collection.setPneumaticsSubsystem(m_pneumatics);
    m_collection.setShooterSubsystem(m_shooter);
    m_collection.setTelescopingArmsSubsystem(m_telescopingArms);
    m_collection.setManualInputInterfaces(m_manualInput);
    m_collection.setOnboardInputInterfaces(m_onboardInput);

    // make sure that all of the buttons have appropriate commands bound to them
    if(m_manualInput != null)
    {
      m_manualInput.initializeButtonCommandBindings();
    }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    return AutonomousCommandBuilder.buildTestCollectAndShoot(m_collection);
  }

  private void initalizeManualInputInterfaces()
  {
    if(InstalledHardware.buttonBoardInstalled &&
      InstalledHardware.coDriverXboxControllerInstalled &&
      InstalledHardware.driverXboxControllerInstalled)
    {
      m_manualInput = new ManualInputInterfaces(m_collection);
      System.out.println("SUCCESS: initalizeManualInputInterfaces");
    }
    else
    {
      System.out.println("FAIL: initalizeManualInputInterfaces");
    }
  }

  private void initalizeOnboardInputInterfaces()
  {
    // TODO - when limelight comes online add it here
    if(InstalledHardware.navxInstalled /* && InstalledHardware.limelightInstalled */)
    {
      m_onboardInput = new OnboardInputInterfaces();
      System.out.println("SUCCESS: initalizeOnboardInputInterfaces");
    }
    else
    {
      System.out.println("FAIL: initalizeOnboardInputInterfaces");
    }
  }

  private void initalizeAngleArms()
  {
    if(InstalledHardware.angleArmsToJawsCylindarSolenoidPneumaticsInstalled && 
      InstalledHardware.angleArmsToChassisCylindarSolenoidPneumaticsInstalled)
    {
      m_angleArms = new AngleArms();
      // no need for a default command as buttons control this subsystem
      System.out.println("SUCCESS: initalizeAngleArms");
    }
    else
    {
      System.out.println("FAIL: initalizeAngleArms");
    }
  }

  private void initalizeBallStorage()
  {
    if(InstalledHardware.bottomBallStorageMotorInstalled && 
      InstalledHardware.topBallStorageMotorInstalled && 
      InstalledHardware.forwardBallStorageBeamBreakSensorInstalled &&
      InstalledHardware.rearBallStorageBeamBreakSensorInstalled)
    {
      m_ballStorage = new BallStorage();
      System.out.println("SUCCESS: initalizeBallStorage");
    }
    else
    {
      System.out.println("FAIL: initalizeBallStorage");
    }
  }

  private void initalizeDriveTrain()
  {
    if(InstalledHardware.leftFinalDriveShaftEncoderInstalled && 
      InstalledHardware.leftFrontDriveMotorInstalled && 
      InstalledHardware.leftRearDriveMotorInstalled && 
      InstalledHardware.rightFinalDriveShaftEncoderInstalled && 
      InstalledHardware.rightFrontDriveMotorInstalled && 
      InstalledHardware.rightRearDriveMotorInstalled)
    {
      m_driveTrain = new DriveTrain();
      m_driveTrain.setDefaultCommand(
        new RunCommand(
          () ->
          m_driveTrain.arcadeDrive(
            m_manualInput.getInputArcadeDriveX(),
            m_manualInput.getInputArcadeDriveY()),
          m_driveTrain));
      System.out.println("SUCCESS: initalizeDriveTrain");
    }
    else
    {
      System.out.println("FAIL: initalizeDriveTrain");
    }
  }

  private void initializeJaws()
  {
    if(InstalledHardware.topJawsDriveMotorInstalled &&
      InstalledHardware.bottomJawsDriveMotorInstalled &&
      InstalledHardware.intakeStopJawsLmitSwitchInstalled &&
      InstalledHardware.jawsClutchCylindarSolenoidPneumaticsInstalled)
    {
      // JAWS!!!
      m_jaws = new Jaws();
      m_jaws.setDefaultCommand(
          new RunCommand(
            () ->
            m_jaws.setJawsSpeedManual(m_manualInput.getInputJaws()),
          m_jaws));
      System.out.println("SUCCESS: initializeJaws");
    }
    else
    {
      System.out.println("FAIL: initializeJaws");
    }
  }

  private void initializePneumatics()
  {
    if(InstalledHardware.compressorInstalled &&
      InstalledHardware.pressureReliefSwitchInstalled)
    {
      m_pneumatics = new Pneumatics();
      m_pneumatics.setDefaultCommand(
          new RunCommand(
            () ->
            m_pneumatics.compressorOn(),
          m_pneumatics));    
      System.out.println("SUCCESS: initializePneumatics");
    }
    else
    {
      System.out.println("FAIL: initializePneumatics");
    }
  }


<<<<<<< HEAD
  private void configureButtonBindings() {

    JoystickButton buttonA = new JoystickButton(coDriverController, 1);
    JoystickButton buttonB = new JoystickButton(coDriverController, 2);
    JoystickButton buttonY = new JoystickButton(coDriverController, 3);
    JoystickButton buttonX = new JoystickButton(coDriverController, 4);
    JoystickButton bumperLeft = new JoystickButton(coDriverController, XboxController.Button.kLeftBumper.value);
    JoystickButton bumperRight = new JoystickButton(coDriverController, XboxController.Button.kRightBumper.value);
    JoystickButton joystickLeftButton = new JoystickButton(coDriverController, XboxController.Button.kLeftStick.value);
    JoystickButton joystickRightButton = new JoystickButton(coDriverController, XboxController.Button.kRightStick.value);
    JoystickButton triggerLeft = new JoystickButton(coDriverController, XboxController.Axis.kLeftTrigger.value);
    JoystickButton triggerRight = new JoystickButton(coDriverController, XboxController.Axis.kRightTrigger.value);
    JoystickButton backButton = new JoystickButton(coDriverController, XboxController.Button.kBack.value);
    JoystickButton startButton = new JoystickButton(coDriverController, XboxController.Button.kStart.value);


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

    //testing
    buttonA.whenPressed(new JawsIntake(m_jaws));
    buttonB.whenPressed(new JawsForwardLowGoal(m_jaws));
    buttonY.whenPressed(new JawsForwardHighGoal(m_jaws));
    buttonX.whenPressed(new RobotCalibration(m_jaws, m_telescopingArm));
  
    //BUTTON BOARD
    //0 disable button board?   ??.java

    //1 Jaws pos 1        JawsIntake.java
    //2 Jaws pos 2        JawsForwardLowGoal.java
    //3 Jaws default      JawsForwardHighGoal.java

    //4 climber default   climber lock + climberS1Default.java
    //5 climber pos 1     climber unlock + climberS1Extended.java 
    //6 climber pos 2     wait for climb then climberlock + climberS1Endgame.java

    //7 eat               JawsShooter + intakeEat + index eat 
    //8 barf              intakeBard (high speed) + index barf 
    //9 barf low          ShooterBarf (low speed) + index barf 

    //10 grab Jaws in 
    //11 grab Jaws out


    //BUTTON BOARD
    button1.whenPressed(new JawsIntake(m_jaws));
    button2.whenPressed(new JawsForwardLowGoal(m_jaws));
    button3.whenPressed(new JawsForwardHighGoal(m_jaws));

    button4.whenPressed(new TelescopingArmRetract(m_telescopingArm));
    button5.whenPressed(new TelescopingArmExtendMiddle(m_telescopingArm));
    button6.whenPressed(new TelescopingArmExtendHigh(m_telescopingArm));

    button7.whenPressed(new ShooterIntake(m_shooter, m_ballStorage));
    button8.whenPressed(new ShooterForwardLowShot(m_shooter, m_ballStorage));
    button9.whenPressed(new ShooterForwardHighShot(m_shooter, m_ballStorage));
    button10.whenPressed(new ShooterReverseHighShot(m_shooter, m_ballStorage));

=======
  private void initalizeShooter()
  {
    if(InstalledHardware.topShooterDriveMotorInstalled &&
      InstalledHardware.bottomShooterDriveMotorInstalled)
    {
      m_shooter = new Shooter();
      m_shooter.setDefaultCommand(
          new RunCommand(
            () ->
            m_shooter.shooterManual(m_manualInput.getInputShooter()),
          m_shooter));
      System.out.println("SUCCESS: initalizeShooter");
    }
    else
    {
      System.out.println("FAIL: initalizeShooter");
    }
>>>>>>> main
  }


  private void initalizeTelescopingArms()
  {
    if(InstalledHardware.leftTelescopingArmsDriveMotorInstalled &&
      InstalledHardware.rightTelescopingArmsDriveMotorInstalled)
    {
      m_telescopingArms = new TelescopingArms();
      m_telescopingArms.setDefaultCommand(
          new RunCommand(
            () ->
            m_telescopingArms.setTelescopingArmsSpeedManual(m_manualInput.getInputTelescopingArms()),
          m_telescopingArms));
      System.out.println("SUCCESS: initalizeTelescopingArms");
    }
    else
    {
      System.out.println("FAIL: initalizeTelescopingArms");
    }
  }


}
