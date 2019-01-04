package com.oneandone.cdi.testanalyzer;

import com.oneandone.cdi.weldstarter.WeldSetupClass;
import com.oneandone.cdi.weldstarter.spi.TestExtensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.spi.Extension;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SetupCreator {
    static Logger logger = LoggerFactory.getLogger(SetupCreator.class);
    private final Configuration configuration;

    public SetupCreator(Configuration configuration) {
        this.configuration = configuration;
    }

    private Collection<Extension> findExtensions() {
        List<Extension> result = new ArrayList<>();
        for (TestExtensionService testExtensionService : configuration.testerExtensionsConfigsFinder.testExtensionServices) {
            result.addAll(testExtensionService.getExtensions());
        }
        return result;
    }

    private void handleWeldExtensions(final Method method, final WeldSetupClass weldSetup) {
        try {
            for (Class<? extends Extension> extensionClass : configuration.getElseClasses().extensionClasses) {
                if (extensionClass.getName().contains(".ProducerConfigExtension")) {
                    Constructor<? extends Extension> constructor =
                            extensionClass.getConstructor(Method.class);
                    Extension producerConfig = constructor.newInstance(method);
                    weldSetup.addExtensionObject(producerConfig);
                } else {
                    weldSetup.addExtensionObject(extensionClass.newInstance());
                }
            }
            for (Extension e : configuration.getElseClasses().extensionObjects) {
                Class<? extends Extension> extensionClass = e.getClass();
                final Constructor<?>[] declaredConstructors = extensionClass.getDeclaredConstructors();
                if (declaredConstructors.length == 1 && declaredConstructors[0].getParameters().length == 0) {
                    weldSetup.addExtensionObject(extensionClass.newInstance());
                } else {
                    weldSetup.addExtensionObject(e);
                }
            }
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }
    }

    public WeldSetupClass buildWeldSetup(Method method) {
        WeldSetupClass weldSetup = new WeldSetupClass();
        weldSetup.setBeanClasses(configuration.getObligatory());
        weldSetup.setAlternativeClasses(configuration.getEnabledAlternatives());
        weldSetup.setEnabledAlternativeStereotypes(configuration.getElseClasses().foundAlternativeStereotypes);
        if (logger.isTraceEnabled()) {
            for (Class<?> i : configuration.getElseClasses().decorators) {
                logger.trace("buildWeldSetup Decorator:   {}", i);
            }
            for (Class<?> i : configuration.getElseClasses().interceptors) {
                logger.trace("buildWeldSetup Interceptor: {}", i);
            }
        }
        weldSetup.setEnabledDecorators(configuration.getElseClasses().decorators);
        weldSetup.setEnabledInterceptors(configuration.getElseClasses().interceptors);
        handleWeldExtensions(method, weldSetup);
        for (Extension e : findExtensions()) {
            weldSetup.addExtensionObject(e);
        }
        return weldSetup;
    }
}