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
 * �򿨿��Ʋ�
 * 
 * @author ��ï��
 *
 */
@Controller
@RequestMapping("/punchs")
public class PunchController {
	
	/*
	 * 1.�������Controllerע���RequestMappingע�⡣��ע��򿨵ķ���㡣
	 * 2.findAllPunchsΪ�������д���Ϣ�������÷�����������������һ��page����ǰҳ��һ��pageSize��ʾÿһҳչʾ���������÷�������ModelAndView��������������������ݺ�������ͼ���÷������÷�����
	 * findAll(page, pageSize)�������������ô����ݷ��ʲ��findTotalCounts������ȡ���ݿ��������������findStaffsByPage(p)�����������ȡ�����η�ҳ��ѯ����ʾ���ݣ�
	 * �ٵ���User���ݷ��ʲ��findAllStaffs��������ȡ���е�Ա�����ݣ�������Ӵ���Ϣ��ʱ�����ѡ���û���
	 * 3.findPunchsFuzzy����Ϊģ����ѯ����Ϣ�������÷������ղ���name���÷�������ModelAndView�����÷�����findByFuzzy�������������name��findByFuzzy��������һ������Ϣ���ϡ�������findByFuzzy����
	 * ���ݷ��ʲ��findPunchsByFuzzy(name)���������ڻ�ȡ������nameģ����ѯ���Ĵ���Ϣ���ϡ�
	 * 4.addPunchΪ��Ӵ򿨼�¼�������÷�������һ��Message�������ڸ�ǰ�˷�����Ϣ���÷�������������������һ���Ǵ򿨶��󣬵ڶ�����Ա����id���÷������÷�����addPunch(punch, uId)���������ڻ�ȡ
	 * ����ֵMessage���󡣷�����У����жϵ���ʱ���Ƿ�С�����ʱ�䣬������Ǿͷ�����Ϣ������ǣ����ж�ɽ�ϴ����û�id�Ƿ�Ϊ�գ����Ϊ�վͷ�����Ϣ�������Ϊ�գ��͸���id��ȥ���ݿ����ҵ�
	 * User�����ж�User�Ƿ�Ϊ�գ����Ϊ�գ��ͷ�����Ϣ�����򣬾����ô򿨵���Ϣ��Ȼ��������ݷ��ʲ��addPunch(punch)��������µĴ򿨼�¼��
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
			msg.setMsg("�����쳣��");
			return msg;
		}
	}

}
