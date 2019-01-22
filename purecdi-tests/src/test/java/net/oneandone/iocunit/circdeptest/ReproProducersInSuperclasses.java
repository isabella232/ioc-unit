package net.oneandone.iocunit.circdeptest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.spi.Extension;

import org.jboss.weld.bootstrap.api.CDI11Bootstrap;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import org.jboss.weld.bootstrap.spi.BeansXml;
import org.jboss.weld.bootstrap.spi.CDI11Deployment;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.bootstrap.spi.Metadata;
import org.jboss.weld.bootstrap.spi.Scanning;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author aschoerk
 */
@RunWith(JUnit4.class)
public class ReproProducersInSuperclasses {
    @Test
    public void test() {
        Weld weld = new Weld()
                .disableDiscovery()
                .addBeanClass(ProducesAlternative.class)
                .addBeanClass(SubContainer.class)
                .addBeanClass(Container.class)
                .addBeanClass(Main.class)
                .addBeanClass(BeanAlt.class)
                .addAlternative(BeanAlt.class)
                .addAlternativeStereotype(ProducesAlternative.class);
        try (WeldContainer container = weld.initialize()) {

        }
    }


    @Test
    public void testOverriding() {
        Weld weld = new Weld() {
            protected Deployment createDeployment(final ResourceLoader resourceLoader, final CDI11Bootstrap bootstrap) {

                final SimpleServiceRegistry services = new SimpleServiceRegistry();
                final BeanDeploymentArchive oneDeploymentArchive = createOneDeploymentArchive(services,
                        SubContainer.class,
                        Main.class);

                oneDeploymentArchive.getServices().add(ResourceLoader.class, resourceLoader);

                Deployment res = new CDI11Deployment() {
                    public BeanDeploymentArchive getBeanDeploymentArchive(Class<?> beanClass) {
                        return oneDeploymentArchive;
                    }

                    public Collection<BeanDeploymentArchive> getBeanDeploymentArchives() {
                        return Arrays.asList(oneDeploymentArchive);
                    }

                    public BeanDeploymentArchive loadBeanDeploymentArchive(Class<?> beanClass) {
                        return oneDeploymentArchive;
                    }

                    public ServiceRegistry getServices() {
                        return services;
                    }

                    public Iterable<Metadata<Extension>> getExtensions() {
                        return Collections.EMPTY_LIST;
                    }
                };

                return res;
            }

        };
        try (WeldContainer container = weld.initialize()) {

        }

    }

    private BeanDeploymentArchive createOneDeploymentArchive(ServiceRegistry services, Class<?>... classes) {
        return new BeanDeploymentArchive() {
            private Set<String> beanClasses = null;

            @Override
            public Collection<BeanDeploymentArchive> getBeanDeploymentArchives() {
                return Collections.EMPTY_LIST;
            }

            @Override
            public Collection<String> getBeanClasses() {
                if(beanClasses == null) {
                    beanClasses = new HashSet<>();
                    for (Class<?> c : classes)
                        beanClasses.add(c.getName());
                }
                return beanClasses;
            }

            @Override
            public BeansXml getBeansXml() {
                return new BeansXml() {
                    @Override
                    public List<Metadata<String>> getEnabledAlternativeStereotypes() {
                        return Collections.EMPTY_LIST;
                    }

                    @Override
                    public List<Metadata<String>> getEnabledAlternativeClasses() {
                        return Collections.EMPTY_LIST;
                    }

                    @Override
                    public List<Metadata<String>> getEnabledDecorators() {
                        return Collections.EMPTY_LIST;
                    }

                    @Override
                    public List<Metadata<String>> getEnabledInterceptors() {
                        return Collections.EMPTY_LIST;
                    }

                    @Override
                    public Scanning getScanning() {
                        return Scanning.EMPTY_SCANNING;
                    }

                    @Override
                    public URL getUrl() {
                        try {
                            return new URL("file:test");
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public BeanDiscoveryMode getBeanDiscoveryMode() {
                        return BeanDiscoveryMode.ALL;
                    }

                    @Override
                    public String getVersion() {
                        return "2.0";
                    }

                    @Override
                    public boolean isTrimmed() {
                        return false;
                    }
                };
            }

            @Override
            public Collection<EjbDescriptor<?>> getEjbs() {
                return null;
            }

            @Override
            public ServiceRegistry getServices() {
                return services;
            }

            @Override
            public String getId() {
                return this.getClass().getSimpleName();
            }
        };
    }
}
