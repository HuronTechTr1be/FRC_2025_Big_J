package frc.robot.subsystems;
import frc.robot.generated.TunerConstants.CoralSubsystemConstants;

// motor 21
public final class CoralSubsystem extends SuperClassMotor {

    public CoralSubsystem(int deviceId) {

        super(deviceId, "CoralGripper");

    }

    @Override
    public boolean atZero() {
        return true;    // No Zero exists
    }

    @Override
    public boolean atRest() {
        return true;    // No AtRest exists
    }


    @Override
    public boolean atLimitHigh() {
        return false;   // No limit exists
    }

    @Override
    public boolean atLimitLow() {
        return false;   // No limit exists
    }


    @Override
    public double speedUpFast() {
        return CoralSubsystemConstants.k_intakeFactor;
    }

    @Override
    public double speedUpSlow() {
        return CoralSubsystemConstants.k_intakeFactor;
    }

    @Override
    public double speedDownFast() {
        return CoralSubsystemConstants.k_releaseFactor;
    }

    @Override
    public double speedDownSlow() {
        return CoralSubsystemConstants.k_releaseFactorLow;
    }


    public void IntakeCoral() {
        goUp();
    }

    public void ReleaseCoral() {
        goDown();
    }

}