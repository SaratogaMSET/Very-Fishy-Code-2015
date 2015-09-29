package org.usfirst.frc.team649.robot.commands.drivetraincommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnOneSideNoPID extends Command {
	
	boolean forward, left;
	double leftSpeed, rightSpeed;
	double deltaTranslationalDistance;
	
	public TurnOneSideNoPID(boolean isLeftSide, double speed, double angle){
		angle = Math.abs(angle);
		deltaTranslationalDistance = (angle / 180.0) * (16.5 * Math.PI);
		left = isLeftSide;
		forward = speed > 0;
		if(left){
			leftSpeed = speed;
			rightSpeed = 0;
		}
		else{
			rightSpeed = speed;
			leftSpeed = 0;
		}
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub)
		FishyRobot2015.drivetrainSubsystem.resetEncoders();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		FishyRobot2015.drivetrainSubsystem.rawDrive(leftSpeed, rightSpeed);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		if(left && forward && FishyRobot2015.drivetrainSubsystem.encoders[0].getDistance() > deltaTranslationalDistance){
			return true;
		}
		else if(left && !forward && FishyRobot2015.drivetrainSubsystem.encoders[0].getDistance() < deltaTranslationalDistance){
			return true;
		}
		else if(!left && forward && FishyRobot2015.drivetrainSubsystem.encoders[1].getDistance() > deltaTranslationalDistance){
			return true;
		}
		else if(!left && !forward && FishyRobot2015.drivetrainSubsystem.encoders[1].getDistance() < deltaTranslationalDistance){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		FishyRobot2015.drivetrainSubsystem.rawDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
