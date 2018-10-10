package team.nobug.staffmanage.dao;

import team.nobug.staffmanage.pojo.Permission;

/**
 * 权限数据访问接口
 * 
 * @author 徐茂鑫
 *
 */
public interface PermissionDao {

	Permission findByName(String name);

}
