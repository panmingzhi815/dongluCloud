package com.donglu.cloud;

import com.donglu.cloud.bean.MsgResponse;
import com.donglu.cloud.repository.ProjectRepository;
import com.donglu.cloud.repository.WeiXinMerchantRepository;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.internal.LinkedTreeMap;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebSecurity
public class WeiXinMerchantControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ProjectRepository projectRepository;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithUserDetails(value = "admin",userDetailsServiceBeanName = "myUserDetailsService")
    public void test() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appId","11");
        jsonObject.put("mchId","22");
        jsonObject.put("appName","33");
        jsonObject.put("appKey","44");
        jsonObject.put("serviceMchId","424");

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id",projectRepository.findAll().get(0).getId());
        jsonObject.put("project",jsonObject1);

        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post( "/pay/weixinMerchant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
                .accept(MediaType.ALL)).andReturn();

        MsgResponse msgResponse = new GsonBuilder().create().fromJson(mvcResult.getResponse().getContentAsString(), MsgResponse.class);
        assert msgResponse.getSuccess();

        MvcResult mvcResult2 = this.mockMvc.perform(
                MockMvcRequestBuilders.get( "/pay/weixinMerchant")
                        .contentType(MediaType.TEXT_HTML)
                        .content(jsonObject.toString())
                        .accept(MediaType.ALL)).andReturn();

        MsgResponse msgResponse2 = new GsonBuilder().create().fromJson(mvcResult2.getResponse().getContentAsString(), MsgResponse.class);
        assert msgResponse2.getSuccess();
        assert ((LinkedTreeMap) ((ArrayList) msgResponse2.getData()).get(0)).containsKey("project");
    }

}
