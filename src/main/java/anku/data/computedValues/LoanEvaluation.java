package anku.data.computedValues;

import anku.data.ApplicantSalary;
import anku.data.InterestRate;
import lombok.EqualsAndHashCode;

import static java.lang.Math.max;
import static java.lang.Math.min;

@EqualsAndHashCode
public class LoanEvaluation {
    private enum FuzzySet {VERY_LOW, LOW, MEDIUM, HIGH, VERY_HIGH}

    private final double membershipOfVeryLow;
    private final double membershipOfLow;
    private final double membershipOfMedium;
    private final double membershipOfHigh;
    private final double membershipOfVeryHigh;

    public LoanEvaluation(ApplicantSalary salary, InterestRate interestRate, ApplicantEvaluation applicantEvaluation, HouseEvaluation houseEvaluation) throws Exception {
        membershipOfVeryLow = calculate(salary, interestRate, applicantEvaluation, houseEvaluation, FuzzySet.VERY_LOW);
        membershipOfLow = calculate(salary, interestRate, applicantEvaluation, houseEvaluation, FuzzySet.LOW);
        membershipOfMedium = calculate(salary, interestRate, applicantEvaluation, houseEvaluation, FuzzySet.MEDIUM);
        membershipOfHigh = calculate(salary, interestRate, applicantEvaluation, houseEvaluation, FuzzySet.HIGH);
        membershipOfVeryHigh = calculate(salary, interestRate, applicantEvaluation, houseEvaluation, FuzzySet.VERY_HIGH);
    }

    private Double calculate(ApplicantSalary salary, InterestRate interestRate, ApplicantEvaluation applicantEvaluation, HouseEvaluation houseEvaluation, FuzzySet fuzzySet) throws Exception{
        return switch (fuzzySet) {
            case VERY_LOW -> calculateVeryLow(salary, interestRate, applicantEvaluation, houseEvaluation);
            case LOW -> calculateLow(salary, interestRate, applicantEvaluation, houseEvaluation);
            case MEDIUM -> calculateMedium(salary, interestRate, applicantEvaluation, houseEvaluation);
            case HIGH -> calculateHigh(salary, interestRate, applicantEvaluation, houseEvaluation);
            case VERY_HIGH -> calculateVeryHigh(salary, interestRate, applicantEvaluation, houseEvaluation);
        };
    }

    private Double calculateVeryLow(ApplicantSalary salary, InterestRate interestRate, ApplicantEvaluation applicantEvaluation, HouseEvaluation houseEvaluation) {
        return max(min(salary.getMembershipOfLow(), interestRate.getMembershipOfMedium()),
                max(min(salary.getMembershipOfLow(), interestRate.getMembershipOfHigh()),
                max(applicantEvaluation.getMembershipOfLow(),
                houseEvaluation.getMembershipOfVeryLow())));
    }

    private Double calculateLow(ApplicantSalary salary, InterestRate interestRate, ApplicantEvaluation applicantEvaluation, HouseEvaluation houseEvaluation) {
        return max(min(salary.getMembershipOfMedium(), interestRate.getMembershipOfHigh()),
                max(min(applicantEvaluation.getMembershipOfMedium(), houseEvaluation.getMembershipOfVeryLow()),
                max(min(applicantEvaluation.getMembershipOfMedium(), houseEvaluation.getMembershipOfLow()),
                min(applicantEvaluation.getMembershipOfHigh(), houseEvaluation.getMembershipOfVeryLow()))));
    }

    private Double calculateMedium(ApplicantSalary salary, InterestRate interestRate, ApplicantEvaluation applicantEvaluation, HouseEvaluation houseEvaluation) {
        return max(min(applicantEvaluation.getMembershipOfMedium(), houseEvaluation.getMembershipOfMedium()),
                min(applicantEvaluation.getMembershipOfHigh(), houseEvaluation.getMembershipOfLow()));
    }

    private Double calculateHigh(ApplicantSalary salary, InterestRate interestRate, ApplicantEvaluation applicantEvaluation, HouseEvaluation houseEvaluation) {
        return max(min(applicantEvaluation.getMembershipOfMedium(), houseEvaluation.getMembershipOfHigh()),
                max(min(applicantEvaluation.getMembershipOfMedium(), houseEvaluation.getMembershipOfVeryHigh()),
                max(min(applicantEvaluation.getMembershipOfHigh(), houseEvaluation.getMembershipOfMedium()),
                min(applicantEvaluation.getMembershipOfHigh(), houseEvaluation.getMembershipOfHigh()))));
    }

    private Double calculateVeryHigh(ApplicantSalary salary, InterestRate interestRate, ApplicantEvaluation applicantEvaluation, HouseEvaluation houseEvaluation) {
        return min(applicantEvaluation.getMembershipOfHigh(),  houseEvaluation.getMembershipOfVeryHigh());
    }

    private Double inferMamdani() {
        double top = 0;
        double bottom = 0;
        for (double i = 0; i <= 500; i+=1) {
            double maxValueAtThatPoint = max(0.0,
                    min(1.0,
                            max(min((125 - i) / 125, membershipOfVeryLow), //VERY LOW
                                    max(min(min((i / 125), ((250 - i) / 125)), membershipOfLow), //LOW
                                            max(min(min(((i - 125) / 125), ((375 - i) / 125)), membershipOfMedium),//MEDIUM
                                                    max(min(min(((i - 250) / 125), ((500 - i) / 125)), membershipOfHigh),//HIGH
                                                            min(((i - 375) / 125), membershipOfVeryHigh)//VERY HIGH
                                                    ))))));

            top += maxValueAtThatPoint * i;
            bottom += maxValueAtThatPoint;
        }
        return top / bottom;
    }

    @Override
    public String toString() {
        return membershipOfVeryLow + ", " + membershipOfLow + ", " + membershipOfMedium + ", " + membershipOfHigh + ", " + membershipOfVeryHigh + " = " + inferMamdani();
    }
}