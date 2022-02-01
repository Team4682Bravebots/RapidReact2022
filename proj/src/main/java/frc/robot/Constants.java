// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Constants.java
// Intent: Forms all constants for the robot.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot;

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
  // shooter \\\
  public static final double highGoalSpeed = 0;
  public static final double lowGoalSpeed = 0;
  public static final double eatSpeed = 0;
  public static final double barfSpeed = 0;

  // Jaws reach points \\
  public static final double JawsReverseHighGoalPosition = 0;
  public static final double JawsIntakePosition = 0;
  public static final double JawsLowGoalPosition = 0;
  public static final double JawsHighGoalPosition = 0;
   
   

  // telescoping arms reach points \\
  public static int s1Default = 0;
  public static int s1Extended = 1; //TODO need to calculate top of telescoping arms
  public static int s1EndGame = 2; //TODO should be just less then double "s1Extended"

  public static int s2Default = 0; 
  public static int s2Extended = 4062 * 1; //TODO need to calculate top of telescoping arm 
  public static int s2EndGame = 4062 * 2; //TODO should be just less then double "s2Extended"

  // Ball storage \\
  public static final double retrieveSpeed = 0;
  public static final double storeSpeed = 0;

  

  // CAN BUS NUMBERS \\
    //TODO - P0 - fill in proper CAN bus index for Jaws motors
    public static int jawsMotorRightCanId = -1;
    public static int jawsMotorLeftCanId = -1;

    //TODO - P0 - fill in proper CAN bus index for telescoping arm motors
    public static int telescopingArmsMotorLeftCanId = -1;
    public static int telescopingArmsMotorRightCanId = -1;

    //TODO - P0 - fill in proper CAN bus index for shooting motors
    public static final int shooterMotorLeftCanId = -1;
    public static final int shooterMotorRightCanId = -1;

    //TODO - P0 - fill in proper CAN bus index for ball storage motors
    public static int ballStorageMotorTopCanId = -1;
    public static int ballStorageMotorBottomCanId = -1;

    //TODO - P0 - fill in proper CAN bus index for drive motors
    public static int driveMotorLeftFrontCanId = -1;
    public static int driveMotorLeftRearCanId = -1;
    public static int driveMotorRightFrontCanId = -1;
    public static int driveMotorRightRearCanId = -1;


  // MOTOR SETTINGS \\

    // Motor magic numbers
    public static boolean driveMotorLeftFrontInverted = false;
    public static boolean driveMotorLeftRearInverted = false;
    public static boolean driveMotorRightFrontInverted = false;
    public static boolean driveMotorRightRearInverted = false;

  // TODO -  the code below needs work ... make it cleaner 



  // TIMING AND SPEEDS \\
    // AngleArm timing \\
    public static final double AngleArmTiming = 0.3;

    // substystem motor speeds \\
    public static final double ShooterEatSpeed = 0.5;
    public static final double ShooterBarfSpeed = 0.5;
    public static final double ShooterDefault = 0.5;




  // HIDS \\
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

  // gains \\
  public static final Gains kGains = new Gains(0.2, 0.0, 0.0, 0.2, 0, 1.0);

}
