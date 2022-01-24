// ************************************************************
// Bischop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: Gains.java
// Intent: A class to hold gains for MotionMagic use.
// ************************************************************

package frc.robot;

public class Gains {
	public final double kP;
	public final double kI;
	public final double kD;
	public final double kF;
	public final int kIzone;
	public final double kPeakOutput;
	
	public Gains(double _kP, double _kI, double _kD, double _kF, int _kIzone, double _kPeakOutput){
		kP = _kP;
		kI = _kI;
		kD = _kD;
		kF = _kF;
		kIzone = _kIzone;
		kPeakOutput = _kPeakOutput;
	}
}
