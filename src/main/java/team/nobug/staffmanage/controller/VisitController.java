package team.nobug.staffmanage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ����ҳ����Ʋ�
 * 
 * @author ��ï��
 *
 */
@Controller
public class VisitController {

	@RequestMapping(value = "/manage", method = { RequestMethod.GET })
	public String visitManage() {
		return "manage";
	}

	@RequestMapping(value = "/personal", method = { RequestMethod.GET })
	public String visitPersonal() {
		return "personal";
	}

	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String visitLogin() {
		return "login";
	}

	@RequestMapping(value = "/employee", method = { RequestMethod.GET })
	public String visitEmployee() {
		return "employee";
	}
	
}
