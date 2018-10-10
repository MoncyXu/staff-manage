package team.nobug.staffmanage.dao;

import java.util.List;

import team.nobug.staffmanage.pojo.Page;
import team.nobug.staffmanage.pojo.User;

/**
 * 用户数据访问接口
 * @author 徐茂鑫
 *
 */
public interface UserDao {

	User checkLogin(String tel, String pwd);

	User findById(String id);

	void updateUser(User user);

	List<User> findStaffsByPage(Page p);

	List<User> findAllStaffs();

	User findByTel(User user);

	void addUser(User user);

	User uniqueTel(String id, String tel);

	Long findTotalCounts();

	List<User> findStaffsByFuzzy(String name);

	void delete(User user);

}
