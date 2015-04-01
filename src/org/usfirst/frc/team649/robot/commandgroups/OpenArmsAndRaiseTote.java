package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPIDThreeState;
import org.usfirst.frc.team649.robot.commands.lift.RaiseToteWithoutPID;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class OpenArmsAndRaiseTote extends CommandGroup {
    
    public  OpenArmsAndRaiseTote(boolean up) {
    	SmartDashboard.putString("Now setting arms", "to release");
    	if(!FishyRobot2015.chainLiftSubsystem.isPastTop) {
	    	//addSequential(new SetIntakeArmPositionWithoutPIDThreeState(IntakePortSubsystem.PIDConstants.RELEASING_STATE));
	    	addSequential(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.RELEASING_STATE));
    	}
	    	//addSequential(new WaitCommand(1));
    	addSequential(new RaiseToteWithoutPID(up));
        //addSequential(new SetIntakeArmPositionWithoutPIDThreeState(IntakePortSubsystem.PIDConstants.GRABBING_STATE));
       addSequential(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.GRABBING_STATE));
        
    }
}
