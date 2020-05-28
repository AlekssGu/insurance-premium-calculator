package calculator;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import policy.Object;
import policy.SubObject;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PremiumAggregatorTest {

    private static final String FIRE_RISK = "FIRE";
    private static final String WATER_RISK = "WATER";

    @InjectMocks
    private PremiumAggregator aggregator;

    private List<Object> objects;

    @Test
    public void aggregatesSumsOfOneRiskType() {
        Object policyObject = buildPolicyObject(buildTwoFireSubObjects());
        Map<String, BigDecimal> sumsByRisk = aggregator.aggregateByRisk(Collections.singletonList(policyObject));

        assertThat(sumsByRisk).hasSize(1);
        assertThat(sumsByRisk).containsKey(FIRE_RISK);
        assertThat(sumsByRisk.get(FIRE_RISK)).isEqualTo(BigDecimal.valueOf(108));
    }

    @Test
    public void aggregatesSumsOfMultipleRiskTypes() {
        Object policyObject = buildPolicyObject(buildSubObjectsWithMultipleRiskTypes());
        Map<String, BigDecimal> sumsByRisk = aggregator.aggregateByRisk(Collections.singletonList(policyObject));

        assertThat(sumsByRisk).hasSize(2);
        assertThat(sumsByRisk).containsKey(FIRE_RISK);
        assertThat(sumsByRisk).containsKey(WATER_RISK);
        assertThat(sumsByRisk.get(FIRE_RISK)).isEqualTo(BigDecimal.valueOf(242.75));
        assertThat(sumsByRisk.get(WATER_RISK)).isEqualTo(BigDecimal.valueOf(18.50));
    }

    private Object buildPolicyObject(List<SubObject> subObjects) {
        return Object.builder()
                .name("Any name")
                .subObjects(subObjects)
                .build();
    }

    private List<SubObject> buildTwoFireSubObjects() {
        return Arrays.asList(buildSubObject(FIRE_RISK, BigDecimal.valueOf(100)), buildSubObject(FIRE_RISK, BigDecimal.valueOf(8)));
    }

    private List<SubObject> buildSubObjectsWithMultipleRiskTypes() {
        return Arrays.asList(buildSubObject(FIRE_RISK, BigDecimal.valueOf(100)),
                buildSubObject(FIRE_RISK, BigDecimal.valueOf(133.25)),
                buildSubObject(FIRE_RISK, BigDecimal.valueOf(0.25)),
                buildSubObject(FIRE_RISK, BigDecimal.valueOf(9.25)),
                buildSubObject(WATER_RISK, BigDecimal.valueOf(8)),
                buildSubObject(WATER_RISK, BigDecimal.valueOf(10.50)));
    }

    private SubObject buildSubObject(String riskType, BigDecimal sumInsured) {
        return SubObject.builder()
                .name("Any name")
                .riskType(riskType)
                .sumInsured(sumInsured)
                .build();
    }
}