package team.nobug.staffmanage.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import team.nobug.staffmanage.dao.VacateDao;
import team.nobug.staffmanage.pojo.Page;
import team.nobug.staffmanage.pojo.Vacate;

/**
 * 请假数据访问实现
 * 
 * @author 徐茂鑫
 *
 */
@Repository
public class VacateDaoImpl implements VacateDao {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public Long findTotalCounts() {
		String hql = "select COUNT(*) from Vacate";
		return sessionFactory.getCurrentSession().createQuery(hql, Long.class).uniqueResult();
	}

	@Override
	public List<Vacate> findVacatesByPage(Page p) {
		String hql = "from Vacate v order by v.time desc";

		return sessionFactory.getCurrentSession().createQuery(hql, Vacate.class)
				.setFirstResult((p.getCurrentPage() - 1) * p.getPageSize())
				.setMaxResults(p.getCurrentPage() * p.getPageSize()).getResultList();
	}

	@Override
	public List<Vacate> findVacatesByFuzzy(String name) {
		String hql = "from Vacate v where v.user.name like :name";
		return sessionFactory.getCurrentSession().createQuery(hql, Vacate.class).setParameter("name", "%" + name + "%")
				.getResultList();
	}

	@Override
	public void add(Vacate vacate) {
		sessionFactory.getCurrentSession().persist(vacate);
	}

	@Override
	public Vacate findById(String id) {
		String hql = "from Vacate v where v.id = :id";
		return sessionFactory.getCurrentSession().createQuery(hql, Vacate.class).setParameter("id", id).uniqueResult();
	}

	@Override
	public void update(Vacate vacate) {
		sessionFactory.getCurrentSession().merge(vacate);
	}

	@Override
	public List<Vacate> findVacatesByUser(String id) {
		String hql = "from Vacate v where v.user.id = :uid";
		return sessionFactory.getCurrentSession().createQuery(hql, Vacate.class).setParameter("uid", id)
				.getResultList();
	}

	@Override
	public void delete(Vacate vacate) {
		sessionFactory.getCurrentSession().delete(vacate);
	}

}
