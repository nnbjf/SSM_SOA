package com.catdog.jd.filter.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter{

	private final static Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

	public LoginInterceptor() {
		super();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		LOGGER.debug("---------{}",path);
		if(path.equals("/demo/login")){

//			DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
//			defaultCookieSerializer.setCookiePath("/");
//			defaultCookieSerializer.setCookieName("lsq");
			LOGGER.info("=============上下文路径====================={}",request.getContextPath());
//			String token = response.getHeader("token");
//			Session session = (Session) request.getSession().getAttribute(token);
			HttpSession session = request.getSession();
//			String user = (String) session.getAttribute("user");
//			String sessionId = session.getId();
			LOGGER.info("--sessionID: {}",session.getId());
//			if(user == null){
//				String sessionId = session.getId();
				String name = request.getParameter("name");
				String pwd = request.getParameter("pwd");
				session.setAttribute("user",name + "-" + pwd );
//				Cookie cookie = new Cookie("token",sessionId);
//				response.addCookie(cookie);
//				response.
				return true;
//			}
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
}
