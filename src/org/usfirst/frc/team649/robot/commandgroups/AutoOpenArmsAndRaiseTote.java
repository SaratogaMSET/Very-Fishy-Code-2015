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
public class AutoOpenArmsAndRaiseTote extends CommandGroup {
    
    public  AutoOpenArmsAndRaiseTote(boolean up) {
    	SmartDashboard.putString("Now setting arms", "to release");
    	
	    	addParallel(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.RELEASING_STATE));
	    //	addSequential(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.RELEASING_STATE));
  
	    	//addSequential(new WaitCommand(1));
    	addSequential(new RaiseToteWithoutPID(up));
        //addSequential(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.GRABBING_STATE));
       //addSequential(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.GRABBING_STATE));
        
    }
}
