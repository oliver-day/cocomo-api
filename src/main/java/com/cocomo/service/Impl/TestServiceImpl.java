package com.cocomo.service.Impl;

import com.cocomo.dao.UserMapper;
import com.cocomo.pojo.User;
import com.cocomo.service.TestService;
import com.cocomo.vo.TestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Implementation of TestService.
 * @author Haoming Zhang
 * @date 01/25/2019 12:04
 */
@Slf4j
@Service("testService")
public class TestServiceImpl implements TestService {

    @Autowired
    private UserMapper userMapper;

    /**
     * Assemble the view object.
     * @author Haoming Zhang
     * @date 01/25/2019 12:54
     */
    @Override
    public ResponseEntity<TestVO> assembleResult(String name,
                                                 Integer age) {
        TestVO testVO = new TestVO();
        testVO.setName(name);
        testVO.setAge(age);
        return new ResponseEntity<>(testVO, HttpStatus.OK);
    }

    /**
     * Query from db
     * @author Haoming Zhang
     * @date 2/11/19 21:46
     */
    @Override
    public ResponseEntity<TestVO> queryById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        TestVO testVO = new TestVO();
        BeanUtils.copyProperties(user, testVO);

        return new ResponseEntity<>(testVO, HttpStatus.OK);
    }

    /**
     * Test transactional
     * @author Haoming Zhang
     * @date 3/6/19 18:08
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
        userMapper.insert(user);
        String string  = null;
        if(string.equals("")) {
            int i = 0;
        }
    }
}
