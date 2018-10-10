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
 * ֪ͨ���Ʋ�
 * 
 * @author ��ï��
 *
 */
@Controller
@RequestMapping("notices")
public class NoticeController {
	
	/*
	 * 1.�������Controllerע���RequestMappingע�⡣��ע��֪ͨ�ķ���㡣
	 * 2.findAllNotices����Ϊ��������֪ͨ�ķ������÷�����������������һ��page����ǰҳ��һ��pageSize��ʾÿһҳչʾ���������÷�������ModelAndView��������������������ݺ�������ͼ���÷������÷�����findAll������
	 * ����page��pageSize��������������֪ͨ���ݷ��ʲ��findTotalCounts������ȡ���ݿ��������������findNoticesByPage�����������ȡ�����η�ҳ��ѯ����ʾ���ݣ��ٵ���User���ݷ��ʲ��
	 * findAllStaffs��������ȡ���е�Ա�����ݣ��������֪ͨ��ʱ�����ѡ���û���
	 * 3.findNoticesFuzzy����Ϊģ����ѯ֪ͨ�ķ������÷������ղ���title���÷�������ModelAndView�����÷�����findByFuzzy�������������title��findByFuzzy��������һ��֪ͨ���ϡ�������findByFuzzy����
	 * ���ݷ��ʲ��findNoticesByFuzzy(title)���������ڻ�ȡ������titleģ����ѯ����֪ͨ���ϡ�
	 * 4.addNotice����Ϊ���֪ͨ�ķ������÷�������һ��Message�������ڸ�ǰ�˷�����Ϣ���÷�������������������һ����֪ͨ���󣬵ڶ�����Ա���ǵ�id�ַ������÷������÷�����addNotice(notice,staffs)���������ڻ�ȡ
	 * ����ֵMessage���󡣷��������У��ȵ������ݷ��ʲ��addNotice(notice)���������ݿ���ӱ�֪ͨ�����ж��Ƿ����û���id���룬û����֪ͨ�������û����У������м��NoticeUser׷���û���֪ͨ�����ݡ�
	 * 5.updateNoticeΪ����֪ͨ�������÷�������һ��Message�������ڸ�ǰ�˷�����Ϣ���÷�������������������һ����֪ͨ���󣬵ڶ�����Ա���ǵ�id�ַ������÷������÷�����updateNotice(notice,staffs)����,���ڻ�ȡ
	 * ����ֵMessage���󡣷�����У������ݲ���notice��idȥ�����������Ƿ���ڸö�����������ھͷ�����Ϣ��������ڣ����ж��û��Ƿ�����޸���ֵ�����û�оͷ�����Ϣ������޸��ˣ��ͼ���Ƿ��ϴ����û�
	 * ��id������ϴ��˾ͱ�ʾҪ��ԭ��֪ͨ�û����������������û���ͨ���û�idȥ���ݿ��в��Ҷ�Ӧ�û�����ͨ��֪ͨid���û�idȥ�м���в����Ƿ�֪ͨ���û��Ѿ����������û�й����������м�������֪ͨ���û���
	 * ������Ϣ������Ͳ���ӡ�����ٵ������ݷ��ʲ��update(notice)��������֪ͨ�������÷�����Ϣ��
	 * 6.deleteNoticeΪɾ��֪ͨ�������÷�������һ��Message�������ڸ�ǰ�˷�����Ϣ���÷�������һ������id���÷������÷�����deleteNotice(id)���������ڻ�ȡ����ֵMessage���󡣷�����У��ȸ��ݴ����idֵ��ȥ
	 * ���ݿ��в�����Ӧ����������󲻴��ڣ��ͷ�����Ϣ�����������ڣ��͵������ݷ��ʲ��delete(notice)����ɾ��֪ͨ��������ʾ��Ϣ��
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
			msg.setMsg("�����쳣��");
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
			msg.setMsg("�����쳣��");
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
			msg.setMsg("�����쳣��");
			return msg;
		}
	}

}
