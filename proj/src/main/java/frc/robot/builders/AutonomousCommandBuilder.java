// ************************************************************
// Bishop Blanchet Robotics
// Home of the Cybears
// FRC - Rapid React - 2022
// File: AutonomousCommandBuilder.java
// Intent: Forms the autonomus command initiation logic for various autonomous seutps.
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

public class AutonomousCommandBuilder
{

    /**
     * A method to build an initial set of automated steps for first 15 seconds
     * @param collection - The grouping of subystems and input content necessary to control various operations in the robot
     * @return The command that represents a succession of commands/steps that form the action associated with this method  
     */
    public static Command buildTestCollectAndShoot(SubsystemCollection collection)
    {
        // TODO - much work needed in this command group here!!!

        // example automation to attempt to
        // 1. shoot first ball
        JawsReverseHighGoal jawsToReverseHighGoal = new JawsReverseHighGoal(collection.getJawsSubsystem());
        ShooterAutomatic shootReverseHighGoal = new ShooterAutomatic(
            collection.getShooterSubsystem(),
            collection.getBallStorageSubsystem(),
            collection.getJawsSubsystem(),
            true,
            false);

        // 2. move toward the second ball 
        DriveCommand driveForwardToBall = new DriveCommand(collection.getDriveTrainSubsystem(), 49.7, 10.0, 1.9);
        JawsIntake jawsIntake = new JawsIntake(collection.getJawsSubsystem());
        ParallelCommandGroup forwardAndIntake = new ParallelCommandGroup(driveForwardToBall, jawsIntake);

        // 3. pickup another ball by intake/storage turned on and move forward to scoop the ball
        ShooterAutomatic shooterIntake = new ShooterAutomatic(
            collection.getShooterSubsystem(),
            collection.getBallStorageSubsystem(),
            collection.getJawsSubsystem(),
            false,
            false);
        DriveCommand driveForwardBallScoop = new DriveCommand(collection.getDriveTrainSubsystem(), 5.0, 0.0, 0.5);
        ParallelCommandGroup ballIntakeScoop = new ParallelCommandGroup(driveForwardBallScoop, shooterIntake);

        // 4. move back to the shooting position and move the arms up to 
        DriveCommand driveReverseToHighGoal = new DriveCommand(collection.getDriveTrainSubsystem(), 54.7, -10.0, 2.1);
        ParallelCommandGroup returnToShooterSpot = new ParallelCommandGroup(driveReverseToHighGoal, jawsToReverseHighGoal);

        // 5. shoot second ball
        ShooterAutomatic shootSecondReverseHighGoal = new ShooterAutomatic(
            collection.getShooterSubsystem(),
            collection.getBallStorageSubsystem(),
            collection.getJawsSubsystem(),
            true,
            false);

        // 6. build the command group
        SequentialCommandGroup commandGroup = new SequentialCommandGroup(
            jawsToReverseHighGoal,
            shootReverseHighGoal,
            forwardAndIntake,
            ballIntakeScoop,
            returnToShooterSpot,
            shootSecondReverseHighGoal
        );

        return commandGroup;
    }
    

    /**
     * A method to build all of the stop commands and run them in parallel
     * @param collection - The grouping of subystems and input content necessary to control various operations in the robot
     * @return The command that represents a succession of commands/steps that form the action associated with this method  
     */
    public static Command buildAllStop(SubsystemCollection collection)
    {
        ParallelCommandGroup stopCommands = new ParallelCommandGroup();

        if(collection.getBallStorageSubsystem() != null)
        {
            stopCommands.addCommands(new BallStorageAllStopManual(collection.getBallStorageSubsystem()));
        }

        if(collection.getDriveTrainSubsystem() != null)
        {
            stopCommands.addCommands(new RunCommand(
                () ->
                collection.getDriveTrainSubsystem().arcadeDrive(0.0, 0.0),
                collection.getDriveTrainSubsystem()));
        }

        if(collection.getJawsSubsystem() != null)
        {
            stopCommands.addCommands(new JawsAllStop(collection.getJawsSubsystem()));
        }

        if(collection.getShooterSubsystem() != null)
        {
            stopCommands.addCommands(new ShooterAllStop(collection.getShooterSubsystem()));
        }

        return stopCommands;
    }
}
