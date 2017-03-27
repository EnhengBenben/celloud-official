package com.celloud.action;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class AppActionTest extends BaseActionTest {

    @Test
    public void testListByClassifyId() throws Exception {
        mockMvc.perform(
                get("/app/listByClassifyId").param("classifyId", "3").param("currentPage", "1").param("pageSize", "3"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAddOrRemoveApp() throws Exception {
        mockMvc.perform(put("/app/addOrRemoveApp").param("userId", "23").param("appId", "73").param("isAdd", "1"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/app/82").param("userId", "23")).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testListByCondition() throws Exception {
        mockMvc.perform(get("/app/listByCondition").param("currentPage", "1").param("pageSize", "3")
                .param("flag", "0").param("userId", "23")).andDo(MockMvcResultHandlers.print());
    }

}
