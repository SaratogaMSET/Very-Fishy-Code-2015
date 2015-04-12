package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.HalEffectCompensationOffset;
import org.usfirst.frc.team649.robot.commands.lift.ResetEncoders;
import org.usfirst.frc.team649.robot.commands.lift.RunTilResetLimit;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ResetLift extends CommandGroup {
    
    public  ResetLift() {
    	if (FishyRobot2015.chainLiftSubsystem.setpointHeight <= PIDConstants.MIN_HAL_EFFECT_COMPENSATION_HEIGHT){
			addSequential(new HalEffectCompensationOffset(true));
		}
    	
    	addSequential(new RunTilResetLimit());
    	addSequential(new ResetEncoders());
    	
		addSequential(new ChangeLiftHeight(PIDConstants.CONTAINER_RESET_OFFSET));

    }
}
