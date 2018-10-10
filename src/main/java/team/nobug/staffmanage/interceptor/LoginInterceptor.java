package team.nobug.staffmanage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import team.nobug.staffmanage.dto.UserDto;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		String path = request.getContextPath();
		
		if(user == null) {
			response.sendRedirect(path + "/login");
			return false;
		}
		
		return true;
	}

}
