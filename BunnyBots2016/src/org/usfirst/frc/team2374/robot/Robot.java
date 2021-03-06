package org.usfirst.frc.team2374.robot;

import org.usfirst.frc.team2374.robot.commands.DriveStraight;
import org.usfirst.frc.team2374.robot.commands.TimedStraight;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team2374.robot.subsystems.Gun;
import org.usfirst.frc.team2374.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
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
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	public static Drivetrain drivetrain;
	public static Turret turret;
	public static Gun gun;

	Preferences prefs;
	SendableChooser autoChooser;
	Command autonomousCommand;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		drivetrain = new Drivetrain();
		turret = new Turret();
		gun = new Gun();
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Drive Straight", new DriveStraight(3, 0.5));
		autoChooser.addDefault("Timed Straight", new TimedStraight(2.75, 1));
		autoChooser.addObject("Do Nothing", null);
		SmartDashboard.putData("Autonomus Command", autoChooser);
		SmartDashboard.putData(Scheduler.getInstance());
		prefs = Preferences.getInstance();
		prefs.putDouble("DriveStraight - Meters", 5);
		prefs.putDouble("DriveStraight - Speed", 0.5);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		gun.setPower(false);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		/*
		 * double meters = prefs.getDouble("DriveStraight - Meters", 5); double
		 * speed = prefs.getDouble("DriveStraight - Speed", 0.5); String
		 * autoSelected = SmartDashboard.getString("Autonomus Command",
		 * "Drive Straight"); switch (autoSelected) { case "Drive Straight":
		 * default: autonomousCommand = new DriveStraight(meters, speed); case
		 * "Do Nothing": autonomousCommand = null; break; }
		 */
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		double[] dtrainValues = drivetrain.getMotorValues();
		SmartDashboard.putNumber("fLeftCIM", dtrainValues[0]);
		SmartDashboard.putNumber("fRightCIM", dtrainValues[1]);
		SmartDashboard.putNumber("bLeftCIM", dtrainValues[2]);
		SmartDashboard.putNumber("bRightCIM", dtrainValues[3]);
		SmartDashboard.putNumber("gyroAngle", drivetrain.getGyro());
		SmartDashboard
				.putNumber("displacementX", drivetrain.getDisplacementX());
		SmartDashboard
				.putNumber("displacementY", drivetrain.getDisplacementY());
		SmartDashboard.putBoolean("isCoupled", drivetrain.isCoupled());
		SmartDashboard.putNumber("turretCIM", turret.getMotorValue());
		SmartDashboard.putNumber("gunPower", gun.getPowerValue());
		SmartDashboard.putBoolean("isRolling", gun.isRolling());
		SmartDashboard.putBoolean("isPushing", gun.isPushing());
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		gun.setPower(true);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		double[] dtrainValues = drivetrain.getMotorValues();
		SmartDashboard.putNumber("fLeftCIM", dtrainValues[0]);
		SmartDashboard.putNumber("fRightCIM", dtrainValues[1]);
		SmartDashboard.putNumber("bLeftCIM", dtrainValues[2]);
		SmartDashboard.putNumber("bRightCIM", dtrainValues[3]);
		SmartDashboard.putNumber("gyroAngle", drivetrain.getGyro());
		SmartDashboard
				.putNumber("displacementX", drivetrain.getDisplacementX());
		SmartDashboard
				.putNumber("displacementY", drivetrain.getDisplacementY());
		SmartDashboard.putBoolean("isCoupled", drivetrain.isCoupled());
		SmartDashboard.putNumber("turretCIM", turret.getMotorValue());
		SmartDashboard.putNumber("gunPower", gun.getPowerValue());
		SmartDashboard.putBoolean("isRolling", gun.isRolling());
		SmartDashboard.putBoolean("isPushing", gun.isPushing());
		SmartDashboard.putNumber("Accel X", drivetrain.getAcclerationX());
		SmartDashboard.putNumber("Accel Y", drivetrain.getAcclerationY());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
