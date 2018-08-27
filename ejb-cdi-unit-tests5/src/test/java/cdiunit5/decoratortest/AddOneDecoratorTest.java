package cdiunit5.decoratortest;

import com.oneandone.ejbcdiunit5.JUnit5Extension;
import org.jglue.cdiunit.AdditionalClasses;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Created by pcasaes on 30/03/17.
 */
@ExtendWith(JUnit5Extension.class)
@AdditionalClasses({
        DecoratedImpl.class,
        AddOneDecorator.class
})
public class AddOneDecoratorTest {

    @Inject
    private DecoratedInterface decorated;

    @Test
    public void testAddOne() {
        assertEquals(1, decorated.calculate());
    }
}