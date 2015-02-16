package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.intakecommands.IntakeTote;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPosition;
import org.usfirst.frc.team649.robot.commands.lift.RaiseTote;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeLeftSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class PickUpToteSequance extends CommandGroup {
    
    public  PickUpToteSequance() {
    	addParallel(new SetIntakeArmPosition(IntakeLeftSubsystem.PIDConstants.GRABBING_STATE));
    	addSequential(new IntakeTote());
    	addSequential(new WaitCommand(0.2));
    	addSequential(new SetIntakeArmPosition(IntakeLeftSubsystem.PIDConstants.RELEASING_STATE));
    	addSequential(new RaiseTote(ChainLiftSubsystem.PIDConstants.UP));
    }
}
