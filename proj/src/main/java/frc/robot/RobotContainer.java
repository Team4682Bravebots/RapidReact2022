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
