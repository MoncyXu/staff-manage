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
 * �û�����ʵ��
 * 
 * @author ��ï��
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
			msg.setMsg("��֤ʧ��");
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
			msg.setMsg("�޸�ʧ�ܣ���У���������룡");
			return msg;
		}

		UserDto u = (UserDto) session.getAttribute("user");

		User user1 = userDao.findById(u.getId());
		if (user1 != null) {
			// ���ֵ�Ƿ����ı�
			if (user1.getName().equals(user.getName()) && user1.getTel().equals(user.getTel())
					&& user1.getPosition().equals(user.getPosition())) {
				msg.setMsg("����û���޸���Ϣ��");
				return msg;
			}

			// �����µ��ֻ����Ƿ�������ʹ��
			User user2 = userDao.uniqueTel(user.getId(), user.getTel());
			if (user2 == null) {
				// ���ı���ְʱ��
				user1.setName(user.getName());
				user1.setTel(user.getTel());
				user1.setPosition(user.getPosition());
				userDao.updateUser(user1);
				msg.setMsg("�޸ĳɹ�");
			} else {
				msg.setMsg("���ֻ����ѹ�������Ա��������ϸ��飡");
			}
		} else {
			msg.setMsg("�޸Ķ��󲻴��ڣ���ˢ�£�");
		}

		return msg;
	}

	@Override
	public Message changePwd(String pwd, String newpwd, String repwd, HttpSession session) {
		Message msg = new Message();
		// �ж���������ظ�����
		if (!newpwd.equals(repwd)) {
			msg.setMsg("ȷ������������벻һ�£�");
			return msg;
		}

		// ��session�л�ȡ��ǰ�û�
		UserDto user = (UserDto) session.getAttribute("user");
		if (user == null) {
			msg.setMsg("�û���Ϣ��ʧ����ˢ�£�");
		} else {
			User u = userDao.findById(user.getId());
			if (u != null) {
				if (EncryptUtils.encryptBySHA1(pwd).equals(u.getPwd())) {
					u.setPwd(EncryptUtils.encryptBySHA1(newpwd));
					userDao.updateUser(u);
					msg.setMsg("success");
				} else {
					msg.setMsg("��������֤ʧ�ܣ�");
				}
			} else {
				msg.setMsg("��ǰ�û������ڣ���ˢ�£�");
			}
		}

		return msg;
	}

}
