package anku.data.computedValues;

import anku.data.ApplicantSalary;
import anku.data.LocationOfTheHouse;
import anku.data.inputValues.MarketValueOfTheHouse;
import anku.exceptions.NegativeValueException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;

@EqualsAndHashCode
@Getter
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

    private Double inferMamdani() {
        double top = 0;
        double bottom = 0;
        for (double i = 0; i <= 10; i+=0.1) {
            double maxValueAtThatPoint = max(0.0,
                                        min(1.0,
                                        max(min((3 - i) / 3, membershipOfVeryLow), //VERY LOW
                                        max(min(min((i / 3), ((6 - i) / 3)), membershipOfLow), //LOW
                                        max(min(min(((i - 2) / 3), ((8 - i) / 3)), membershipOfMedium),//MEDIUM
                                        max(min(min(((i - 4) / 3), ((10 - i) / 3)), membershipOfHigh),//HIGH
                                        min(((i - 7) / 3), membershipOfVeryHigh)//VERY HIGH
                                        ))))));

            top += maxValueAtThatPoint * i;
            bottom += maxValueAtThatPoint;
        }
        return top / bottom;
    }

    @Override
    public String toString() {
        return "House Evaluation = " + inferMamdani();
    }
}
