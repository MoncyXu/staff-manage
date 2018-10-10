package team.nobug.staffmanage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.Vacate;
import team.nobug.staffmanage.service.VacateService;

/**
 * 请假控制层
 * 
 * @author 徐茂鑫
 *
 */
@Controller
@RequestMapping("/vacates")
public class VacateController {
	
	/*
	 * 1.给本类加Controller注解和RequestMapping注解。并注入请假的服务层。
	 * 2.findAllVacates方法为查找所有请假的方法，该方法接收两个参数，一个page代表当前页，一个pageSize表示每一页展示的条数，该方法返回ModelAndView，用于设置请求域的数据和设置视图。该方法调用服务层的findAll方法，
	 * 传入page和pageSize参数。服务层调用请假数据访问层的findTotalCounts方法获取数据库的总条数，调用findNoticesByPage并传入参数获取到本次分页查询的显示数据，再调用User数据访问层的findAllStaffs方法来获取
	 * 所有的员工数据，用于添加请假记录的时候可以选择用户。
	 * 3.findVacatesFuzzy方法为模糊查询请假信息的方法，该方法接收参数name，该方法返回ModelAndView，调用服务层的findByFuzzy方法并输入参数name，findByFuzzy方法返回一个请假信息集合。服务层的findByFuzzy
	 * 调用数据访问层的findVacatesByFuzzy(name)方法，用于获取到根据name模糊查询出的请假信息集合。
	 * 4.addVacate方法为添加请假的方法，该方法返回一个Message对象，用于给前端反馈信息，该方法接收两个参数，第一个是请假对象，第二个是员工的id字符串。该方法调用服务层的addVacate(vacate, uId)方法，用于获取
	 * 返回值Message对象。服务器层中，先判断用户的id是否为空字符串，如果是，反馈信息；否则，依据id去数据库查询用户信息，如果用户不存在，反馈信息；否则，填充请假对象并调用数据访问层的add(vacate)方法，保存该对象。
	 * 5.vacatePermission为批准请假方法，该方法返回一个Message对象，用于给前端反馈信息，该方法接收参数用户id。该方法调用服务层的changeState(id, 1)方法,用于获取返回值Message对象。服务层中，先依据参数
	 * id去查找数据中是否存在该对象，如果不存在就返回信息；如果存在，就设置对象的approve属性为1，然后调用数据访问层的update(vacate)方法，更新对象。
	 * 6.vacateDecline为批准请假方法，该方法返回一个Message对象，用于给前端反馈信息，该方法接收参数用户id。该方法调用服务层的changeState(id, -1)方法,用于获取返回值Message对象。服务层中，先依据参数
	 * id去查找数据中是否存在该对象，如果不存在就返回信息；如果存在，就设置对象的approve属性为-1，然后调用数据访问层的update(vacate)方法，更新对象。
	 * 7.vacateCountermand为批准请假方法，该方法返回一个Message对象，用于给前端反馈信息，该方法接收参数用户id。该方法调用服务层的changeState(id, 0)方法,用于获取返回值Message对象。服务层中，先依据参数
	 * id去查找数据中是否存在该对象，如果不存在就返回信息；如果存在，就设置对象的approve属性为0，然后调用数据访问层的update(vacate)方法，更新对象。
	 * 
	 * */

	@Resource
	private VacateService vService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView findAllVacates(int page, int pageSize) {
		ModelAndView mv = new ModelAndView();
		Message message = vService.findAll(page, pageSize);
		mv.addObject("data", message);
		mv.setViewName("vacates");

		return mv;
	}

	@RequestMapping(value = "/fuzzy", method = RequestMethod.GET)
	public ModelAndView findVacatesFuzzy(String name) {
		ModelAndView mv = new ModelAndView();
		Message message = vService.findByFuzzy(name);
		mv.addObject("data", message);
		mv.setViewName("vacates");

		return mv;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Message addVacate(Vacate vacate, String uId) {
		Message msg = new Message();
		try {
			msg = vService.addVacate(vacate, uId);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("操作异常");
			return msg;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/permission/{id}", method = RequestMethod.PUT)
	public Message vacatePermission(@PathVariable("id") String id) {
		Message msg = new Message();
		try {
			msg = vService.changeState(id, 1);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			return msg;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/decline/{id}", method = RequestMethod.PUT)
	public Message vacateDecline(@PathVariable("id") String id) {
		Message msg = new Message();
		try {
			msg = vService.changeState(id, -1);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			return msg;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/countermand/{id}", method = RequestMethod.PUT)
	public Message vacateCountermand(@PathVariable("id") String id) {
		Message msg = new Message();
		try {
			msg = vService.changeState(id, 0);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			return msg;
		}
	}

}
