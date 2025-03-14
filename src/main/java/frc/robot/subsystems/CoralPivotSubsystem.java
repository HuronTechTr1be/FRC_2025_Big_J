package frc.robot.subsystems;
import frc.robot.generated.TunerConstants.AlgaePivotSubsystemConstants;
import frc.robot.generated.TunerConstants.CoralPivotSubsystemConstants;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// motor 22
public final class CoralPivotSubsystem extends SuperClassMotor {

    private String m_target = new String();

    // private ShuffleboardTab coralTab = Shuffleboard.getTab("Coral");

    public CoralPivotSubsystem(int deviceId) {

        super(deviceId, "CoralPivot");

        // coralTab.addNumber("Coral Position", () -> getPivotEncoder());
        // coralTab.addBoolean("Coral Raised", () -> isRaised());
        // coralTab.addBoolean("Coral Lowered", () -> isLowered());

        //gotoZeroInit();   // comment this out to skip the automatic homing process
        setPositionZero();  // in lieu of automatic homing, just set position to zero

    }


    @Override
    public boolean atZero() {

        boolean done = onSwitch();  //atLimitHigh();

        if (done == false) {
            goUpSlow();
        }

        return done;
        //return true;    //skip for testing
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


    private boolean onSwitch() {
        return m_limitSwitch.isPressed();
    }

    @Override
    public boolean atLimitHigh() {
        //return m_limitSwitch.isPressed();
        //return ((getPosition() <= 1) || m_limitSwitch.isPressed());
        return (getPosition() <= 2.5);
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


    @Override
    public void goStill() {

        m_target = "";
        super.goStill();

    }


    public boolean atMiddle() {
        return (Math.abs(getPosition() - CoralPivotSubsystemConstants.k_pointMiddle) < 1.5);
    }

    public void gotoMiddle() {

        m_target = "Middle";

        if (atMiddle()) {
            goStill();
        } else if (getPosition() < CoralPivotSubsystemConstants.k_pointMiddle) {
            goDown();
        } else if (getPosition() > CoralPivotSubsystemConstants.k_pointMiddle) {
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