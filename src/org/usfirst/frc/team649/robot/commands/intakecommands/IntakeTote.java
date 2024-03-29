package org.usfirst.frc.team649.robot.commands.intakecommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeStarboardSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeTote extends Command {

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		FishyRobot2015.intakeLeftSubsystem.roller.set(-IntakePortSubsystem.INTAKE_ROLLER_SPEED);
		FishyRobot2015.intakeRightSubsystem.roller.set(-IntakeStarboardSubsystem.INTAKE_ROLLER_SPEED);
	}

	@Override
	protected void execute() {

		SmartDashboard.putString("ACTIVE COMMAND", "IntakeTote");
		// TODO Auto-generated method stub
		
		//so the tote will only stop moving when both triggers have been hit
//		if (FishyRobot2015.intakeLeftSubsystem.isToteLimitPressed()){
//			FishyRobot2015.intakeLeftSubsystem.roller.set(.3);
//		}
//		if (FishyRobot2015.intakeRightSubsystem.isToteLimitPressed()){
//			FishyRobot2015.intakeRightSubsystem.roller.set(.3);
//		}
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return FishyRobot2015.intakeLeftSubsystem.isToteLimitPressed() || FishyRobot2015.oi.driver.isManualOverride() || !FishyRobot2015.intakeLeftSubsystem.withinBounds();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		FishyRobot2015.intakeLeftSubsystem.roller.set(0);
		FishyRobot2015.intakeRightSubsystem.roller.set(0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
