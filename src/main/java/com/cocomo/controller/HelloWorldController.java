package com.cocomo.controller;

import com.cocomo.common.Const;
import com.cocomo.pojo.User;
import com.cocomo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Sample controller
 * @author Haoming Zhang
 * @date 01/24/2019 14:46
 */
@Controller
@RequestMapping("test")
public class HelloWorldController {

    // Always inject the interface.
    // An interface is like a protocol that defines some certain behaviors
    // regardless of the concrete implementation, implementations could be changed
    // but interfaces stay the same.
    // Google Spring IOC / Dependency Injection if you have more concerns.
    @Autowired
    private TestService testService;

    /**
     * Sample API
     * @author Haoming Zhang
     * @date 01/24/2019 14:51
     */
    @RequestMapping(value = "{name}/{age}", method = RequestMethod.GET)
    public ResponseEntity getName(@PathVariable(value = "name") String name,
                                  @PathVariable(value = "age") Integer age) {
        // Input verification
        // Consider moving this to the service layer if it can be reused.
        if (age >= Const.MAX_AGE) { // age >= 100 is bad coding style(magic numbers),
                                    // however, since you may not have enough time to
                                    // figure out what exactly each number means in the
                                    // original code that you are going to migrate, I guess
                                    // magic numbers can be tolerated for now.


            return new ResponseEntity<>(Const.ErrorMessages.INVALID_AGE, HttpStatus.BAD_REQUEST);
        }

        // The reason why we may want an extra layer is that different APIs may share some common logic,
        // and this approach can reduce redundancy significantly and make the project
        // easy to be maintained when it becomes complicated.
        return testService.assembleResult(name, age);
    }

    /**
     * Query from db
     * @author Haoming Zhang
     * @date 2/11/19 21:43
     */
    @RequestMapping(value = "id/{id}", method = RequestMethod.GET)
    public ResponseEntity getName(@PathVariable(value = "id") Integer id) {
        if (id == null) {
            return new ResponseEntity<>("Empty id", HttpStatus.BAD_REQUEST);
        }

        return testService.queryById(id);
    }

    /**
     * Insert user
     * @author Haoming Zhang
     * @date 3/6/19 18:26
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ResponseEntity addUser() {
        User user = new User();
        user.setName("Haoming");
        user.setAge(25);
        testService.addUser(user);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
