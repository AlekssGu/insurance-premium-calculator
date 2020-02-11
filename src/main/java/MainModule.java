import calculator.CalculatorModule;
import com.google.inject.AbstractModule;
import policy.PolicyModule;
import risk.RiskModule;

public class MainModule extends AbstractModule {

    @Override
    protected void configure() {
        installSubmodules();
    }

    private void installSubmodules() {
        install(new RiskModule());
        install(new PolicyModule());
        install(new CalculatorModule());
    }
}
