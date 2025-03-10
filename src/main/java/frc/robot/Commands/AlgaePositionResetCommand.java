package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaePivotSubsystem;

public class AlgaePositionResetCommand extends Command {

    private final AlgaePivotSubsystem m_algaePosition;

    public AlgaePositionResetCommand(AlgaePivotSubsystem algaePosition) {

        m_algaePosition = algaePosition;
        addRequirements(m_algaePosition);

    }

    @Override
    public void initialize() {

        m_algaePosition.gotoZeroInit();

    }

    @Override
    public boolean isFinished() {

        return true;

    }
}