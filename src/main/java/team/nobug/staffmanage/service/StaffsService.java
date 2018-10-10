package team.nobug.staffmanage.service;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.User;

/**
 * staffs服务接口
 * 
 * @author 徐茂鑫
 *
 */
public interface StaffsService {

	Message findAll(int page, int pageSize);

	Message addStaff(User user);

	Message resetStaffPwd(String id);

	Message updateStaff(User user);

	Message findByFuzzy(String name);

	Message deleteStaff(String id);

}
