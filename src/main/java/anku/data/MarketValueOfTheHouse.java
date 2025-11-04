package anku.data;

import anku.exceptions.NegativeValueException;
import lombok.*;

import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MarketValueOfTheHouse {

    private enum FuzzySet {LOW, MEDIUM, HIGH, VERY_HIGH}

    private double membershipOfLow;
    private double membershipOfMedium;
    private double membershipOfHigh;
    private double membershipOfVeryHigh;

    public MarketValueOfTheHouse(double value) {
        this.membershipOfLow = fuzzify(value, FuzzySet.LOW).orElseThrow(NegativeValueException::new);
        this.membershipOfMedium = fuzzify(value, FuzzySet.MEDIUM).orElseThrow(NegativeValueException::new);
        this.membershipOfHigh = fuzzify(value, FuzzySet.HIGH).orElseThrow(NegativeValueException::new);
        this.membershipOfVeryHigh = fuzzify(value, FuzzySet.VERY_HIGH).orElseThrow(NegativeValueException::new);
    }

    private Optional<Double> fuzzify(double value, FuzzySet fuzzySet) {
        if (value < 0) {
            return Optional.empty();
        }

        double fuzzyValue = max(0.0,
                            min(1.0,
                            switch (fuzzySet) {
                                case LOW -> (100 - value) / 10;
                                case MEDIUM -> min(((value - 50) / 50), ((250 - value) / 50));
                                case HIGH -> min(((value - 200) / 100), ((850 - value) / 200));
                                case VERY_HIGH -> ((value - 650) / 200);
                            }));

        return Optional.of(fuzzyValue);
    }
}
