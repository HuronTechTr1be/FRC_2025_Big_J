package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLimitSwitch;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public abstract class SuperClassMotor extends SubsystemBase {

    private SparkMax m_motor;
    private RelativeEncoder m_relativeEncoder;
    protected SparkLimitSwitch m_limitSwitch;

    private String m_deviceName;
    private boolean m_findZero = false;
    private boolean m_findAtRest = false;
    private boolean m_goingUp;
    private boolean m_goingDown;


    public SuperClassMotor(int deviceId, String deviceName) {

        m_motor = new SparkMax(deviceId, MotorType.kBrushless);
        m_relativeEncoder = m_motor.getEncoder();
        m_limitSwitch = m_motor.getForwardLimitSwitch();

        m_deviceName = deviceName;

        // gotoZeroInit() should be called from the derived class constructor, and NOT from here
        // Only classes having a known zero position should use this feature

    }


    /// Override atZero() to specify a sequence for placing the motor at a known zero reference position
    /// Implementation should return true once that position is achieved
    protected abstract boolean atZero();

    /// Override atRest() to move the motor to a resting position after homing is complete
    /// Implementation should simply return true if the zero switch is also resting
    /// Otherwise, specify a sequence for placing the motor and return true once achieved
    protected abstract boolean atRest();

    
    /// gotoZeroInit begins the process of homing the motor to a known zero reference
    /// This should be called at the end of the derived class constructor, or anytime a re-zero is desired
    /// This calls the atZero method which should be implemented in the derived class
    public void gotoZeroInit() {

        m_findZero = !atZero();

    }

    /// gotoZeroFinish completes the process of homing the motor to a known zero reference
    /// It automatically begins to move to a specified resting position, which could also be zero
    private void gotoZeroFinish() {

        if (atZero()) {

            goStill();
            setPositionZero();

            m_findZero = false;
            gotoAtRestInit();

        }
    }

    /// gotoAtRestInit begins the process of setting the motor to a resting position
    /// This calls the atRest method which should be implemented in the derived class
    public void gotoAtRestInit() {

        m_findAtRest = !atRest();

    }

    /// gotoAtRestFinish completes the process of setting the motor to a resting position
    private void gotoAtRestFinish() {

        if (atRest()) {

            goStill();

            m_findAtRest = false;
        }
    }

    public double getPosition() {

        return m_relativeEncoder.getPosition();

    }

    public void setPositionZero() {

        m_relativeEncoder.setPosition(0);

    }

    protected void setPosition(double setTo) {

        m_relativeEncoder.setPosition(setTo);
        
    }


    protected abstract double speedUpFast();
    protected abstract double speedUpSlow();
    protected abstract double speedDownFast();
    protected abstract double speedDownSlow();


    public boolean goingUp() {

        return m_goingUp;

    }

    public boolean goingDown() {

        return m_goingDown;

    }

    public void goUp() {

        if (atLimitHigh()) {
            goStill();
        } else {
            m_motor.set(speedUpFast());
            m_goingUp = true;
            m_goingDown = false;
        }

    }

    public void goUpSlow() {

        if (atLimitHigh()) {
            goStill();
        } else {
            m_motor.set(speedUpSlow());
            m_goingUp = true;
            m_goingDown = false;
        }

    }

    // public void goUp(double speed) {

    //     speed = -(Math.abs(speed));

    //     if (isRaised()) {
    //         goStill();
    //     } else {
    //         m_motor.set(speed);
    //         m_goingUp = true;
    //         m_goingDown = false;
    //     }

    // }

    public void goDown() {

        m_goingDown = true;

        if (atLimitLow()) {
            goStill();
        } else {
            m_motor.set(speedDownFast());
            m_goingUp = false;
            m_goingDown = true;
        }

    }

    public void goDownSlow() {

        m_goingDown = true;

        if (atLimitLow()) {
            goStill();
        } else {
            m_motor.set(speedDownSlow());
            m_goingUp = false;
            m_goingDown = true;
        }

    }

    // public void goDown(double speed) {
        
    //     speed = Math.abs(speed);

    //     if (isLowered()) {
    //         goStill();
    //     } else {
    //         m_motor.set(speed);
    //         m_goingUp = false;
    //         m_goingDown = true;
    //     }

    // }

    public void goStill() {

        m_motor.set(0);
        m_goingUp = false;
        m_goingDown = false;

    }


    /// Override atLimitHigh() to specify logic indicating the travel upper limit for the motor
    /// This could be a limitSwitch signal or a set position
    /// Implementation should return true once that position is achieved
    public abstract boolean atLimitHigh();

    /// Override atLimitLow() to specify logic indicating the travel lower limit for the motor
    /// This could be a limitSwitch signal or a set position
    /// Implementation should return true once that position is achieved
    public abstract boolean atLimitLow();


    public void gotoLimitHigh() {

        m_goingUp = true;
        m_goingDown = false;

        if (atLimitHigh()) {
            goStill();
        } else { 
            goUp();
        }

    }

    public void gotoLimitLow() {

        m_goingUp = false;
        m_goingDown = true;

        if (atLimitLow()) {
            goStill();
        } else { 
            goDown();
        }

    }

    private void UpdateDashboard() {

        SmartDashboard.putNumber(m_deviceName + " Position", getPosition());
        SmartDashboard.putBoolean(m_deviceName + " Upper Limit", atLimitHigh());
        SmartDashboard.putBoolean(m_deviceName + " Lower Limit", atLimitLow());
        SmartDashboard.putBoolean(m_deviceName + " Find Zero", m_findZero);
        SmartDashboard.putBoolean(m_deviceName + " Find AtRest", m_findAtRest);
        SmartDashboard.putBoolean(m_deviceName + " Going Up", m_goingUp);
        SmartDashboard.putBoolean(m_deviceName + " Going Down", m_goingDown);
    }


    public void periodic() {

        if (m_findZero) {
            gotoZeroFinish();
        }

        if (m_findAtRest) {
            gotoAtRestFinish();
        }

        if (atLimitHigh() && (m_goingUp)) {
            goStill();
        }

        if (atLimitLow() && (m_goingDown)) {
            goStill();
        }

        UpdateDashboard();
    }

}