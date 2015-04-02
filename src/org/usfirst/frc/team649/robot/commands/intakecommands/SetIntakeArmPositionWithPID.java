package org.usfirst.frc.team649.robot.commands.intakecommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem.PIDConstants;
import org.usfirst.frc.team649.robot.subsystems.IntakeStarboardSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetIntakeArmPositionWithPID extends Command {
	double relevantSetpointRight;
	double relevantSetpointLeft;
	PIDController pidLeft, pidRight;
	boolean changePIDGrabberToRelease;
	
	public SetIntakeArmPositionWithPID(double st){
		//0 is grabbing, 1 is releasing, 2 is storage
		changePIDGrabberToRelease = false;
		SmartDashboard.putBoolean("ClosePID?", true);
		 if (st == PIDConstants.GRABBING_STATE){
			 relevantSetpointRight = IntakeStarboardSubsystem.PIDConstants.ARM_POS_GRABBING;
			 relevantSetpointLeft = IntakePortSubsystem.PIDConstants.ARM_POS_GRABBING;
			 
		 }
		 else if (st == PIDConstants.RELEASING_STATE){
			 relevantSetpointRight = IntakeStarboardSubsystem.PIDConstants.ARM_POS_RELEASE;
			 relevantSetpointLeft = IntakePortSubsystem.PIDConstants.ARM_POS_RELEASE;
			 if (relevantSetpointRight > FishyRobot2015.intakeRightSubsystem.getPot()){
				 changePIDGrabberToRelease = true;
				 SmartDashboard.putBoolean("ClosePID?", false);
			 }
		 }
		 else if (st == PIDConstants.STORE_STATE){
			 relevantSetpointRight = IntakeStarboardSubsystem.PIDConstants.ARM_POS_STORING;
			 relevantSetpointLeft = IntakePortSubsystem.PIDConstants.ARM_POS_STORING;
		 }
		 else { 
			 relevantSetpointRight = FishyRobot2015.intakeRightSubsystem.getPot();
			 relevantSetpointLeft = FishyRobot2015.intakeLeftSubsystem.getPot();
		 }
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		pidRight = FishyRobot2015.intakeRightSubsystem.getPIDController();
		pidRight.enable();
		SmartDashboard.putNumber("Setpoint Right", relevantSetpointRight);
		pidRight.setSetpoint(relevantSetpointRight);
		
		pidLeft = FishyRobot2015.intakeLeftSubsystem.getPIDController();
		pidLeft.enable();
		SmartDashboard.putNumber("Setpoint Left", relevantSetpointLeft);
		pidLeft.setSetpoint(relevantSetpointLeft);
		//  changing PID left side
//		
//		if (changePIDGrabberToRelease){
//			pidRight.setPID(IntakeStarboardSubsystem.PIDConstants.P_GRABBER_TO_RELEASE, IntakeStarboardSubsystem.PIDConstants.I_GRABBER_TO_RELEASE, IntakeStarboardSubsystem.PIDConstants.D_GRABBER_TO_RELEASE);
//			pidLeft.setPID(IntakePortSubsystem.PIDConstants.P_GRABBER_TO_RELEASE, IntakePortSubsystem.PIDConstants.I_GRABBER_TO_RELEASE, IntakePortSubsystem.PIDConstants.D_GRABBER_TO_RELEASE);
//		}
//		else{
//			pidRight.setPID(IntakeStarboardSubsystem.PIDConstants.P_REGULAR, IntakeStarboardSubsystem.PIDConstants.I_REGULAR, IntakeStarboardSubsystem.PIDConstants.D_REGULAR);
//			pidLeft.setPID(IntakePortSubsystem.PIDConstants.P_REGULAR, IntakePortSubsystem.PIDConstants.I_REGULAR, IntakePortSubsystem.PIDConstants.D_REGULAR);
//		}

	
	}

	@Override
	protected void execute() {
		if(pidRight.onTarget()) {
			pidRight.disable();
			FishyRobot2015.intakeRightSubsystem.arm.set(0);
		}	
		if(pidLeft.onTarget()) {
			pidLeft.disable();
			FishyRobot2015.intakeLeftSubsystem.arm.set(0);
		}
	}

	@Override
	protected boolean isFinished() {
		//when both have reached
		return (pidRight.onTarget() && pidLeft.onTarget()) || FishyRobot2015.intakeLeftSubsystem.isArmLimitPressed() /*|| FishyRobot2015.intakeRightSubsystem.isArmLimitPressed() */|| FishyRobot2015.oi.driver.isManualOverride() || !FishyRobot2015.intakeLeftSubsystem.withinBounds(); 
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		pidRight.disable();
		pidLeft.disable();
		
		FishyRobot2015.intakeLeftSubsystem.pid.disable();
		FishyRobot2015.intakeRightSubsystem.pid.disable();
		
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
