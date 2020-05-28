package risk;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.function.Predicate;

@Singleton
public class RiskRepository {

    private Map<String, Risk> configuredRisks;

    @Inject
    RiskRepository(Map<String, Risk> configuredRisks) {
        this.configuredRisks = configuredRisks;
    }

    public Risk getConfiguredRiskBy(String type) {
        return configuredRisks.entrySet().stream()
                .filter(existsGiven(type))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(RiskTypeNotImplemented::new);
    }

    private Predicate<Map.Entry<String, Risk>> existsGiven(String type) {
        return configurationEntry -> configurationEntry.getKey().equals(type);
    }

}
