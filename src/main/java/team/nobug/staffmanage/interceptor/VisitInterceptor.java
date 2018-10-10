package team.nobug.staffmanage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import team.nobug.staffmanage.dto.UserDto;

/**
 * ����������
 * 
 * @author ��ï��
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

		// �û�Ϊ��ֻ�ܷ��ʵ�¼ҳ��
		if (user == null && (uri.endsWith("/manage") || uri.endsWith("/employee"))) {
			response.sendRedirect(path + "/login");
			return false;
		}

		if (user != null) {
			String role = user.getPermission();
			// �������ٷ��ʵ�¼����
			if (uri.endsWith("/login") || uri.equals(path)) {
				if (role.equals("user:common")) {
					response.sendRedirect(path + "/employee");
				} else if (role.equals("user:admin")) {
					response.sendRedirect(path + "/manage");
				}
				return false;
			}

			// ��ͨ�û���ֹ���ʹ���ҳ
			if (uri.endsWith("/manage") && role.equals("user:common")) {
				response.sendRedirect(path + "/employee");
				return false;
			}

			// ����Ա��ֹ����Ա��ҳ
			if (uri.endsWith("/employee") && role.equals("user:admin")) {
				response.sendRedirect(path + "/manage");
				return false;
			}
		}

		return true;

	}

}
