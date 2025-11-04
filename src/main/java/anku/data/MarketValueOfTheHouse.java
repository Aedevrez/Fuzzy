package anku.data;

import anku.exceptions.NegativeValueException;
import lombok.*;

import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@EqualsAndHashCode
@ToString
public class MarketValueOfTheHouse {

    private enum FuzzySet {LOW, MEDIUM, HIGH, VERY_HIGH}

    private final double membershipOfLow;
    private final double membershipOfMedium;
    private final double membershipOfHigh;
    private final double membershipOfVeryHigh;

    public MarketValueOfTheHouse(double value) throws Exception {
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
                    case LOW -> (100 - value) / 10;
                    case MEDIUM -> min(((value - 50) / 50), ((250 - value) / 50));
                    case HIGH -> min(((value - 200) / 100), ((850 - value) / 200));
                    case VERY_HIGH -> ((value - 650) / 200);
                }));
    }
}
