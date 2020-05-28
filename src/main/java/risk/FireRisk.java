package risk;

import java.math.BigDecimal;

public class FireRisk implements Risk {

    static final String FIRE_RISK = "FIRE";

    private static final int THRESHOLD = 100;
    private static final BigDecimal LOWER_THRESHOLD_RATE = BigDecimal.valueOf(0.013);
    private static final BigDecimal OVER_THRESHOLD_RATE = BigDecimal.valueOf(0.023);

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
        return sumInsured.compareTo(BigDecimal.valueOf(THRESHOLD)) > 0;
    }
}
