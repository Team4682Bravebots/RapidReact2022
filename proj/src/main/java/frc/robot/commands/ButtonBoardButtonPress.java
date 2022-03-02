// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: ButtonBoardButtonPress.java
// Intent: Forms a manual command to print the button number.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ButtonBoardButtonPress extends CommandBase {

 private int buttonNumberPressed = 0;
 private String explanationString = "";

  /**
   * The constructor 
   */
  public ButtonBoardButtonPress(String explanation, int buttonNumber)
  {
      buttonNumberPressed = buttonNumber;
      explanationString = explanation;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
      System.out.println(explanationString + " button pressed == " + buttonNumberPressed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return true;
  }
}
