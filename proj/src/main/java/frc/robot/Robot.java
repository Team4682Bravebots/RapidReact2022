<<<<<<< HEAD
<<<<<<< Updated upstream:proj/src/main/java/frc/robot/Robot.java
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
=======
// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Robot.java
// Intent: Robot implementation robot spec.
// ************************************************************
>>>>>>> Stashed changes:code/src/main/java/frc/robot/Robot.java
=======
// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: RobotContainer.java
// Intent: Wrapper class standard stub for robot in FRC challange.
// ************************************************************
>>>>>>> main

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;



  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    //m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
   // if (m_autonomousCommand != null) {
     // m_autonomousCommand.schedule();
    //}
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    //if (m_autonomousCommand != null) {
    //  m_autonomousCommand.cancel();
    //}
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    //RobotContainer.m_drivetrain.schedule();

  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    //from frosty
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}

/*
cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccccccccc:;,;;:cccccccccccccccccccccc::cccccccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccccccc::,'....',;::ccccccccccccccc:;,'',::ccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccccc:;'.........',;;;;;;;;;;:::;'.......,:cccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccccc:'....''''...............'....''.....,:ccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccccc:,......,,,'...'''''''''''............':ccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccc:;'.......',,,,'',,,,,,,,,,,,,'..........,;::cccccccccccccccccccccccccccc
ccccccccccccccccccccccccc:;'....'....',,,,,,,,,,,,,,,,,,,,,''.'''.....',;:cccccccccccccccccccccccccc
cccccccccccccccccccccccc:,....',,,''',,,,,,,,,,,,,,,,,,,,,,,,,,,,,'''....,:ccccccccccccccccccccccccc
cccccccccccccccccccccc:;'....',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'...',;::ccccccccccccccccccccc
ccccccccccccccccccc::,'.....',,,,,,,,,,,,,,,,,,,,,,,,,,'..,:'',,,,,,,''......'',,;;::ccccccccccccccc
ccccccccccccccccc:;,.......',,,,,,,,,,,,,,,,,,,,,,,,,,,'..cd:'.'''...,:lodxxdoolc:,'';:ccccccccccccc
ccccccccccccccc:;'........',,,,,,,,,,,,,,,,,,,,,,,,,,,,,'..,,'....':ok0K00000K0dc;,...':cccccccccccc
cccccccccccccc:,..........',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,''''''..;d0K00000000Kk,.......;cccccccccccc
ccccccccccccc:,...........',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,..cOK0000000000KOd:,'....;cccccccccccc
cccccccccccc:,.............','''''''',,,,,,,,,,,,,,,,,,,,,,,,'.:OK000000000000000Ol;c;':cccccccccccc
ccccccccccc:;.......................',,,,,,,,,,,,,,,,,,,,,,,,.'o00000K0Okkkkkkkkxo;',';:cccccccccccc
ccccccccccc:'......................',,,,,,,,,,,,,,,,,,,,,,,,,.,kK00K0dc,''''''''....';:ccccccccccccc
ccccccccccc;........................',,,,,,,,,,,,,,,,,,,,,,,'.cOK00x:'.......'..,;;';:cccccccccccccc
ccccccccccc;..........................'',,,,,,,,,,,,,,,,,,,,.'d000d,......',;:;,:c:::ccccccccccccccc
ccccccccccc;............................',,,,,,,,,,,,,,,,,,'.;kKKx;',''.';:c:c::cccccccccccccccccccc
ccccccccccc;'............................',,,,,,,,,,,,,,,,,..:OK0l,dOkl'';:::ccccccccccccccccccccccc
ccccccccccc:'............................',,,,,,,,,,,,,,,,'..;kK0o,d00o'.',,:ccccccccccccccccccccccc
cccccccccccc;.............................',,,,,,,,,,,,,,,'..'lOKOc,lkk:...,:ccccccccccccccccccccccc
cccccccccccc:,.............................'',,,,,,,,,,,,,,'...:dkOdc;:,....;ccccccccccccccccccccccc
ccccccccccccc:,........................''.....'',,,,,,,,,,,,'....,:clcccoo,.;ccccccccccccccccccccccc
cccccccccccccc:;'......................',,'......''',,,,,,,,,,'.......';:;',:ccccccccccccccccccccccc
cccccccccccccccc:,'.....................',,,''.......',,,,,,,,''....';;;;;::cccccccccccccccccccccccc
cccccccccccccccccc:;,....................',,,,,'......,,,,,','.....':ccccccccccccccccccccccccccccccc
cccccccccccccccccccc::,'...................',,,,'.....',,,'......';:cccccccccccccccccccccccccccccccc
ccccccccccccccccccccccc:;,'.................',,,'....',,,'.......,:ccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccc:;'................','.....'''.........;cccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccc::,'..............'.................,:cccccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccccccc:;,'...........................';:ccccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccccccccc:;'......................',;:ccccccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccccccccccc:;,'.................';:cccccccccccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccccccccccccccc:;'............',::cccccccccccccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccccccccccccccccc::;,''''''',;:ccccccccccccccccccccccccccccccccccccccccccccc
ccccccccccccccccccccccccccccccccccccccccccccc::::::ccccccccccccccccccccccccccccccccccccccccccccccccc
cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc
*/
