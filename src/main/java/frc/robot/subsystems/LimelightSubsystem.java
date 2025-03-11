package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;

import frc.robot.subsystems.LimelightHelpers;

public class LimelightSubsystem extends SubsystemBase{

   private LimelightHelpers limelight;

    public LimelightSubsystem(LimelightHelpers m_limelight) {

        limelight = m_limelight;

    } 

    public double getTX(){
        return limelight.getTX("");
    }

    public void periodic(){
        SmartDashboard.putNumber("TX", getTX());
        SmartDashboard.putNumber("TY", limelight.getTY(""));
        SmartDashboard.putNumber("difference", 23-getTX());

    }
    
}
