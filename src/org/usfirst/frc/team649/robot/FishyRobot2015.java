package org.usfirst.frc.team649.robot;

import org.usfirst.frc.team649.robot.commandgroups.AutoPickUpOneTote;
import org.usfirst.frc.team649.robot.commandgroups.AutoWithContainerPickUp;
import org.usfirst.frc.team649.robot.commandgroups.ContainerAndToteAuto;
import org.usfirst.frc.team649.robot.commandgroups.ContainerFirstToteSemiAuto;
import org.usfirst.frc.team649.robot.commandgroups.DriveBackAndTurnAuto;
import org.usfirst.frc.team649.robot.commandgroups.ThreeToteAutoCalGames;
import org.usfirst.frc.team649.robot.commandgroups.ThreeToteAutoFull;
import org.usfirst.frc.team649.robot.commandgroups.ThreeToteAutoPart1;
import org.usfirst.frc.team649.robot.commandgroups.Debug;
import org.usfirst.frc.team649.robot.commandgroups.OpenArmsAndRaiseTote;
import org.usfirst.frc.team649.robot.commandgroups.PickUpToteSequence;
import org.usfirst.frc.team649.robot.commandgroups.ScoreAllAndResetFromTop;
import org.usfirst.frc.team649.robot.commandgroups.ScoreTotesOnPlatform;
import org.usfirst.frc.team649.robot.commandgroups.TurnAndPickUpToteAuto;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveForwardRotate;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnSetTimeCommand;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;
import org.usfirst.frc.team649.robot.commands.intakecommands.IntakeTote;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRollers;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPIDThreeState;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.PickUpContainer;
import org.usfirst.frc.team649.robot.commands.lift.RaiseToteWithoutPID;
//import org.usfirst.frc.team649.robot.commands.lift.RaiseTote;
import org.usfirst.frc.team649.robot.commands.lift.RunLift; //my name is suneel
import org.usfirst.frc.team649.robot.commands.lift.RunTilResetLimit;
import org.usfirst.frc.team649.robot.subsystems.AutoWinchSubsystem;
import org.usfirst.frc.team649.robot.subsystems.CameraSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem.PIDConstants;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeStarboardSubsystem;

