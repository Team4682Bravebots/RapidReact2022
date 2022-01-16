// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {


 // hid ports \\ 
   public static int portDriverController = 0;
   public static int portCoDriverController = 1;

 //motor ports \\  //TODO 
	 public static int climberS1MotorLeft = -1;
   public static int climberS1MotorRight = 9;

   public static int climberS2MotorLeft = -1;
   public static int climberS2MotorRight = 8;

   public static int armPort = -1;
   
   public static final int intakeMotorLeft = 1;
   public static final int intakeMotorRight = 2;



  // climber reach points \\
   public static int s1Defualt = 0;
   public static int s1Extended = 1; //TODO need to calculate top of climber
   public static int s1EndGame = 2; //TODO should be just less then double "s1Extended"
   
   public static int s2Defualt = 0; 
   public static int s2Extended = 4062 * 1; //TODO need to calculate top of climber 
   public static int s2EndGame = 4062 * 2; //TODO should be just less then double "s2Extended"

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

 public static final Gains kGains = new Gains(0.2, 0.0, 0.0, 0.2, 0, 1.0);




  



}
