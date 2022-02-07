// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: DriveTrain.java
// Intent: Forms model for the DriveTrain subsystem.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.common;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class ConsecutiveDigitalInput extends DigitalInput
{

    /* *********************************************************************
    CONSTRUCTORS
    ************************************************************************/
    private Timer timer = new Timer();
    private boolean lastStatus = false;
    private int lastStatusCount = 0;

    // constructor for Jaws
    public ConsecutiveDigitalInput(int channel) 
    {
        super(channel);
    }

    @Override
    public boolean get()
    {
        boolean rtnVal = super.get();
        if(rtnVal != lastStatus)
        {
            lastStatus = rtnVal;
            lastStatusCount = 0;
            timer.reset();
        }
        else
        {
            ++lastStatusCount;
        }
        return rtnVal;
    }

    // method to will tell you how many times in a row this most recent status check has occured - 0 based counter
    public int getStatusCount()
    {
        return lastStatusCount;
    }

    // a method to know how long this digital input has been reporting the most recent status check
    public double getStatusTimeInSeconds()
    {
        return timer.get();
    }
}
