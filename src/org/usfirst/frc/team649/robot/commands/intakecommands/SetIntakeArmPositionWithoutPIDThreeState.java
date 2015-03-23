package org.usfirst.frc.team649.robot.commands.intakecommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem.PIDConstants;
import org.usfirst.frc.team649.robot.subsystems.IntakeStarboardSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetIntakeArmPositionWithoutPIDThreeState extends Command {
	double relevantSetpointRight;
	double relevantSetpointLeft;
//	boolean changePIDGrabberToRelease;
	boolean leftDone, rightDone;
	
	double armPower;
	int state;
	
	public SetIntakeArmPositionWithoutPIDThreeState(int st){
		state = st;
		//0 is grabbing, 1 is releasing, 2 is storage
		if (st == PIDConstants.GRABBING_STATE){
			 relevantSetpointRight = IntakeStarboardSubsystem.PIDConstants.ARM_POS_GRABBING;
			 relevantSetpointLeft = IntakePortSubsystem.PIDConstants.ARM_POS_GRABBING;
			 
		 }
		 else if (st == PIDConstants.RELEASING_STATE){
			 relevantSetpointRight = IntakeStarboardSubsystem.PIDConstants.ARM_POS_RELEASE;
			 relevantSetpointLeft = IntakePortSubsystem.PIDConstants.ARM_POS_RELEASE;

		 }
		 else if (st == PIDConstants.STORE_STATE){
			 relevantSetpointRight = IntakeStarboardSubsystem.PIDConstants.ARM_POS_STORING;
			 relevantSetpointLeft = IntakePortSubsystem.PIDConstants.ARM_POS_STORING;
		 }
		 else { 
			 relevantSetpointRight = FishyRobot2015.intakeRightSubsystem.getPot();
			 relevantSetpointLeft = FishyRobot2015.intakeLeftSubsystem.getPot();
		 }
		 

		if (FishyRobot2015.intakeLeftSubsystem.getPot() > relevantSetpointLeft && FishyRobot2015.intakeRightSubsystem.getPot() > relevantSetpointRight){
			armPower = IntakePortSubsystem.PIDConstants.ARMS_IN_POWER;
		}
		else if (FishyRobot2015.intakeLeftSubsystem.getPot() < relevantSetpointLeft && FishyRobot2015.intakeRightSubsystem.getPot() < relevantSetpointRight){
			armPower = IntakePortSubsystem.PIDConstants.ARMS_OUT_POWER;
		}
		else{
			armPower = 0;
		}
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("Setpoint Left", relevantSetpointLeft);
    	SmartDashboard.putNumber("Setpoint Right", relevantSetpointRight);
    	
		leftDone = false;
		rightDone = false;
    	
	}

	@Override
	protected void execute() {
		if (leftDone || Math.abs(FishyRobot2015.intakeLeftSubsystem.getPot() - relevantSetpointLeft) < 0.03){
			leftDone = true;
			FishyRobot2015.intakeLeftSubsystem.arm.set(0);
		} 
//		else if (Math.abs(FishyRobot2015.intakeLeftSubsystem.getPot() - relevantSetpointLeft) < 0.1){
//			FishyRobot2015.intakeLeftSubsystem.arm.set(armPower/1.5);
//		}
		
		else{
			FishyRobot2015.intakeLeftSubsystem.arm.set(armPower);
		}
		
		if (rightDone || Math.abs(FishyRobot2015.intakeRightSubsystem.getPot() - relevantSetpointRight) < 0.03){
			rightDone = true;
			FishyRobot2015.intakeRightSubsystem.arm.set(0);
		}
//		else if (Math.abs(FishyRobot2015.intakeRightSubsystem.getPot() - relevantSetpointRight) < 0.1){
//			FishyRobot2015.intakeRightSubsystem.arm.set(-armPower/1.5);
//		}
		else{
			SmartDashboard.putNumber("Power For Arms", armPower);
			FishyRobot2015.intakeRightSubsystem.arm.set(-armPower);
		}
	}

	@Override
	protected boolean isFinished() {
		//when both have reached
		return leftDone && rightDone || FishyRobot2015.oi.driver.isManualOverride() || !FishyRobot2015.intakeLeftSubsystem.withinBounds() || !FishyRobot2015.intakeRightSubsystem.withinBounds();
	}
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
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
