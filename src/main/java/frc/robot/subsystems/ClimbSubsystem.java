package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLimitSwitch;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.ClimbSubsystemConstants;

// motor 51
public class ClimbSubsystem extends SubsystemBase {

    private SparkMax climb;
    private RelativeEncoder m_relativeEncoder;
    private SparkLimitSwitch m_limitSwitch;

    public ClimbSubsystem(int deviceId) {

        climb = new SparkMax(deviceId, MotorType.kBrushless);
        m_relativeEncoder = climb.getEncoder();
        m_limitSwitch = climb.getForwardLimitSwitch();

        // Maybe: If the second magnet is still attached and we power up on the switch,
        // move down until just off the switch before setting zero, similar to algae
        // pivot
        climbEncoderZero();
    }

    public double getClimbEncoder() {

        return m_relativeEncoder.getPosition();

    }

    public void climbEncoderZero() {

        m_relativeEncoder.setPosition(0);

    }

    public void climbUp() {

        if (isRaised()) {
            climb.set(0);
            m_relativeEncoder.setPosition(0);
        } else {
            climb.set((ClimbSubsystemConstants.k_speedUpFactor));
        }

    }

    public void climbDown() {

        if (isLowered()) {
            climb.set(0);
        } else {
            climb.set(ClimbSubsystemConstants.k_speedDownFactor);
        }

    }

    public void climbUpInit() {

        climb.set(ClimbSubsystemConstants.k_speedUpFactor);

    }

    public void climbStill() {

        climb.set(0);
        if (isRaised()) {
            m_relativeEncoder.setPosition(0);
        }

    }

    public boolean isLowered() {

        return m_limitSwitch.isPressed();

    }

    public boolean isRaised() {

        return (Math.abs(getClimbEncoder()) <= 2);

    }

}