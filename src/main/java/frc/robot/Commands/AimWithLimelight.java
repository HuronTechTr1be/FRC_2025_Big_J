
package frc.robot.Commands;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LimelightHelpers;
import frc.robot.subsystems.LimelightSubsystem;

public class AimWithLimelight extends Command {

    private final LimelightSubsystem limelight;
    private final CommandSwerveDrivetrain drivetrain;

     private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); 
    // 3/4 of a rotation per second max angular velocity
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond);
 
    
    private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric()
        .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1)
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    public AimWithLimelight(LimelightSubsystem m_limelight, CommandSwerveDrivetrain drive) {

        limelight = m_limelight;
        drivetrain = drive;
        addRequirements(limelight, drivetrain);

    }

    @Override
    public void initialize() {

        drivetrain.setDefaultCommand(drivetrain.applyRequest(() -> drive.withVelocityX(0) 
        .withVelocityY((23-limelight.getTX())*.1) 
        .withRotationalRate(0) 
    ));



    }

    @Override
    public boolean isFinished() {

        // m_elevator.ElevatorStill();
        return true;

    }

}
