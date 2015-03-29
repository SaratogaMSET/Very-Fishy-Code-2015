package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.intakecommands.IntakeTote;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRollers;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.RaiseToteWithoutPID;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem.PIDConstants;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeStarboardSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ContainerFirstToteSemiAuto extends CommandGroup {
	//ASSUME IN RIGHT POSITION, CHECKED WITHIN FISHY, after score all
	public ContainerFirstToteSemiAuto(double numTotes){
		//down 2 totes
		System.out.println("Entered Cont Semi Auto");
		addSequential(new ChangeLiftHeight(-numTotes * PIDConstants.TOTE_PICK_UP_HEIGHT));
		
		//second stage, run rollers quickly before pick up
		//addSequential(new IntakeTote()); //add a timeout to this fct...maybe  TODO PLS add back with bumpers
		
		//just in case
		addSequential(new RunRollers(IntakePortSubsystem.INTAKE_ROLLER_SPEED, IntakeStarboardSubsystem.INTAKE_ROLLER_SPEED));
		addSequential(new WaitCommand(.3));
		addSequential(new RunRollers(0,0));
		
		addSequential(new RaiseToteWithoutPID(ChainLiftSubsystem.PIDConstants.UP));
	}
	
	
}
