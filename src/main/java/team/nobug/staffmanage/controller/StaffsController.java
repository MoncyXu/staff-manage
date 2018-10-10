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
	 * 1.�������Controllerע���RequestMappingע�⡣��ע��Ա���ķ���㡣
	 * 2.findAllStaffs����Ϊ��������Ա�����÷�����������������һ��page����ǰҳ��һ��pageSize��ʾÿһҳչʾ���������÷�������ModelAndView��������������������ݺ�������ͼ���÷������÷�����findAll������
	 * ����page��pageSize��������������Ա�����ݷ��ʲ��findTotalCounts������ȡ���ݿ��������������findStaffsByPage�����������ȡ�����η�ҳ��ѯ����ʾ���ݡ�
	 * 3.findStaffsFuzzy����Ϊģ������Ա�����÷������ղ���name���÷�������ModelAndView�����÷�����findByFuzzy�������������name��findByFuzzy��������һ��Ա�����ϡ�������findByFuzzy����
	 * ���ݷ��ʲ��findStaffsByFuzzy(name)���������ڻ�ȡ������nameģ����ѯ����Ա�����ϡ�
	 * 4.addUser����Ϊ���Ա�����÷�������һ��Message�������ڸ�ǰ�˷�����Ϣ���÷������ղ���User���÷������÷�����addStaff(user)���������ڻ�ȡ����ֵMessage���󡣷�����У��ȶ��ϴ��������м��жϣ�
	 * ���������Ϊ���ַ�����������Ϣ�����򣬾͸����ϴ������е�telȥ���ݿ�����Ƿ�����ͬ�ֻ��ŵ��û��������������Ϣ�������Ϊ�û�������Ϣ���������û���Ȩ��Ϊ��ͨ�û���"user:common"��Ȼ��
	 * �������ݷ��ʲ��addUser(user)���������Ա����
	 * 5.updateUserΪ����Ա�����÷�������һ��Message�������ڸ�ǰ�˷�����Ϣ���÷������ղ���User���÷������÷�����updateStaff(user)����,���ڻ�ȡ����ֵMessage���󡣷�����У��ȸ��ݲ���User��id
	 * ȥ�����в�����Ӧ�û���������ҽ��Ϊ�գ��ͷ�����Ϣ�����򣬾�ȥ��֤�޸ĺ���û��绰�����Ƿ������ݿ���Ψһ�������Ψһ���ͷ�����Ϣ������������ݷ��ʲ��updateUser(u)�����������û���Ϣ��
	 * 6.resetUserPwdΪ����Ա�����룬�÷�������һ��Message�������ڸ�ǰ�˷�����Ϣ���÷������ղ���id���÷������÷�����resetStaffPwd(id)����,���ڻ�ȡ����ֵMessage�����ڷ�����У������ݲ���idȥ���ݿ�
	 * �в�����Ӧ�û���������ҽ��Ϊ�գ��ͷ�����Ϣ�����򣬽��û�����������Ϊ"123456"���������ݷ��ʲ��updateUser(user)���и��¡�
	 * 6.deleteStaffΪɾ��Ա�����÷�������һ��Message�������ڸ�ǰ�˷�����Ϣ���÷�������һ������id���÷������÷�����deleteStaff(id)���������ڻ�ȡ����ֵMessage���󡣷�����У��ȸ��ݴ����idֵ��ȥ
	 * ���ݿ��в�����Ӧ����������󲻴��ڣ��ͷ�����Ϣ�����������ڣ��͸����û�idȥ���Һ��û���صĴ���Ϣ�������Ϣ��֪ͨ��Ϣ����ɾ����Щ���û���ص���Ϣ��Ȼ��������ݷ��ʲ��е�delete(user)
	 * ������ɾ��Ա����
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
			msg.setMsg("�����쳣��");
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
			msg.setMsg("�����쳣��");
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
			msg.setMsg("�����쳣��");
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
			msg.setMsg("�����쳣��");
			return msg;
		}
	}

}
