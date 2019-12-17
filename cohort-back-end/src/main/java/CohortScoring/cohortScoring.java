package CohortScoring;

import CohortDataClasses.*;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;
import CohortsSolverData.CohortSolution;
public class cohortScoring implements EasyScoreCalculator {


    public HardSoftScore calculateScore(Object o) {
        return HardSoftScore.of(
                HardScoringFunctions.scoreSchedule((CohortSolution)o),
                SoftScoringFunctions.scoreSchedule((CohortSolution)o)
        );
    }
}
