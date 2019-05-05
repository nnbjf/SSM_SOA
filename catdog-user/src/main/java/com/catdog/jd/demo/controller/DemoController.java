package com.catdog.jd.demo.controller;

import com.catdog.jd.demo.service.DemoService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "demo")
public class DemoController {

	private final static Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	private DemoService demoService;
	@Autowired
	private RedisTemplate redisTemplate;

	@GetMapping(value = "get")
	@ApiOperation(value = "demo接口",notes = "demo测试接口")
	public Map<String,Object> get(){
		try {
			LOGGER.info("----------------{}","DemoController");
			return demoService.get();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

//	@GetMapping(value = "add")
//	@ApiOperation(value = "add",notes = "添加500万条数据")
	public boolean add(){

		demoService.add();
		return true;
	}
	@PostMapping("login")
	public boolean login(@RequestParam(value = "name")String name,@RequestParam(value = "pwd")String pwd, HttpServletRequest request){
//		HttpSession session = request.getSession();
//		session.setAttribute("user",name);
//		session.setAttribute("pwd",pwd);
//		session.setAttribute("user-pwd",name + "-" + pwd);
//		redisTemplate.opsForValue().set("user-pwd1",name + "-" + pwd);
		return true;
	}

//	@GetMapping("login")
//	public String getSession(HttpServletRequest request){
//		return (String) request.getSession().getAttribute("user-pwd1");
//	}

	@GetMapping("getSession")
	@ApiOperation(value = "获取session接口测试",notes = "获取session接口测试")
	public String getSession(HttpServletRequest request){
//		Session session = (Session) request.getSession().getAttribute(request.getSession().getId());
		LOGGER.info("--sessionID: {}",request.getSession().getId());
		return (String) request.getSession().getAttribute("user");
	}
}
