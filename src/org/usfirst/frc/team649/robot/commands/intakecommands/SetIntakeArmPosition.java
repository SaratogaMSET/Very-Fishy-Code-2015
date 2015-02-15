package org.usfirst.frc.team649.robot.commands.intakecommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.IntakeLeftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeLeftSubsystem.PIDConstants;
import org.usfirst.frc.team649.robot.subsystems.IntakeRightSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

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
		pidLeft = FishyRobot2015.intakeLeftSubsystem.getPIDController();
		//for different positions for each arm, this command will need 2 state inputs
		pidRight.setSetpoint(relevantSetpointRight);
		pidLeft.setSetpoint(relevantSetpointLeft);
		
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		//when both have reached
		return FishyRobot2015.intakeLeftSubsystem.getPot() == relevantSetpointLeft && FishyRobot2015.intakeRightSubsystem.getPot() == relevantSetpointRight || (FishyRobot2015.intakeLeftSubsystem.isArmLimitPressed() || FishyRobot2015.intakeRightSubsystem.isArmLimitPressed());
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