import com.ni.vision.NIVision.InterpolatePointsResult;

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
 * documentation. If you change th
 * 
 * ``
 * `
 * `
 * `
 * `
 * `
 * ``````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````e name of this class or the package after
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
	
	
	public static CameraSubsystem cameraSubsystemSubsystem;
	public static PowerDistributionPanel pdp;

	// previous states for button press v hold
	public boolean prevStateRaiseTote, prevStateLowerTote;
	public boolean prevStateScore;
	public boolean prevStateContainerSequence, prevStateLowerContainer;
	public boolean prevStateGrab, prevStateRelease, prevStateStore;
	public boolean prevStateManualOverride1, prevStateManualOverride2;

	public Command joyChainLift;

	public SendableChooser autoChooser;
	public Command autoCommand;
	public String autoMode;
	public boolean driveLeftEncoderState, driveRightEncoderState, chainEncoderState;
	
	public SendableChooser containerChooser;
	private boolean prevStateIntake;
	private boolean prevStatePurge;
	public static boolean containerState = false;

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
		autoChooser.addObject("Practice Pick Up Tote", "one tote");
		autoChooser.addObject("Get Three Totes", "three totes");
		autoChooser.addObject("Drive Forward Into Autozone", "drive forward");
		autoChooser.addDefault("Do Nothing Autonomous", "none");

		SmartDashboard.putData("Autonomous Mode", autoChooser);
		// instantiate the command used for the autonomous period
		// autonomousCommand = new ExampleCommand();
		//SmartDashboard.putData(Scheduler.getInstance());

		// idk if this works
		// SmartDashboard.putData("Cam", (Sendable)
		// commandBase.cameraSubsystem.cam);
		// cam must be configured from smartdashboard
		containerChooser = new SendableChooser();
		containerChooser.addDefault("Tote State", false);
		containerChooser.addObject("Container State", true);
		
	SmartDashboard.putData("Container Mode", containerChooser);
	
		chainLiftSubsystem.resetEncodersAndVariables();
		
		prevStateRaiseTote = false;
		prevStateLowerTote = false;
		prevStateContainerSequence = false;
		prevStateLowerContainer = false;
		prevStateScore = false;
		
		prevStateIntake = false;
		prevStatePurge = false;
		
		prevStateRelease = false;
		prevStateGrab = false;
		prevStateStore = false;
		
		prevStateManualOverride1 = false;
		prevStateManualOverride2 = false;
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
		switch (autoMode) {
		case "debugger mode":
			autoCommand = new Debug();
			break;
		case "one tote":
			autoCommand = new PickUpToteSequence();
			break;
		case "three totes":
			autoCommand = new AutoPickUpOneTote();
			break;
		case "drive forward":
			autoCommand = new DriveSetDistanceWithPID(63);
		case "none":
			autoCommand = null;
			break;
		}
//		// //
//		//
		if (autoCommand != null) { // for the case of none
			autoCommand.start();
		}
		//OLD DRIVE BACK
		//new DriveSetDistanceWithPID(-48).start();
		
	//	new Run
		new RunRollers(0,0).start();
	//	new PickUpToteSequence().start();
	//	new AutoWithContainerPickUp().start();
		//new AutoPickUpOneTote().start();
	//	new TurnWithPIDCommand(45).start();
		
		//CAL GAMES AUTO BETA
		//new ThreeToteAutoCalGames().start();
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
		
		SmartDashboard.putNumber("Chain Height", chainLiftSubsystem.setpointHeight);
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
		SmartDashboard.putBoolean("ENTERED CONTAINER LOOP", false);
		new RunLift(0).start();
		new DriveForwardRotate(0, 0).start();
	//	new SetIntakeArmPositionWithPID(IntakePortSubsystem.PIDConstants.CURRENT_STATE).start();
		new RunRollers(0,0).start();
		new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.CURRENT_STATE).start();

		SmartDashboard.putBoolean("IS RESET TRIPPED AT ALL", false);
		
		chainLiftSubsystem.resetLimitedVariables();
		//chainLiftSubsystem.resetEncodersAndVariables();
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
		new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.CURRENT_STATE).start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		SmartDashboard.putString("ACTIVE COMMAND", "none");
		
		Scheduler.getInstance().run();
		
		containerState = (boolean) containerChooser.getSelected();

		
//		if (oi.operatorJoystick.getRawButton(9)) {
//			new RunLift(oi.operatorJoystick.getY()).start();
////			intakeRightSubsystem.arm.set(oi.operatorJoystick.getY() / 3.0);
//		//	intakeLeftSubsystem.arm.set(-oi.operatorJoystick.getY()/ 3.0);
//		}if(oi.operatorJoystick.getRawButton(7)) {
//			intakeRightSubsystem.arm.set(oi.operatorJoystick.getY() / 3.0);
//		} if(oi.operatorJoystick.getRawButton(8)) {
//			intakeLeftSubsystem.arm.set(oi.operatorJoystick.getY() / 3.0);
//		}
		
		// new RunRollers(oi.operatorJoystick.getY()).start();

	//	oi.operator.intakeButton.whenReleased(new RunLift(0));

		new DriveForwardRotate(oi.driver.getDriveForward(), oi.driver.getDriveRotation()).start();

		// if(oi.operator.purgeButton.get()) {
		// //new
		// SetIntakeArmPosition(IntakeLeftSubsystem.PIDConstants.RELEASING_STATE).start();
		//
		// //new RunRollers(IntakeLeftSubsystem.INTAKE_ROLLER_SPEED).start();;
		// }
		
		if (oi.operator.intakeButton.get() && oi.operator.twistRight()) {
			new RunRollers(0.3, -0.3).start();
		} else if (oi.operator.intakeButton.get() && oi.operator.twistLeft()) {
			new RunRollers(-0.3, 0.3).start();
		} else if (oi.operator.intakeButton.get()) {
			new RunRollers(0.6, 0.6).start();
		} else if (oi.operator.purgeButton.get()) {
			new RunRollers(-0.5, -0.5).start();
		} else if ((!oi.operator.intakeButton.get() && prevStateIntake) || (!oi.operator.purgeButton.get() && prevStatePurge)){
			new RunRollers(0,0).start();
		}
		

//TODO CHECK whenPressed and whenReleased functions
		if (oi.operator.raiseToteButton.get() && !prevStateRaiseTote) {
			SmartDashboard.putString("FIRST CONTAINER OFFSET", "no");
			new OpenArmsAndRaiseTote(true).start();	
			//new RaiseToteWithoutPID(true).start();
		}
		else if (oi.operator.containerSequenceButton.get() && !prevStateContainerSequence){
			SmartDashboard.putBoolean("ENTERED CONTAINER LOOP", true);
			//conditions for starting containerSequence
			if (chainLiftSubsystem.readyToPickContainer && chainLiftSubsystem.getNumTotes() >= 2){ //&& (intakeLeftSubsystem.isToteLimitPressed() || intakeRightSubsystem.isToteLimitPressed())){ //TODO change bool logic
				new ContainerFirstToteSemiAuto((double)chainLiftSubsystem.getNumTotes()).start();
				chainLiftSubsystem.readyToPickContainer = false;
			}
		}

		if (oi.operator.lowerToteButton.get() && !prevStateLowerTote) {
			new OpenArmsAndRaiseTote(false).start();
			//new RaiseToteWithoutPID(false).start();
		}
		
		//actually must be double pressed if you want a full score, e.g. no container
		if (oi.operator.scoreAllButton.get() && oi.operator.scoreAllSafteyButton.get() && !prevStateScore) {
			//only if it is in the first stage will the button trigger a new command
			//new ScoreAllAndResetFromTop().start();
			new ScoreTotesOnPlatform().start();
		}

		
		//safety in case operator picks up container manually? TODO
		
		
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

	

		// throttle
		if (oi.operator.releaseButton.get() && !prevStateRelease) {
			new SetIntakeArmPositionWithoutPIDThreeState(IntakePortSubsystem.PIDConstants.RELEASING_STATE).start();
		} else if (oi.operator.grabButton.get() && !prevStateGrab) {
			new SetIntakeArmPositionWithoutPIDThreeState(IntakePortSubsystem.PIDConstants.GRABBING_STATE).start();
		} 
		else if(oi.operator.storeButton.get() && !prevStateStore) {
			new SetIntakeArmPositionWithoutPIDThreeState(IntakePortSubsystem.PIDConstants.STORE_STATE).start();
		}
	
	
	
	
//			if (oi.operator.isGrabArmState()) {
//				new SetIntakeArmPositionWithoutPID(IntakeStarboardSubsystem.PIDConstants.GRABBING_STATE).start();
//				SmartDashboard.putString("button 11", "is pressed");
//			}
//			if (oi.operator.isReleaseArmState()) {
//				SmartDashboard.putString("button 11", "is pressed");
//				new SetIntakeArmPositionWithoutPID(IntakeStarboardSubsystem.PIDConstants.RELEASING_STATE).start();
//			}
//			if (oi.operator.isStoreArmState()) {
//				new SetIntakeArmPositionWithoutPID(IntakeStarboardSubsystem.PIDConstants.STORE_STATE).start();
//				SmartDashboard.putString("button 11", "is pressed");
//			}
//		}


		// set the previous states
		prevStateRaiseTote = oi.operator.raiseToteButton.get();
		prevStateLowerTote = oi.operator.lowerToteButton.get();
		prevStateContainerSequence = oi.operator.containerSequenceButton.get();
		prevStateScore = oi.operator.scoreAllButton.get();
		prevStateGrab = oi.operator.grabButton.get();
		prevStateRelease = oi.operator.releaseButton.get();
		prevStateStore = oi.operator.storeButton.get();
		prevStateManualOverride1 = oi.driver.manualOverrideButton1.get();
		prevStateManualOverride2 = oi.driver.manualOverrideButton2.get();
		prevStateIntake = oi.operator.intakeButton.get();
		prevStatePurge = oi.operator.purgeButton.get();
		/**************** MANUAL **********************/
		if (oi.driver.isManualOverride()) {

			chainLiftSubsystem.firstStageOfScore = true;
			
			chainLiftSubsystem.setPower(oi.manual.getLiftPower()/2);
//no.
			if (Math.abs(oi.manual.leftHardStopInPower()) > 0.05) {
				intakeLeftSubsystem.arm.set(-oi.manual.leftHardStopInPower());
			} else if(oi.manual.leftHardStopOut.get()) {
				intakeLeftSubsystem.arm.set(0.2);
			} else {
				intakeLeftSubsystem.arm.set(0.0);
			}
			
			if (Math.abs(oi.manual.rightHardStopInPower()) > 0.05) {
				intakeRightSubsystem.arm.set(oi.manual.rightHardStopInPower());
			} else if(oi.manual.rightHardStopOut.get()) {
				intakeRightSubsystem.arm.set(-0.3);
			} else {
				intakeRightSubsystem.arm.set(0.0);
			}
			
		
			intakeLeftSubsystem.roller.set(-oi.manual.getRollerPower());
			intakeRightSubsystem.roller.set(-oi.manual.getRollerPower());

		}
	//	SmartDashboard.putData("Container [. 8Mode", containerChooser);
		SmartDashboard.putBoolean("Container State", containerState);
		SmartDashboard.putData("Chain Encoder 1", chainLiftSubsystem.encoders[0]);
		SmartDashboard.putData("Chain Encoder 2", chainLiftSubsystem.encoders[1]);
		
		SmartDashboard.putData("Drive Encoder Right", drivetrainSubsystem.encoders[0]);
		SmartDashboard.putData("Drive Encoder Left", drivetrainSubsystem.encoders[1]);
		
		//SmartDashboard.putNumber("Drive Distance", drivetrainSubsystem.getDistance());
		SmartDashboard.putBoolean("Max Hal", chainLiftSubsystem.isMaxLimitPressed());
		SmartDashboard.putBoolean("Reset Ryan Lewis", chainLiftSubsystem.isResetLimitPressed());
		SmartDashboard.putBoolean("Bumper Right", intakeRightSubsystem.isToteLimitPressed());
		SmartDashboard.putBoolean("Bumper Left", intakeLeftSubsystem.isToteLimitPressed());
		SmartDashboard.putBoolean("Arm Left Limit", intakeLeftSubsystem.isArmLimitPressed());
		SmartDashboard.putBoolean("Arm Right Limit", intakeRightSubsystem.isArmLimitPressed());
		
		SmartDashboard.putBoolean("READY TO PICK UP (BUMPER)", intakeLeftSubsystem.isToteLimitPressed());
		//SmartDashboard.
		
		SmartDashboard.putBoolean("IS PAST TOP", chainLiftSubsystem.isPastTop);
		SmartDashboard.putBoolean("IS PAST BOTTOM", chainLiftSubsystem.isPastBottom);
		
		SmartDashboard.putBoolean("IS AT BASE VARIABLE", chainLiftSubsystem.isAtBase);

		SmartDashboard.putNumber("Chain Height", chainLiftSubsystem.getHeight());
		SmartDashboard.putNumber("GOAL HEIGHT", chainLiftSubsystem.offsetHeight + chainLiftSubsystem.setpointHeight);
		SmartDashboard.putNumber("number of totes", chainLiftSubsystem.getNumTotes());
		SmartDashboard.putBoolean("Ready for Container", chainLiftSubsystem.readyToPickContainer);

		SmartDashboard.putNumber("Intake Right Pot",intakeRightSubsystem.getPot()); /// IntakeRightSubsystem.PIDConstants.CONVERSION_DEGREES_TO_POT);
		SmartDashboard.putNumber("Intake Left Pot", intakeLeftSubsystem.getPot()); // / IntakeLeftSubsystem.PIDConstants.CONVERSION_DEGREES_TO_POT);
		SmartDashboard.putNumber("Lift Right Current", pdp.getCurrent(12));
		SmartDashboard.putNumber("Lift Left Current", pdp.getCurrent(13));
		SmartDashboard.putNumber("Left Arm Current", pdp.getCurrent(10));
		SmartDashboard.putNumber("Right Arm Current", pdp.getCurrent(8));
		SmartDashboard.putNumber("Roller Right Current", pdp.getCurrent(4));
		SmartDashboard.putNumber("Roller Left Current", pdp.getCurrent(9)); //no.
		//SmartDashboard.putNumber("Ultra Sonic", chainLiftSubsystem.ultra.);
		SmartDashboard.putNumber("gryo", drivetrainSubsystem.gyro.getAngle());
		
		SmartDashboard.putBoolean("WITHIN BOUNDS LEFT", intakeLeftSubsystem.withinBounds());
		SmartDashboard.putBoolean("WITHIN BOUNDS RIGHT", intakeRightSubsystem.withinBounds());
		SmartDashboard.putNumber("LEFT arm STATE", intakeLeftSubsystem.state);
		SmartDashboard.putNumber("RIGHT arm STATE", intakeRightSubsystem.state);
		SmartDashboard.putBoolean("Ready to pick up cont", chainLiftSubsystem.readyToPickContainer);
		SmartDashboard.putBoolean("First Stage of Score", chainLiftSubsystem.firstStageOfScore);
//		
		if (chainLiftSubsystem.isResetLimitPressed()){
			SmartDashboard.putBoolean("IS RESET TRIPPED AT ALL", true);
		}
		
		intakeLeftSubsystem.setStateBasedOnPID();
		intakeRightSubsystem.setStateBasedOnPID();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();

	}
}
