package anku.data.computedValues;

import anku.data.ApplicantAssets;
import anku.data.ApplicantSalary;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@EqualsAndHashCode
@Getter
public class ApplicantEvaluation {
    private enum FuzzySet {LOW, MEDIUM, HIGH}

    private final double membershipOfLow;
    private final double membershipOfMedium;
    private final double membershipOfHigh;

    public ApplicantEvaluation(ApplicantAssets assets, ApplicantSalary salary) throws Exception {
        membershipOfLow = calculate(assets, salary, FuzzySet.LOW);
        membershipOfMedium = calculate(assets, salary, FuzzySet.MEDIUM);
        membershipOfHigh = calculate(assets, salary, FuzzySet.HIGH);
    }

    private Double calculate(ApplicantAssets assets, ApplicantSalary salary, FuzzySet fuzzySet) throws Exception{
        return switch (fuzzySet) {
            case LOW -> calculateLow(assets, salary);
            case MEDIUM -> calculateMedium(assets, salary);
            case HIGH -> calculateHigh(assets, salary);
        };
    }

    private Double calculateLow(ApplicantAssets assets, ApplicantSalary salary) {
        return max(min(assets.getMembershipOfLow(), salary.getMembershipOfLow()),
                max(min(assets.getMembershipOfLow(), salary.getMembershipOfMedium()),
                min(assets.getMembershipOfMedium(), salary.getMembershipOfLow())));
    }

    private Double calculateMedium(ApplicantAssets assets, ApplicantSalary salary) {
        return max(min(assets.getMembershipOfLow(), salary.getMembershipOfHigh()),
                max(min(assets.getMembershipOfMedium(), salary.getMembershipOfMedium()),
                max(min(assets.getMembershipOfHigh(), salary.getMembershipOfLow()),
                min(assets.getMembershipOfHigh(), salary.getMembershipOfMedium()))));
    }

    private Double calculateHigh(ApplicantAssets assets, ApplicantSalary salary) {
        return max(min(assets.getMembershipOfLow(), salary.getMembershipOfVeryHigh()),
                max(min(assets.getMembershipOfMedium(), salary.getMembershipOfHigh()),
                max(min(assets.getMembershipOfMedium(), salary.getMembershipOfVeryHigh()),
                max(min(assets.getMembershipOfHigh(), salary.getMembershipOfHigh()),
                min(assets.getMembershipOfHigh(), salary.getMembershipOfVeryHigh())))));
    }

    private Double inferMamdani() {
        double top = 0;
        double bottom = 0;
        for (double i = 0; i <= 10; i+=0.1) {
            double maxValueAtThatPoint = max(
                    min(
                            max(0, min((4 - i) / 2, 1)),
                            membershipOfLow
                    ),
                    max(
                            min(
                                    max(
                                            0,
                                            min(
                                                    min((i - 2) / 3, (8 - i) / 3),
                                                    1
                                            )
                                    ),
                                    membershipOfMedium
                            ),
                            min(
                                    max(0, min((i - 6) / 2, 1)),
                                    membershipOfHigh
                            )
                    )
            );

            top += maxValueAtThatPoint * i;
            bottom += maxValueAtThatPoint;
        }
        return top / bottom;
    }

    @Override
    public String toString() {
        return "Applicant Evaluation = " + inferMamdani();
    }
}