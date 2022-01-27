// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

public class Popper extends SubsystemBase {
  /** Creates a new popper. */
  
  public Popper() {}
  
  private final DoubleSolenoid popperSolenoid = new DoubleSolenoid(null, 2,3); 

  public void solenoidPopForward(){
    popperSolenoid.set(kForward);
  }
  public void solenoidPopBackward(){
    popperSolenoid.set(kReverse);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
