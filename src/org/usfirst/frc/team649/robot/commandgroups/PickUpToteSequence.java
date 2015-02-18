package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.intakecommands.IntakeTote;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPosition;
import org.usfirst.frc.team649.robot.commands.lift.RaiseTote;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class PickUpToteSequence extends CommandGroup {
    
    public  PickUpToteSequence() {
    	if (FishyRobot2015.intakeRightSubsystem.isToteLimitPressed()){
    		addSequential(new SetIntakeArmPosition(IntakePortSubsystem.PIDConstants.RELEASING_STATE));
    		addSequential(new RaiseTote(ChainLiftSubsystem.PIDConstants.UP));
    	}
    }
}
