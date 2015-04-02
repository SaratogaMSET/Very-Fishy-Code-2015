package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.intakecommands.IntakeTote;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithPID;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class IntakeToteAndSetArm extends CommandGroup {
    
    public  IntakeToteAndSetArm() {

    	addSequential(new SetIntakeArmPositionWithPID(IntakePortSubsystem.PIDConstants.GRABBING_STATE));
    	addSequential(new IntakeTote());
    }
}
