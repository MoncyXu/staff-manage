package team.nobug.staffmanage.dao;

import java.util.List;

import team.nobug.staffmanage.pojo.Notice;
import team.nobug.staffmanage.pojo.Page;

/**
 * 通知数据访问层接口
 * @author 徐茂鑫
 *
 */
public interface NoticeDao {

	Long findTotalCounts();

	List<Notice> findNoticesByPage(Page p);

	List<Notice> findNoticesByFuzzy(String title);

	void addNotice(Notice notice);

	Notice findById(String id);

	void update(Notice notice);

	void delete(Notice notice);

}
