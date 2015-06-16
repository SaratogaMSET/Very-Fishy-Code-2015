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

	String direction;
	
	double leftArmPower;
	double rightArmPower;
	int localState;
	public int currentLeftState;
	public int currentRightState;
	
	public SetIntakeArmPositionWithoutPIDThreeState(int st){
		leftDone = false;
		rightDone = false;
		
		localState = st;
				//POWER SKETCHY STUFF
//		if (FishyRobot2015.intakeLeftSubsystem.getPot() > relevantSetpointLeft){ //> IntakePortSubsystem.PIDConstants.NO_PID_TOLERANCE){
//			leftArmPower = IntakePortSubsystem.PIDConstants.ARMS_IN_POWER;
//		}
//		else if (FishyRobot2015.intakeLeftSubsystem.getPot() < relevantSetpointLeft){ //< -IntakePortSubsystem.PIDConstants.NO_PID_TOLERANCE){
//			leftArmPower = IntakePortSubsystem.PIDConstants.ARMS_OUT_POWER;
//		}
//		else{
//			leftArmPower = 0;
//			leftDone = true;
//		}
//		
//		if (FishyRobot2015.intakeRightSubsystem.getPot() > relevantSetpointRight){ //> IntakeStarboardSubsystem.PIDConstants.NO_PID_TOLERANCE){
//			rightArmPower = IntakeStarboardSubsystem.PIDConstants.ARMS_IN_POWER;
//		}
//		else if (FishyRobot2015.intakeRightSubsystem.getPot() < relevantSetpointRight){ //< -IntakeStarboardSubsystem.PIDConstants.NO_PID_TOLERANCE){
//			rightArmPower = IntakeStarboardSubsystem.PIDConstants.ARMS_OUT_POWER;
//		}
//		else{
//			rightArmPower = 0;
//			rightDone = true;
//		}
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("Setpoint Left", relevantSetpointLeft);
    	SmartDashboard.putNumber("Setpoint Right", relevantSetpointRight);
    	
//    	FishyRobot2015.intakeRightSubsystem.state = localState;
//    	FishyRobot2015.intakeLeftSubsystem.state = localState;
    	currentLeftState = FishyRobot2015.intakeLeftSubsystem.state;
		currentRightState = FishyRobot2015.intakeRightSubsystem.state;
		
		//0 is grabbing, 1 is releasing, 2 is storage
		if (localState == PIDConstants.GRABBING_STATE){
			 relevantSetpointRight = IntakeStarboardSubsystem.PIDConstants.ARM_POS_GRABBING;
			 relevantSetpointLeft = IntakePortSubsystem.PIDConstants.ARM_POS_GRABBING;
			 
		 }
		 else if (localState == PIDConstants.RELEASING_STATE){
			 relevantSetpointRight = IntakeStarboardSubsystem.PIDConstants.ARM_POS_RELEASE;
			 relevantSetpointLeft = IntakePortSubsystem.PIDConstants.ARM_POS_RELEASE;

		 }
		 else if (localState == PIDConstants.STORE_STATE){
			 relevantSetpointRight = IntakeStarboardSubsystem.PIDConstants.ARM_POS_STORING;
			 relevantSetpointLeft = IntakePortSubsystem.PIDConstants.ARM_POS_STORING;
		 }
		 else { 
			 relevantSetpointRight = FishyRobot2015.intakeRightSubsystem.getPot();
			 relevantSetpointLeft = FishyRobot2015.intakeLeftSubsystem.getPot();
		 }

		//LEFT
		if (localState == currentLeftState){
			//at where you want to go
			leftArmPower = 0;
			leftDone = true;
		}
		else if (localState < currentLeftState){
			//move in
			leftArmPower = IntakePortSubsystem.PIDConstants.ARMS_IN_POWER;
		}
		else if (localState > currentLeftState){
			//move in
			leftArmPower = IntakePortSubsystem.PIDConstants.ARMS_OUT_POWER;
		}
		
		//RIGHT
		if (localState == currentRightState){
			//at where you want to go
			rightArmPower = 0;
			rightDone = true;
		}
		else if (localState < currentRightState){
			//move in
			rightArmPower = IntakeStarboardSubsystem.PIDConstants.ARMS_IN_POWER;
		}
		else if (localState > currentRightState){
			//move in
			rightArmPower = IntakeStarboardSubsystem.PIDConstants.ARMS_OUT_POWER;
		}
		

	}

	@Override
	protected void execute() {
		SmartDashboard.putString("ACTIVE COMMAND", "Setting 3-STATE Arm Pos: " + localState);
		
		//LEFT
		if (leftDone || Math.abs(FishyRobot2015.intakeLeftSubsystem.getPot() - relevantSetpointLeft) < IntakePortSubsystem.PIDConstants.NO_PID_TOLERANCE){
			leftDone = true;
			FishyRobot2015.intakeLeftSubsystem.arm.set(0);
		} 
		else{
			FishyRobot2015.intakeLeftSubsystem.arm.set(leftArmPower);
		}
		
		//RIGHT
		if (rightDone || Math.abs(FishyRobot2015.intakeRightSubsystem.getPot() - relevantSetpointRight) < IntakePortSubsystem.PIDConstants.NO_PID_TOLERANCE){
			rightDone = true;
			FishyRobot2015.intakeRightSubsystem.arm.set(0);
		}
		else{
			FishyRobot2015.intakeRightSubsystem.arm.set(rightArmPower);
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
