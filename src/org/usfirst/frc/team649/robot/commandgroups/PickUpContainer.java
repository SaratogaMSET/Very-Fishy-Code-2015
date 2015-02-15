package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.containergrabbercommands.ClampContainerGrabber;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class PickUpContainer extends CommandGroup {

	public boolean canPickUp;
	
	public PickUpContainer() {
		if (!FishyRobot2015.chainLiftSubsystem.isAtBase) {
			canPickUp = false;
		} else {
			// otherwise actually raise it
			canPickUp = true;
			addSequential(new ClampContainerGrabber(true));
			addSequential(new WaitCommand(.2));
			addSequential(new ChangeLiftHeight(ChainLiftSubsystem.PIDConstants.CONTAINER_PICK_UP_RAISE_HEIGHT));
		}
	}
}
