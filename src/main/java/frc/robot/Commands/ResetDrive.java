
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
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class ResetDrive extends Command {

    private final CommandSwerveDrivetrain drivetrain;
    private final CommandXboxController joystick;
    private final double EffectiveSpeed;

     private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); 
    // 3/4 of a rotation per second max angular velocity
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond);

    private double SpeedHigh = MaxSpeed * 0.72;
    private double SpeedLow = MaxSpeed * 0.11;
    private double RotationHigh = MaxAngularRate * 0.55;
    private double RotationLow = MaxAngularRate * 0.25;


 
    
    private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric()
        .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1)
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    public ResetDrive(CommandSwerveDrivetrain drive, CommandXboxController controller, double effective) {

        drivetrain = drive;
        joystick = controller;
        EffectiveSpeed = effective;

        addRequirements(drivetrain);

    }

    @Override
    public void initialize() {

        
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            // Drive forward with negative Y (forward)
            // Drive left with negative X (left)
            // Drive counterclockwise with negative X (left)
            drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * EffectiveSpeed) 
                .withVelocityY(-joystick.getLeftX() * EffectiveSpeed) 
                .withRotationalRate(-joystick.getRightX() * RotationHigh) 
            ));


    }

    @Override
    public boolean isFinished() {

       
        return true;

    }

}
