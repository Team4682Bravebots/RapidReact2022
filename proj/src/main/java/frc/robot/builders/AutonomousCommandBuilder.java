// ************************************************************
// Bishop Blanchet Robotics
// Historic home of the 'BraveBots'
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
    
}
