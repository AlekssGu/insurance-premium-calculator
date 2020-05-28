package risk;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

import static risk.FireRisk.FIRE_RISK;
import static risk.WaterRisk.WATER_RISK;

public class RiskModule extends AbstractModule {

    @Override
    protected void configure() {
        bindClasses();
        bindRiskTypes();
    }

    private void bindClasses() {
        bind(RiskRepository.class);
        bind(RiskTypeNotImplemented.class);
    }

    private void bindRiskTypes() {
        MapBinder<String, Risk> mapBinder = MapBinder
                .newMapBinder(binder(), String.class, Risk.class);
        mapBinder.addBinding(FIRE_RISK).to(FireRisk.class);
        mapBinder.addBinding(WATER_RISK).to(WaterRisk.class);
    }
}
