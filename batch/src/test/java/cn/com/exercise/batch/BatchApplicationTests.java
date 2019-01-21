package cn.com.exercise.batch;

import cn.com.exercise.batch.entity.UserEntity;
import cn.com.exercise.batch.entity.UserEntity2;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 两个入参的查询
     * @throws Exception
     */
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

    /**
     * 入参为对象的某个字段的查询
     * @throws Exception
     */
    @Test
    public void testSelect2() throws Exception{
        List<Integer> list = new ArrayList<>();
        list.add(11);
        list.add(12);
        list.add(13);
        UserEntity2 entity = new UserEntity2();
        entity.setIds(list);
        MvcResult mvcResult = mockMvc.perform(get("/object/batchSelect2")
                .param("entity",JSON.toJSONString(entity))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(JSON.parseArray(result,UserEntity2.class));
    }

    /**
     * 入参为array的查询
     * @throws Exception
     */
    @Test
    public void testSelect3() throws Exception{
        Integer[] ids = {11,12,13};
        MvcResult mvcResult = mockMvc.perform(get("/object/batchSelect3")
                .param("getStr",JSON.toJSONString(ids))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(JSON.parseArray(result,UserEntity2.class));
    }

    /**
     * 入参为Map的查询
     * @throws Exception
     */
    @Test
    public void testSelect4() throws Exception{
        Map<String,Object> myMap = new HashMap<>();
        List<Integer> ids = new ArrayList();
        ids.add(11);
        ids.add(12);
        ids.add(13);
        myMap.put("idMap",ids);
        myMap.put("ageMap",32);
        MvcResult mvcResult = mockMvc.perform(get("/object/batchSelect4")
                .param("myMap",JSON.toJSONString(myMap))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(JSON.parseArray(result,UserEntity.class));
    }

    /**
     * 入参为List<?>的批量插入
     * @throws Exception
     */
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

    /**
     * 入参为List,使用@Param的批量删除
     * @throws Exception
     */
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

    /**
     * 两个入参的批量更新
     * @throws Exception
     */
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

