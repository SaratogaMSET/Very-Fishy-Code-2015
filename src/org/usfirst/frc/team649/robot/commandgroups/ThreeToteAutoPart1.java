package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.autowinchcommands.WinchTotesIn;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRollers;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPID;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.RaiseToteWithoutPID;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedDriving;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ThreeToteAutoPart1 extends CommandGroup {
    
    public  ThreeToteAutoPart1() {
    	//move arms out (state position), winch in the three containers, drive forward, all sequential
    	//Pick Up first tote
    	addParallel(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.GRABBING_STATE));
    	addParallel(new DriveSetDistanceWithPID(DrivetrainSubsystem.EncoderBasedDriving.AUTO_START_TO_TOTE, 0.1));
    	addSequential(new PickUpToteSequence());
    	
    	
    	//Get contarior out of the way and move to next tote
    	addSequential(new RaiseToteWithoutPID(true));
    	addParallel(new ChangeLiftHeight(3.0));
    	addParallel(new RunRollers(-1.0, -1.0));
    	addParallel(new DriveSetDistanceWithPID(DrivetrainSubsystem.EncoderBasedDriving.AUTO_BETWEEN_TOTES, 0.1));
    	addSequential(new WaitCommand(2.0));
    
    	addSequential(new RunRollers(0.0, 0.0));
    	
    	//pick up second tote
//    	addSequential(new PickUpToteSequence());
//		addParallel(new RaiseTote(false));

    }
}
