package org.faketri.usecase.price;

public abstract class DefaultPricePolicy {

    final double MIN_PRICE_RIDE = 100;
    final double DEFAULT_MULTIPLIER = 1.7;
    final double DEFAULT_KILOMETER_PRICE = 25;

    final double DEFAULT_WEATHER_MULTIPLIER = 1;
    final double RAIN_WEATHER_MULTIPLIER = 1.25;
    final double SNOW_WEATHER_MULTIPLIER = 1.5;

    double calculate(double distance) {
        return distance * DEFAULT_KILOMETER_PRICE * DEFAULT_MULTIPLIER * DEFAULT_WEATHER_MULTIPLIER + MIN_PRICE_RIDE;
    }
}
