package frc.robot.subsystems;
import frc.robot.generated.TunerConstants.AlgaePivotSubsystemConstants;

// motor 62
public final class AlgaePivotSubsystem extends SuperClassMotor {

    private String m_target = new String();

    public AlgaePivotSubsystem(int deviceId) {

        super(deviceId, "AlgaePivot");

        //gotoZeroInit();   // comment this out to skip the automatic homing process
        setPositionZero();  // in lieu of automatic homing, just set position to zero
    }


    @Override
    public boolean atZero() {

        // boolean done = atLimitHigh();

        // if (done == false) {
        //     goUpSlow();
        // }

        // return done;
        return true;    //skip for testing
    }

    @Override
    public boolean atRest() {
        
        // boolean done = atMiddle();

        // if (done == false) {
        //     gotoMiddle();
        // }

        // return done;
        return true;    //skip for testing
    }


    @Override
    public boolean atLimitHigh() {
        return m_limitSwitch.isPressed();
        //return ((getPosition() <= 1) || m_limitSwitch.isPressed());
    }

    @Override
    public boolean atLimitLow() {
        return ((AlgaePivotSubsystemConstants.k_pointLowered - getPosition()) <= 1.5);
    }


    @Override
    public double speedUpFast() {
        return AlgaePivotSubsystemConstants.k_speedUpFactor;
    }

    @Override
    public double speedUpSlow() {
        return AlgaePivotSubsystemConstants.k_speedUpFactor;
    }

    @Override
    public double speedDownFast() {
        return AlgaePivotSubsystemConstants.k_speedDownFactor;
    }

    @Override
    public double speedDownSlow() {
        return AlgaePivotSubsystemConstants.k_speedDownFactor;
    }


    @Override
    public void goStill() {

        m_target = "";
        super.goStill();

    }


    public boolean atMiddle() {
        return (Math.abs(getPosition() - AlgaePivotSubsystemConstants.k_pointMiddle) < 1.5);
    }

    public void gotoMiddle() {

        m_target = "Middle";

        if (atMiddle()) {
            goStill();
        } else if (getPosition() < AlgaePivotSubsystemConstants.k_pointMiddle) {
            goDown();
        } else if (getPosition() > AlgaePivotSubsystemConstants.k_pointMiddle) {
            goUp();
        }
    }


    @Override
    public void periodic() {

        super.periodic();

        if (atMiddle() && (m_target == "Middle")) {
            goStill();
        }

    }

}