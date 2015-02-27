package org.usfirst.frc.team649.robot.commands;

import org.usfirst.frc.team649.robot.FishyRobot2015;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class WaitTilButtonPressed extends Command {
	public Button button;
	
	public WaitTilButtonPressed(Button b) {
		button = b;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return FishyRobot2015.oi.driver.isManualOverride() || button.get();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
