//DONT NEED ANY OF THIS

//package org.usfirst.frc.team649.robot.subsystems;
//
//import org.usfirst.frc.team649.robot.RobotMap;
//
//import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import edu.wpi.first.wpilibj.command.Subsystem;
//
///**
// *
// */
//public class ContainerGrabberSubsystem extends Subsystem {
//
//	// Put methods for controlling this subsystem
//	// here. Call these from Commands.
//
//	DoubleSolenoid piston1;
//	//DoubleSolenoid piston2;
//	Value grabberState;
//	
//	//for manual only
//	public boolean grabberStateBooleanForManualOnly;
//	
//	public static final Value GRABBER_DEFAULT_STATE = DoubleSolenoid.Value.kReverse;
//	public static final Value GRABBER_CLOSED_STATE = DoubleSolenoid.Value.kForward;
//	public static final Value GRABBER_OPENED_STATE = DoubleSolenoid.Value.kReverse;
//	//in mili-seconds
//	public static final double TIME_TO_CLOSE_PISTONS = 200;
//
//	public ContainerGrabberSubsystem() {
//		piston1 = new DoubleSolenoid(
//				RobotMap.CONTAINER_GRABBER.PISTON_1_FORWARD_CHANNEL,
//				RobotMap.CONTAINER_GRABBER.PISTON_1_REVERSE_CHANNEL);
////		piston2 = new DoubleSolenoid(
////				RobotMap.CONTAINER_GRABBER.PISTON_2_FORWARD_CHANNEL,
////				RobotMap.CONTAINER_GRABBER.PISTON_2_REVERSE_CHANNEL);
//		grabberState = GRABBER_DEFAULT_STATE;
//		setGrabberState(GRABBER_DEFAULT_STATE);
//		grabberStateBooleanForManualOnly = false;
//	}
//
//	public void setGrabberState(Value state) {
//		if (!state.equals(grabberState)) {
//			grabberState = state;
//			piston1.set(grabberState);
//		//	piston2.set(grabberState);
//		}
//
//	}
//
//	public int getGrabberState() {
//		//return where it THINKS the pistons are (ie the "setpoint" of the pistons)
//		return piston1.get().value;
//	}
//
//	public void initDefaultCommand() {
//		// Set the default command for a subsystem here.
//		// setDefaultCommand(new MySpecialCommand());
//	}
//}
