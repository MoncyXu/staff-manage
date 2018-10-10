package team.nobug.staffmanage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.Punch;
import team.nobug.staffmanage.service.PunchService;

/**
 * 打卡控制层
 * 
 * @author 徐茂鑫
 *
 */
@Controller
@RequestMapping("/punchs")
public class PunchController {
	
	/*
	 * 1.给本类加Controller注解和RequestMapping注解。并注入打卡的服务层。
	 * 2.findAllPunchs为查找所有打卡信息方法，该方法接收两个参数，一个page代表当前页，一个pageSize表示每一页展示的条数，该方法返回ModelAndView，用于设置请求域的数据和设置视图。该方法调用服务层的
	 * findAll(page, pageSize)方法，服务层调用打卡数据访问层的findTotalCounts方法获取数据库的总条数，调用findStaffsByPage(p)并传入参数获取到本次分页查询的显示数据，
	 * 再调用User数据访问层的findAllStaffs方法来获取所有的员工数据，用于添加打卡信息的时候可以选择用户。
	 * 3.findPunchsFuzzy方法为模糊查询打卡信息方法，该方法接收参数name，该方法返回ModelAndView，调用服务层的findByFuzzy方法并输入参数name，findByFuzzy方法返回一个打卡信息集合。服务层的findByFuzzy调用
	 * 数据访问层的findPunchsByFuzzy(name)方法，用于获取到根据name模糊查询出的打卡信息集合。
	 * 4.addPunch为添加打卡记录方法，该方法返回一个Message对象，用于给前端反馈信息，该方法接收两个参数，第一个是打卡对象，第二个是员工的id。该方法调用服务层的addPunch(punch, uId)方法，用于获取
	 * 返回值Message对象。服务层中，先判断到岗时间是否小于离岗时间，如果不是就反馈信息；如果是，就判断山上传的用户id是否为空，如果为空就反馈信息；如果不为空，就根据id先去数据库中找到
	 * User对象。判断User是否为空，如果为空，就反馈信息；否则，就设置打卡的信息，然后调用数据访问层的addPunch(punch)方法添加新的打卡记录。
	 * 
	 * */

	@Resource
	private PunchService punchService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView findAllPunchs(int page, int pageSize) {
		ModelAndView mv = new ModelAndView();
		Message message = punchService.findAll(page, pageSize);
		mv.addObject("data", message);
		mv.setViewName("punchs");

		return mv;
	}

	@RequestMapping(value = "/fuzzy", method = RequestMethod.GET)
	public ModelAndView findPunchsFuzzy(String name) {
		ModelAndView mv = new ModelAndView();
		Message message = punchService.findByFuzzy(name);
		mv.addObject("data", message);
		mv.setViewName("punchs");

		return mv;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Message addPunch(Punch punch, String uId) {
		Message msg = new Message();
		try {
			msg = punchService.addPunch(punch, uId);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("操作异常！");
			return msg;
		}
	}

}
