package org.faketri.usecase.policy;

import org.springframework.stereotype.Component;

@Component
public class FindDriverPolicy {

    private static final int DEFAULT_DISPATCH_DISTANCE = 3000;
    private static final int DEFAULT_DISPATCH_DRIVER_LIMIT = 20;
    private static final String DEFAULT_DRIVER_STATUS = "free";
    private static final String IF_LAST_ROUND_DRIVER_STATUS = "busy";

    public int getDispatchDistance(int round){
        return DEFAULT_DISPATCH_DISTANCE * round;
    }

    public int getDispatchDriverLimit(){
        return DEFAULT_DISPATCH_DRIVER_LIMIT;
    }

    public String getDriverStatus(int round){
        return DispatchStatePolicy.getMaxRound() == round ?
                IF_LAST_ROUND_DRIVER_STATUS : DEFAULT_DRIVER_STATUS;
    }
}
