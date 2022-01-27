// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

import edu.wpi.first.wpilibj.Compressor;


public class Pnuematics extends SubsystemBase {
  //TODO add the correct pnuematics module 
  private final Compressor compressor = new Compressor(0, null);
  private final DoubleSolenoid Solenoid = new DoubleSolenoid(null, 0,1);
  private final DoubleSolenoid graber = new DoubleSolenoid(null, 2,3);

  
  public void solenoidIntakeArmForward() {
    Solenoid.set(kForward);
  }

 
  public void solenoidIntakeArmBackward() {
    Solenoid.set(kReverse);
  }

  public void graberLock(){
    graber.set(kForward);
  }

  public void graberUnlock(){
    graber.set(kReverse);
  }

  public void armToGraberLock(){
    graber.set(kForward);
  }

  public void armToGraberUnlock(){
    graber.set(kReverse);
  }

  public void compressorOn() {
    compressor.enableDigital(); //maybe if no work enable analog?
  }

  public void compressorOff() {
    compressor.disable();
  }
}
