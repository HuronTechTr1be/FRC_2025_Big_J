package frc.robot.subsystems;
import frc.robot.generated.TunerConstants.CoralRotateSubsystemConstants;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// motor 23
public final class CoralRotateSubsystem extends SuperClassMotor {

    // private ShuffleboardTab coralTab = Shuffleboard.getTab("Coral");

    public CoralRotateSubsystem(int deviceId) {

        super(deviceId, "CoralRotate");

        // coralTab.addNumber("Coral Position", () -> getPivotEncoder());
        // coralTab.addBoolean("Coral Raised", () -> isRaised());
        // coralTab.addBoolean("Coral Lowered", () -> isLowered());

        gotoZeroInit();

    }


    @Override
    public boolean atZero() {

        boolean done = atLimitLow();

        if (done == false) {
            goDownSlow();
        }

        return done;
        //return true;    //skip for testing
    }

    @Override
    public boolean atRest() {
        
        // boolean done = atLimitLow();

        // if (done == false) {
        //     goDownSlow();
        // }

        // return done;
        return true;    //skip for testing
    }


    @Override
    public boolean atLimitHigh() {
        return (CoralRotateSubsystemConstants.k_pointRaised - getPosition() <= 1);
    }

    @Override
    public boolean atLimitLow() {
        return m_limitSwitch.isPressed();
    }


    @Override
    public double speedUpFast() {
        return CoralRotateSubsystemConstants.k_speedUpFactor;
    }

    @Override
    public double speedUpSlow() {
        return CoralRotateSubsystemConstants.k_speedUpFactor;
    }

    @Override
    public double speedDownFast() {
        return CoralRotateSubsystemConstants.k_speedDownFactor;
    }

    @Override
    public double speedDownSlow() {
        return CoralRotateSubsystemConstants.k_speedDownFactor;
    }

}