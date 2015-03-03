package org.usfirst.frc.team649.robot;

import org.usfirst.frc.team649.robot.commandgroups.AutoPickUpThreeTotes;
import org.usfirst.frc.team649.robot.commandgroups.AutoWinchAndDrive;
import org.usfirst.frc.team649.robot.commandgroups.Debug;
import org.usfirst.frc.team649.robot.commandgroups.PickUpToteSequence;
import org.usfirst.frc.team649.robot.commandgroups.ScoreAllAndResetFromTop;
import org.usfirst.frc.team649.robot.commandgroups.ScoreTotesOnPlatform;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveForwardRotate;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetTimeCommand;
import org.usfirst.frc.team649.robot.commands.intakecommands.IntakeTote;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRollers;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPID;
import org.usfirst.frc.team649.robot.commands.lift.PickUpContainer;
import org.usfirst.frc.team649.robot.commands.lift.RaiseTote;
import org.usfirst.frc.team649.robot.commands.lift.RunLift; //my name is suneel
import org.usfirst.frc.team649.robot.commands.lift.RunTilResetLimit;
import org.usfirst.frc.team649.robot.subsystems.AutoWinchSubsystem;
import org.usfirst.frc.team649.robot.subsystems.CameraSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem.PIDConstants;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeStarboardSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class FishyRobot2015 extends IterativeRobot {

	public static OI oi;
	public static DrivetrainSubsystem drivetrainSubsystem; //yolo
	public static ChainLiftSubsystem chainLiftSubsystem;
	public static IntakePortSubsystem intakeLeftSubsystem;
	public static IntakeStarboardSubsystem intakeRightSubsystem;
	public static AutoWinchSubsystem autoWinchSubsystem;
	public static CameraSubsystem cameraSubsystem;
	public static PowerDistributionPanel pdp;

	// previous states for button press v hold
	public boolean prevStateRaiseTote, prevStateLowerTote;
	public boolean prevStateScore;
	public boolean prevStateRaiseContainer, prevStateLowerContainer;

	public Command joyChainLift;

	public SendableChooser autoChooser;
	public Command autoCommand;
	public String autoMode;
	public boolean driveLeftEncoderState, driveRightEncoderState, chainEncoderState;
	
	public SendableChooser containerChooser;
	public static boolean containerState;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		drivetrainSubsystem = new DrivetrainSubsystem();
		chainLiftSubsystem = new ChainLiftSubsystem();
		intakeLeftSubsystem = new IntakePortSubsystem();
		intakeRightSubsystem = new IntakeStarboardSubsystem();
		pdp = new PowerDistributionPanel();
		// autoWinchSubsystem = new AutoWinchSubsystem();
		// cameraSubsystem = new CameraSubsystem();
		oi = new OI();

		autoChooser = new SendableChooser();

		autoChooser.addObject("Debugger Mode", "debugger mode");
		autoChooser.addObject("Winch Autonomous", "winch in totes");
		autoChooser.addObject("Get Three Totes", "three totes");
		autoChooser.addDefault("Do Nothing Autonomous", "none");
		autoChooser.addObject("Drive Forward", "drive forward");

		SmartDashboard.putData("Autonomous Mode", autoChooser);
		// instantiate the command used for the autonomous period
		// autonomousCommand = new ExampleCommand();
		SmartDashboard.putData(Scheduler.getInstance());

		// idk if this works
		// SmartDashboard.putData("Cam", (Sendable)
		// commandBase.cameraSubsystem.cam);
		// cam must be configured from smartdashboard
		containerChooser = new SendableChooser();
		containerChooser.addDefault("Tote State", false);
		containerChooser.addDefault("Container State", true);
		
		SmartDashboard.putData("Container Mode", containerChooser);
		
		prevStateRaiseTote = false;
		prevStateLowerTote = false;
		prevStateRaiseContainer = false;
		prevStateLowerContainer = false;
		prevStateScore = false;
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		containerState = (boolean) containerChooser.getSelected();
		
		// // schedule the autonomous command (example)
		autoMode = (String) autoChooser.getSelected();
		driveLeftEncoderState = false;
		driveRightEncoderState = false;
		chainEncoderState = false;
//
//		// obviously names will be changed
//		switch (autoMode) {
//		case "debugger mode":
//			autoCommand = new Debug();
//			break;
//		case "winch in totes":
//			autoCommand = new AutoWinchAndDrive();
//			break;
//		case "three totes":
//			autoCommand = new AutoPickUpThreeTotes();
//			break;
//		case "none":
//			autoCommand = null;
//			break;
//		}
//		// //
//		//
//		if (autoCommand != null) { // for the case of none
//			autoCommand.start();
//		}
		
		new DriveSetDistanceWithPID(42, 0.1).start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		containerState = (boolean) containerChooser.getSelected();

		if (autoMode.equals("debugger mode") && !autoCommand.isRunning()) {
			if (drivetrainSubsystem.encoders[0].get() != 0) {
				driveLeftEncoderState = true;
			}
			if (drivetrainSubsystem.encoders[1].get() != 0) {
				driveRightEncoderState = true;
			}
			if (chainLiftSubsystem.getHeight() != 0) {
				chainEncoderState = true;
			}
		}

		SmartDashboard.putBoolean("Left Drive Encoder", driveLeftEncoderState);
		SmartDashboard.putBoolean("Right Drive Encoder", driveRightEncoderState);
		SmartDashboard.putBoolean("Chain Encoder", chainEncoderState);
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.

		// FIGURE OUT HOW TO CLEAR SMARTDASHBOARD REMOTELY
		// chainLiftSubsystem.definePID();
		//

		// SmartDashboard.n
		
		containerState = (boolean) containerChooser.getSelected();

		new RunLift(0).start();
		new DriveForwardRotate(0, 0).start();
		new SetIntakeArmPositionWithPID(IntakePortSubsystem.PIDConstants.CURRENT_STATE).start();
		new RunRollers(0,0).start();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {
		new RunLift(0).start();
		new DriveForwardRotate(0, 0).start();
		new SetIntakeArmPositionWithPID(IntakePortSubsystem.PIDConstants.CURRENT_STATE).start();
		new RunRollers(0,0).start();
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		containerState = (boolean) containerChooser.getSelected();

		
		if (oi.operatorJoystick.getRawButton(9)) {
		//	new RunLift(oi.operatorJoystick.getY()).start();
			FishyRobot2015.intakeRightSubsystem.arm.set(oi.operatorJoystick.getY() / 3.0);
			FishyRobot2015.intakeLeftSubsystem.arm.set(-oi.operatorJoystick.getY()/ 3.0);
		}if(oi.operatorJoystick.getRawButton(7)) {
			intakeRightSubsystem.arm.set(oi.operatorJoystick.getY() / 3.0);
		} if(oi.operatorJoystick.getRawButton(8)) {
			intakeLeftSubsystem.arm.set(oi.operatorJoystick.getY() / 3.0);
		}
		
		// new RunRollers(oi.operatorJoystick.getY()).start();

		oi.operator.intakeButton.whenReleased(new RunLift(0));

		new DriveForwardRotate(oi.driver.getDriveForward(), oi.driver.getDriveRotation()).start();

		// if(oi.operator.purgeButton.get()) {
		// //new
		// SetIntakeArmPosition(IntakeLeftSubsystem.PIDConstants.RELEASING_STATE).start();
		//
		// //new RunRollers(IntakeLeftSubsystem.INTAKE_ROLLER_SPEED).start();;
		// }
		
		if(oi.operator.intakeButton.get()) {
			if(oi.operator.twistRight()) {
				new RunRollers(0.15, -0.15).start();
			} else if(oi.operator.twistLeft()) {
				new RunRollers(-0.15, 0.15).start();
			} else {
				new RunRollers(0.5, 0.5).start();
			}
		} else if(oi.operator.purgeButton.get()) {
			new RunRollers(-0.5, -0.5).start();
		} else {
			new RunRollers(0.0, 0.0).start();
		}
		

//TODO CHECK whenPressed and whenReleased functions
		if (oi.operator.raiseToteButton.get() && !prevStateRaiseTote) {
			new RaiseTote(true).start();	
		}
		else if (oi.operator.raiseContainerButton.get() && !prevStateRaiseContainer){
			//new PickUpContainer(true).start(); //TODO uncomment this
		}

		if (oi.operator.lowerToteButton.get() && !prevStateLowerTote) {
			new RaiseTote(false).start();
		}
		else if (oi.operator.lowerContainerButton.get() && !prevStateLowerContainer){
			//new PickUpContainer(false).start(); //TODO uncomment this
		}
		
		//actually must be double pressed if you want a full score, e.g. no container
		if (oi.operator.scoreAllButton.get() && !prevStateScore) {
			// new ScoreAllAndResetFromTop().start();
			//only if it is in the first stage will the button trigger a new command
			if (chainLiftSubsystem.firstStageOfScore){
				new ScoreAllAndResetFromTop().start();
				chainLiftSubsystem.firstStageOfScore = false;
			}
		}

		// set the previous states
		prevStateRaiseTote = oi.operator.raiseToteButton.get();
		prevStateLowerTote = oi.operator.lowerToteButton.get();
		prevStateRaiseContainer = oi.operator.raiseContainerButton.get();
		prevStateLowerContainer = oi.operator.lowerContainerButton.get();
		prevStateScore = oi.operator.scoreAllButton.get();

		
		
		// if (oi.operator.intakeButton.get()){
		// new PickUpToteSequence().start();
		// } //LOLOLOL

		// if(oi.operator.containerButton.get()) {
		// new FullContainerAndFirstToteSequence(true).start();
		// }
		// if(oi.operator.stepButton.get()) {
		// new ChangeOffsetHeight(ChainLiftSubsystem.PIDConstants.STEP_HEIGHT);
		// }
		// if(oi.operator.storeButton.get()) {
		// new
		// ChangeOffsetHeight(ChainLiftSubsystem.PIDConstants.PLATFORM_HEIGHT);
		// }
		// //if(oi.operator.)

		/**************** MANUAL **********************/

		// throttle
		if (oi.operator.throttleOverrideButton.get()) {
			if (oi.operator.isGrabArmState()) {
				new SetIntakeArmPositionWithPID(IntakeStarboardSubsystem.PIDConstants.GRABBING_STATE).start();
				SmartDashboard.putString("button 11", "is pressed");

			}
			if (oi.operator.isReleaseArmState()) {
				SmartDashboard.putString("button 11", "is pressed");
				new SetIntakeArmPositionWithPID(IntakeStarboardSubsystem.PIDConstants.RELEASING_STATE).start();
			}
			if (oi.operator.isStoreArmState()) {
				new SetIntakeArmPositionWithPID(IntakeStarboardSubsystem.PIDConstants.STORE_STATE).start();
				SmartDashboard.putString("button 11", "is pressed");
			}
		}

		if (oi.driver.isManualOverride()) {

			chainLiftSubsystem.setPower(oi.manual.getLiftPower());
//LOLOL suneel was here
			if (oi.manual.leftHardStopIn.get()) {
				intakeLeftSubsystem.arm.set(0.4);
			} else {
				intakeRightSubsystem.arm.set(0.0);
			}

			if (oi.manual.leftHardStopOut.get()) {
				intakeLeftSubsystem.arm.set(-0.5);
			} else {
				intakeLeftSubsystem.arm.set(0.0);
			}
			if (oi.manual.rightHardStopIn.get()) {
				intakeRightSubsystem.arm.set(0.4);
			} else {
				intakeRightSubsystem.arm.set(0.0);
			}
			if (oi.manual.rightHardStopOut.get()) {
				intakeRightSubsystem.arm.set(-0.5);
			} else {
				intakeRightSubsystem.arm.set(0.0);
			}

			if (oi.manual.rollersIn.get()) {
				intakeLeftSubsystem.roller.set(IntakePortSubsystem.INTAKE_ROLLER_SPEED);
				intakeRightSubsystem.roller.set(IntakeStarboardSubsystem.INTAKE_ROLLER_SPEED);
			} else if (oi.manual.rollersOut.get()) {
				intakeLeftSubsystem.roller.set(IntakePortSubsystem.INTAKE_ROLLER_SPEED);
				intakeRightSubsystem.roller.set(IntakeStarboardSubsystem.PURGE_ROLLER_SPEED);
			} else {
				intakeLeftSubsystem.roller.set(IntakePortSubsystem.INTAKE_ROLLER_OFF_SPEED);
				intakeRightSubsystem.roller.set(IntakeStarboardSubsystem.INTAKE_ROLLER_OFF_SPEED);

			}
		}
		SmartDashboard.putData("Container Mode", containerChooser);
		SmartDashboard.putBoolean("Container State", containerState);
		SmartDashboard.putData("Chain Encoder 1", chainLiftSubsystem.encoders[0]);
		SmartDashboard.putData("Chain Encoder 2", chainLiftSubsystem.encoders[1]);
		
		SmartDashboard.putData("Drive Encoder Left", drivetrainSubsystem.encoders[0]);
		SmartDashboard.putData("Drive Encoder Right", drivetrainSubsystem.encoders[1]);
		
		SmartDashboard.putNumber("Drive Distance", drivetrainSubsystem.getDistance());
		SmartDashboard.putBoolean("Max Hal", chainLiftSubsystem.isMaxLimitPressed());
		SmartDashboard.putBoolean("Reset Ryan Lewis", chainLiftSubsystem.isResetLimitPressed());
		SmartDashboard.putBoolean("Bumper Right", intakeRightSubsystem.isToteLimitPressed());
		SmartDashboard.putBoolean("Bumper Left", intakeLeftSubsystem.isToteLimitPressed());
		SmartDashboard.putBoolean("Arm Left Limit", intakeLeftSubsystem.isArmLimitPressed());
		SmartDashboard.putBoolean("Arm Right Limit", intakeRightSubsystem.isArmLimitPressed());
		
		SmartDashboard.putBoolean("IS PAST TOP", chainLiftSubsystem.isPastTop);
		SmartDashboard.putBoolean("IS PAST BOTTOM", chainLiftSubsystem.isPastBottom);

		SmartDashboard.putNumber("Chain Height", chainLiftSubsystem.getHeight());
		SmartDashboard.putNumber("GOAL HEIGHT", chainLiftSubsystem.offsetHeight + chainLiftSubsystem.setpointHeight);
		SmartDashboard.putNumber("number of totes", FishyRobot2015.chainLiftSubsystem.getNumTotes());

		SmartDashboard.putNumber("Intake Right Pot",intakeRightSubsystem.getPot()); /// IntakeRightSubsystem.PIDConstants.CONVERSION_DEGREES_TO_POT);
		SmartDashboard.putNumber("Intake Left Pot", intakeLeftSubsystem.getPot()); // / IntakeLeftSubsystem.PIDConstants.CONVERSION_DEGREES_TO_POT);
		SmartDashboard.putNumber("Lift Right Current", pdp.getCurrent(12));
		SmartDashboard.putNumber("Lift Left Current", pdp.getCurrent(13));
		SmartDashboard.putNumber("Left Arm Current", pdp.getCurrent(10));
		SmartDashboard.putNumber("Right Arm Current", pdp.getCurrent(8));
		SmartDashboard.putNumber("Roller Right Current", pdp.getCurrent(4));
		SmartDashboard.putNumber("Roller Left Current", pdp.getCurrent(9)); //no.
		SmartDashboard.putNumber("Joy y", oi.operatorJoystick.getY());
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();

	}
}
