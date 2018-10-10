package team.nobug.staffmanage.service;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.Notice;

/**
 * ֪ͨ�����ӿ�
 * @author ��ï��
 *
 */
public interface NoticeService {

	Message findAll(int page, int pageSize);

	Message findByFuzzy(String title);

	Message addNotice(Notice notice, String staffs);

	Message updateNotice(Notice notice, String staffs);

	Message deleteNotice(String id);

}
