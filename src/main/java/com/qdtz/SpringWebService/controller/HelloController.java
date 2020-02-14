package com.qdtz.SpringWebService.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qdtz.SpringWebService.annotation.PointAnnotation;
import com.qdtz.SpringWebService.annotation.TestEnum;
import com.qdtz.SpringWebService.model.Car;
import com.qdtz.SpringWebService.service.TestOracleService;

@Controller
public class HelloController {
    
    @Autowired
    private TestOracleService testOracleService;
    
    @PointAnnotation(description = TestEnum.TEST_ENUM02, str = "", list = { TestEnum.TEST_ENUM02.TEST_ENUM01 })
    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "hello spring boot";
    }
    
    @PointAnnotation(str="car", description = TestEnum.TEST_ENUM01, list = { TestEnum.TEST_ENUM01.TEST_ENUM01 })
    @ResponseBody
    @GetMapping("/car")
    public String car(Car car, HttpServletRequest request){
    	HttpSession session = request.getSession();
    	session.setAttribute("msg", "car session");
        return car.toString();
    }
    
    @GetMapping("/toTestHtml")
    public String testHtml(Model model){
    	model.addAttribute("msg", "测试模块的取值！！！");
        return "test";
    }
    
    @ResponseBody
    @GetMapping(value="/userCount")
    public Integer getUserCount() {
        
        return testOracleService.getSlaveUserCount();
    }
    
    @ResponseBody
    @GetMapping(value="/slaveUserCount")
    public Integer getSlaveUserCount() {
        
        return testOracleService.getUserCount();
    }
    
    @ResponseBody
    @GetMapping(value="/updateUserLastLogin")
    public Integer updateUserLastLogin() {
        
        return testOracleService.updateUserLastLogin();
    }
    
    @ResponseBody
    @GetMapping(value="/updateSlaveUserLastLogin")
    public Integer updateSlaveUserLastLogin() {
        
        return testOracleService.updateSlaveUserLastLogin();
    }
}
