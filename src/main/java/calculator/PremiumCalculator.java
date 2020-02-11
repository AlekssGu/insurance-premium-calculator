package calculator;


import policy.Policy;

import java.math.BigDecimal;

public interface PremiumCalculator {

    BigDecimal calculate(Policy policy);

}
