package cn.com.exercise.batch;

import cn.com.exercise.batch.entity.UserEntity;
import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchApplicationTests {

	@Test
	public void contextLoads() {
	}

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void before(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testSelect() throws Exception{
		List<String> list = new ArrayList<>();
        list.add("wangwu");
        list.add("zhaoliu");
        list.add("baiqi");
        UserEntity entity = new UserEntity();
        entity.setAge(32);
		MvcResult mvcResult = mockMvc.perform(get("/object/batchSelect")
											.param("list",JSON.toJSONString(list))
                                            .param("entity",JSON.toJSONString(entity))
											.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(JSON.parseArray(result,UserEntity.class));
	}

    @Test
    public void testAdd() throws Exception{
        List<UserEntity> list = new ArrayList<>();
        UserEntity entity1 = new UserEntity();
        entity1.setAge(1);
        entity1.setName("1");
        entity1.setGender("f");
        entity1.setPsw("123456");
        entity1.setSeq(8);
        UserEntity entity2 = new UserEntity();
        entity2.setAge(2);
        entity2.setName("2");
        entity2.setGender("m");
        entity2.setPsw("123456");
        entity2.setSeq(9);
        UserEntity entity3 = new UserEntity();
        entity3.setAge(3);
        entity3.setName("3");
        entity3.setGender("m");
        entity3.setPsw("123456");
        entity3.setSeq(10);
        list.add(entity1);
        list.add(entity2);
        list.add(entity3);
        MvcResult mvcResult = mockMvc.perform(get("/object/batchAdd")
                .param("userEntity",JSON.toJSONString(list))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testDelete() throws Exception{
        List<Integer> list = new ArrayList<>();
        list.add(17);
        list.add(18);
        list.add(19);
        MvcResult mvcResult = mockMvc.perform(get("/object/batchDelete")
                .param("idStr",JSON.toJSONString(list))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testUpdateOneVariable() throws Exception{
	    UserEntity userEntity = new UserEntity();
	    userEntity.setPsw("123");
        List<Integer> list = new ArrayList<>();
        list.add(17);
        list.add(18);
        list.add(19);
        MvcResult mvcResult = mockMvc.perform(get("/object/batchUpdateOneVariable")
                .param("getStr",JSON.toJSONString(list))
                .param("getUser",JSON.toJSONString(userEntity))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
    }
}

