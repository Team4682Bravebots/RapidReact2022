// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Constants.java
// Intent: Forms all constants for the robot.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot;

import frc.robot.*;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * A class devoted to installed hardware constants.  Use this to decide if hardware is enabled on the robot or not.
 * All developers to use this to protect their subsystems and commands from using hardware that is not actually installed
 * on the robot at hand.  Used to assist in development stages and make it easier to quickly remove a piece of hardware
 * from the robot.
 */
public class InstalledHardware
{
    // Installed I/O Electronic hardware

    // Hardware that is N/A - mostly because these pieces of hardware have no communication with the RoboRio
    // batteryInstalled = true;
    // voltageRegulartorModuleInstalled = true;
    // robotEnabledLightInstalled = true;
    // roboRioInstalled = true; - if it wasn't installed then the code wouldn't be running

    // Basic Hardware
    public static final boolean powerDistributionPannelInstalled = true;
    
    // Communication Hardware
    public static final boolean wifiRadioInstalled = false;
    public static final boolean buttonBoardInstalled = true;
    public static final boolean driverXboxControllerInstalled = true;
    public static final boolean coDriverXboxControllerInstalled = true;

    // Orentation/Navigation Hardware
    public static final boolean navxInstalled = true;
    public static final boolean limelightInstalled = false;

    // Pnematics Hardware
    public static final boolean compressorInstalled = false;
    public static final boolean pressureReliefSwitchInstalled = false;

    // DriveTrain Related Hardware
    public static final boolean leftFinalDriveShaftEncoderInstalled = true;
    public static final boolean rightFinalDriveShaftEncoderInstalled = true;
    public static final boolean leftFrontDriveMotorInstalled = true;
    public static final boolean leftRearDriveMotorInstalled = true;
    public static final boolean rightFrontDriveMotorInstalled = true;
    public static final boolean rightRearDriveMotorInstalled = true;

    // TelescopingArms Related Hardware
    public static final boolean leftTelescopingArmsDriveMotorInstalled = false;
    public static final boolean rightTelescopingArmsDriveMotorInstalled = false;

    // Jaws Related Hardware
    public static final boolean topJawsDriveMotorInstalled = false;
    public static final boolean bottomJawsDriveMotorInstalled = false;
    public static final boolean intakeStopJawsLmitSwitchInstalled = false;
    public static final boolean jawsClutchCylindarSolenoidPneumaticsInstalled = false;

    // Shooter Related Hardware
    public static final boolean topShooterDriveMotorInstalled = false;
    public static final boolean bottomShooterDriveMotorInstalled = false;

    // Ball Storage Related Hardware
    public static final boolean topBallStorageMotorInstalled = false;
    public static final boolean bottomBallStorageMotorInstalled = false;
    public static final boolean forwardBallStorageBeamBreakSensorInstalled = false;
    public static final boolean rearBallStorageBeamBreakSensorInstalled = false;

    // Jaws Related Hardware
    public static final boolean angleArmsToJawsCylindarSolenoidPneumaticsInstalled = true;
    public static final boolean angleArmsToChassisCylindarSolenoidPneumaticsInstalled = true;
}
