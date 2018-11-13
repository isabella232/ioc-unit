package com.oneandone.cdi.weld3starter;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.oneandone.cdi.weldstarter.WeldStarterTestBase;

/**
 * @author aschoerk
 */
public class Weld3StarterTest extends WeldStarterTestBase {

    @Before
    public void init() {
        setWeldStarter(new WeldStarterImpl());
    }

    static class TestBean {

    }

    @Test
    public void test1() {
        setBeanClasses(TestBean.class);
        start();
        assertNotNull(selectGet(TestBean.class));
    }
}
