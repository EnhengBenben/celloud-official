package com.celloud.action;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class AppCommentActionTest extends BaseActionTest {

    @Test
    @Rollback(false)
    public void testListByAppId() throws Exception {
        mockMvc.perform(
                get("/appComments").param("currentPage", "1").param("pageSize", "20").param("appId", "118"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testSaveAppComment() throws Exception {
        mockMvc.perform(post("/appComments").param("appId", "82").param("score", "5").param("comment", "HBV_SNP2超级好用")
                .param("userId", "23")).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testUpdateAppComment() throws Exception {
        mockMvc.perform(
                put("/appComments/19").param("score", "2")
                .param("comment", "HBV_SNP2超级好用是真的")
                        .param("userId", "23"))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testGetAppComment() {
        fail("Not yet implemented");
    }

    @Test
    public void testCountScore() {
        fail("Not yet implemented");
    }

}
