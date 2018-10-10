package team.nobug.staffmanage.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.User;
import team.nobug.staffmanage.service.UserService;

/**
 * �û����Ʋ�
 * 
 * @author ��ï��
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	/*
	 * 1.�������Controllerע���RequestMappingע�⡣��ע���û��ķ���㡣
	 * 2.userLoginΪ��֤�û���¼�ķ������÷������������������ֱ����û��绰����tel���û�����pwd��HTTP�Ựsession���÷�������ModelAndView��������������������ݺ�������ͼ���÷�������
	 * ������checkLogin(tel, pwd)����������������֤�ɹ����ͽ��û������ݴ���session�С���������жϴ����tel��pwd�Ƿ�Ϊ���ַ���������ǣ��ͷ�����Ϣ�����򣬾͵������ݷ��ʲ��
	 * checkLogin(tel, EncryptUtils.encryptBySHA1(pwd))������ȥ��֤�û����룬Ȼ������Ϣ��
	 * 3.userLogout����Ϊ�û�ע���ķ������÷�������һ��������HTTP�Ựsession���÷�������Message�������ڸ�ǰ�˷�����Ϣ�������ǰsession��û���û���Ϣ���ͷ�����Ϣ�����򣬾���session
	 * ���Ƴ���ǰ�û���Ϣ��Ȼ������Ϣ��
	 * 4.updateUser����Ϊ�����û����÷���������������User user�� HttpSession session������һ��Message�����ڷ�����У��жϴ���Ĳ�����ֵ�Ƿ�Ϊ��֧����������п��ַ������ͷ�����Ϣ��
	 * ���򣬾͸��ݲ�����Ϣȥ���ݿ��в����Ƿ��и��û������û�У�������Ϣ������ȥ�ж��Ƿ�ֵ�����ı䣬���û�иı䣬������Ϣ������ȥ������ĵ��ֻ����Ƿ�������ʹ�ã������ʹ�ã�������Ϣ������
	 * �������ݷ��ʲ��updateUser(user1)�����������û���
	 * 5.changePwd����Ϊ�޸����룬�÷�������4��������pwd�����룬newpwd�����룬repwdȷ�����룬sessionHttp�Ự���÷�������Message������Ϣ�������˷�����changePwd(pwd, newpwd, repwd, session)
	 * �������õ�Message�����ڷ�����У����ж���������ظ������Ƿ�һ�£���һ�£�������Ϣ�����򣬴�session�л�ȡ����ǰ���û���Ȼ�����ݿ���Ҹ��û��Ƿ���ڣ���������ڣ�������Ϣ�����򣬵�������
	 * ���ʲ��updateUser(u)�����û��������벢���÷�����Ϣ��
	 * 
	 * */

	@Resource
	private UserService userService;

	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public ModelAndView userLogin(String tel, String pwd, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Message message = userService.checkLogin(tel, pwd);
		if (message.getMsg().equals("success")) {
			if (message.getUser().getPermission().equals("user:admin")) {
				mv.setViewName("redirect:/manage");
			} else if (message.getUser().getPermission().equals("user:common")) {
				mv.setViewName("redirect:/employee");
			}
			session.setAttribute("user", message.getUser());
		} else {
			mv.setViewName("redirect:/login");
			session.setAttribute("info", message);
			session.setMaxInactiveInterval(5);
		}

		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Message userLogout(HttpSession session) {
		Message msg = new Message();
		if (session.getAttribute("user") == null) {
			msg.setMsg("��ǰû���û���");
		} else {
			session.removeAttribute("user");
			msg.setMsg("ע���ɹ���");
		}
		return msg;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public Message updateUser(User user, HttpSession session) {
		Message msg = new Message();
		try {
			msg = userService.updateUser(user, session);
			session.removeAttribute("user");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("�����쳣");
			return msg;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/pwd", method = RequestMethod.PUT)
	public Message changePwd(String pwd, String newpwd, String repwd, HttpSession session) {
		Message msg = new Message();
		try {
			msg = userService.changePwd(pwd, newpwd, repwd, session);
			session.removeAttribute("user");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("�����쳣");
			return msg;
		}
	}
}
