package calculator;

import com.google.inject.AbstractModule;

public class CalculatorModule extends AbstractModule {

    @Override
    protected void configure() {
        bindClasses();
    }

    private void bindClasses() {
        bind(PremiumAggregator.class);
        bind(PremiumCalculator.class).to(DefaultPremiumCalculator.class);
    }
}
