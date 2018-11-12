package com.oneandone.ejbcdiunit.weldstarter;

import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.spi.Metadata;

import javax.enterprise.inject.spi.DeploymentException;
import javax.enterprise.inject.spi.Extension;
import java.util.Collection;
import java.util.List;

public interface WeldSetup {
    public static class ServiceConfig<S extends Service> {
        public ServiceConfig(Class<S> serviceClass, S service) {
            this.serviceClass = serviceClass;
            this.service = service;
        }

        public Class<S> serviceClass;
        public S service;
    }

    Collection<String> getBeanClasses();

    List<Metadata<String>> getAlternativeClasses();

    List<Metadata<String>> getEnabledAlternativeStereotypes();

    void setDeploymentException(DeploymentException deploymentException);

    void setServices(List<ServiceConfig> services);

    Iterable<Metadata<Extension>> getExtensions();

    void registerServices(ServiceRegistry serviceRegistry);
}
