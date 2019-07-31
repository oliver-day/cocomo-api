package com.cocomo.service.impl;

import com.cocomo.pojo.User;
import com.cocomo.service.TestService;
import com.cocomo.vo.TestVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/applicationContext.xml"})
public class TestServiceImplTest {

    @Autowired
    private TestService testService;

    @Test
    public void assembleResult() {
        String testName = "Zhang San";
        Integer testAge = 20;
//        Assert.assertEquals(testName, "Zhang San");
        ResponseEntity<TestVO> result = testService.assembleResult(testName, testAge);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getBody().getName(), testName);
        Assert.assertEquals(result.getBody().getAge(), testAge);
    }

    @Test
    public void addUserTest(){
        User user = new User();
        user.setName("Haoming");
        user.setAge(25);
        testService.addUser(user);
    }
}