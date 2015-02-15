package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.autowinchcommands.WinchTotesIn;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPosition;
import org.usfirst.frc.team649.robot.subsystems.IntakeLeftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedDriving;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoWinchAndDrive extends CommandGroup {
    
    public  AutoWinchAndDrive() {
    	//move arms out (state position), winch in the three containers, drive forward, all sequential
    	addSequential(new SetIntakeArmPosition(IntakeLeftSubsystem.PIDConstants.STORE_STATE));
    	addParallel(new WinchTotesIn());
    	//wait command amount == time between start winching & start driving
    	addSequential(new WaitCommand(1));
    	addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_WINCH_DRIVE_DISTANCE));

    }
}
