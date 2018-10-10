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
 * staffs����ʵ��
 * 
 * @author ��ï��
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
		// ������ҳʵ��
		Page p = new Page(page, pageSize);
		
		// ��ѯ������
		int total = userDao.findTotalCounts().intValue();
		p.setTotalPages(total);
		msg.setPage(p);

		// ��ѯԱ��
		List<User> staffList = userDao.findStaffsByPage(p);
		msg.setStaffs(UserDto.change(staffList));

		return msg;
	}

	@Override
	public Message addStaff(User user) {
		Message msg = new Message();
		// �ж�ǰ�˴����ֵ
		if (user.getName().equals("") || user.getPwd().equals("") || user.getPosition().equals("")
				|| user.getTel().equals("")) {
			msg.setMsg("���ʧ�ܣ���У����������ֵ��");
			return msg;
		}
		
		// �����Ƿ�����ͬ�ֻ��ŵ�Ա��
		User u = userDao.findByTel(user);
		if (u == null) {
			// ��������
			String id = UUID.randomUUID().toString().substring(0, 5);
			Date time = new Date();
			user.setId(id);
			// ��ְʱ��
			user.setTime(time);
			// ����Ա����Ȩ��
			Permission permission = permissionDao.findByName("user:common");
			user.setPermission(permission);
			// ���Ա��
			userDao.addUser(user);
			UserDto userDto = new UserDto(user);
			// ���÷���ǰ��չʾ��ʵ��
			msg.setUser(userDto);
			msg.setMsg("��ӳɹ�");
		} else {
			msg.setMsg("���ֻ������ѹ��������û�������ϸ�����д���ֻ����룡");
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
			msg.setMsg("�������óɹ���");
		} else {
			msg.setMsg("��������ʧ�ܣ��ö��󲻴��ڣ�");
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
				// ֻ���Ը�����3���ֶ�
				u.setName(user.getName());
				u.setPosition(user.getPosition());
				u.setTel(user.getTel());
				userDao.updateUser(u);
				msg.setMsg("�޸ĳɹ�");
				UserDto userDto = new UserDto(u);
				msg.setUser(userDto);
			} else {
				msg.setMsg("���ֻ����ѱ��󶨣�����ϸ�˶��ֻ��ţ�");
			}
		} else {
			msg.setMsg("�ö��󲻴��ڣ���ˢ�£�");
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
		// ����Ҫɾ�������Ƿ������ݿ��д���
		User user = userDao.findById(id);
		if (user != null) {
			// ɾ�����û���صĴ���Ϣ
			List<Punch> punchs = punchDao.findPunchsByUser(id);
			for (Punch punch : punchs) {
				punchDao.deletePunch(punch);
			}
			
			// ɾ�����û���ص������Ϣ
			List<Vacate> vacates = vacateDao.findVacatesByUser(id);
			for (Vacate vacate : vacates) {
				vacateDao.delete(vacate);
			}
			
			// ɾ�����û���ص�֪ͨ��Ϣ
			List<NoticeUser> noticeUsers = nuDao.findByUserId(id);
			for (NoticeUser noticeUser : noticeUsers) {
				nuDao.delete(noticeUser);
			}
			
			userDao.delete(user);
			msg.setMsg("success");
		}else {
			msg.setMsg("Ҫɾ���Ķ��󲻴��ڣ���ˢ�£�");
		}

		return msg;
	}

}
