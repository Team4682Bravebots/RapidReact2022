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
  // declaring interfaces
  private ManualInputInterfaces m_interfaces = null;

  // declaring and init subsystems  
  private AngleArms m_angleArms = null;
  private BallStorage m_ballStorage = null;
  private DriveTrain m_driveTrain = null;
  private Jaws m_jaws = null;
  private Pneumatics m_pneumatics  = null;
  private Shooter m_shooter = null;
  private TelescopingArms m_telescopingArms = null;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer()
  {
    // create the input interface
    this.initalizeManualInputInterfaces();

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

    // make sure that all of the buttons have appropriate commands bound to them
    if(m_interfaces != null)
    {
      m_interfaces.initializeButtonCommandBindings(m_angleArms, m_ballStorage, m_driveTrain, m_jaws, m_shooter, m_telescopingArms);
    }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // TODO - much work needed in this command group here!!!

    // example automation to attempt to
    // 1. shoot first ball
    // 2. pickup another ball
    // 3. shoot second ball
    DriveCommand driveReverseToShot = new DriveCommand(m_driveTrain, -22.7, 0.0, 1.9);
    JawsForwardHighGoal jawsToHighGoal = new JawsForwardHighGoal(m_jaws);
    ShooterForwardHighShot shootHighGoal = new ShooterForwardHighShot(m_shooter, m_ballStorage);
    DriveCommand rotateTowardBall = new DriveCommand(m_driveTrain, 0.0, 130.0, 1.0);
    DriveCommand driveForwardToBall = new DriveCommand(m_driveTrain, 99.7, 10.0, 2.9);
    JawsIntake jawsIntake = new JawsIntake(m_jaws);
    ShooterIntake shooterIntake = new ShooterIntake(m_shooter, m_ballStorage);
    DriveCommand rotateTowardBasket = new DriveCommand(m_driveTrain, 0.0, -130.0, 1.0);
    DriveCommand driveForwardToHighGoal = new DriveCommand(m_driveTrain, 99.7, -10.0, 2.9);
    JawsForwardHighGoal jawsToHighGoal2 = new JawsForwardHighGoal(m_jaws);
    ShooterForwardHighShot shootHighGoal2 = new ShooterForwardHighShot(m_shooter, m_ballStorage);
    SequentialCommandGroup commandGroup = new SequentialCommandGroup(
      driveReverseToShot,
      jawsToHighGoal,
      shootHighGoal,
      rotateTowardBall,
      driveForwardToBall,
      jawsIntake,
      shooterIntake,
      rotateTowardBasket,
      driveForwardToHighGoal,
      jawsToHighGoal2,
      shootHighGoal2
    );

    return commandGroup;
  }

  private void initalizeManualInputInterfaces()
  {
    if(InstalledHardware.buttonBoardInstalled &&
      InstalledHardware.coDriverXboxControllerInstalled &&
      InstalledHardware.driverXboxControllerInstalled)
    {
      m_angleArms = new AngleArms();
      CommandScheduler.getInstance().registerSubsystem(m_angleArms);
    }
  }

  private void initalizeAngleArms()
  {
    if(InstalledHardware.angleArmsToJawsCylindarSolenoidPneumaticsInstalled && 
      InstalledHardware.angleArmsToChassisCylindarSolenoidPneumaticsInstalled)
    {
      m_angleArms = new AngleArms();
      CommandScheduler.getInstance().registerSubsystem(m_angleArms);
      // no need for a default command as buttons control this subsystem
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
      CommandScheduler.getInstance().registerSubsystem(m_ballStorage);
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
      CommandScheduler.getInstance().registerSubsystem(m_driveTrain);
      m_driveTrain.setDefaultCommand(
        new RunCommand(
          () ->
          m_driveTrain.arcadeDrive(
            m_interfaces.getInputArcadeDriveX(),
            m_interfaces.getInputArcadeDriveY()),
          m_driveTrain));
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
      CommandScheduler.getInstance().registerSubsystem(m_jaws);
      m_jaws.setDefaultCommand(
          new RunCommand(
            () ->
            m_jaws.setJawsSpeedManual(m_interfaces.getInputJaws()),
            m_jaws));
    }
  }

  private void initializePneumatics()
  {
    if(InstalledHardware.compressorInstalled &&
      InstalledHardware.pressureReliefSwitchInstalled)
    {
      m_pneumatics = new Pneumatics();
      CommandScheduler.getInstance().registerSubsystem(m_pneumatics);
      m_pneumatics.setDefaultCommand(
          new RunCommand(
            () ->
            m_pneumatics.compressorOn(),
            m_pneumatics));    
      }
  }

  private void initalizeShooter()
  {
    if(InstalledHardware.topShooterDriveMotorInstalled &&
      InstalledHardware.bottomShooterDriveMotorInstalled)
    {
      m_shooter = new Shooter();
      CommandScheduler.getInstance().registerSubsystem(m_shooter);
      m_shooter.setDefaultCommand(
          new RunCommand(
            () ->
            m_shooter.shooterManual(m_interfaces.getInputShooter()),
            m_shooter));
      }
  }

  private void initalizeTelescopingArms()
  {
    if(InstalledHardware.leftTelescopingArmsDriveMotorInstalled &&
      InstalledHardware.rightTelescopingArmsDriveMotorInstalled)
    {
      m_telescopingArms = new TelescopingArms();
      CommandScheduler.getInstance().registerSubsystem(m_telescopingArms);
      m_telescopingArms.setDefaultCommand(
          new RunCommand(
            () ->
            m_telescopingArms.setTelescopingArmsSpeedManual(m_interfaces.getInputTelescopingArms()),
            m_telescopingArms));
    }
  }

}
