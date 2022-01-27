// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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
  public static Arm m_arm = new Arm();
  public static Pneumatics m_pnuematics  = new Pneumatics();
  public static Intake m_intake = new Intake();
  public static DriveTrain m_drivetrain = new DriveTrain();
  public static ClimberS1 m_climbers1 = new ClimberS1();
  public static Graber m_graber = new Graber();
  public static Popper m_popper = new Popper();
  public static Interfaces m_interfaces = new Interfaces();


  //declering hids
  private Joystick driverController;
  private XboxController coDriverController; 
  private Joystick buttonBoard;

  int pov = -1;
  int _pov = -1;
  int _smoothing = 0;





  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    //TODO //substystems and defualt commands
  CommandScheduler.getInstance().registerSubsystem(m_arm);
 //CommandScheduler.getInstance().setDefaultCommand(m_arm, new armDefualt(m_arm));

  CommandScheduler.getInstance().registerSubsystem(m_climbers1);
  //CommandScheduler.getInstance().setDefaultCommand(m_climbers1, new climberS1Defualt(m_climbers1, m_interfaces));

  CommandScheduler.getInstance().registerSubsystem(m_graber);
  //TODO CommandScheduler.getInstance().setDefaultCommand(m_climbers2, new climberS2Defualt(m_climbers2));

  CommandScheduler.getInstance().registerSubsystem(m_drivetrain);
 // CommandScheduler.getInstance().setDefaultCommand(m_drivetrain, new driveCommand(m_drivetrain));

  CommandScheduler.getInstance().registerSubsystem(m_intake);
 // CommandScheduler.getInstance().setDefaultCommand(m_intake, new intakeDefualt(m_intake, m_pnuematics));
  
  CommandScheduler.getInstance().registerSubsystem(m_pnuematics);
  //CommandScheduler.getInstance().setDefaultCommand(m_pnuematics, new popperDefualt(m_pnuematics));

  CommandScheduler.getInstance().registerSubsystem(m_popper);
  //CommandScheduler.getInstance().setDefaultCommand(m_popper, new popperDefualt(m_popper));;

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
//1 arm defualt     armDefualt.java 
//2 arm pos 1       armPos1.java
//3 arm pos 2       armPos2.java

//4 climber defualt   climber lock + climberS1Defualt.java
//5 climber pos 1     climber unlock + climberS1Extended.java 
//6 climber pos 2     wait for climb then climberlock + climberS1Endgame.java

//7 eat               armPos1 + intkaeEat + index eat 
//8 barf              intkaeBard (high speed) + index barf 
//9 barf low          intakeBarf (low speed) + index barf 

//10 grab arm in 
//11 grab arm out


    //BUTTONBOARD
    button1.whenPressed(new ArmDefault(m_arm));
    button2.whenPressed(new ArmPos1(m_arm));
    button3.whenPressed(new ArmPos2(m_arm));

    button4.whenPressed(new ClimberS1Default(m_climbers1));//TODO
    button5.whenPressed(new ClimberS1Extended(m_climbers1));//TODO
    button6.whenPressed(new ClimberS1EndGame(m_climbers1));//TODO

    button7.whenPressed(new ArmPos1(m_arm));
    button7.whenPressed(new IntakeEat(m_intake, m_pnuematics));
    //TODO

    button8.whenPressed(new IntakeBarf(m_intake, m_pnuematics, m_interfaces));
    button9.whenPressed(new IntakeBarf(m_intake, m_pnuematics, m_interfaces));

    //button10.whenPressed(new lockArmToGraber());
    //button10.whenPressed(new unlockGraber());

    //button11.whenPressed(new graberIn());
    //button12.whenPressed(new grabOut());


//CO Driver Contrller. 
//left stick arm
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
    //left stick arm (scedule)
    //right stikc climber (scedule)
    //left trigger intake (scedule )
    //right trigger shoot (scedule)
    //TODO grabber stuff
/*
    buttonA.whenPressed(new graberLock());
    buttonB.whenPressed(new grabberUnlock());
    buttonX.whenPressed(new armGrabberLock());
    buttonY.whenPressed(new armGrabberUnlovk());

    joystickLeftButton.whenPressed(new indexEat());
    joystickRightButton.whenPressed(new intakeBarf());

*/

    buttonA.whenPressed(new ArmDefault(m_arm));
    buttonX.whenPressed(new ArmPos1(m_arm));
    buttonY.whenPressed(new ArmPos2(m_arm));

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

