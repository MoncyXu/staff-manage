package team.nobug.staffmanage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import team.nobug.staffmanage.dao.NoticeUserDao;
import team.nobug.staffmanage.dao.PermissionDao;
import team.nobug.staffmanage.dao.PunchDao;
import team.nobug.staffmanage.dao.UserDao;
import team.nobug.staffmanage.dao.VacateDao;
import team.nobug.staffmanage.dto.UserDto;
import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.NoticeUser;
import team.nobug.staffmanage.pojo.Page;
import team.nobug.staffmanage.pojo.Permission;
import team.nobug.staffmanage.pojo.Punch;
import team.nobug.staffmanage.pojo.User;
import team.nobug.staffmanage.pojo.Vacate;
import team.nobug.staffmanage.service.StaffsService;
import team.nobug.staffmanage.utils.EncryptUtils;

/**
 * staffs服务实现
 * 
 * @author 徐茂鑫
 *
 */
@Service
@Transactional
public class StaffsServiceImpl implements StaffsService {

	@Resource
	private UserDao userDao;

	@Resource
	private PermissionDao permissionDao;
	
	@Resource
	private PunchDao punchDao;
	
	@Resource
	private VacateDao vacateDao;
	
	@Resource
	private NoticeUserDao nuDao;

	@Override
	public Message findAll(int page, int pageSize) {
		Message msg = new Message();
		// 创建分页实体
		Page p = new Page(page, pageSize);
		
		// 查询总条数
		int total = userDao.findTotalCounts().intValue();
		p.setTotalPages(total);
		msg.setPage(p);

		// 查询员工
		List<User> staffList = userDao.findStaffsByPage(p);
		msg.setStaffs(UserDto.change(staffList));

		return msg;
	}

	@Override
	public Message addStaff(User user) {
		Message msg = new Message();
		// 判断前端传入的值
		if (user.getName().equals("") || user.getPwd().equals("") || user.getPosition().equals("")
				|| user.getTel().equals("")) {
			msg.setMsg("添加失败，请校验您的输入值！");
			return msg;
		}
		
		// 查找是否有相同手机号的员工
		User u = userDao.findByTel(user);
		if (u == null) {
			// 设置属性
			String id = UUID.randomUUID().toString().substring(0, 5);
			Date time = new Date();
			user.setId(id);
			// 入职时间
			user.setTime(time);
			// 查找员工的权限
			Permission permission = permissionDao.findByName("user:common");
			user.setPermission(permission);
			// 添加员工
			userDao.addUser(user);
			UserDto userDto = new UserDto(user);
			// 设置返回前端展示的实体
			msg.setUser(userDto);
			msg.setMsg("添加成功");
		} else {
			msg.setMsg("该手机号码已关联其他用户，请仔细检查填写的手机号码！");
		}

		return msg;
	}

	@Override
	public Message resetStaffPwd(String id) {
		Message msg = new Message();
		User user = userDao.findById(id);

		if (user != null) {
			user.setPwd(EncryptUtils.encryptBySHA1("123456"));
			userDao.updateUser(user);
			msg.setMsg("密码重置成功！");
		} else {
			msg.setMsg("密码重置失败，该对象不存在！");
		}

		return msg;
	}

	@Override
	public Message updateStaff(User user) {
		Message msg = new Message();
		User u = userDao.findById(user.getId());
		if (u != null) {
			User u1 = userDao.uniqueTel(user.getId(), user.getTel());
			if (u1 == null) {
				// 只可以更新这3个字段
				u.setName(user.getName());
				u.setPosition(user.getPosition());
				u.setTel(user.getTel());
				userDao.updateUser(u);
				msg.setMsg("修改成功");
				UserDto userDto = new UserDto(u);
				msg.setUser(userDto);
			} else {
				msg.setMsg("该手机号已被绑定，请仔细核对手机号！");
			}
		} else {
			msg.setMsg("该对象不存在，请刷新！");
		}

		return msg;
	}

	@Override
	public Message findByFuzzy(String name) {
		Message msg = new Message();
		msg.setStaffs(UserDto.change(userDao.findStaffsByFuzzy(name)));

		return msg;
	}

	@Override
	public Message deleteStaff(String id) {
		Message msg = new Message();
		// 查找要删除对象是否在数据库中存在
		User user = userDao.findById(id);
		if (user != null) {
			// 删除和用户相关的打卡信息
			List<Punch> punchs = punchDao.findPunchsByUser(id);
			for (Punch punch : punchs) {
				punchDao.deletePunch(punch);
			}
			
			// 删除和用户相关的请假信息
			List<Vacate> vacates = vacateDao.findVacatesByUser(id);
			for (Vacate vacate : vacates) {
				vacateDao.delete(vacate);
			}
			
			// 删除和用户相关的通知信息
			List<NoticeUser> noticeUsers = nuDao.findByUserId(id);
			for (NoticeUser noticeUser : noticeUsers) {
				nuDao.delete(noticeUser);
			}
			
			userDao.delete(user);
			msg.setMsg("success");
		}else {
			msg.setMsg("要删除的对象不存在，请刷新！");
		}

		return msg;
	}

}
