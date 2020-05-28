package calculator;

import policy.Object;
import policy.Policy;
import risk.Risk;
import risk.RiskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;

@Singleton
class DefaultPremiumCalculator implements PremiumCalculator {

    private RiskRepository riskRepository;
    private PremiumAggregator premiumAggregator;

    @Inject
    DefaultPremiumCalculator(RiskRepository riskRepository, PremiumAggregator premiumAggregator) {
        this.riskRepository = riskRepository;
        this.premiumAggregator = premiumAggregator;
    }

    public BigDecimal calculate(Policy policy) {
        return calculatePremium(policy.getObjects());
    }

    private BigDecimal calculatePremium(List<Object> objects) {
        BigDecimal premium = BigDecimal.ZERO;
        for (Entry<String, BigDecimal> totalRiskSum : premiumAggregator.aggregateByRisk(objects).entrySet()) {
            premium = premium.add(calculatePremiumPartFor(totalRiskSum));
        }
        return premium;
    }

    private BigDecimal calculatePremiumPartFor(Entry<String, BigDecimal> totalRiskSum) {
        String riskType = totalRiskSum.getKey();
        BigDecimal riskSumInsured = totalRiskSum.getValue();
        Risk risk = riskRepository.getConfiguredRiskBy(riskType);
        return risk.getPremium(riskSumInsured);
    }

}
