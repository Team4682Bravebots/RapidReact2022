// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.interfaces;
import frc.robot.subsystems.graber;


public class graberControll extends CommandBase {
  public graber graberSubsystem;
  public interfaces interfaceSubsystem;
  double imput;
  int pov;
  int _smoothing;
  int _pov;

  public graberControll(graber graberSubsystem, interfaces interfaceSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.graberSubsystem = graberSubsystem;
    addRequirements(graberSubsystem);

    this.interfaceSubsystem = interfaceSubsystem;
    addRequirements(interfaceSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
 
    imput = Math.round(interfaceSubsystem.getXboxRawAxis(Constants.joystickX) * 10) / 10;
    int imputToDegree = 2048/360 * 2000;

    double targetPos = imput * imputToDegree;
    double currentPOS = Math.round(graberSubsystem.getPosition());


    
    System.out.println(currentPOS);
    System.out.println("imput" + imput);
    
    if(targetPos < currentPOS-300 || targetPos > currentPOS+300){
    
       graberSubsystem.setClimberPostion(targetPos, imputToDegree);
    }

     pov = interfaceSubsystem.getXboxPov();

        if (_pov == pov) {
			/* no change */
		} else if (_pov == 180) { // D-Pad down
			/* Decrease smoothing */
			_smoothing--;
			if (_smoothing < 0)
				_smoothing = 0;
        graberSubsystem.setSmoothing(_smoothing);

			System.out.println("Smoothing is set to: " + _smoothing);
		} else if (_pov == 0) { // D-Pad up
			/* Increase smoothing */
			_smoothing++;
			if (_smoothing > 8)
				_smoothing = 8;
      graberSubsystem.setSmoothing(_smoothing);
			System.out.println("Smoothing is set to: " + _smoothing);
		}
		_pov = pov; /* save the pov value for next time */


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}