// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.armDefualt;
import frc.robot.commands.armPos1;
import frc.robot.commands.armPos2;
import frc.robot.commands.climberS1Defualt;
import frc.robot.commands.intakeBarf;
import frc.robot.commands.level;
import frc.robot.commands.intakeDefualt;
import frc.robot.commands.popperPop;
import frc.robot.commands.zeroSensors;
import frc.robot.commands.popperDefualt;
import frc.robot.subsystems.arm;
import frc.robot.subsystems.climberS1;
import frc.robot.subsystems.climberS2;
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.interfaces;
import frc.robot.subsystems.pnuematics;
import frc.robot.subsystems.popper;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  //declaring and init subsystems  
  public static arm m_arm = new arm();
  public static pnuematics m_pnuematics  = new pnuematics();
  public static intake m_intake = new intake();
  public static driveTrain m_drivetrain = new driveTrain();
  public static climberS1 m_climbers1 = new climberS1();
  public static climberS2 m_climbers2 = new climberS2();
  public static popper m_popper = new popper();
  public static interfaces m_interfaces = new interfaces();

  //declering hids
  private Joystick driverController;
  private XboxController coDriverController; 

  int pov = -1;
  int _pov = -1;
  int _smoothing = 0;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer()
  {
    //TODO //substystems and defualt commands
    CommandScheduler.getInstance().registerSubsystem(m_arm);
    //CommandScheduler.getInstance().setDefaultCommand(m_arm, new armDefualt(m_arm));

    CommandScheduler.getInstance().registerSubsystem(m_climbers1);
    //CommandScheduler.getInstance().setDefaultCommand(m_climbers1, new climberS1Defualt(m_climbers1, m_interfaces));

    CommandScheduler.getInstance().registerSubsystem(m_climbers2);
    //TODO CommandScheduler.getInstance().setDefaultCommand(m_climbers2, new climberS2Defualt(m_climbers2));

    CommandScheduler.getInstance().registerSubsystem(m_drivetrain);
    // CommandScheduler.getInstance().setDefaultCommand(m_drivetrain, new driveCommand(m_drivetrain));

    CommandScheduler.getInstance().registerSubsystem(m_intake);
    // CommandScheduler.getInstance().setDefaultCommand(m_intake, new intakeDefualt(m_intake, m_pnuematics));

    CommandScheduler.getInstance().registerSubsystem(m_pnuematics);
    //CommandScheduler.getInstance().setDefaultCommand(m_pnuematics, new popperDefualt(m_pnuematics));

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
  public double getJoystickRawAxis(int axis)
  {
    return driverController.getRawAxis(axis);
  }

  public double getXboxRawAxis(int axis)
  {
    return coDriverController.getRawAxis(axis);
  }

  ///
  /// Use this method to bind various controller inputs to actions in the robot.
  ///
  private void configureButtonBindings()
  {
    JoystickButton buttonA = new JoystickButton(coDriverController, XboxController.Button.kA.value);
    JoystickButton buttonB = new JoystickButton(coDriverController, XboxController.Button.kB.value);
    JoystickButton buttonY = new JoystickButton(coDriverController, XboxController.Button.kY.value);
    JoystickButton buttonX = new JoystickButton(coDriverController, XboxController.Button.kX.value);
    JoystickButton bumperLeft = new JoystickButton(coDriverController, XboxController.Button.kBumperLeft.value);
    JoystickButton bumperRight = new JoystickButton(coDriverController, XboxController.Button.kBumperRight.value);
    JoystickButton joystickLeftButton = new JoystickButton(coDriverController, XboxController.Button.kStickLeft.value);
    JoystickButton joystickRightButton = new JoystickButton(coDriverController, XboxController.Button.kStickRight.value);

    // example for button commands buttonB.whenPressed(new limeLightTargetX());
    //buttonA.whenPressed(new pnuematicsCmd(solenoidIntakeArmForward));

    //TODO
    //buttonB.whenPressed(new popperPop(m_popper));
    joystickLeftButton.whenPressed(new zeroSensors(m_arm, m_climbers1, m_climbers2));
    //buttonB.whenPressed(new zeroSensors(m_arm, m_climbers1, m_climbers2));

    buttonA.whenPressed(new armDefualt(m_arm));
    buttonX.whenPressed(new armPos1(m_arm));
    buttonY.whenPressed(new armPos2(m_arm));

    //bumperLeft.whileHeld(new level(m_arm));
    bumperRight.whileHeld(new climberS1Defualt(m_climbers1));
    
    bumperLeft.whenPressed(new intakeBarf(m_intake, m_pnuematics, m_interfaces));

   // buttonB.whenPressed(new climberS1Extended());
    //buttonX.whenPressed(new climberS1EndGame());

    //bumperLeft.whenPressed(new intakeEat());
    //bumperRight.whenPressed(new intakeDefualt());

    //buttonY.whenPressed(new popper());


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

