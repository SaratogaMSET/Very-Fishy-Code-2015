package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.intakecommands.IntakeTote;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRollers;
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
		addSequential(new SetIntakeArmPosition(IntakePortSubsystem.PIDConstants.GRABBING_STATE));
		addSequential(new IntakeTote());
		//run for a bit extra
		addSequential(new RunRollers(IntakePortSubsystem.INTAKE_ROLLER_SPEED));
		addSequential(new WaitCommand(.3));
		addSequential(new RunRollers(IntakePortSubsystem.INTAKE_ROLLER_OFF_SPEED));
		addSequential(new WaitCommand(.3));
		addSequential(new RaiseTote(ChainLiftSubsystem.PIDConstants.UP));
    }
}
