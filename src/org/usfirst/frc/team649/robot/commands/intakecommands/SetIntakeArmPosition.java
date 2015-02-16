package org.usfirst.frc.team649.robot.commands.intakecommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.IntakeLeftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeLeftSubsystem.PIDConstants;
import org.usfirst.frc.team649.robot.subsystems.IntakeRightSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetIntakeArmPosition extends Command {
	double relevantSetpointRight;
	double relevantSetpointLeft;
	PIDController pidLeft, pidRight;
	
	public SetIntakeArmPosition(double st){
		//0 is grabbing, 1 is releasing, 2 is storage
		 if (st == PIDConstants.GRABBING_STATE){
			 relevantSetpointRight = IntakeRightSubsystem.PIDConstants.ARM_POS_GRABBING;
			 relevantSetpointLeft = IntakeLeftSubsystem.PIDConstants.ARM_POS_GRABBING;
		 }
		 else if (st == PIDConstants.RELEASING_STATE){
			 relevantSetpointRight = IntakeRightSubsystem.PIDConstants.ARM_POS_RELEASE;
			 relevantSetpointLeft = IntakeLeftSubsystem.PIDConstants.ARM_POS_RELEASE;
		 }
		 else{
			 relevantSetpointRight = IntakeRightSubsystem.PIDConstants.ARM_POS_STORING;
			 relevantSetpointLeft = IntakeLeftSubsystem.PIDConstants.ARM_POS_STORING;
		 }
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		pidRight = FishyRobot2015.intakeRightSubsystem.getPIDController();
		pidRight.enable();
		pidRight.setSetpoint(relevantSetpointRight);
		
		pidLeft = FishyRobot2015.intakeLeftSubsystem.getPIDController();
		pidLeft.enable();
		pidLeft.setSetpoint(relevantSetpointLeft);
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		//when both have reached
		return pidRight.onTarget() && pidLeft.onTarget(); 
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		pidRight.disable();
		pidLeft.disable();
		
		FishyRobot2015.intakeLeftSubsystem.arm.set(0);
		FishyRobot2015.intakeRightSubsystem.arm.set(0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		FishyRobot2015.intakeLeftSubsystem.arm.set(0);
		FishyRobot2015.intakeRightSubsystem.arm.set(0);
	}

}
