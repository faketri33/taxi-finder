package org.faketri.usecase.price;

public class SubscriberPricePolicy extends DefaultPricePolicy {
    @Override
    double calculate(double distance) {
        return super.calculate(distance) * 0.5;
    }
}
