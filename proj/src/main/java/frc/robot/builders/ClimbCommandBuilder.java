// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: ClimbCommandBuilder.java
// Intent: Forms the key command build logic for the robot to climb in the 'hangar zone' at the end of the match.
// ************************************************************

// ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ ʕ •ᴥ•ʔ ʕ•ᴥ•  ʔ ʕ  •ᴥ•ʔ ʕ •`ᴥ´•ʔ ʕ° •° ʔ 

package frc.robot.builders;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;

import frc.robot.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class ClimbCommandBuilder
{
    /**
     * A builder method to assemble a succession of commands/steps that will attain the medium bar climb
     * Notes (Jonathan to fill in his steps from your notes here!!!):
     * 1. action starts with robot at ground level where, in plan view, the telescoping arms have been placed between the medium bar and the high bar
     * 2. extend telescoping arms to higher than medium bar
     * 3. robot drives backwards until telescoping arms make contact with the medium bar
     * 4. retract telescoping arms to lift robot to medium bar
     * 5. angle arms swing forward until hooks make contact with medium bar
     * @param collection - The grouping of subystems and input content necessary to control various operations in the robot
     * @return The command that represents a succession of commands/steps that form the action associated with this method  
     */
    public static Command buildMediumBarClimb(SubsystemCollection collection)
    {
        // TODO - Jonathan we need your story to be built into here!!!
        // After the notes above are done,  the idea is to take the actions described there and convert them into a set of steps that use the building block commands.
        TelescopingArmExtendHigh extendArms = new TelescopingArmExtendHigh(collection.getTelescopingArmsSubsystem());
        DriveCommand driveReverseToBar = new DriveCommand(collection.getDriveTrainSubsystem(), -5, 0, 0);
        TelescopingArmRetract liftRobot = new TelescopingArmRetract(collection.getTelescopingArmsSubsystem());
        JawsAngleVariable angleArmForward = new JawsAngleVariable(collection.getJawsSubsystem(), 45);
        ParallelCommandGroup driveAndExtendGroup = new ParallelCommandGroup(extendArms, driveReverseToBar);     
        SequentialCommandGroup overallCommandGroup = new SequentialCommandGroup(driveAndExtendGroup, liftRobot, angleArmForward);        
        return overallCommandGroup;
    }

    /**
     * A builder method to assemble a succession of commands/steps that will attain the high bar climb
     * Notes (Jonathan to fill in his steps from your notes here!!!):
     * 1. action starts with robot hanging from the angle arms on the medium bar where there is essentially no weight on the angle arm hooks
     * 2. angle arms swing forwards to shift weight onto angle arms, disconnect telescoping arms, and tilt robot
     * 3. extend telescoping arms until it has extended past high bar
     * 4. angle arms swing backwards to tilt robot until telescoping arms make contact with high bar
     * 5. retract telescoping arms until angle arms are disconnected from middle bar
     * 6. angle arms completely swing back in front of high bar
     * 7. retract telescoping arms up to high bar
     * 8. angle arms swing forward to hook onto high bar
     * @param collection - The grouping of subystems and input content necessary to control various operations in the robot
     * @return The command that represents a succession of commands/steps that form the action associated with this method  
     */
    public static Command buildHighBarClimb(SubsystemCollection collection)
    {
        // TODO - Jonathan we need your story to be built into here!!!
        // After the notes above are done,  the idea is to take the actions described there and convert them into a set of steps that use the building block commands.
        JawsAngleVariable angleArmForwardTilt = new JawsAngleVariable(collection.getJawsSubsystem(), 90);
        TelescopingArmExtendHigh extendArms = new TelescopingArmExtendHigh(collection.getTelescopingArmsSubsystem());
        JawsAngleVariable angleArmBackwardTilt = new JawsAngleVariable(collection.getJawsSubsystem(), -30); 
        TelescopingArmRetract liftRobot = new TelescopingArmRetract(collection.getTelescopingArmsSubsystem());
        JawsAngleVariable angleArmBackward = new JawsAngleVariable(collection.getJawsSubsystem(), -90); 
        JawsAngleVariable angleArmForward = new JawsAngleVariable(collection.getJawsSubsystem(), 30);
        ParallelCommandGroup liftAndAngleArmGroup = new ParallelCommandGroup(liftRobot, angleArmBackward);
        ParallelCommandGroup tiltAndExtendGroup = new ParallelCommandGroup(extendArms, angleArmForwardTilt);   
        SequentialCommandGroup overallCommandGroup = new SequentialCommandGroup(tiltAndExtendGroup, angleArmBackwardTilt, liftAndAngleArmGroup, angleArmForward);        
        return overallCommandGroup;
    }
    
    /**
     * A builder method to assemble a succession of commands/steps that will attain the traversal bar climb
     * Notes (Jonathan to fill in his steps from your notes here!!!):
     * 1. action starts with robot hanging from the angle arms on the high bar where there is essentially no weight on the angle arm hooks
     * 2. angle arms swing forward to shift weight onto angle arms, disconnect telescoping arms, and tilt robot
     * 3. extend telescoping arms until it has extended past high bar
     * 4. angle arms swing back to tilt robot until telescoping arms make contact with high bar
     * 5. retract telescoping arms to lift robot to high bar
     * @param collection - The grouping of subystems and input content necessary to control various operations in the robot
     * @return The command that represents a succession of commands/steps that form the action associated with this method  
     */
    public static Command buildTraversalBarClimb(SubsystemCollection collection)
    {
        // TODO - Jonathan we need your story to be built into here!!!
        // After the notes above are done,  the idea is to take the actions described there and convert them into a set of steps that use the building block commands.
        JawsAngleVariable angleArmForwardTilt = new JawsAngleVariable(collection.getJawsSubsystem(), 90);
        TelescopingArmExtendHigh extendArms = new TelescopingArmExtendHigh(collection.getTelescopingArmsSubsystem());
        JawsAngleVariable angleArmBackwardTilt = new JawsAngleVariable(collection.getJawsSubsystem(), -30); 
        TelescopingArmRetract liftRobot = new TelescopingArmRetract(collection.getTelescopingArmsSubsystem());
        ParallelCommandGroup tiltAndExtendGroup = new ParallelCommandGroup(extendArms, angleArmForwardTilt);   
        SequentialCommandGroup overallCommandGroup = new SequentialCommandGroup(tiltAndExtendGroup, angleArmBackwardTilt, liftRobot);    
        return overallCommandGroup;
    }

}
