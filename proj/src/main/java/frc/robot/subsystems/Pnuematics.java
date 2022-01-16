// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

import edu.wpi.first.wpilibj.Compressor;


public class pnuematics extends SubsystemBase {
  private final Compressor compressor = new Compressor(0);
  private final DoubleSolenoid Solenoid = new DoubleSolenoid(0,1);

  
  public void solenoidIntakeArmForward() {
    Solenoid.set(kForward);
  }

 
  public void solenoidIntakeArmBackward() {
    Solenoid.set(kReverse);
  }

  public void compressorOn() {
    compressor.start(); 
  }

  public void compressorOff() {
    compressor.stop();
  }
}
