package team.nobug.staffmanage.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import team.nobug.staffmanage.dao.UserDao;
import team.nobug.staffmanage.pojo.Page;
import team.nobug.staffmanage.pojo.User;

/**
 * 用户数据访问层
 * 
 * @author 徐茂鑫
 *
 */
@Repository
public class UserDaoImpl implements UserDao {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public User checkLogin(String tel, String pwd) {
		String hql = "from User u where u.tel = :tel and u.pwd = :pwd";

		return sessionFactory.getCurrentSession().createQuery(hql, User.class).setParameter("tel", tel)
				.setParameter("pwd", pwd).uniqueResult();
	}

	@Override
	public User findById(String id) {
		String hql = "from User u where u.id = :id";
		return sessionFactory.getCurrentSession().createQuery(hql, User.class).setParameter("id", id).uniqueResult();
	}

	@Override
	public void updateUser(User user) {
		sessionFactory.getCurrentSession().merge(user);
	}

	@Override
	public List<User> findStaffsByPage(Page p) {
		String hql = "from User u where u.permission.name != :name order by u.time desc";

		return sessionFactory.getCurrentSession().createQuery(hql, User.class).setParameter("name", "user:admin")
				.setFirstResult((p.getCurrentPage() - 1) * p.getPageSize())
				.setMaxResults(p.getCurrentPage() * p.getPageSize()).getResultList();
	}

	@Override
	public List<User> findAllStaffs() {
		String hql = "from User u where u.permission.name != :name order by u.time desc";

		return sessionFactory.getCurrentSession().createQuery(hql, User.class).setParameter("name", "user:admin")
				.getResultList();
	}

	@Override
	public User findByTel(User user) {
		String hql = "from User u where u.tel = :tel";
		return sessionFactory.getCurrentSession().createQuery(hql, User.class).setParameter("tel", user.getTel())
				.uniqueResult();
	}

	@Override
	public void addUser(User user) {
		sessionFactory.getCurrentSession().persist(user);
	}

	@Override
	public User uniqueTel(String id, String tel) {
		String hql = "from User u where u.tel = :tel and u.id != :id";
		return sessionFactory.getCurrentSession().createQuery(hql, User.class).setParameter("tel", tel)
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public Long findTotalCounts() {
		String hql = "select COUNT(*) from User";
		return sessionFactory.getCurrentSession().createQuery(hql, Long.class).uniqueResult();
	}

	@Override
	public List<User> findStaffsByFuzzy(String name) {
		String hql = "from User u where u.name like :name";
		return sessionFactory.getCurrentSession().createQuery(hql, User.class).setParameter("name", "%" + name + "%")
				.getResultList();
	}

	@Override
	public void delete(User user) {
		sessionFactory.getCurrentSession().delete(user);		
	}

}
