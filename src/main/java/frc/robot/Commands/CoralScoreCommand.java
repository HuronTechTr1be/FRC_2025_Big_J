package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralPivotSubsystem;

public class CoralScoreCommand extends Command {

    private final CoralPivotSubsystem m_coralPosition;

    // Make code raise to scoring angle

    public CoralScoreCommand(CoralPivotSubsystem coralPosition) {

        m_coralPosition = coralPosition;
        addRequirements(m_coralPosition);

    }

    // NOT USED RIGHT NOW

    @Override
    public void initialize() {

        // m_pivot.pivotUp();
        // while (m_pivot.isRaised() == false) {

        // }

        // m_pivot.pivotEncoderZero();
        // m_pivot.pivotStill();

    }

    @Override
    public boolean isFinished() {

        return true;

    }

}