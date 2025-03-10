package frc.robot.subsystems;

import frc.robot.generated.TunerConstants.ElevatorSubsystemConstants;


public class ElevatorBasic extends SuperClassMotor {

    private boolean m_switchLow;
    private boolean m_switchHigh;

    public ElevatorBasic(int deviceId, String deviceName, boolean switchLow, boolean switchHigh) {

        super(deviceId, deviceName);

        m_switchLow = switchLow;
        m_switchHigh = switchHigh;
    }


    @Override
    public boolean atZero() {

        // boolean done = atLimitLow();

        // if (done == false) {
        //     goDownSlow();
        // }

        // return done;
        return true;    // Skip this - performed in ElevatorSubsystem
    }

    @Override
    public boolean atRest() {
        return true;    // Zero is atRest
    }


    @Override
    public boolean atLimitHigh() {
        return (m_switchHigh && m_limitSwitch.isPressed());
    }

    @Override
    public boolean atLimitLow() {
        return (m_switchLow && m_limitSwitch.isPressed());
    }


    @Override
    public double speedUpFast() {
        return ElevatorSubsystemConstants.k_ElevatorSpeedUp;
    }

    @Override
    public double speedUpSlow() {
        return ElevatorSubsystemConstants.k_ElevatorSpeedUp;
    }

    @Override
    public double speedDownFast() {
        return ElevatorSubsystemConstants.k_ElevatorSpeedDown;
    }

    @Override
    public double speedDownSlow() {
        return ElevatorSubsystemConstants.k_ElevatorSpeedDownSlow;
    }


 
    public boolean isOnSwitch() {
        return (m_limitSwitch.isPressed());
    }

    // public double getCurrent() {
    //     double ElevatorCurrent = elevator.getOutputCurrent();
    //     SmartDashboard.putNumber(m_elevatorSide + "ArmCurrent", ElevatorCurrent);
    //     return ElevatorCurrent;
    // }


}