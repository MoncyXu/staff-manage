package team.nobug.staffmanage.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import team.nobug.staffmanage.dao.NoticeUserDao;
import team.nobug.staffmanage.pojo.NoticeUser;

/**
 * 通知和用户中间表数据访问层实现
 * 
 * @author 徐茂鑫
 *
 */
@Repository
public class NoticeUserDaoImpl implements NoticeUserDao {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public List<NoticeUser> findByUserId(String id) {
		String hql = "from NoticeUser nu where nu.user.id = :uid";

		return sessionFactory.getCurrentSession().createQuery(hql, NoticeUser.class).setParameter("uid", id)
				.getResultList();
	}

	@Override
	public void add(NoticeUser nu) {
		sessionFactory.getCurrentSession().persist(nu);
	}

	@Override
	public NoticeUser findByNoticeAndUser(String id, String uId) {
		String hql = "from NoticeUser nu where nu.notice.id = :nid and nu.user.id = :uid";
		return sessionFactory.getCurrentSession().createQuery(hql, NoticeUser.class).setParameter("nid", id)
				.setParameter("uid", uId).uniqueResult();
	}

	@Override
	public void delete(NoticeUser noticeUser) {
		sessionFactory.getCurrentSession().delete(noticeUser);
	}

}
