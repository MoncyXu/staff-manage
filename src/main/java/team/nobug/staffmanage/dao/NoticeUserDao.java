package team.nobug.staffmanage.dao;

import java.util.List;

import team.nobug.staffmanage.pojo.NoticeUser;

/**
 * ֪ͨ���û��м�����ݷ��ʽӿ�
 * @author ��ï��
 *
 */
public interface NoticeUserDao {

	List<NoticeUser> findByUserId(String id);

	void add(NoticeUser nu);

	NoticeUser findByNoticeAndUser(String id, String uId);

	void delete(NoticeUser noticeUser);

}
