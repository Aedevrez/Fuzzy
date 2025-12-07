package anku.data;

import anku.exceptions.NegativeValueException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@EqualsAndHashCode
public class InterestRate {
    private enum FuzzySet {LOW, MEDIUM, HIGH}

    private final double membershipOfLow;
    private final double membershipOfMedium;
    private final double membershipOfHigh;

    public InterestRate(double value) throws Exception {
        this.membershipOfLow = fuzzify(value, FuzzySet.LOW);
        this.membershipOfMedium = fuzzify(value, FuzzySet.MEDIUM);
        this.membershipOfHigh = fuzzify(value, FuzzySet.HIGH);
    }

    private Double fuzzify(double value, FuzzySet fuzzySet) throws Exception{
        if (value < 0) {
            throw new NegativeValueException(value + " is negative!");
        }

        return max(0.0,
                min(1.0,
                        switch (fuzzySet) {
                            case LOW -> (5 - value) / 3;
                            case MEDIUM -> min(((value - 2) / 2), ((8 - value) / 2));
                            case HIGH -> ((value - 6) / 2.5);
                        }));
    }

    @Override
    public String toString() {
        return membershipOfLow + ", " + membershipOfMedium + ", " + membershipOfHigh;
    }
}
