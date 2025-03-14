// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.Date;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
import frc.robot.Commands.ElevatorResetCommand;
import frc.robot.Commands.ElevatorSetHighCommand;
import frc.robot.Commands.ElevatorSetLoweredCommand;
import frc.robot.Commands.ElevatorSetMiddleCommand;
import frc.robot.Commands.ElevatorStillCommand;
import frc.robot.Commands.ElevatorUpCommand;
import frc.robot.Commands.ResetDrive;
import frc.robot.Commands.AlgaeGrabCommand;
import frc.robot.Commands.CoralGrabCommand;
import frc.robot.Commands.CoralPositionResetCommand;
import frc.robot.Commands.CoralDownCommand;
import frc.robot.Commands.CoralUpCommand;
import frc.robot.Commands.CoralStillCommand;
import frc.robot.Commands.CoralPositionStillCommand;
import frc.robot.Commands.AimWithLimelight;
import frc.robot.Commands.AlgaeDownCommand;
import frc.robot.Commands.AlgaeMiddleCommand;
import frc.robot.Commands.AlgaePositionResetCommand;
import frc.robot.Commands.AlgaePositionStillCommand;
import frc.robot.Commands.AlgaeUpCommand;
import frc.robot.Commands.AlgaeReleaseCommand;
import frc.robot.Commands.AlgaeStillCommand;
import frc.robot.Commands.CoralReleaseCommand;
import frc.robot.Commands.CoralRotateDownCommand;
import frc.robot.Commands.CoralRotateResetCommand;
import frc.robot.Commands.CoralRotateStillCommand;
import frc.robot.Commands.CoralRotateUpCommand;
import frc.robot.Commands.CoralSoftReleaseCommand;
import frc.robot.subsystems.AlgaePivotSubsystem;
import frc.robot.subsystems.AlgaeSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.CoralPivotSubsystem;
import frc.robot.subsystems.CoralRotateSubsystem;
import frc.robot.subsystems.CoralSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LimelightHelpers;
import frc.robot.subsystems.LimelightSubsystem;


