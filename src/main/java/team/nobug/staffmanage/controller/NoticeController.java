package team.nobug.staffmanage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.Notice;
import team.nobug.staffmanage.service.NoticeService;

/**
 * 通知控制层
 * 
 * @author 徐茂鑫
 *
 */
@Controller
@RequestMapping("notices")
public class NoticeController {
	
	/*
	 * 1.给本类加Controller注解和RequestMapping注解。并注入通知的服务层。
	 * 2.findAllNotices方法为查找所有通知的方法，该方法接收两个参数，一个page代表当前页，一个pageSize表示每一页展示的条数，该方法返回ModelAndView，用于设置请求域的数据和设置视图。该方法调用服务层的findAll方法，
	 * 传入page和pageSize参数。服务层调用通知数据访问层的findTotalCounts方法获取数据库的总条数，调用findNoticesByPage并传入参数获取到本次分页查询的显示数据，再调用User数据访问层的
	 * findAllStaffs方法来获取所有的员工数据，用于添加通知的时候可以选择用户。
	 * 3.findNoticesFuzzy方法为模糊查询通知的方法，该方法接收参数title，该方法返回ModelAndView，调用服务层的findByFuzzy方法并输入参数title，findByFuzzy方法返回一个通知集合。服务层的findByFuzzy调用
	 * 数据访问层的findNoticesByFuzzy(title)方法，用于获取到根据title模糊查询出的通知集合。
	 * 4.addNotice方法为添加通知的方法，该方法返回一个Message对象，用于给前端反馈信息，该方法接收两个参数，第一个是通知对象，第二个是员工们的id字符串。该方法调用服务层的addNotice(notice,staffs)方法，用于获取
	 * 返回值Message对象。服务器层中，先调用数据访问层的addNotice(notice)方法向数据库添加本通知，再判断是否有用户的id传入，没有则通知不关联用户；有，则向中间表NoticeUser追加用户和通知的数据。
	 * 5.updateNotice为更新通知方法，该方法返回一个Message对象，用于给前端反馈信息，该方法接收两个参数，第一个是通知对象，第二个是员工们的id字符串。该方法调用服务层的updateNotice(notice,staffs)方法,用于获取
	 * 返回值Message对象。服务层中，先依据参数notice的id去查找数据中是否存在该对象，如果不存在就返回信息；如果存在，先判断用户是否真的修改了值，如果没有就返回信息，如果修改了，就检查是否上传了用户
	 * 的id，如果上传了就表示要在原有通知用户基础上再增加新用户。通过用户id去数据库中查找对应用户，再通过通知id和用户id去中间表中查找是否通知和用户已经关联，如果没有关联，就在中间表中添加通知和用户的
	 * 关联信息，否则就不添加。最后再调用数据访问层的update(notice)方法更新通知对象并设置反馈信息。
	 * 6.deleteNotice为删除通知方法，该方法返回一个Message对象，用于给前端反馈信息，该方法接收一个参数id，该方法调用服务层的deleteNotice(id)方法，用于获取返回值Message对象。服务层中，先根据传入的id值，去
	 * 数据库中查找相应对象，如果对象不存在，就反馈信息；如果对象存在，就调用数据访问层的delete(notice)方法删除通知并设置提示信息。
	 * 
	 * */

	@Resource
	private NoticeService nService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView findAllNotices(int page, int pageSize) {
		ModelAndView mv = new ModelAndView();
		Message message = nService.findAll(page, pageSize);
		mv.addObject("data", message);
		mv.setViewName("notices");

		return mv;
	}

	@RequestMapping(value = "/fuzzy", method = RequestMethod.GET)
	public ModelAndView findNoticesFuzzy(String title) {
		ModelAndView mv = new ModelAndView();
		Message message = nService.findByFuzzy(title);
		mv.addObject("data", message);
		mv.setViewName("notices");

		return mv;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Message addNotice(Notice notice, String staffs) {
		Message msg = new Message();
		try {
			msg = nService.addNotice(notice, staffs);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("操作异常！");
			return msg;
		}
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public Message updateNotice(Notice notice, String staffs) {
		Message msg = new Message();
		try {
			msg = nService.updateNotice(notice, staffs);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("操作异常！");
			return msg;
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public Message deleteNotice(@PathVariable("id")String id) {
		Message msg = new Message();
		try {
			msg = nService.deleteNotice(id);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("操作异常！");
			return msg;
		}
	}

}
