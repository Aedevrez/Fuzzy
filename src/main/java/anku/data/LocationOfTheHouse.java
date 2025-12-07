package anku.data;

import anku.exceptions.NegativeValueException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@EqualsAndHashCode
public class LocationOfTheHouse {
    private enum FuzzySet {BAD, FAIR, EXCELLENT}

    private final double membershipOfBad;
    private final double membershipOfFair;
    private final double membershipOfExcellent;

    public LocationOfTheHouse(double value) throws Exception {
        this.membershipOfBad = fuzzify(value, FuzzySet.BAD);
        this.membershipOfFair = fuzzify(value, FuzzySet.FAIR);
        this.membershipOfExcellent = fuzzify(value, FuzzySet.EXCELLENT);
    }

    private Double fuzzify(double value, FuzzySet fuzzySet) throws Exception{
        if (value < 0) {
            throw new NegativeValueException(value + " is negative!");
        }

        return max(0.0,
                min(1.0,
                        switch (fuzzySet) {
                            case BAD -> (4 - value) / 2.5;
                            case FAIR -> min(((value - 2.5) / 2.5), ((8.5 - value) / 2.5));
                            case EXCELLENT -> ((value - 6) / 2.5);
                        }));
    }

    @Override
    public String toString() {
        return membershipOfBad + ", " + membershipOfFair + ", " + membershipOfExcellent;
    }
}
