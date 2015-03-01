//SERIOUSLY UNECCESSARY (well not really...useful for command groups) BUT I MADE IT ANYWAYS

package org.usfirst.frc.team649.robot.commands.intakecommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeStarboardSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RunRollers extends Command {

	public double choosenSpeed;
	//allows for a command to stop
	public RunRollers(double speed){
		choosenSpeed = speed;
	}
	@Override
	protected void initialize() {
		FishyRobot2015.intakeLeftSubsystem.roller.set(choosenSpeed);
		FishyRobot2015.intakeRightSubsystem.roller.set(-choosenSpeed);
	}

	@Override
	protected void execute() {

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
