//SERIOUSLY UNECCESSARY (well not really...useful for command groups) BUT I MADE IT ANYWAYS

package org.usfirst.frc.team649.robot.commands.grabbercommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.IntakeLeftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeRightSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RunRoller extends Command {

	public double speed;
	//allows for a command to stop
	public RunRoller(double speed){
		this.speed = speed;
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void execute() {
		FishyRobot2015.intakeLeftSubsystem.roller.set(speed);
		FishyRobot2015.intakeRightSubsystem.roller.set(speed);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
