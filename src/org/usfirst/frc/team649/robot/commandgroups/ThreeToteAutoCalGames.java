package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnOneSideNoPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnSetTimeCommand;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRollers;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPIDThreeState;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.ResetEncoders;
import org.usfirst.frc.team649.robot.commands.lift.RunTilResetLimit;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedDriving;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 *
 */
public class ThreeToteAutoCalGames extends CommandGroup {
    
    public  ThreeToteAutoCalGames() {
    	double pickUpTime = 1.5;
    	addSequential(new ResetEncoders());
		//move in, assume already with tote inside
    	//addSequential(new SetIntakeArmPositionWithoutPID(PIDConstants.GRABBING_STATE));
    	
//    	addSequential(new RunRollers(0.6, 0.6));
//    	addSequential(new WaitCommand(pickUpTime));
//    	addSequential(new RunRollers(0,0));
    	
    	addSequential(new AutoOpenArmsAndRaiseTote(true));
    	addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_START_TO_TOTE));
    	
    	//addSequential(new WaitCommand(1.2));
    	
    	//second tote
    	addParallel(new SetIntakeArmPositionWithoutPID(PIDConstants.GRABBING_STATE));

    	addSequential(new RunRollers(0.6, 0.6));
    	addSequential(new WaitCommand(pickUpTime));
    	addSequential(new RunRollers(0,0));
    	addSequential(new WaitCommand(0.2));
    	
    	addSequential(new RunRollers(0.5, 0.5));
    	addSequential(new WaitCommand(0.2));
    	addSequential(new RunRollers(0,0));
    	
    	addSequential(new WaitForChildren());
    	
    	addSequential(new AutoOpenArmsAndRaiseTote(true));
    	addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_BETWEEN_TOTES));
    	
    	//addSequential(new WaitCommand(1.2));
    	
    	//third tote
    	addParallel(new SetIntakeArmPositionWithoutPID(PIDConstants.GRABBING_STATE));
    	addSequential(new RunRollers(0.6, 0.6));
//    	addSequential(new WaitCommand(pickUpTime));
//    	addSequential(new RunRollers(0,0));
    	
    //	addSequential(new WaitForChildren());
    	addSequential(new WaitCommand(.1));
    	
    	//addSequential(new AutoOpenArmsAndRaiseTote(true));
    	addSequential(new TurnSetTimeCommand(1.2, 0.7, 0.03));
    	
    	addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_TOTE_TO_AUTO_ZONE));
    	
    	//addSequential(new WaitCommand(0.5));
    	addSequential(new RunRollers(0,0));
    	addSequential(new SetIntakeArmPositionWithoutPID(PIDConstants.RELEASING_STATE));
    	addSequential(new ChangeLiftHeight(-ChainLiftSubsystem.PIDConstants.TOTE_PICK_UP_HEIGHT));
    	
    	addParallel(new DriveSetDistanceWithPID(-5.0));
    	addSequential(new RunTilResetLimit());
		//addSequential(new ChangeLiftHeight(ChainLiftSubsystem.PIDConstants.ENCODER_RESET_OFFSET));
		addSequential(new ResetEncoders());
		
		//^^automatically makes the firstStageOfScore variable true again
    	
    }
}
