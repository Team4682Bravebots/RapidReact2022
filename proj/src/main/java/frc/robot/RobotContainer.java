// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: RobotContainer.java
// Intent: Forms the key command initiation logic of the robot.
// ************************************************************

package frc.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {


  //declaring and init subsystems  
  public static Jaws m_jaws = new Jaws();
  public static Pneumatics m_pnuematics  = new Pneumatics();
  public static Shooter m_Shooter = new Shooter();
  public static DriveTrain m_drivetrain = new DriveTrain();
  public static BallStorage m_climbers1 = new BallStorage();
  public static TelescopingArms m_TelescopingArm = new TelescopingArms();
  public static AngleArms m_AngleArm = new AngleArms();
  public static Interfaces m_interfaces = new Interfaces();


  //declering hids
  private Joystick driverController;
  private XboxController coDriverController; 
  private Joystick buttonBoard;

  int pov = -1;
  int _pov = -1;
  int _smoothing = 0;


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer()
  {
    //TODO //substystems and defualt commands
    CommandScheduler.getInstance().registerSubsystem(m_jaws);
    //CommandScheduler.getInstance().setDefaultCommand(m_Jaws, new JawsDefualt(m_Jaws));

    CommandScheduler.getInstance().registerSubsystem(m_climbers1);
    //CommandScheduler.getInstance().setDefaultCommand(m_climbers1, new climberS1Defualt(m_climbers1, m_interfaces));

    CommandScheduler.getInstance().registerSubsystem(m_TelescopingArm);
    //TODO CommandScheduler.getInstance().setDefaultCommand(m_climbers2, new climberS2Defualt(m_climbers2));

    CommandScheduler.getInstance().registerSubsystem(m_drivetrain);
    // CommandScheduler.getInstance().setDefaultCommand(m_drivetrain, new driveCommand(m_drivetrain));

    CommandScheduler.getInstance().registerSubsystem(m_Shooter);
    // CommandScheduler.getInstance().setDefaultCommand(m_Shooter, new ShooterDefualt(m_Shooter, m_pnuematics));

    CommandScheduler.getInstance().registerSubsystem(m_pnuematics);
    //CommandScheduler.getInstance().setDefaultCommand(m_pnuematics, new AngleArmDefualt(m_pnuematics));

    CommandScheduler.getInstance().registerSubsystem(m_AngleArm);
    //CommandScheduler.getInstance().setDefaultCommand(m_AngleArm, new AngleArmDefualt(m_AngleArm));;

    CommandScheduler.getInstance().registerSubsystem(m_interfaces);

    // init hids \\
    driverController = new Joystick(Constants.portDriverController); // sets joystick varibles to joysticks
    coDriverController = new XboxController(Constants.portCoDriverController);

    // Configure the button bindings
    configureButtonBindings();
  }

  
  //gets the joystick axis value where ever you want, 
  //for y use Robot.m_robotContainer.getJoystickRawAxis(Constants.joystickY); 
  //for x use Robot.m_robotContainer.getJoystickRawAxis(Constants.joystickX);
  public double getJoystickRawAxis(int axis){
    return driverController.getRawAxis(axis);
  }
  public double getXboxRawAxis(int axis){
    return coDriverController.getRawAxis(axis);
  }


  private void configureButtonBindings() {


    JoystickButton buttonA = new JoystickButton(coDriverController, XboxController.Button.kA.value);
    JoystickButton buttonB = new JoystickButton(coDriverController, XboxController.Button.kB.value);
    JoystickButton buttonY = new JoystickButton(coDriverController, XboxController.Button.kY.value);
    JoystickButton buttonX = new JoystickButton(coDriverController, XboxController.Button.kX.value);
    JoystickButton bumperLeft = new JoystickButton(coDriverController, XboxController.Button.kLeftBumper.value);
    JoystickButton bumperRight = new JoystickButton(coDriverController, XboxController.Button.kRightBumper.value);
    JoystickButton joystickLeftButton = new JoystickButton(coDriverController, XboxController.Button.kLeftStick.value);
    JoystickButton joystickRightButton = new JoystickButton(coDriverController, XboxController.Button.kRightStick.value);


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

    //BUTTTON BOARD
    //1 Jaws defualt     JawsDefualt.java 
    //2 Jaws pos 1       JawsShooter.java
    //3 Jaws pos 2       JawsForwardLowGoal.java

    //4 climber defualt   climber lock + climberS1Defualt.java
    //5 climber pos 1     climber unlock + climberS1Extended.java 
    //6 climber pos 2     wait for climb then climberlock + climberS1Endgame.java

    //7 eat               JawsShooter + intkaeEat + index eat 
    //8 barf              intkaeBard (high speed) + index barf 
    //9 barf low          ShooterBarf (low speed) + index barf 

    //10 grab Jaws in 
    //11 grab Jaws out


    //BUTTONBOARD
    button1.whenPressed(new JawsDefault(m_jaws));
    button2.whenPressed(new JawsIntake(m_jaws));
    button3.whenPressed(new JawsForwardLowGoal(m_jaws));

    button4.whenPressed(new TelescopingArmRetract(m_TelescopingArm, m_interfaces));//TODO
    button5.whenPressed(new TelescopingArmExtend(m_TelescopingArm, m_interfaces));//TODO
    button6.whenPressed(new TelescopingArmEndGame(m_TelescopingArm, m_interfaces));//TODO

    button7.whenPressed(new JawsIntake(m_jaws));
    button7.whenPressed(new ShooterIntake(m_Shooter, m_pnuematics));
   
    button8.whenPressed(new ShooterForwardLowShot(m_Shooter, m_pnuematics, m_interfaces));
    button9.whenPressed(new ShooterForwardLowShot(m_Shooter, m_pnuematics, m_interfaces));

    //button10.whenPressed(new lockJawsToTelescopingArm());
    //button10.whenPressed(new unlockTelescopingArm());

    //button11.whenPressed(new TelescopingArmIn());
    //button12.whenPressed(new grabOut());


//CO Driver Contrller. 
//left stick Jaws
//right stick climber 
//left bumper eat
//Right bumper barf
//A + rightstick grabber
//B pnuematics 1
//C pnuemaitcs 2
//D pnuematics 3 
//Start index 1
//Menue index 2 

    //CODRIVER CONTROLLER 
    //left stick Jaws (scedule)
    //right stikc climber (scedule)
    //left trigger Shooter (scedule )
    //right trigger shoot (scedule)
    //TODO grabber stuff
    /*
    buttonA.whenPressed(new TelescopingArmLock());
    buttonB.whenPressed(new grabberUnlock());
    buttonX.whenPressed(new JawsGrabberLock());
    buttonY.whenPressed(new JawsGrabberUnlovk());

    joystickLeftButton.whenPressed(new indexEat());
    joystickRightButton.whenPressed(new ShooterBarf());

    */

    // TODO - this is incorrect below and needs much work ...
    buttonA.whenPressed(new JawsDefault(m_jaws));
    buttonX.whenPressed(new JawsIntake(m_jaws));
    buttonY.whenPressed(new JawsForwardLowGoal(m_jaws));

    // D-Pad Stuff \\

    double pov = coDriverController.getPOV();
    System.out.println(pov);


    // joystick fun stuff \\
    double joystickThrottleValue = driverController.getThrottle();


  }

  

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  //public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return m_autoCommand;
  }

