
package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralPivotSubsystem;

public class CoralResetCommand extends Command {

    private final CoralPivotSubsystem m_coralPosition;

    public CoralResetCommand(CoralPivotSubsystem coralPosition) {

        m_coralPosition = coralPosition;
        addRequirements(m_coralPosition);

    }

    @Override
    public void initialize() {

        m_coralPosition.pivotDown();
        
    }

    @Override
    public boolean isFinished() {

        return true;

    }

}
