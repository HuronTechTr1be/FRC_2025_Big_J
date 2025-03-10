package frc.robot.subsystems;
import frc.robot.generated.TunerConstants.AlgaeSubsystemConstants;

// motor 61
public final class AlgaeSubsystem extends SuperClassMotor {

    public AlgaeSubsystem(int deviceId) {

        super(deviceId, "AlgaeGripper");

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
        return AlgaeSubsystemConstants.k_intakeFactor;
    }

    @Override
    public double speedUpSlow() {
        return AlgaeSubsystemConstants.k_intakeFactor;
    }

    @Override
    public double speedDownFast() {
        return AlgaeSubsystemConstants.k_releaseFactor;
    }

    @Override
    public double speedDownSlow() {
        return AlgaeSubsystemConstants.k_releaseFactor;
    }

    
    public void IntakeAlgae() {
        goUp();
    }

    public void ReleaseAlgae() {
        goDown();
    }

}