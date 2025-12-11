package anku.data;

import anku.exceptions.NegativeValueException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@EqualsAndHashCode
public class ApplicantSalary {
    private enum FuzzySet {LOW, MEDIUM, HIGH, VERY_HIGH}

    private final double membershipOfLow;
    private final double membershipOfMedium;
    private final double membershipOfHigh;
    private final double membershipOfVeryHigh;

    public ApplicantSalary(double value) throws Exception {
        this.membershipOfLow = fuzzify(value, FuzzySet.LOW);
        this.membershipOfMedium = fuzzify(value, FuzzySet.MEDIUM);
        this.membershipOfHigh = fuzzify(value, FuzzySet.HIGH);
        this.membershipOfVeryHigh = fuzzify(value, FuzzySet.VERY_HIGH);
    }

    private Double fuzzify(double value, FuzzySet fuzzySet) throws Exception{
        if (value < 0) {
            throw new NegativeValueException(value + " is negative!");
        }

        return max(0.0,
                min(1.0,
                        switch (fuzzySet) {
                            case LOW -> (25 - value) / 13;
                            case MEDIUM -> min(((value - 15) / 20), ((55 - value) / 20));
                            case HIGH -> min(((value - 40) / 20), ((80 - value) / 20));
                            case VERY_HIGH -> ((value - 60) / 20);
                        }));
    }

    @Override
    public String toString() {
        return membershipOfLow + ", " + membershipOfMedium + ", " + membershipOfHigh + ", " + membershipOfVeryHigh;
    }
}
