package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRollers;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPIDThreeState;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.ResetEncoders;
import org.usfirst.frc.team649.robot.commands.lift.RunTilResetLimit;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedDriving;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ThreeToteAutoCalGames extends CommandGroup {
    
    public  ThreeToteAutoCalGames() {
    	double pickUpTime = 1;
    	addSequential(new ResetEncoders());
		//move in, assume already with tote inside
    	addSequential(new SetIntakeArmPositionWithoutPID(PIDConstants.GRABBING_STATE));
    	
    	addSequential(new RunRollers(0.6, 0.6));
    	addSequential(new WaitCommand(pickUpTime));
    	addSequential(new RunRollers(0,0));
    	
    	addSequential(new AutoOpenArmsAndRaiseTote(true));
    	addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_BETWEEN_TOTES));
    	
    	//addSequential(new WaitCommand(1.2));
    	
    	//second tote
    	addSequential(new SetIntakeArmPositionWithoutPID(PIDConstants.GRABBING_STATE));

    	addSequential(new RunRollers(0.6, 0.6));
    	addSequential(new WaitCommand(pickUpTime));
    	addSequential(new RunRollers(0,0));
    	
    	addSequential(new AutoOpenArmsAndRaiseTote(true));
    	addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_BETWEEN_TOTES));
    	
    	//addSequential(new WaitCommand(1.2));
    	
    	//third tote
    	addSequential(new SetIntakeArmPositionWithoutPID(PIDConstants.GRABBING_STATE));
    	addSequential(new RunRollers(0.6, 0.6));
    	addSequential(new WaitCommand(pickUpTime));
    	addSequential(new RunRollers(0,0));
    	
    	addSequential(new AutoOpenArmsAndRaiseTote(true));
    	addSequential(new TurnWithPIDCommand(90));
    	
    	addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_TOTE_TO_AUTO_ZONE));
    	
    	//addSequential(new WaitCommand(0.5));
    	
    	addSequential(new SetIntakeArmPositionWithoutPID(PIDConstants.RELEASING_STATE));
    	addSequential(new RunTilResetLimit());
		//addSequential(new ChangeLiftHeight(ChainLiftSubsystem.PIDConstants.ENCODER_RESET_OFFSET));
		addSequential(new ResetEncoders());
		//^^automatically makes the firstStageOfScore variable true again
    	
    }
}