public class RobotContainer {
    // kSpeedAt12Volts desired top speed
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); 
    // 3/4 of a rotation per second max angular velocity
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond);

    /* Setting up bindings for necessary control of the swerve drive platform */

    // The following "drive" should be used for a field-centric design
    // private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
    //         .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
    //         .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    // The following "drive" should be used for a robot-centric design
    private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric()
        .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1)
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
    
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private LimelightHelpers limelighthelpers = new LimelightHelpers();
    private LimelightSubsystem limelight = new LimelightSubsystem(limelighthelpers);

    private final CommandXboxController joystick = new CommandXboxController(0);
    private final CommandXboxController operator = new CommandXboxController(1);
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    private CoralSubsystem m_coral = new CoralSubsystem(51);
    private CoralRotateSubsystem m_coralRotate = new CoralRotateSubsystem(21);
    private CoralPivotSubsystem m_coralPivot = new CoralPivotSubsystem(22);

    private AlgaeSubsystem m_algae = new AlgaeSubsystem(61);
    private AlgaePivotSubsystem m_algaePivot = new AlgaePivotSubsystem(62);

    //private ClimbSubsystem m_climb = new ClimbSubsystem();
    private ElevatorSubsystem m_elevator = new ElevatorSubsystem();

    Trigger XButtonOp = operator.x();
    Trigger YButtonOp = operator.y();
    Trigger BButtonOp = operator.b();
    Trigger AButtonOp = operator.a();
    Trigger LeftBumperOp = operator.leftBumper();
    Trigger RightBumperOp = operator.rightBumper();
    Trigger LeftTriggerOp = operator.leftTrigger();
    Trigger RightTriggerOp = operator.rightTrigger();
    Trigger DpadUpOp = operator.povUp();
    Trigger DpadDownOp = operator.povDown();
    Trigger DpadLeftOp = operator.povLeft();
    Trigger DpadRightOp = operator.povRight();

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
    Trigger DpadLeftDriver = joystick.povLeft();
    Trigger DpadRightDriver = joystick.povRight();


    ElevatorUpCommand elevatorUp = new ElevatorUpCommand(m_elevator);
    ElevatorDownCommand elevatorDown = new ElevatorDownCommand(m_elevator);
    ElevatorStillCommand elevatorStill = new ElevatorStillCommand(m_elevator);
    ElevatorSetHighCommand ElevatorSetHigh = new ElevatorSetHighCommand(m_elevator);
    ElevatorSetMiddleCommand ElevatorSetMiddle = new ElevatorSetMiddleCommand(m_elevator);
    ElevatorSetLoweredCommand ElevatorSetLowered = new ElevatorSetLoweredCommand(m_elevator);
    ElevatorResetCommand elevatorReset = new ElevatorResetCommand(m_elevator);

    //ClimbDownCommand climbDown = new ClimbDownCommand(m_climb);
    //ClimbUpCommand climbUp = new ClimbUpCommand(m_climb);
    //ClimbStillCommand climbStill = new ClimbStillCommand(m_climb);

    CoralDownCommand CoralPositionDown = new CoralDownCommand(m_coralPivot);
    CoralUpCommand CoralPositionUp = new CoralUpCommand(m_coralPivot);
    CoralPositionStillCommand CoralPositionStill = new CoralPositionStillCommand(m_coralPivot);
    CoralPositionResetCommand coralPositionReset = new CoralPositionResetCommand(m_coralPivot);

    CoralRotateUpCommand CoralRotateUp = new CoralRotateUpCommand(m_coralRotate);
    CoralRotateDownCommand CoralRotateDown = new CoralRotateDownCommand(m_coralRotate);
    CoralRotateStillCommand CoralRotateStill = new CoralRotateStillCommand(m_coralRotate);
    CoralRotateResetCommand coralRotateReset = new CoralRotateResetCommand(m_coralRotate);

    CoralGrabCommand CoralGrab = new CoralGrabCommand(m_coral);
    CoralReleaseCommand CoralRelease = new CoralReleaseCommand(m_coral);
    CoralSoftReleaseCommand CoralSoftRelease = new CoralSoftReleaseCommand(m_coral);
    CoralStillCommand CoralStill = new CoralStillCommand(m_coral);


    AlgaeDownCommand AlgaePositionDown = new AlgaeDownCommand(m_algaePivot);
    AlgaeMiddleCommand AlgaePositionMiddle = new AlgaeMiddleCommand(m_algaePivot);
    AlgaeUpCommand AlgaePositionUp = new AlgaeUpCommand(m_algaePivot);
    AlgaePositionStillCommand AlgaePositionStill = new AlgaePositionStillCommand(m_algaePivot);
    AlgaePositionResetCommand algaePositionReset = new AlgaePositionResetCommand(m_algaePivot);

    AlgaeGrabCommand AlgaeGrab = new AlgaeGrabCommand(m_algae, m_algaePivot);
    AlgaeReleaseCommand AlgaeRelease = new AlgaeReleaseCommand(m_algae);
    AlgaeStillCommand AlgaeStill = new AlgaeStillCommand(m_algae);

    AimWithLimelight LimelightAim = new AimWithLimelight(limelight, drivetrain);
    ResetDrive DriveReset = new ResetDrive(drivetrain, joystick, EffectiveSpeed());


    PathPlannerAuto testautoooo;
    PathPlannerAuto newauto;
    PathPlannerAuto straight;
    PathPlannerAuto straightAlgae;
    PathPlannerAuto rightHander;
    PathPlannerAuto spinauto;
    PathPlannerAuto gavindumb;

    private final SendableChooser<Command> autoChooser;


    public RobotContainer() {

        NamedCommands.registerCommand("elevatorUp", elevatorUp);
        NamedCommands.registerCommand("elevatorDown", elevatorDown);
        NamedCommands.registerCommand("elevatorStill", elevatorStill);

        NamedCommands.registerCommand("ElevatorSetHigh", ElevatorSetHigh);
        NamedCommands.registerCommand("ElevatorSetMiddle", ElevatorSetMiddle);
        NamedCommands.registerCommand("ElevatorSetLowered", ElevatorSetLowered);

        NamedCommands.registerCommand("CoralRotateUp", CoralRotateUp);
        NamedCommands.registerCommand("CoralRotateDown", CoralRotateDown);


        // NamedCommands.registerCommand("climbUp", climbUp);
        // NamedCommands.registerCommand("climbDown", climbDown);
        // NamedCommands.registerCommand("climbStill", climbStill);

        // Coral
        NamedCommands.registerCommand("CoralPositionScore", CoralPositionUp);
        NamedCommands.registerCommand("CoralPositionDown", CoralPositionDown);
        NamedCommands.registerCommand("CoralPositionStill", CoralPositionStill);

        NamedCommands.registerCommand("CoralGrab", CoralGrab);
        NamedCommands.registerCommand("CoralRelease", CoralRelease);
        NamedCommands.registerCommand("CoralSoftRelease", CoralSoftRelease);
        NamedCommands.registerCommand("CoralStill", CoralStill);

        // Algae
        NamedCommands.registerCommand("AlgaePositionDown", AlgaePositionDown);
        NamedCommands.registerCommand("AlgaePositionMiddle", AlgaePositionMiddle);
        NamedCommands.registerCommand("AlgaePositionUp", AlgaePositionUp);
        NamedCommands.registerCommand("AlgaePositionStill", AlgaePositionStill);

        NamedCommands.registerCommand("AlgaeGrab", AlgaeGrab);
        NamedCommands.registerCommand("AlgaeRelease", AlgaeRelease);
        NamedCommands.registerCommand("AlgaeStill", AlgaeStill);


        testautoooo = new PathPlannerAuto("TestAuto");
        newauto = new PathPlannerAuto("New Auto");
        spinauto = new PathPlannerAuto("Spin");
        gavindumb = new PathPlannerAuto("Gavins dumb little path");

        autoChooser = AutoBuilder.buildAutoChooser("Straight");
        SmartDashboard.putData("Auto Mode", autoChooser);


        configureBindings();
    }

    private double SpeedHigh = MaxSpeed * 0.72;
    private double SpeedLow = MaxSpeed * 0.11;
    private double RotationHigh = MaxAngularRate * 0.55;
    private double RotationLow = MaxAngularRate * 0.25;

    private double EffectiveSpeed() {
        return (LeftTriggerDriver.getAsBoolean() ? SpeedLow : SpeedHigh);
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            // Drive forward with negative Y (forward)
            // Drive left with negative X (left)
            // Drive counterclockwise with negative X (left)
            drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * EffectiveSpeed()) 
                .withVelocityY(-joystick.getLeftX() * EffectiveSpeed()) 
                .withRotationalRate(-joystick.getRightX() * RotationHigh) 
            ));

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(
                () -> point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        // joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        // joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        // joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        // joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        //joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);

        // Hold down the Y Button to allow Limelight to center on the tag
        // YButtonDriver.whileTrue(
        //     drivetrain.applyRequest(() -> drive.withVelocityX(0) 
        //         .withVelocityY((23 - limelight.getTX()) * 0.05) 
        //         .withRotationalRate(0) 
        //     )
        // );


        // Sample to use Runnable commands - not working properly
        //YButtonOp.onTrue(m_coralPivot.runOnce(m_coralPivot.SetPivotLowered()));

        // Elevator commands
        YButtonOp.onTrue(ElevatorSetHigh);
        YButtonOp.onTrue(CoralRotateDown);
        YButtonOp.onTrue(CoralPositionUp);
        //YButtonOp.onFalse(CoralPositionStill); // remove to allow press and release

        BButtonOp.onTrue(ElevatorSetMiddle);
        BButtonOp.onTrue(CoralRotateDown);
        BButtonOp.onTrue(CoralPositionUp);
        //BButtonOp.onFalse(CoralPositionStill); // remove to allow press and release
        
        AButtonOp.onTrue(ElevatorSetLowered);
        AButtonOp.onTrue(CoralRotateUp);
        AButtonOp.onTrue(CoralPositionDown);
        //AButtonOp.onFalse(CoralPositionStill); // remove to allow press and release

        XButtonOp.onTrue(ElevatorSetLowered);
        XButtonOp.onTrue(CoralRotateUp);
        XButtonOp.onTrue(CoralPositionUp);
        //XButtonOp.onFalse(CoralPositionStill); // remove to allow press and release

        DpadUpOp.onTrue(elevatorUp);
        DpadUpOp.onFalse(elevatorStill);
        
        DpadDownOp.onTrue(elevatorDown);
        DpadDownOp.onFalse(elevatorStill);

        StartButtonDriver.and(BackButtonDriver).onTrue(elevatorReset);
        StartButtonDriver.and(BackButtonDriver).onTrue(coralPositionReset);
        StartButtonDriver.and(BackButtonDriver).onTrue(coralRotateReset);
        StartButtonDriver.and(BackButtonDriver).onTrue(algaePositionReset);

        //Algea Knocker Downer
        //DpadRightDriver.onTrue(climbDown);
        //DpadRightDriver.onFalse(climbStill);

        //DpadLeftDriver.onTrue(climbUp);
        //DpadLeftDriver.onFalse(climbStill);


        // Coral commands
        RightTriggerOp.onTrue(CoralPositionDown);
        RightTriggerOp.onFalse(CoralPositionStill);

        RightBumperOp.onTrue(CoralPositionUp);
        RightBumperOp.onFalse(CoralPositionStill);

        LeftTriggerOp.onTrue(CoralRelease);
        LeftTriggerOp.onFalse(CoralStill);

        LeftBumperOp.onTrue(CoralGrab);
        LeftBumperOp.onFalse(CoralStill);

        DpadLeftOp.onTrue(CoralRotateDown);
        DpadLeftOp.onFalse(CoralRotateStill);

        DpadRightOp.onTrue(CoralRotateUp);
        DpadRightOp.onFalse(CoralRotateStill);

        // Algae commands
        RightTriggerDriver.onTrue(AlgaePositionDown);
        RightTriggerDriver.onTrue(AlgaeGrab);
        RightTriggerDriver.onFalse(AlgaePositionMiddle);
        RightTriggerDriver.onFalse(AlgaeStill);
        

        //RightBumperDriver.onTrue(AlgaePositionUp); // Commented out to remain in middle position
        RightBumperDriver.onTrue(AlgaeRelease);
        RightBumperDriver.onFalse(AlgaeStill);

        // Testing Limelight command
        // RightBumperDriver.onTrue(LimelightAim);
        // RightBumperDriver.onFalse(DriveReset);



        // Climb commands
        //StartButtonDriver.onTrue(AlgaePositionDown);

    }

    public void autonPeriodic(){
        if(m_coralPivot.atLimitLow()){
            if(m_coralPivot.goingUp()){
                
            }
            else{
                m_coralPivot.goStill();
            }
        }
        if(m_coralPivot.atLimitHigh()){
            if(m_coralPivot.goingDown()){
                
            }
            else{
                m_coralPivot.goStill();
            }
        }
        SmartDashboard.putNumber("coral pivot", m_coralPivot.getPosition());
        
        //m_coral.ElevatorIsLowered(m_elevator.ElevatorLowered());

    }


    public void periodic() {

        //SmartDashboard.putNumber("difference", 23-limelight.getTX());
        limelight.periodic();
        //m_coral.ElevatorIsLowered(m_elevator.ElevatorLowered());


        
        // if (LeftTriggerDriver.getAsBoolean()) {
        //     m_algaePivot.algaePivotUp();
        //     }
                


            // climb functions for testing

        // if (StartButtonDriver.getAsBoolean() && BackButtonDriver.getAsBoolean()) {
        //     m_climb.ClimbDown();
        // } else if (YButtonDriver.getAsBoolean()) {
        //     m_climb.ClimbUp();
        // } else {
        //     m_climb.ClimbStill();
        // }
        

    }


    public Command getAutonomousCommand() {
        // return Commands.print("No autonomous command configured");
        //return newauto;
        return autoChooser.getSelected();
    }
}
