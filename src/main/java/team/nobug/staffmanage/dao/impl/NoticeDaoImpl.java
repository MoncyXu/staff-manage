package team.nobug.staffmanage.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import team.nobug.staffmanage.dao.NoticeDao;
import team.nobug.staffmanage.pojo.Notice;
import team.nobug.staffmanage.pojo.Page;

/**
 * 通知数据访问层实现
 * 
 * @author 徐茂鑫
 *
 */
@Repository
public class NoticeDaoImpl implements NoticeDao {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public Long findTotalCounts() {
		String hql = "select count(*) from Notice";
		return sessionFactory.getCurrentSession().createQuery(hql, Long.class).uniqueResult();
	}

	@Override
	public List<Notice> findNoticesByPage(Page p) {
		String hql = "from Notice n order by n.time desc";
		return sessionFactory.getCurrentSession().createQuery(hql, Notice.class)
				.setFirstResult((p.getCurrentPage() - 1) * p.getPageSize())
				.setMaxResults(p.getCurrentPage() * p.getPageSize()).getResultList();
	}

	@Override
	public List<Notice> findNoticesByFuzzy(String title) {
		String hql = "from Notice n where n.title like :title";
		return sessionFactory.getCurrentSession().createQuery(hql, Notice.class)
				.setParameter("title", "%" + title + "%").getResultList();
	}

	@Override
	public void addNotice(Notice notice) {
		sessionFactory.getCurrentSession().persist(notice);
	}

	@Override
	public Notice findById(String id) {
		String hql = "from Notice n where n.id = :id";
		return sessionFactory.getCurrentSession().createQuery(hql, Notice.class).setParameter("id", id).uniqueResult();
	}

	@Override
	public void update(Notice notice) {
		sessionFactory.getCurrentSession().merge(notice);
	}

	@Override
	public void delete(Notice notice) {
		sessionFactory.getCurrentSession().delete(notice);
	}

}
