package anku.data;

import anku.exceptions.NegativeValueException;
import lombok.*;

import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@EqualsAndHashCode
public class MarketValueOfTheHouse {

    private enum FuzzySet {LOW, MEDIUM, HIGH, VERY_HIGH}

    private final double membershipOfLow;
    private final double membershipOfMedium;
    private final double membershipOfHigh;
    private final double membershipOfVeryHigh;

    public MarketValueOfTheHouse(double value) throws Exception {
        this.membershipOfLow = singleFuzzify(value, FuzzySet.LOW);
        this.membershipOfMedium = singleFuzzify(value, FuzzySet.MEDIUM);
        this.membershipOfHigh = singleFuzzify(value, FuzzySet.HIGH);
        this.membershipOfVeryHigh = singleFuzzify(value, FuzzySet.VERY_HIGH);
    }

    private Double singleFuzzify(double value, FuzzySet fuzzySet) throws Exception{
        if (value < 0) {
            throw new NegativeValueException(value + " is negative!");
        }

        return max(0.0,
                min(1.0,
                switch (fuzzySet) {
                    case LOW -> (100 - value) / 10;
                    case MEDIUM -> min(((value - 50) / 50), ((250 - value) / 50));
                    case HIGH -> min(((value - 200) / 100), ((850 - value) / 200));
                    case VERY_HIGH -> ((value - 650) / 200);
                }));
    }

    private Double totalFuzzify(double value) {
        return max(membershipOfLow, max(membershipOfMedium, max(membershipOfHigh, membershipOfVeryHigh)));
    }

    public Double defuzzify() {
        double sumOfTop = 0;
        double sumOfBottom = 0;

        for (int i = 0; i < 200; i++) {
            sumOfTop += (i * totalFuzzify(i));
            sumOfBottom += totalFuzzify(i);
        }

        return sumOfTop / sumOfBottom;
    }

    @Override
    public String toString() {
        return defuzzify().toString();
    }
}
