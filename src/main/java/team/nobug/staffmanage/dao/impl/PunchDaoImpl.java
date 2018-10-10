package team.nobug.staffmanage.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import team.nobug.staffmanage.dao.PunchDao;
import team.nobug.staffmanage.pojo.Page;
import team.nobug.staffmanage.pojo.Punch;

/**
 * 打卡数据访问层实现
 * 
 * @author 徐茂鑫
 *
 */
@Repository
public class PunchDaoImpl implements PunchDao {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public List<Punch> findStaffsByPage(Page p) {
		String hql = "from Punch p order by p.time desc";
		return sessionFactory.getCurrentSession().createQuery(hql, Punch.class)
				.setFirstResult((p.getCurrentPage() - 1) * p.getPageSize())
				.setMaxResults(p.getCurrentPage() * p.getPageSize()).getResultList();
	}

	@Override
	public Long findTotalCounts() {
		String hql = "select COUNT(*) from Punch";
		return sessionFactory.getCurrentSession().createQuery(hql, Long.class).uniqueResult();
	}

	@Override
	public List<Punch> findPunchsByFuzzy(String name) {
		String hql = "from Punch p where p.user.name like :name";
		return sessionFactory.getCurrentSession().createQuery(hql, Punch.class).setParameter("name", "%" + name + "%")
				.getResultList();
	}

	@Override
	public void addPunch(Punch punch) {
		sessionFactory.getCurrentSession().persist(punch);
	}

	@Override
	public List<Punch> findPunchsByUser(String uid) {
		String hql = "from Punch p where p.user.id = :uid";

		return sessionFactory.getCurrentSession().createQuery(hql, Punch.class).setParameter("uid", uid)
				.getResultList();
	}

	@Override
	public void deletePunch(Punch punch) {
		sessionFactory.getCurrentSession().delete(punch);
	}

}
