package com.catdog.jd.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "user")
@Api(value = "测试类",tags = "测试APi接口")
public class UserController {

	@GetMapping(value = "get")
	@ApiOperation(value = "测试接口",notes = "get")
	public String get(){
//		Map map = new HashMap();
//		map.put("你好","你好");
//		map.put("步步","前期");
//		map.put("111","前期11");
		return "你啊你";
	}

}
