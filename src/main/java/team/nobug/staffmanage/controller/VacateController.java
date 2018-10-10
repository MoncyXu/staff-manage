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
 * ��ٿ��Ʋ�
 * 
 * @author ��ï��
 *
 */
@Controller
@RequestMapping("/vacates")
public class VacateController {
	
	/*
	 * 1.�������Controllerע���RequestMappingע�⡣��ע����ٵķ���㡣
	 * 2.findAllVacates����Ϊ����������ٵķ������÷�����������������һ��page����ǰҳ��һ��pageSize��ʾÿһҳչʾ���������÷�������ModelAndView��������������������ݺ�������ͼ���÷������÷�����findAll������
	 * ����page��pageSize��������������������ݷ��ʲ��findTotalCounts������ȡ���ݿ��������������findNoticesByPage�����������ȡ�����η�ҳ��ѯ����ʾ���ݣ��ٵ���User���ݷ��ʲ��findAllStaffs��������ȡ
	 * ���е�Ա�����ݣ����������ټ�¼��ʱ�����ѡ���û���
	 * 3.findVacatesFuzzy����Ϊģ����ѯ�����Ϣ�ķ������÷������ղ���name���÷�������ModelAndView�����÷�����findByFuzzy�������������name��findByFuzzy��������һ�������Ϣ���ϡ�������findByFuzzy
	 * �������ݷ��ʲ��findVacatesByFuzzy(name)���������ڻ�ȡ������nameģ����ѯ���������Ϣ���ϡ�
	 * 4.addVacate����Ϊ�����ٵķ������÷�������һ��Message�������ڸ�ǰ�˷�����Ϣ���÷�������������������һ������ٶ��󣬵ڶ�����Ա����id�ַ������÷������÷�����addVacate(vacate, uId)���������ڻ�ȡ
	 * ����ֵMessage���󡣷��������У����ж��û���id�Ƿ�Ϊ���ַ���������ǣ�������Ϣ����������idȥ���ݿ��ѯ�û���Ϣ������û������ڣ�������Ϣ�����������ٶ��󲢵������ݷ��ʲ��add(vacate)����������ö���
	 * 5.vacatePermissionΪ��׼��ٷ������÷�������һ��Message�������ڸ�ǰ�˷�����Ϣ���÷������ղ����û�id���÷������÷�����changeState(id, 1)����,���ڻ�ȡ����ֵMessage���󡣷�����У������ݲ���
	 * idȥ�����������Ƿ���ڸö�����������ھͷ�����Ϣ��������ڣ������ö����approve����Ϊ1��Ȼ��������ݷ��ʲ��update(vacate)���������¶���
	 * 6.vacateDeclineΪ��׼��ٷ������÷�������һ��Message�������ڸ�ǰ�˷�����Ϣ���÷������ղ����û�id���÷������÷�����changeState(id, -1)����,���ڻ�ȡ����ֵMessage���󡣷�����У������ݲ���
	 * idȥ�����������Ƿ���ڸö�����������ھͷ�����Ϣ��������ڣ������ö����approve����Ϊ-1��Ȼ��������ݷ��ʲ��update(vacate)���������¶���
	 * 7.vacateCountermandΪ��׼��ٷ������÷�������һ��Message�������ڸ�ǰ�˷�����Ϣ���÷������ղ����û�id���÷������÷�����changeState(id, 0)����,���ڻ�ȡ����ֵMessage���󡣷�����У������ݲ���
	 * idȥ�����������Ƿ���ڸö�����������ھͷ�����Ϣ��������ڣ������ö����approve����Ϊ0��Ȼ��������ݷ��ʲ��update(vacate)���������¶���
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
			msg.setMsg("�����쳣");
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
