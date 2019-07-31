package com.cocomo.service;

import com.cocomo.pojo.User;
import com.cocomo.vo.TestVO;
import org.springframework.http.ResponseEntity;

/**
 * TestService interface.
 * @author Haoming Zhang
 * @date 01/25/2019 13:16
 */
public interface TestService {

    /**
     * Assemble the view object.
     * @author Haoming Zhang
     * @date 01/25/2019 12:54
     */
    ResponseEntity<TestVO> assembleResult(String name, Integer age);

    /**
     * Query from db
     * @author Haoming Zhang
     * @date 2/11/19 21:45
     */
    ResponseEntity<TestVO> queryById(Integer id);

    /**
     * Test transactional
     * @author Haoming Zhang
     * @date 3/6/19 18:08
     */
    void addUser(User user);
}
