package anku.data.computedValues;

import anku.data.ApplicantSalary;
import anku.data.LocationOfTheHouse;
import anku.data.MarketValueOfTheHouse;
import anku.exceptions.NegativeValueException;
import lombok.EqualsAndHashCode;

import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;

@EqualsAndHashCode
public class HouseEvaluation {
    private enum FuzzySet {VERY_LOW, LOW, MEDIUM, HIGH, VERY_HIGH}

    private final double membershipOfVeryLow;
    private final double membershipOfLow;
    private final double membershipOfMedium;
    private final double membershipOfHigh;
    private final double membershipOfVeryHigh;

    public HouseEvaluation(MarketValueOfTheHouse marketValue, LocationOfTheHouse location) throws Exception {
        membershipOfVeryLow = calculate(marketValue, location, FuzzySet.VERY_LOW);
        membershipOfLow = calculate(marketValue, location, FuzzySet.LOW);
        membershipOfMedium = calculate(marketValue, location, FuzzySet.MEDIUM);
        membershipOfHigh = calculate(marketValue, location, FuzzySet.HIGH);
        membershipOfVeryHigh = calculate(marketValue, location, FuzzySet.VERY_HIGH);
    }

    private Double calculate(MarketValueOfTheHouse marketValue, LocationOfTheHouse location, FuzzySet fuzzySet) throws Exception{
        return switch (fuzzySet) {
            case VERY_LOW -> calculateVeryLow(marketValue, location);
            case LOW -> calculateLow(marketValue, location);
            case MEDIUM -> calculateMedium(marketValue, location);
            case HIGH -> calculateHigh(marketValue, location);
            case VERY_HIGH -> calculateVeryHigh(marketValue, location);
        };
    }

    private Double calculateVeryLow(MarketValueOfTheHouse marketValue, LocationOfTheHouse location) {
        return min(marketValue.getMembershipOfLow(), location.getMembershipOfBad());
    }

    private Double calculateLow(MarketValueOfTheHouse marketValue, LocationOfTheHouse location) {
        return max(marketValue.getMembershipOfLow(),
                max(location.getMembershipOfBad(),
                max(min(location.getMembershipOfBad(), marketValue.getMembershipOfMedium()),
                        min(location.getMembershipOfFair(), marketValue.getMembershipOfLow()))));
    }

    private Double calculateMedium(MarketValueOfTheHouse marketValue, LocationOfTheHouse location) {
        return max(min(location.getMembershipOfBad(), marketValue.getMembershipOfHigh()),
                max(min(location.getMembershipOfFair(), marketValue.getMembershipOfMedium()),
                    min(location.getMembershipOfExcellent(), marketValue.getMembershipOfLow())));
    }

    private Double calculateHigh(MarketValueOfTheHouse marketValue, LocationOfTheHouse location) {
        return max(min(location.getMembershipOfBad(), marketValue.getMembershipOfVeryHigh()),
                max(min(location.getMembershipOfFair(), marketValue.getMembershipOfHigh()),
                    min(location.getMembershipOfExcellent(), marketValue.getMembershipOfMedium())));
    }

    private Double calculateVeryHigh(MarketValueOfTheHouse marketValue, LocationOfTheHouse location) {
        return max(min(location.getMembershipOfFair(), marketValue.getMembershipOfVeryHigh()),
                max(min(location.getMembershipOfExcellent(), marketValue.getMembershipOfHigh()),
                        min(location.getMembershipOfExcellent(), marketValue.getMembershipOfVeryHigh())));
    }

    @Override
    public String toString() {
        return membershipOfLow + ", " + membershipOfMedium + ", " + membershipOfHigh + ", " + membershipOfVeryHigh;
    }
}
