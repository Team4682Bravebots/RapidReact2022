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
    
    // A method to make sure that values are retained within the boundaries
    public static double truncateValue(
        double value,
        double minBoundary,
        double maxBoundary)
    {
        double trimmedValue = value;
        if(value < minBoundary)
        {
            trimmedValue = minBoundary;
        }
        else if (value > maxBoundary)
        {
            trimmedValue = maxBoundary;
        }
        return trimmedValue;
    }  
}
