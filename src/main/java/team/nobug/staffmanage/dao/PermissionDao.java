package team.nobug.staffmanage.dao;

import team.nobug.staffmanage.pojo.Permission;

/**
 * Ȩ�����ݷ��ʽӿ�
 * 
 * @author ��ï��
 *
 */
public interface PermissionDao {

	Permission findByName(String name);

}
