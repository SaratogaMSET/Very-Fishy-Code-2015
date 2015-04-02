package org.usfirst.frc.team649.robot.commands;

import org.usfirst.frc.team649.robot.FishyRobot2015;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WaitTilButtonPressed extends Command {
	public Button button1;
	public Button button2;
	public boolean twoButtons;
	
	public WaitTilButtonPressed(Button b1) {
		button1 = b1;
		twoButtons = false;
	}
	
	public WaitTilButtonPressed(Button b1, Button b2) {
		button1 = b1;
		button2 = b2;
		twoButtons = true;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		SmartDashboard.putString("ACTIVE COMMAND", "WaitTilButtonPressed");
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		boolean done = button1.get();
		
		if (twoButtons){
			done = done && button2.get();
		}
		
		return FishyRobot2015.oi.driver.isManualOverride() || done;
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
