// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Constants.java
// Intent: The class that will contain all 'hard coded' values.
// This should be the only place in all of the classes that 
// 'magic numbers' should appear.
// ************************************************************

package frc.robot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{
  // CAN bus ports

  // motor CAN ports 
  //TODO - P0 - fill in proper CAN bus index for arm motors
  public static int armMotorLeftCanId = -1;
  public static int armMotorRightCanId = -1;

  //TODO - P0 - fill in proper CAN bus index for drive motors
  public static int driveMotorLeftFrontCanId = -1;
  public static int driveMotorLeftRearCanId = -1;
  public static int driveMotorRightFrontCanId = -1;
  public static int driveMotorRightRearCanId = -1;

  //TODO - P0 - fill in proper CAN bus index for climber motors
  public static int frontClimbersMotorLeftCanId = -1;
  public static int frontClimbersMotorRightCanId = -1;

  //TODO - P0 - fill in proper CAN bus index for intake motors (temporary test values below)
  public static final int intakeMotorLeftCanId = 1;
  public static final int intakeMotorRightCanId = 2;

  // pneumatics CAN port
  //TODO - P0 - fill in proper CAN bus index for pneumatics control module
  public static int pneumaticsControlModuleCanId = -1;

  // climber magic numbers
  //TODO - P0 - fill in proper spool values based on emperical testing
  // will use a linear interpolation mechanism to determine how  
  public static double spoolLineLengthMillimeters = 0; // min stop to max stop
  public static double spoolDiameter0PercentCoiledMillimeters = 0;
  public static double spoolDiameter25PercentCoiledMillimeters = 0;
  public static double spoolDiameter50PercentCoiledMillimeters = 0;
  public static double spoolDiameter75PercentCoiledMillimeters = 0;
  public static double spoolDiameter100PercentCoiledMillimeters = 0;

  // Motor magic numbers
  public static boolean driveMotorLeftFrontInverted = false;
  public static boolean driveMotorLeftRearInverted = false;
  public static boolean driveMotorRightFrontInverted = false;
  public static boolean driveMotorRightRearInverted = false;

  // arm reach points \\
  public static int armDefualt = 0;
  public static int armPos1 = 2048 * 1;//TODO the mathh to find some good points depending on the gearing 
  public static int armPos2 = 2048 * 2;

  // popper timing \\
  public static final double popperTiming = 0.3;

  // substystem motor speeds \\
  public static final double intakeEatSpeed = 0.5;
  public static final double intakeBarfSpeed = 0.5;
  public static final double intakeDefualt = 0.5;

  // hid ports \\ 
  public static int portDriverController = 0;
  public static int portCoDriverController = 1;

  // xbox buttons \\
  public final static int buttonA = 1;
  public final static int buttonB = 2;
  public final static int buttonX = 3; 
  public final static int buttonY = 4;
  public final int buttonO1 = -1;
  public final int buttonO2 = 8;
  public final int stickLeftDown = 9;
  public final int stickRightDown = 10;
  public final static int bumperLeft = 5;
  public final static int bumperRight = 6;

  //joystick axis lables 
  public static final int joystickX = 0;
  public static final int joystickY = 1;
  public static final int joystickZ = 2;

  // util \\ //TODO if needed 
  public static final int kPIDLoopIdx = 0;
  public static final int kTimeoutMs = 30;
  public static final int kSlotIdx = 0;

  // solonoid directions
  public static Value hookSolenoidEnguage = Value.kForward;
  public static Value hookSolenoidRelease = Value.kReverse;

  public static final Gains kGains = new Gains(0.2, 0.0, 0.0, 0.2, 0, 1.0);

    /*
   * Talon FX has 2048 units per revolution
   * 
   * https://phoenix-documentation.readthedocs.io/en/latest/ch14_MCSensor.html#
   * sensor-resolution
   */
  public static final int kUnitsPerRevolution = 2048; /* this is constant for Talon FX */

}
