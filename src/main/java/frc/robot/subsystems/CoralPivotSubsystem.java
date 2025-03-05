package frc.robot.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.generated.TunerConstants.CoralPivotSubsystemConstants;

// motor 22
public final class CoralPivotSubsystem extends SuperClassMotor {

    // private ShuffleboardTab coralTab = Shuffleboard.getTab("Coral");

    public CoralPivotSubsystem(int deviceId) {

        super(deviceId, "CoralPivot");

        // coralTab.addNumber("Coral Position", () -> getPivotEncoder());
        // coralTab.addBoolean("Coral Raised", () -> isRaised());
        // coralTab.addBoolean("Coral Lowered", () -> isLowered());

        gotoZeroInit();

    }


    @Override
    public boolean atZero() {

        boolean done = atLimitHigh();

        if (done == false) {
            goUpSlow();
        }

        return done;
    }

    @Override
    public boolean atRest() {
        
        boolean done = atLimitLow();

        if (done == false) {
            goDownSlow();
        }

        return done;
    }


    @Override
    public boolean atLimitHigh() {
        return m_limitSwitch.isPressed();
    }

    @Override
    public boolean atLimitLow() {
        return (Math.abs(CoralPivotSubsystemConstants.k_pointLowered - getPosition()) <= 1.5);
    }


    @Override
    public double speedUpFast() {
        return CoralPivotSubsystemConstants.k_speedUpFactor;
    }

    @Override
    public double speedUpSlow() {
        return CoralPivotSubsystemConstants.k_speedUpFactor;
    }

    @Override
    public double speedDownFast() {
        return CoralPivotSubsystemConstants.k_speedDownFactor;
    }

    @Override
    public double speedDownSlow() {
        return CoralPivotSubsystemConstants.k_speedDownFactor;
    }


    // private void UpdateDashboard() {

    //     SmartDashboard.putNumber("Coral Position", getPosition());
    //     SmartDashboard.putBoolean("Coral Raised", isRaised());
    //     SmartDashboard.putBoolean("Coral Lowered", isLowered());
    //     SmartDashboard.putBoolean("Coral Find Home", m_findHome);
    //     SmartDashboard.putBoolean("Coral Find Low", m_findLow);
    // }

    // public void periodic() {

    //     UpdateDashboard();

    // }

}