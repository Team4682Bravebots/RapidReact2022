package frc.robot.common;

public class MotorUtils
{

    // a method to validate speed input and throw if the speed value is invalid   
    public static void validateMotorSpeedInput(
        double speed,
        String prependMessage,
        String appendMessage)
    {
        if(speed > 1.0 || speed < -1.0)
        {
            throw new IllegalArgumentException(
                prependMessage == null ? "" : prependMessage +
                "input outside of acceptable motor speed range (valid range from -1.0 to 1.0)" +
                appendMessage == null ? "" : appendMessage);
        }        
    }
    
}
