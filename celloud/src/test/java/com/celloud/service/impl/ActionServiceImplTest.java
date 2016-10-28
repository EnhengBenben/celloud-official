package com.celloud.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.celloud.model.mysql.ActionLog;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ActionLogService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springMVC.xml" })
public class ActionServiceImplTest {
    @Resource
    private ActionLogService service;
    private ActionLog log1;
    // private ActionLog log2;

    @Before
    public void setUp() throws Exception {
        log1 = new ActionLog();
        log1.setAddress("北京");
        log1.setBrowser("");
        log1.setBrowserVersion("");
        log1.setIp("");
        log1.setLogDate(new Date());
        log1.setMessage("单元测试1");
        log1.setOperate("单元测试1");
        log1.setOs("");
        log1.setUserId(1);
        log1.setUsername("demo");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLog() {
        // fail("Not yet implemented");
    }

    @Test
    public void testInsert() {
        service.insert(log1);
    }

    @Test
    public void testFindLogs() {
        PageList<ActionLog> pageList = service.findLogs(23, new Page(1, 20));
        Assert.assertNotNull(pageList);
        Assert.assertNotNull(pageList.getDatas());
        Assert.assertNotNull(pageList.getPage());
        Assert.assertEquals(pageList.getPage().getCurrentPage(), 1);
        Assert.assertEquals(pageList.getPage().getPageSize(), 20);
    }

}
