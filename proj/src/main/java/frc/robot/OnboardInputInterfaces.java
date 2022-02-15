// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
// FRC - Rapid React - 2022
// File: OnboardInputInterfaces.java
// Intent: Forms a class that grants access to onboard inputs.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.common.*;

public class OnboardInputInterfaces
{
    private AHRS navigationSensor = null;
    private double yawOffset = 0.0;

    /**
     * The constructor to build this 'onboard input' conduit
     */
    public OnboardInputInterfaces()
    {
        if(InstalledHardware.navxInstalled)
        {
            navigationSensor = new AHRS(SPI.Port.kMXP);
        }
    }

    /**
     * Initalize the onboard interfaces
     * @param yawOffsetFromFieldLongAxis - Positive values are clockwise offset from long axis of FRC field, negtave are counterclockwise.
     * Parameter intended to allow the robot to reference its position from a known axis on the field.  North (heading 0.0) is assumed to be
     * on the side of the starting side of the field (red or blue). 
     */
    public void initialize(double yawOffsetFromFieldLongAxis)
    {
        if(navigationSensor != null)
        {
            navigationSensor.zeroYaw();
        }
        yawOffset = yawOffsetFromFieldLongAxis;
    }

    /**
     * A method to get the pitch angle of the robot
     * 
     * see: https://pdocs.kauailabs.com/navx-mxp/guidance/terminology/ for more information on what is in this method
     * Note: this code assumes the RoboRio is laying horizontal with NavX attached directly
     * 
     * @return - a double value associated with the magnitude of the pitch (-180.0 to 180.0) for the robot.  Positive values imply nose up attitude.
     */
    public double getPitchAngle()
    {
        double rtnVal = 0.0;
        if(navigationSensor != null)
        {
            switch(Constants.roboRioOrentation)
            {
                // navx - X positive direction, implies pitch comes from negative roll
                case RelayForward:
                    rtnVal = navigationSensor.getRoll() * -1.0;
                    break;
                // navx - X negative direction, implies pitch comes from positive roll
                case UsbForward:
                    rtnVal = navigationSensor.getRoll();
                    break;
                // navx - Y negative direction, implies pitch comes from negative pitch
                case DioForward:
                    rtnVal = navigationSensor.getPitch() * -1.0;
                    break;
                // navx - Y positive direction, implies pitch comes from positive pitch
                case PwmForward:
                    rtnVal = navigationSensor.getPitch();
                    break;
            }

        }
        return rtnVal;
    }

    /**
     * A method to get the roll angle of the robot
     * 
     * see: https://pdocs.kauailabs.com/navx-mxp/guidance/terminology/ for more information on what is in this method
     * Note: this code assumes the RoboRio is laying horizontal with NavX attached directly
     * 
     * @return - a double value associated with the magnitude of the roll (-180.0 to 180.0) for the robot.  Positive values imply left hand roll (left lower than right).
     */
    public double getRollAngle()
    {
        double rtnVal = 0.0;
        if(navigationSensor != null)
        {
            switch(Constants.roboRioOrentation)
            {
                // navx - X positive direction, implies roll comes from negative pitch
                case RelayForward:
                    rtnVal = navigationSensor.getPitch() * -1.0;
                    break;
                // navx - X negative direction, implies roll comes from positive pitch
                case UsbForward:
                    rtnVal = navigationSensor.getPitch();
                    break;
                // navx - Y negative direction, implies roll comes from negative roll
                case DioForward:
                    rtnVal = navigationSensor.getRoll() * -1.0;
                    break;
                // navx - Y positive direction, implies roll comes from positive roll
                case PwmForward:
                    rtnVal = navigationSensor.getRoll();
                    break;
            }

        }
        return rtnVal;
    }

    /**
     * A method to get the yaw angle of the robot - this is the 'spin angle'
     * 
     * see: https://pdocs.kauailabs.com/navx-mxp/guidance/yaw-drift/ for info about yaw drift
     * Note: this code assumes the RoboRio is laying horizontal with NavX attached directly
     * 
     * @return - a double value associated with the magnitude of the yaw (-180.0 to 180.0) for the robot.  Positive values are clockwise, negative are counter-clockwise.
     */
    public double getYawAngle()
    {
        double rtnVal = 0.0;
        if(navigationSensor != null)
        {
            rtnVal = navigationSensor.getYaw() + yawOffset;
            if(rtnVal > 180.0)
            {
                rtnVal = -180.0 + (rtnVal - 180.0);
            }
            else if(rtnVal < -180.0)
            {
                rtnVal = 180.0 - (rtnVal + 180.0);
            }
        }
        return rtnVal;
    }

}
