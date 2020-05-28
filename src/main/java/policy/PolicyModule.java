package policy;

import com.google.inject.AbstractModule;

public class PolicyModule extends AbstractModule {

    @Override
    protected void configure() {
        bindClasses();
    }

    private void bindClasses() {
        bind(Policy.class);
        bind(Object.class);
        bind(SubObject.class);
    }
}
