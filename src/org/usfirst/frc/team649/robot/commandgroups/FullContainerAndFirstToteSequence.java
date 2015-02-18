package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.containergrabbercommands.ClampContainerGrabber;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.IntakeTote;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPosition;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.RaiseTote;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedDriving;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class FullContainerAndFirstToteSequence extends CommandGroup {

	//TODO true is for adding a drive command while we are waiting for the button, only for autonomous
	public FullContainerAndFirstToteSequence(boolean autonomous) { 
		PickUpContainer pickUpContainer = new PickUpContainer();
		if (!pickUpContainer.canPickUp){
			//if it cant pick up, exit
			return;
		}
		
		addSequential(pickUpContainer);
		// NOW CHECK FOR BUTTONS

		// wait until intake button pressed...check for problems with multiple
		// systems TODO
		
		if (autonomous){
			//add a drive command
	    	addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_CONTAINER_TO_TOTE));
		}
		
		while (!FishyRobot2015.oi.operator.purgeButton.get()) {
			
		}
		addParallel(new SetIntakeArmPosition(IntakePortSubsystem.PIDConstants.GRABBING_STATE));

		// pull in tote and unclamp
		addSequential(new IntakeTote());
		addSequential(new WaitCommand(.2));
		addSequential(new ClampContainerGrabber(false));
		addSequential(new WaitCommand(.1));
		// go down and regrip
		addSequential(new ChangeLiftHeight(ChainLiftSubsystem.PIDConstants.CONTAINER_REGRIP_LOWER_HEIGHT));
		addSequential(new WaitCommand(.2));
		addSequential(new ClampContainerGrabber(true));
		// continue to drive height offset (you should be at intermediate step
		// here), hopefully you catch the tote
		addParallel(new SetIntakeArmPosition(IntakePortSubsystem.PIDConstants.RELEASING_STATE));
		addSequential(new WaitCommand(.2));
		addSequential(new RaiseTote(ChainLiftSubsystem.PIDConstants.UP));
	}
}
