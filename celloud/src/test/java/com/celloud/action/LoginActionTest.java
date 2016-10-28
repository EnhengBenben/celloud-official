package com.celloud.action;

import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.celloud.constants.Constants;
import com.celloud.model.PrivateKey;
import com.celloud.model.mysql.RSAKey;
import com.celloud.model.mysql.User;
import com.celloud.service.RSAKeyService;
import com.celloud.utils.RSAUtil;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springMVC.xml" })
@WebAppConfiguration
public class LoginActionTest extends BaseActionTest {
    @Resource
    private RSAKeyService service;
    private RSAKey publicKey;
    private String modulus = "c82376ad281d246c535fb4378a2d6d155712e2f652db6c252e8951ea6a92028f2d8b22b7983c008fc118524b8a939f1915c35848d7abc7bb33da87ee54ea988edd0917e8f9657b0247084af998b913043c4bf3aae23bf503536716ff47b4e6c5f9f316820944ca394031d5658d18a98245e62ef770b18547da52c0283a8eda8f";
    private String priExponent = "5e96d43a16615f9b8af40a7acd6596cea52e99f20d4b031bb798d30c557dd893ced4997de80743c4ad604f54c093f077ac9f7c9440ca4145aac637c45e81d9c3a56eb87ef29129c28d846ab747f18663d6a7d18868cc172112ff457c006ce75ef0ef9da25efe5d0004b5209204c13fd2b10460fa9d68d1031fe647b671f07861";
    private String pubExponent = "10001";
    private PrivateKey privateKey;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        publicKey = new RSAKey();
        publicKey.setCreateTime(new Date());
        publicKey.setUserId(23);
        publicKey.setModulus(modulus);
        publicKey.setPriExponent(priExponent);
        publicKey.setPubExponent(pubExponent);
        privateKey = new PrivateKey(new BigInteger(modulus, 16), new BigInteger(priExponent, 16));
        // service.insert(publicKey);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testToLogin() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("publicKey"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        Assert.assertNotNull(result.getModelAndView().getModel().get("user"));
    }

    @Test
    public void testLoginWithWrongUsername() throws Exception {
        MvcResult result = login("demodemodemo", "123", "1234", false, MockMvcResultMatchers.view().name("login"),
                MockMvcResultMatchers.model().attributeExists("info", "user", "publicKey"));
        User loginUser = (User) result.getRequest().getSession().getAttribute(Constants.SESSION_LOGIN_USER);
        Assert.assertNull(loginUser);
    }

    @Test
    public void testLoginWithWrongPassword() throws Exception {
        MvcResult result = login("demo", "1234", "1234", false, MockMvcResultMatchers.view().name("login"),
                MockMvcResultMatchers.model().attributeExists("info", "user", "publicKey"));
        User loginUser = (User) result.getRequest().getSession().getAttribute(Constants.SESSION_LOGIN_USER);
        Assert.assertNull(loginUser);
    }

    @Test
    public void testLoginWithWrongKaptchaCode() throws Exception {
        MvcResult result = login("demo", "1234", "2211", false, MockMvcResultMatchers.view().name("login"),
                MockMvcResultMatchers.model().attributeExists("info", "user", "publicKey"));
        User loginUser = (User) result.getRequest().getSession().getAttribute(Constants.SESSION_LOGIN_USER);
        Assert.assertNull(loginUser);
    }

    @Test
    public void testLoginSuccess() throws Exception {
        MvcResult result = login("demo", "123", "1234", false, MockMvcResultMatchers.view().name("loading"),
                MockMvcResultMatchers.model().attributeDoesNotExist("info"),
                MockMvcResultMatchers.model().attributeExists("user", "publicKey"));
        User loginUser = (User) result.getRequest().getSession().getAttribute(Constants.SESSION_LOGIN_USER);
        Assert.assertNotNull(loginUser);
        Assert.assertNull(result.getRequest().getSession().getAttribute(Constants.SESSION_RSA_PRIVATEKEY));
        Assert.assertEquals(loginUser.getUserId(), user.getUserId());
    }

    public MvcResult login(String username, String password, String kaptchaCode, boolean checked,
            ResultMatcher... matchers) throws Exception {
        // 模拟js加密
        String encryptedPassword = RSAUtil.encryptedString(modulus, pubExponent, StringUtils.reverse(password));
        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.post("/login")
                .sessionAttr(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, "1234")
                .sessionAttr(Constants.SESSION_RSA_PRIVATEKEY, privateKey).param("password", encryptedPassword)
                .param("username", username).param("kaptchaCode", kaptchaCode).param("modulus", modulus)
                .param("exponent", pubExponent).param("checked", String.valueOf(checked).toLowerCase())
                .header("user-agent", ua);
        ResultActions resultActions = mockMvc.perform(builders);
        if (matchers != null) {
            for (ResultMatcher matcher : matchers) {
                resultActions.andExpect(matcher);
            }
        }
        MvcResult result = resultActions.andDo(MockMvcResultHandlers.print()).andReturn();
        return result;
    }

    @Test
    public void testLogout() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/logout").sessionAttr(Constants.SESSION_LOGIN_USER, user))
                .andExpect(MockMvcResultMatchers.view().name("redirect:login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("login")).andDo(MockMvcResultHandlers.print())
                .andReturn();
        User loginUser = (User) result.getRequest().getSession().getAttribute(Constants.SESSION_LOGIN_USER);
        Assert.assertNull(loginUser);
    }

}
