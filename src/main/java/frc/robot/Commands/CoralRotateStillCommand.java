package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralRotateSubsystem;

public class CoralRotateStillCommand extends Command {

    private final CoralRotateSubsystem m_coralRotate;

    // Make code raise to scoring angle

    public CoralRotateStillCommand(CoralRotateSubsystem coralRotate) {

        m_coralRotate = coralRotate;
        addRequirements(m_coralRotate);

    }

    // NOT USED RIGHT NOW

    @Override
    public void initialize() {

        m_coralRotate.goStill();
    }

    @Override
    public boolean isFinished() {

        return true;

    }

}