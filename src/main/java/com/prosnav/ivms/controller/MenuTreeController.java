package com.prosnav.ivms.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prosnav.ivms.core.Function;
import com.prosnav.ivms.core.JWTService;
import com.prosnav.ivms.core.RedisStoreUser;
import com.prosnav.ivms.core.User;
import com.prosnav.ivms.model.Menu;

@Controller
public class MenuTreeController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String type = "menu";
	
	@Autowired
	private RedisStoreUser redisStoreUser;
	
	@Autowired
	private JWTService jwtService;
	
	@RequestMapping(value="/menuTree", method=RequestMethod.GET)
	public @ResponseBody List<Menu> menuTree(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("get menu tree");
		String jwt = jwtService.getJWTFromReq(request);
		String username = jwtService.getUsernameByJWT(jwt);
		User user = redisStoreUser.GetUser(username);
		List<Function> functions = user.getFunctionList();
		List<Menu> menuTree = new ArrayList<Menu>();
		
		for(Function func: functions) {
			if(type.equals(func.getType()) && !func.isHidden()) {
				Menu menu = new Menu();				
				if(func.getTreeLevel() == 1) {
					menu.setDivider(true);
				}else {
					menu.setDivider(false);
				}
				menu.setId(func.get_id());
				menu.setFuncAction(func.getFuncAction());
				menu.setName(func.getName());
				menuTree.add(menu);
			}
		}
		Collections.sort(menuTree, new Comparator<Menu>(){  
			public int compare(Menu arg0, Menu arg1) {  
			    return arg0.getId().compareTo(arg1.getId());  
			}  
	    });  
		return menuTree;
	}
}
