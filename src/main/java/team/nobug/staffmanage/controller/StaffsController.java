package team.nobug.staffmanage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.User;
import team.nobug.staffmanage.service.StaffsService;

@Controller
@RequestMapping("/staffs")
public class StaffsController {
	
	/*
	 * 1.给本类加Controller注解和RequestMapping注解。并注入员工的服务层。
	 * 2.findAllStaffs方法为查找所有员工，该方法接收两个参数，一个page代表当前页，一个pageSize表示每一页展示的条数，该方法返回ModelAndView，用于设置请求域的数据和设置视图。该方法调用服务层的findAll方法，
	 * 传入page和pageSize参数。服务层调用员工数据访问层的findTotalCounts方法获取数据库的总条数，调用findStaffsByPage并传入参数获取到本次分页查询的显示数据。
	 * 3.findStaffsFuzzy方法为模糊查找员工，该方法接收参数name，该方法返回ModelAndView，调用服务层的findByFuzzy方法并输入参数name，findByFuzzy方法返回一个员工集合。服务层的findByFuzzy调用
	 * 数据访问层的findStaffsByFuzzy(name)方法，用于获取到根据name模糊查询出的员工集合。
	 * 4.addUser方法为添加员工，该方法返回一个Message对象，用于给前端反馈信息，该方法接收参数User。该方法调用服务层的addStaff(user)方法，用于获取返回值Message对象。服务层中，先对上传参数进行简单判断，
	 * 如果参数都为空字符串，则反馈信息；否则，就根据上传参数中的tel去数据库查找是否有相同手机号的用户，如果有则反馈信息；否则就为用户设置信息，并设置用户的权限为普通用户即"user:common"，然后
	 * 调用数据访问层的addUser(user)方法，添加员工。
	 * 5.updateUser为更新员工，该方法返回一个Message对象，用于给前端反馈信息，该方法接收参数User。该方法调用服务层的updateStaff(user)方法,用于获取返回值Message对象。服务层中，先根据参数User的id
	 * 去数据中查找相应用户，如果查找结果为空，就反馈信息；否则，就去验证修改后的用户电话号码是否在数据库中唯一，如果不唯一，就反馈信息；否则调用数据访问层的updateUser(u)方法，更新用户信息。
	 * 6.resetUserPwd为重置员工密码，该方法返回一个Message对象，用于给前端反馈信息，该方法接收参数id。该方法调用服务层的resetStaffPwd(id)方法,用于获取返回值Message对象。在服务层中，先依据参数id去数据库
	 * 中查找相应用户，如果查找结果为空，就反馈信息；否则，将用户的密码重置为"123456"，调用数据访问层的updateUser(user)进行更新。
	 * 6.deleteStaff为删除员工，该方法返回一个Message对象，用于给前端反馈信息，该方法接收一个参数id，该方法调用服务层的deleteStaff(id)方法，用于获取返回值Message对象。服务层中，先根据传入的id值，去
	 * 数据库中查找相应对象，如果对象不存在，就反馈信息；如果对象存在，就根据用户id去查找和用户相关的打卡信息、请假信息和通知信息，并删除这些和用户相关的信息，然后调用数据访问层中的delete(user)
	 * 方法，删除员工。
	 * 
	 * */

	@Resource
	private StaffsService staffsService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView findAllStaffs(int page, int pageSize) {
		ModelAndView mv = new ModelAndView();
		Message message = staffsService.findAll(page, pageSize);
		mv.addObject("data", message);
		mv.setViewName("staffs");

		return mv;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Message addUser(User user) {
		Message msg = new Message();
		try {
			msg = staffsService.addStaff(user);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("操作异常！");
			return msg;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/pwd", method = RequestMethod.PUT)
	public Message resetUserPwd(String id) {
		Message msg = new Message();
		try {
			msg = staffsService.resetStaffPwd(id);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("操作异常！");
			return msg;
		}
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public Message updateUser(User user) {
		Message msg = new Message();
		try {
			msg = staffsService.updateStaff(user);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("操作异常！");
			return msg;
		}
	}

	@RequestMapping(value = "/fuzzy", method = RequestMethod.GET)
	public ModelAndView findStaffsFuzzy(String name) {
		ModelAndView mv = new ModelAndView();
		Message message = staffsService.findByFuzzy(name);
		mv.addObject("data", message);
		mv.setViewName("staffs");

		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Message deleteStaff(@PathVariable("id") String id) {
		Message msg = new Message();
		try {
			msg = staffsService.deleteStaff(id);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("操作异常！");
			return msg;
		}
	}

}
