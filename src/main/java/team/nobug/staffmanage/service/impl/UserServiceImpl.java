package team.nobug.staffmanage.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import team.nobug.staffmanage.dao.UserDao;
import team.nobug.staffmanage.dto.UserDto;
import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.User;
import team.nobug.staffmanage.service.UserService;
import team.nobug.staffmanage.utils.EncryptUtils;

/**
 * 用户服务实现
 * 
 * @author 徐茂鑫
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Override
	public Message checkLogin(String tel, String pwd) {
		Message msg = new Message();
		if (tel.equals("") || pwd.equals("")) {
			msg.setMsg("验证失败");
			return msg;
		}

		User user = userDao.checkLogin(tel, EncryptUtils.encryptBySHA1(pwd));
		if (user != null) {
			UserDto userDto = new UserDto(user);
			msg.setUser(userDto);
			msg.setMsg("success");
		} else {
			msg.setMsg("fail");
		}

		return msg;
	}

	@Override
	public Message updateUser(User user, HttpSession session) {
		Message msg = new Message();
		if (user.getName().equals("") || user.getPosition().equals("") || user.getTel().equals("")) {
			msg.setMsg("修改失败，请校验您的输入！");
			return msg;
		}

		UserDto u = (UserDto) session.getAttribute("user");

		User user1 = userDao.findById(u.getId());
		if (user1 != null) {
			// 检测值是否发生改变
			if (user1.getName().equals(user.getName()) && user1.getTel().equals(user.getTel())
					&& user1.getPosition().equals(user.getPosition())) {
				msg.setMsg("您并没有修改信息！");
				return msg;
			}

			// 检查更新的手机号是否被其他人使用
			User user2 = userDao.uniqueTel(user.getId(), user.getTel());
			if (user2 == null) {
				// 不改变入职时间
				user1.setName(user.getName());
				user1.setTel(user.getTel());
				user1.setPosition(user.getPosition());
				userDao.updateUser(user1);
				msg.setMsg("修改成功");
			} else {
				msg.setMsg("该手机号已关联其他员工，请仔细检查！");
			}
		} else {
			msg.setMsg("修改对象不存在，请刷新！");
		}

		return msg;
	}

	@Override
	public Message changePwd(String pwd, String newpwd, String repwd, HttpSession session) {
		Message msg = new Message();
		// 判断新密码和重复密码
		if (!newpwd.equals(repwd)) {
			msg.setMsg("确认密码和新密码不一致！");
			return msg;
		}

		// 从session中获取当前用户
		UserDto user = (UserDto) session.getAttribute("user");
		if (user == null) {
			msg.setMsg("用户信息丢失，请刷新！");
		} else {
			User u = userDao.findById(user.getId());
			if (u != null) {
				if (EncryptUtils.encryptBySHA1(pwd).equals(u.getPwd())) {
					u.setPwd(EncryptUtils.encryptBySHA1(newpwd));
					userDao.updateUser(u);
					msg.setMsg("success");
				} else {
					msg.setMsg("旧密码验证失败！");
				}
			} else {
				msg.setMsg("当前用户不存在，请刷新！");
			}
		}

		return msg;
	}

}
