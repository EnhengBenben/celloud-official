package com.celloud.action;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.celloud.constants.Constants;

public class UploadActionTest extends BaseActionTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testUploadManyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("test", "test", null,
                new FileInputStream(new File("/Users/sun8wd/Downloads/CS1.6CH.dmg")));
        MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.fileUpload("/uploadFile/uploadManyFile").file(file)
                .param("chunk", "1").param("chunks", "1").header("user-agent", ua).param("name", "testName")
                .param("originalName", "testOriginalName").param("onlyName", "testOnlyName").param("md5", "md5")
                .sessionAttr(Constants.SESSION_LOGIN_USER, user);
        MvcResult result = mockMvc.perform(builders).andExpect(MockMvcResultMatchers.content().string("uploadMSuc"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        Assert.assertNotNull(result.getResponse().getContentType());
    }

}
