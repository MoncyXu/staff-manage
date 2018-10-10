package team.nobug.staffmanage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import team.nobug.staffmanage.dto.UserDto;

/**
 * 访问拦截器
 * 
 * @author 徐茂鑫
 *
 */
public class VisitInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String path = request.getContextPath();
		String uri = request.getRequestURI();

		// 用户为空只能访问登录页面
		if (user == null && (uri.endsWith("/manage") || uri.endsWith("/employee"))) {
			response.sendRedirect(path + "/login");
			return false;
		}

		if (user != null) {
			String role = user.getPermission();
			// 不允许再访问登录界面
			if (uri.endsWith("/login") || uri.equals(path)) {
				if (role.equals("user:common")) {
					response.sendRedirect(path + "/employee");
				} else if (role.equals("user:admin")) {
					response.sendRedirect(path + "/manage");
				}
				return false;
			}

			// 普通用户禁止访问管理页
			if (uri.endsWith("/manage") && role.equals("user:common")) {
				response.sendRedirect(path + "/employee");
				return false;
			}

			// 管理员禁止访问员工页
			if (uri.endsWith("/employee") && role.equals("user:admin")) {
				response.sendRedirect(path + "/manage");
				return false;
			}
		}

		return true;

	}

}
