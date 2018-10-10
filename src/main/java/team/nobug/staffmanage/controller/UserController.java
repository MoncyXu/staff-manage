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
 * 用户控制层
 * 
 * @author 徐茂鑫
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	/*
	 * 1.给本类加Controller注解和RequestMapping注解。并注入用户的服务层。
	 * 2.userLogin为验证用户登录的方法，该方法接收三个参数，分别是用户电话号码tel，用户密码pwd，HTTP会话session，该方法返回ModelAndView，用于设置请求域的数据和设置视图。该方法调用
	 * 服务层的checkLogin(tel, pwd)方法，如果服务层验证成功，就将用户的数据存入session中。服务层先判断传入的tel和pwd是否为空字符串，如果是，就反馈信息；否则，就调用数据访问层的
	 * checkLogin(tel, EncryptUtils.encryptBySHA1(pwd))方法，去验证用户密码，然后反馈信息。
	 * 3.userLogout方法为用户注销的方法，该方法接收一个参数，HTTP会话session，该方法反馈Message对象，用于给前端反馈信息。如果当前session中没有用户信息，就反馈信息；否则，就在session
	 * 中移除当前用户信息，然后反馈信息。
	 * 4.updateUser方法为更新用户，该方法接收两个参数User user和 HttpSession session，返回一个Message对象。在服务层中，判断传入的参数的值是否为空支付串，如果有空字符串，就反馈信息；
	 * 否则，就根据参数信息去数据库中查找是否有该用户，如果没有，反馈信息；否则，去判断是否值有所改变，如果没有改变，反馈信息；否则，去检验更改的手机号是否被其他人使用，如果被使用，反馈信息；否则，
	 * 调用数据访问层的updateUser(user1)方法，更新用户。
	 * 5.changePwd方法为修改密码，该方法接收4个参数：pwd旧密码，newpwd新密码，repwd确认密码，sessionHttp会话。该方法返回Message反馈信息，调用了服务层的changePwd(pwd, newpwd, repwd, session)
	 * 方法，得到Message对象。在服务层中，先判断新密码和重复密码是否一致，不一致，反馈信息；否则，从session中获取到当前的用户，然后到数据库查找该用户是否存在，如果不存在，反馈信息；否则，调用数据
	 * 访问层的updateUser(u)设置用户的新密码并设置反馈信息。
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
			msg.setMsg("当前没有用户！");
		} else {
			session.removeAttribute("user");
			msg.setMsg("注销成功！");
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
			msg.setMsg("操作异常");
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
			msg.setMsg("操作异常");
			return msg;
		}
	}
}
