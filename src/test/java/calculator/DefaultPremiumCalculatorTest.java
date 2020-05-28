package calculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import policy.Object;
import policy.Policy;
import risk.FireRisk;
import risk.RiskRepository;
import risk.WaterRisk;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPremiumCalculatorTest {

    private static final String FIRE_RISK = "FIRE";
    private static final String WATER_RISK = "WATER";

    @Mock
    private RiskRepository riskRepository;
    @Mock
    private Policy policy;
    @Mock
    private PremiumAggregator premiumAggregator;

    @InjectMocks
    private DefaultPremiumCalculator calculator;

    private List<Object> objects = Collections.emptyList();

    @Before
    public void setup() {
        given(riskRepository.getConfiguredRiskBy(FIRE_RISK)).willReturn(new FireRisk());
        given(riskRepository.getConfiguredRiskBy(WATER_RISK)).willReturn(new WaterRisk());
    }

    @Test
    public void premiumIsZeroWhenNoObjectsArePresent() {
        given(premiumAggregator.aggregateByRisk(objects)).willReturn(new HashMap<>());
        BigDecimal premium = calculator.calculate(policy);
        assertThat(premium).isZero();
    }

    @Test
    public void premiumIsZeroIfSumInsuredIsZero() {
        Map<String, BigDecimal> riskSums = new HashMap<>();
        riskSums.put("FIRE", BigDecimal.ZERO);
        given(premiumAggregator.aggregateByRisk(objects)).willReturn(riskSums);

        BigDecimal premium = calculator.calculate(policy);

        assertThat(premium).isZero();
    }

    @Test
    public void calculatesPremiumWhenValuesAreLowerThanThreshold() {
        Map<String, BigDecimal> riskSums = new HashMap<>();
        riskSums.put(FIRE_RISK, BigDecimal.valueOf(100));
        riskSums.put(WATER_RISK, BigDecimal.valueOf(8));
        given(premiumAggregator.aggregateByRisk(objects)).willReturn(riskSums);

        BigDecimal premium = calculator.calculate(policy);
        assertThat(premium.stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(2.10));
    }

    @Test
    public void calculatesPremiumWhenValuesAreOverThanThreshold() {
        Map<String, BigDecimal> riskSums = new HashMap<>();
        riskSums.put(FIRE_RISK, BigDecimal.valueOf(500));
        riskSums.put(WATER_RISK, BigDecimal.valueOf(100));
        given(premiumAggregator.aggregateByRisk(objects)).willReturn(riskSums);

        BigDecimal premium = calculator.calculate(policy);

        assertThat(premium.stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(16.50));
    }

    @Test
    public void calculatesPremiumWhenMultipleObjectsArePresent() {
        Map<String, BigDecimal> riskSums = new HashMap<>();
        riskSums.put(FIRE_RISK, BigDecimal.valueOf(77));
        riskSums.put(WATER_RISK, BigDecimal.valueOf(109));
        given(premiumAggregator.aggregateByRisk(objects)).willReturn(riskSums);

        BigDecimal premium = calculator.calculate(policy);
        assertThat(premium.stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(6.451));
    }

    @Test(expected = RuntimeException.class)
    public void premiumIsZeroIfNotConfiguredRiskIsPassed() {
        Map<String, BigDecimal> riskSums = new HashMap<>();
        riskSums.put("NOT_IMPLEMENTED", BigDecimal.ZERO);
        given(premiumAggregator.aggregateByRisk(objects)).willReturn(riskSums);

        calculator.calculate(policy);
    }
}