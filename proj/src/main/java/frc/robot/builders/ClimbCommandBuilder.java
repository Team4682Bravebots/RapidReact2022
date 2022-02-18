// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
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
     * 2. ??
     * 3. ??
     * 4. ??
     * @param collection - The grouping of subystems and input content necessary to control various operations in the robot
     * @return The command that represents a succession of commands/steps that form the action associated with this method  
     */
    public static Command buildMediumBarClimb(SubsystemCollection collection)
    {
        // TODO - Jonathan we need your story to be built into here!!!
        // After the notes above are done,  the idea is to take the actions described there and convert them into a set of steps that use the building block commands.
        ParallelCommandGroup movementAndPullupGroup = new ParallelCommandGroup(/* TODO - way more stuff goes here */);        
        SequentialCommandGroup overallCommandGroup = new SequentialCommandGroup(movementAndPullupGroup /* TODO - way more stuff goes here */);        
        return overallCommandGroup;
    }

    /**
     * A builder method to assemble a succession of commands/steps that will attain the high bar climb
     * Notes (Jonathan to fill in his steps from your notes here!!!):
     * 1. action starts with robot hanging from the angle arms on the medium bar where there is essentially no weight on the angle arm hooks
     * 2. ??
     * 3. ??
     * 4. ??
     * @param collection - The grouping of subystems and input content necessary to control various operations in the robot
     * @return The command that represents a succession of commands/steps that form the action associated with this method  
     */
    public static Command buildHighBarClimb(SubsystemCollection collection)
    {
        // TODO - Jonathan we need your story to be built into here!!!
        // After the notes above are done,  the idea is to take the actions described there and convert them into a set of steps that use the building block commands.
        ParallelCommandGroup movementAndPullupGroup = new ParallelCommandGroup(/* TODO - way more stuff goes here */);        
        SequentialCommandGroup overallCommandGroup = new SequentialCommandGroup(movementAndPullupGroup /* TODO - way more stuff goes here */);        
        return overallCommandGroup;
    }
    
    /**
     * A builder method to assemble a succession of commands/steps that will attain the traversal bar climb
     * Notes (Jonathan to fill in his steps from your notes here!!!):
     * 1. action starts with robot hanging from the angle arms on the high bar where there is essentially no weight on the angle arm hooks
     * 2. ??
     * 3. ??
     * 4. ??
     * @param collection - The grouping of subystems and input content necessary to control various operations in the robot
     * @return The command that represents a succession of commands/steps that form the action associated with this method  
     */
    public static Command buildTraversalBarClimb(SubsystemCollection collection)
    {
        // TODO - Jonathan we need your story to be built into here!!!
        // After the notes above are done,  the idea is to take the actions described there and convert them into a set of steps that use the building block commands.
        ParallelCommandGroup movementAndPullupGroup = new ParallelCommandGroup(/* TODO - way more stuff goes here */);        
        SequentialCommandGroup overallCommandGroup = new SequentialCommandGroup(movementAndPullupGroup /* TODO - way more stuff goes here */);        
        return overallCommandGroup;
    }

}
