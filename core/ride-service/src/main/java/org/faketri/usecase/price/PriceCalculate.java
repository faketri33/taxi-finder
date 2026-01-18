package org.faketri.usecase.price;

public interface PriceCalculate {

    static double calculate(double distance, DefaultPricePolicy policy) {
        return policy.calculate(distance);
    }
}
