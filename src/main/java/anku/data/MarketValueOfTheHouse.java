package anku.data;

import lombok.*;

import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
@EqualsAndHashCode
public class MarketValueOfTheHouse {
    private double membershipOfLow;
    private double membershipOfMedium;
    private double membershipOfHigh;
    private double membershipOfVeryHigh;

    public MarketValueOfTheHouse(double value) {

    }

    private Optional<Double> fuzzifyLow(double value) {
        if (value < 0) {
            return Optional.empty();
        }

        double fuzzyValue = max(0.0,
                            min(1.0,
                            (100 - value) / 10));

        return Optional.of(fuzzyValue);
    }

    private Optional<Double> fuzzifyMedium(double value) {
        if (value < 0) {
            return Optional.empty();
        }

        double fuzzyValue = max(0.0,
                            min(1.0,
                            min(((value - 50) / 50), ((250 - value) / 50))));

        return Optional.of(fuzzyValue);
    }

    private double fuzzifyHigh(double value) {
        return 0.;
    }

    private double fuzzifyVeryHigh(double value) {
        return 0.;
    }
}
