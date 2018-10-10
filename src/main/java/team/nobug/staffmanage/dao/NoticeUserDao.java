package team.nobug.staffmanage.dao;

import java.util.List;

import team.nobug.staffmanage.pojo.NoticeUser;

/**
 * 通知和用户中间表数据访问接口
 * @author 徐茂鑫
 *
 */
public interface NoticeUserDao {

	List<NoticeUser> findByUserId(String id);

	void add(NoticeUser nu);

	NoticeUser findByNoticeAndUser(String id, String uId);

	void delete(NoticeUser noticeUser);

}
