package com.celloud.action;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class ClassifyActionTest extends BaseActionTest {

    @Test
    public void testListByPid() throws Exception {
        mockMvc.perform(get("/classifys").param("pid", "0")).andDo(MockMvcResultHandlers.print()).andReturn();
    }

}
