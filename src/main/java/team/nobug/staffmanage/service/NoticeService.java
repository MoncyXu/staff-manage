package team.nobug.staffmanage.service;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.Notice;

/**
 * 通知服务层接口
 * @author 徐茂鑫
 *
 */
public interface NoticeService {

	Message findAll(int page, int pageSize);

	Message findByFuzzy(String title);

	Message addNotice(Notice notice, String staffs);

	Message updateNotice(Notice notice, String staffs);

	Message deleteNotice(String id);

}
