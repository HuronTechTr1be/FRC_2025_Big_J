// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

import frc.robot.Commands.ClimbDownCommand;
import frc.robot.Commands.ClimbStillCommand;
import frc.robot.Commands.ClimbUpCommand;
import frc.robot.Commands.ElevatorDownCommand;
import frc.robot.Commands.ElevatorStillCommand;
import frc.robot.Commands.ElevatorUpCommand;
import frc.robot.Commands.AlgaeGrabCommand;
import frc.robot.Commands.CoralGrabCommand;
import frc.robot.Commands.CoralResetCommand;
import frc.robot.Commands.CoralScoreCommand;
import frc.robot.Commands.CoralPositionStillCommand;
import frc.robot.Commands.AlgaeDownCommand;
import frc.robot.Commands.AlgaePositionStillCommand;
import frc.robot.Commands.AlgaeUpCommand;
import frc.robot.Commands.AlgaeReleaseCommand;
import frc.robot.Commands.AlgaeStillCommand;
import frc.robot.Commands.CoralReleaseCommand;
import frc.robot.subsystems.AlgaePivotSubsystem;
import frc.robot.subsystems.AlgaeSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.CoralPivotSubsystem;
import frc.robot.subsystems.CoralSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second
                                                                                      // max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController joystick = new CommandXboxController(0);
    private final CommandXboxController operator = new CommandXboxController(1);
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    private CoralSubsystem m_coral = new CoralSubsystem(21);

    private CoralPivotSubsystem m_coralPivot = new CoralPivotSubsystem(22);
    private AlgaeSubsystem m_algae = new AlgaeSubsystem(61);
    private AlgaePivotSubsystem m_algaePivot = new AlgaePivotSubsystem(62);

    private ClimbSubsystem m_climb = new ClimbSubsystem(51);
    private ElevatorSubsystem m_elevator = new ElevatorSubsystem();

    Trigger XButtonOp = operator.x();
    Trigger YButtonOp = operator.y();
    Trigger BButtonOp = operator.b();
    Trigger AButtonOp = operator.a();
    Trigger LeftBumperOp = operator.leftBumper();
    Trigger RightBumperOp = operator.rightBumper();
    Trigger LeftTriggerOp = operator.leftTrigger();
    Trigger RightTriggerOp = operator.rightTrigger();

    Trigger XButtonDriver = joystick.x();
    Trigger YButtonDriver = joystick.y();
    Trigger BButtonDriver = joystick.b();
    Trigger AButtonDriver = joystick.a();
    Trigger LeftBumperDriver = joystick.leftBumper();
    Trigger RightBumperDriver = joystick.rightBumper();
    Trigger LeftTriggerDriver = joystick.leftTrigger();
    Trigger RightTriggerDriver = joystick.rightTrigger();
    Trigger StartButtonDriver = joystick.start();
    Trigger BackButtonDriver = joystick.back();

    ElevatorUpCommand elevatorUp = new ElevatorUpCommand(m_elevator);
    ElevatorDownCommand elevatorDown = new ElevatorDownCommand(m_elevator);
    ElevatorStillCommand elevatorStill = new ElevatorStillCommand(m_elevator);

    ClimbDownCommand climbDown = new ClimbDownCommand(m_climb);
    ClimbUpCommand climbUp = new ClimbUpCommand(m_climb);
    ClimbStillCommand climbStill = new ClimbStillCommand(m_climb);

    CoralResetCommand CoralPositionDown = new CoralResetCommand(m_coralPivot);
    CoralScoreCommand CoralPositionScore = new CoralScoreCommand(m_coralPivot);
    CoralPositionStillCommand CoralPositionStill = new CoralPositionStillCommand(m_coralPivot);

    CoralGrabCommand CoralGrab = new CoralGrabCommand(m_coral);
    CoralReleaseCommand CoralRelease = new CoralReleaseCommand(m_coral);

    AlgaeDownCommand AlgaePositionDown = new AlgaeDownCommand(m_algaePivot);
    AlgaeUpCommand AlgaePositionUp = new AlgaeUpCommand(m_algaePivot);
    AlgaePositionStillCommand AlgaePositionStill = new AlgaePositionStillCommand(m_algaePivot);

    AlgaeGrabCommand AlgaeGrab = new AlgaeGrabCommand(m_algae, m_algaePivot);
    AlgaeReleaseCommand AlgaeRelease = new AlgaeReleaseCommand(m_algae);
    AlgaeStillCommand AlgaeStill = new AlgaeStillCommand(m_algae);

    PathPlannerAuto testautoooo;

    public RobotContainer() {

        NamedCommands.registerCommand("elevatorUp", elevatorUp);
        NamedCommands.registerCommand("elevatorDown", elevatorDown);
        NamedCommands.registerCommand("elevatorStill", elevatorStill);

        NamedCommands.registerCommand("climbUp", climbUp);
        NamedCommands.registerCommand("climbDown", climbDown);
        NamedCommands.registerCommand("climbStill", climbStill);

        // Coral
        NamedCommands.registerCommand("CoralPositionScore", CoralPositionScore);
        NamedCommands.registerCommand("CoralPositionDown", CoralPositionDown);
        NamedCommands.registerCommand("CoralPositionStill", CoralPositionStill);

        NamedCommands.registerCommand("CoralGrab", CoralGrab);
        NamedCommands.registerCommand("CoralRelease", CoralRelease);

        // Algae
        NamedCommands.registerCommand("AlgaePositionDown", AlgaePositionDown);
        NamedCommands.registerCommand("AlgaePositionUp", AlgaePositionUp);
        NamedCommands.registerCommand("AlgaePositionStill", AlgaePositionStill);

        NamedCommands.registerCommand("AlgaeGrab", AlgaeGrab);
        NamedCommands.registerCommand("AlgaeRelease", AlgaeRelease);
        NamedCommands.registerCommand("AlgaeStill", AlgaeStill);

        testautoooo = new PathPlannerAuto("TestAuto");

        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
                // Drivetrain will execute this command periodically
                drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with
                                                                                                   // negative Y
                                                                                                   // (forward)
                        .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                        .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with
                                                                                    // negative X (left)
                ));

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(
                () -> point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.

        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public void periodic() {

        // algae functions - 
        // NEED: RUN INTAKE LONGER AT PICKUP
        if (RightTriggerDriver.getAsBoolean()) {
            m_algaePivot.algaePivotDown();
            m_algae.IntakeAlgae();
        } else {
            m_algaePivot.SetAlgaePivotMiddle();
            
        if (RightBumperDriver.getAsBoolean()) {
            m_algae.ReleaseAlgae();
        } else {
            m_algae.Still();
        }
        
            // algae functions for getting encoder values
            // if (XButtonDriver.getAsBoolean()) {
            // m_algaePivot.algaePivotDown();
            // } else if (YButtonDriver.getAsBoolean()) {
            // m_algaePivot.algaePivotUp();
            // } else {
            // m_algaePivot.algaePivotStill();
            // }
            }
        
        if (LeftTriggerDriver.getAsBoolean()) {
            m_algaePivot.algaePivotUp();
            }
    

            // coral pivot testing functions
        if (RightTriggerOp.getAsBoolean()) {
            //SmartDashboard.putBoolean("Right Trigger", true);
            m_coralPivot.pivotDown();
            } else if (RightBumperOp.getAsBoolean()) {
                m_coralPivot.pivotUp();
            } else {
            //SmartDashboard.putBoolean("Right Trigger", false);
                m_coralPivot.pivotStill();
            }
        
            // coral functions
        if (LeftTriggerOp.getAsBoolean()) {
                SmartDashboard.putBoolean("Coral Intake", false);
                SmartDashboard.putBoolean("Coral Release", true);
                m_coral.ReleaseCoral();
            } else if (LeftBumperOp.getAsBoolean()) {
                SmartDashboard.putBoolean("Coral Intake", true);
                SmartDashboard.putBoolean("Coral Release", false);
                m_coral.IntakeCoral();
            } else {
                SmartDashboard.putBoolean("Coral Intake", false);
                SmartDashboard.putBoolean("Coral Release", false);
                m_coral.Still();
            }
        
            // climb functions for testing
        if (StartButtonDriver.getAsBoolean() && BackButtonDriver.getAsBoolean()) {
            m_climb.climbDown();
        } else if (YButtonDriver.getAsBoolean()) {
            m_climb.climbUp();
        } else {
            m_climb.climbStill();
        }
        
            // elevator functions
            // m_elevator.periodic();
        if (YButtonOp.getAsBoolean()) {
            m_elevator.SetElevatorHigh();
            m_coralPivot.pivotDown();
        } else if (AButtonOp.getAsBoolean()) {
            m_elevator.SetElevatorLowered();
            m_coralPivot.pivotDown();
        } else if (BButtonOp.getAsBoolean()) {
            m_elevator.SetElevatorMiddle();
            m_coralPivot.pivotDown();
        } else if (XButtonOp.getAsBoolean()) {
            m_elevator.SetElevatorLowered();
            m_coralPivot.pivotUp();
        }
    }

    // Elevator Functions for Testing
    // if (YButtonOp.getAsBoolean()) {
    // m_elevator.ElevatorUp(.2);
    // } else if (AButtonOp.getAsBoolean()) {
    // m_elevator.ElevatorDown(-.2);
    // } else {
    // m_elevator.ElevatorStill();
    // }

    public Command getAutonomousCommand() {
        // return Commands.print("No autonomous command configured");
        return testautoooo;
    }
}
