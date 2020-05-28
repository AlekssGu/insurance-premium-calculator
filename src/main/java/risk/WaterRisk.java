package risk;

import java.math.BigDecimal;

public class WaterRisk implements Risk {

    static final String WATER_RISK = "WATER";

    private static final int THRESHOLD = 10;
    private static final BigDecimal LOWER_THRESHOLD_RATE = BigDecimal.valueOf(0.1);
    private static final BigDecimal OVER_THRESHOLD_RATE = BigDecimal.valueOf(0.05);

    @Override
    public BigDecimal getPremium(BigDecimal sumInsured) {
        BigDecimal premium;
        if (sumInsuredIsOverThreshold(sumInsured)) {
            premium = sumInsured.multiply(OVER_THRESHOLD_RATE);
        } else {
            premium = sumInsured.multiply(LOWER_THRESHOLD_RATE);
        }
        return premium;
    }

    private boolean sumInsuredIsOverThreshold(BigDecimal sumInsured) {
        return sumInsured.compareTo(BigDecimal.valueOf(THRESHOLD)) >= 0;
    }
}
