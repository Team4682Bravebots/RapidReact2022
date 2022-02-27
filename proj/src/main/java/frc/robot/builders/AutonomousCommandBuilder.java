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
        // 2. pickup another ball
        // 3. shoot second ball
        DriveCommand driveReverseToShot = new DriveCommand(collection.getDriveTrainSubsystem(), -22.7, 0.0, 1.9);
        JawsForwardHighGoal jawsToHighGoal = new JawsForwardHighGoal(collection.getJawsSubsystem());
        ShooterForwardHighShot shootHighGoal = new ShooterForwardHighShot(collection.getShooterSubsystem(), collection.getBallStorageSubsystem());
        DriveCommand rotateTowardBall = new DriveCommand(collection.getDriveTrainSubsystem(), 0.0, 130.0, 1.0);
        DriveCommand driveForwardToBall = new DriveCommand(collection.getDriveTrainSubsystem(), 99.7, 10.0, 2.9);
        JawsIntake jawsIntake = new JawsIntake(collection.getJawsSubsystem());
        ShooterIntake shooterIntake = new ShooterIntake(collection.getShooterSubsystem(), collection.getBallStorageSubsystem());
        DriveCommand rotateTowardBasket = new DriveCommand(collection.getDriveTrainSubsystem(), 0.0, -130.0, 1.0);
        DriveCommand driveForwardToHighGoal = new DriveCommand(collection.getDriveTrainSubsystem(), 99.7, -10.0, 2.9);
        JawsForwardHighGoal jawsToHighGoal2 = new JawsForwardHighGoal(collection.getJawsSubsystem());
        ShooterForwardHighShot shootHighGoal2 = new ShooterForwardHighShot(collection.getShooterSubsystem(), collection.getBallStorageSubsystem());
        SequentialCommandGroup commandGroup = new SequentialCommandGroup(
            driveReverseToShot,
            jawsToHighGoal,
            shootHighGoal,
            rotateTowardBall,
            driveForwardToBall,
            jawsIntake,
            shooterIntake,
            rotateTowardBasket,
            driveForwardToHighGoal,
            jawsToHighGoal2,
            shootHighGoal2
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
