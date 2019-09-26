package com.it.blog.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.it.blog.entity.User;
import com.it.blog.service.UserService;
import com.it.blog.utils.BaseAction;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController extends BaseAction{
	
	@Autowired
	private UserService userServiceImpl;

	@RequestMapping(value="index")
	public ModelAndView test (){
		logger.info("页面跳转");
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	@RequestMapping(value="login")
	public String login (){
		logger.info("页面跳转");
		return "user/test";
	}
	@RequestMapping(value="queryUserList")
    @ResponseBody
	public List<User> queryUserList (HttpServletRequest request){
		System.out.println(request.getRequestURI());
		System.out.println(request.getRequestURL());
		String username = request.getParameter("username");
		String data = request.getParameter("data");
		System.out.println(username + "=============="+data);
		logger.info("================"+username);
		System.out.print("==============================");
		List<User> userList = null;
		try {
			userList = userServiceImpl.queryUserList();
			logger.info("查询用户信息列表成功！");
		} catch (Exception e) {
			logger.error("查询用户列表失败!", e);
		}
		return userList;
	}

	@RequestMapping(value = "queryUserInfoByName")
	public User queryUserInfoByName(HttpServletRequest re){
		String username = re.getParameter("username");
		User user = new User();
		user.setUserName(username);
		System.out.println("BT场景");
		return userServiceImpl.queryUserInfoByName(user);
	}


	@RequestMapping("saveUser")
	public Map<String, Object> saveUser(){
		
		return userServiceImpl.saveUser();
	}
	
	@RequestMapping("queryUserInfo")
	public User queryUserInfo() {
		User user = new User();
		try {
			user.setId(1);
			user.setUserName("西西");
			user.setAge(15);
			user.setSex(1);
			logger.info("查询用户信息成功！");
		} catch (Exception e) {
			logger.debug("查询用户信息失败！", e);
		}
		return user;
	}
	@RequestMapping(value ="queryClassUriJson")
	public String queryClassUriJson(){
		return userServiceImpl.getXmlfile();
	}

}
