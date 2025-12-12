package org.faketri.usecase.policy;

import org.springframework.stereotype.Component;


@Component
public class DispatchStatePolicy {

    private static final int MAX_ROUND = 4;

    public static int getMaxRound(){
        return MAX_ROUND;
    }

    public boolean roundPolicy(int round) {
        return round > 0 && round <= MAX_ROUND;
    }
}
