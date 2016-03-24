package com.celloud.service.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.celloud.model.mysql.RSAKey;
import com.celloud.service.RSAKeyService;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springMVC.xml" })
@Transactional // 启用事务管理，spring会自动将单元测试中对数据库做的所有修改回滚，不会影响数据库里的数据，但是insert会影响主键的自增
public class RSAKeyServiceImplTest extends TestCase {
    private static Logger logger = LoggerFactory.getLogger(RSAKeyServiceImplTest.class);
    @Resource
    private RSAKeyService service;
    private RSAKey key;
    private String modulus = "8aecf6008ecb1ba5345030b97fd9094372348fad541f69a16ded4ccdb6968afdc48aebb9fd9eafcf8b170ec84c6e0ba367b22a06c481353c17a90ee096c43d2b7276d7d263e687f70c72f4441acad050d86d663b428e994680938f8c7602bd3cc153fbe4a761cc6e389cf2ea105299f3409ca03699e8af77c165dd96d103d3ab";
    private String priExponent = "3bdb5f1edc675c90f09a40741c4699fc2a5fc52bec1be0ba420fc550b903e1fcf6cad847f9a37ea2908e2dbb555af0bec5f5f8af93c90bf3c949f8f40161a9ecc4cf81af946fc8d935271170c78912d7748e35444ad53dc23e15298fcfa7c424bece3b8b59179b176d3a0d747c152860ffe215b1644245842cee7c1d7578eda9";
    private String pubExponent = "10001";
    private Date expiresDate;

    @Before
    public void setUp() throws Exception {
        Date createTime = new Date();
        key = new RSAKey();
        key.setCreateTime(createTime);
        key.setUserId(27);
        key.setModulus(modulus);
        key.setPriExponent(priExponent);
        key.setPubExponent(pubExponent);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -8);
        expiresDate = calendar.getTime();
        for (int i = 0; i < 1; i++) {
            service.insert(key);
        }
        key.setCreateTime(expiresDate);
        for (int i = 0; i < 1; i++) {
            service.insert(key);
        }
        key.setCreateTime(createTime);
    }

    @Test
    public void testInsert() {
        boolean result = service.insert(key);
        assertTrue(result);
        service.insert(key);
        assertTrue(result);
    }

    @Test
    public void testGetByModulus() {
        service.insert(key);
        RSAKey rsaKey = service.getByModulus(modulus);
        service.insert(key);
        assertNotNull(rsaKey);
        assertEquals(rsaKey.getModulus(), modulus);
        rsaKey = service.getByModulus(modulus);
        assertNotNull(rsaKey);
        assertEquals(rsaKey.getModulus(), modulus);
    }

    @Test
    public void testDeleteExpiresKeys() {
        int result = service.deleteExpiresKeys(7);
        logger.info("删除了 {} 个过期的key", result);
        RSAKey rsaKey = service.getByModulus(modulus);
        assertTrue("删除所有过期的key之后，又找到了一个过期的key", rsaKey.getCreateTime().after(expiresDate));
    }

    @Test
    public void testDeleteByModulus() {
        int result = service.deleteByModulus(modulus);
        logger.info("删除了 {} 个key", result);
        RSAKey rsaKey = service.getByModulus(modulus);
        assertNull("根据modulus删除key之后，又找到了一个key", rsaKey);
    }

    @Test
    public void testDeleteAllKeys() {
    }

}
