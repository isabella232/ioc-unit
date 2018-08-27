package cdiunit5;

import com.oneandone.ejbcdiunit5.JUnit5Extension;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.ProducerConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashSet;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ExtendWith(JUnit5Extension.class)
@AdditionalClasses(TestProducerConfig.Producers.class)
@TestProducerConfig.ProducerConfigClass(Object.class)
@TestProducerConfig.ProducerConfigNum(0)
public class TestProducerConfig {

    @Inject
    @Named("a")
    private String valueNamedA;

    @Inject
    @Named("object")
    private Object object;

    // example ProducerConfig annotations
    @Retention(RUNTIME)
    @Target({ METHOD, TYPE })
    @ProducerConfig
    public @interface ProducerConfigNum {
        int value();
    }

    @Retention(RUNTIME)
    @Target({ METHOD, TYPE })
    @ProducerConfig
    public @interface ProducerConfigClass {
        Class<?> value();
    }

    // Producers kept out of the injected test class to avoid Weld circularity warnings:
    static class Producers {
        @Produces
        @Named("a")
        private String getValueA(ProducerConfigNum config) {
            return "A" + config.value();
        }

        @Produces
        @Named("object")
        private Object getObject(ProducerConfigClass config) throws Exception {
            return config.value().newInstance();
        }
    }

    @Test
    @ProducerConfigNum(1)
    public void testA1() {
        Assertions.assertEquals("A1", valueNamedA);
    }

    @Test
    @ProducerConfigNum(2)
    public void testA2() {
        Assertions.assertEquals("A2", valueNamedA);
    }

    @Test
    @ProducerConfigClass(ArrayList.class)
    public void testArrayList() {
        Assertions.assertEquals(ArrayList.class, object.getClass());
    }

    @Test
    @ProducerConfigClass(HashSet.class)
    public void testHashSet() {
        Assertions.assertEquals(HashSet.class, object.getClass());
    }

}