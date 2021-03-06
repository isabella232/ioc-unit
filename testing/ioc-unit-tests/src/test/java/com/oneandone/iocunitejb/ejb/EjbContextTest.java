package com.oneandone.iocunitejb.ejb;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.MessageDrivenContext;
import javax.ejb.SessionContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.oneandone.iocunit.IocUnitRunner;

/**
 * @author aschoerk
 */
@RunWith(IocUnitRunner.class)
public class EjbContextTest {

    @Resource
    EJBContext ejbContext;

    @Resource
    SessionContext sessionContext;

    @Resource
    MessageDrivenContext messageDrivenContext;

    @Test
    public void canInjectAllEjbContextTypes() {
        Assert.assertNotNull(ejbContext);
        Assert.assertNotNull(messageDrivenContext);
        Assert.assertNotNull(sessionContext);
    }
}
