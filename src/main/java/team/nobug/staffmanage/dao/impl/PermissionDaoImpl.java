package team.nobug.staffmanage.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import team.nobug.staffmanage.dao.PermissionDao;
import team.nobug.staffmanage.pojo.Permission;

/**
 * Ȩ�����ݷ���ʵ��
 * @author ��ï��
 *
 */
@Repository
public class PermissionDaoImpl implements PermissionDao {
	
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public Permission findByName(String name) {
		String hql = "from Permission p where p.name = :name";
		return sessionFactory.getCurrentSession().createQuery(hql, Permission.class).setParameter("name", name).uniqueResult();
	}

}
