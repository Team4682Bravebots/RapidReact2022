// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: ButtonPress.java
// Intent: Forms a manual command to print the button number.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.commands;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.PS4Controller.Button;

import java.util.ArrayDeque;
import java.util.Iterator;

public class ButtonPress extends CommandBase implements Sendable
{
  private static final int maxPreviousButtonCount = 10;
  private static ArrayDeque<ButtonPress> previousButtons = new ArrayDeque<ButtonPress>(ButtonPress.maxPreviousButtonCount);
  private static Timer timer = new Timer();
  private boolean timerStarted = false;

  private String inputDevice = "";
  private String inputAction = "";
  private double initTime = 0.0;
  private double executeTime = 0.0;
  private double finalTime = 0.0;
  private boolean didAddMe = false;

  /**
   * The constructor 
   */
  public ButtonPress(
    String inputDeviceDescription,
    String inputActionDescription)
  {
      inputDevice = inputDeviceDescription;
      inputAction = inputActionDescription;

      if(!timerStarted)
      {
        timer.reset();
        timer.start();
        timerStarted = true;
      }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    didAddMe = false;
    initTime = timer.get();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    executeTime = timer.get();
  }

  @Override
  public void initSendable(SendableBuilder builder)
  {
    if(didAddMe == false)
    {
      previousButtons.addFirst(this);
      didAddMe = true;
    }

    int inx = 0;
    for (Iterator buttonIter = previousButtons.iterator(); buttonIter.hasNext(); ++inx)
    {
      ButtonPress pastButtonPress = (ButtonPress)buttonIter.next();
      if(inx < ButtonPress.maxPreviousButtonCount)
      {
        builder.addStringProperty("PreviousButtonPress" + inx, pastButtonPress::toString, null);
      }
    }
    for(int jnx = inx - ButtonPress.maxPreviousButtonCount; inx >= 0; --jnx)
    {
      previousButtons.removeLast();
    }
  }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    finalTime = timer.get();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return true;
  }

  @Override
  public String toString()
  {
    return inputDevice + ":" + inputAction + ":" + Math.round(initTime*10.0)/10.0 + ":" + Math.round(executeTime*10)/10 + ":" + Math.round(finalTime*10.0)/10.0;
  }
}
