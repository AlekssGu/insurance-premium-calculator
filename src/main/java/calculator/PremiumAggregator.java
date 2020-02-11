package calculator;

import policy.Object;
import policy.SubObject;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Singleton
class PremiumAggregator {

    Map<String, BigDecimal> aggregateByRisk(List<Object> objects) {
        Map<String, BigDecimal> aggregatedSumsByRiskType = new HashMap<>();
        objects.forEach(object -> aggregatedSumsByRiskType.putAll(calculateSumInsuredByRisk(object)));
        return aggregatedSumsByRiskType;
    }

    private Map<String, BigDecimal> calculateSumInsuredByRisk(Object object) {
        return object.getSubObjects().stream().collect(groupByRiskTypeAndCalculateSumInsured());
    }

    private Collector<SubObject, ?, Map<String, BigDecimal>> groupByRiskTypeAndCalculateSumInsured() {
        return Collectors.toMap(SubObject::getRiskType, SubObject::getSumInsured, BigDecimal::add);
    }

}
