package com.jinm.demo.action;


import com.jinm.demo.service.IDemoService;
import com.jinm.mvcframework.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//虽然，用法一样，但是没有功能
@JMController
@JMRequestMapping("/demo")
public class DemoAction {

  	@JMAutowired private IDemoService demoService;

	@JMRequestMapping("/query")
	public void query(HttpServletRequest req, HttpServletResponse resp,
					  @JMRequestParam("name") String name){
//		String result = demoService.get(name);
		String result = "My name is " + name;
		try {
			resp.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@JMRequestMapping("/add")
	public void add(HttpServletRequest req, HttpServletResponse resp,
					@JMRequestParam("a") Integer a, @JMRequestParam("b") Integer b){
		try {
			resp.getWriter().write(a + "+" + b + "=" + (a + b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@JMRequestMapping("/sub")
	public void sub(HttpServletRequest req, HttpServletResponse resp,
					@JMRequestParam("a") Double a, @JMRequestParam("b") Double b){
		try {
			resp.getWriter().write(a + "-" + b + "=" + (a - b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@JMRequestMapping("/remove")
	public String  remove(@JMRequestParam("id") Integer id){
		return "" + id;
	}

}
