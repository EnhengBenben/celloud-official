package com.celloud.utils;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springMVC.xml" })
public class VelocityUtilTest {
    @Resource
    private VelocityUtil util;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testMergeMailTemplate() {
        String str = util.mergeMailTemplate("child-2.vm", null);
        System.out.println(str);
        str = util.mergeMailTemplate("child-1.vm", null);
        System.out.println(str);
    }

    @Test
    public void testMergeReportTemplate() {
    }

}
