package team.nobug.staffmanage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import team.nobug.staffmanage.dao.NoticeDao;
import team.nobug.staffmanage.dao.NoticeUserDao;
import team.nobug.staffmanage.dao.UserDao;
import team.nobug.staffmanage.dto.UserDto;
import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.Notice;
import team.nobug.staffmanage.pojo.NoticeUser;
import team.nobug.staffmanage.pojo.Page;
import team.nobug.staffmanage.pojo.User;
import team.nobug.staffmanage.service.NoticeService;

/**
 * ֪ͨ�����ʵ��
 * 
 * @author ��ï��
 *
 */
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

	@Resource
	private NoticeDao nDao;

	@Resource
	private UserDao uDao;

	@Resource
	private NoticeUserDao nuDao;

	@Override
	public Message findAll(int page, int pageSize) {
		Message msg = new Message();
		Page p = new Page(page, pageSize);

		int total = nDao.findTotalCounts().intValue();
		p.setTotalPages(total);
		msg.setPage(p);

		List<Notice> notices = nDao.findNoticesByPage(p);
		msg.setNotices(notices);

		List<User> staffs = uDao.findAllStaffs();
		msg.setStaffs(UserDto.change(staffs));

		return msg;
	}

	@Override
	public Message findByFuzzy(String title) {
		Message msg = new Message();
		msg.setNotices(nDao.findNoticesByFuzzy(title));
		List<User> staffs = uDao.findAllStaffs();
		msg.setStaffs(UserDto.change(staffs));

		return msg;
	}

	@Override
	public Message addNotice(Notice notice, String staffs) {
		Message msg = new Message();
		// ���û���û����򵥶�����֪ͨ
		// ���֪ͨ
		String nId = UUID.randomUUID().toString().substring(0, 5);
		notice.setId(nId);
		notice.setTime(new Date());
		nDao.addNotice(notice);
		// ������û�
		if (staffs != null) {
			// ��ȡ�û�id
			String[] uIds = staffs.split(",");
			for (String id : uIds) {
				// ����id�����û�
				User user = uDao.findById(id);

				// �ж��û��Ƿ�Ϊ�գ���Ϊ�վ����м��������ݣ�Ϊ�վͲ�����
				if (user != null) {
					NoticeUser nu = new NoticeUser();
					String nuId = UUID.randomUUID().toString().substring(0, 5);
					nu.setId(nuId);
					nu.setUser(user);
					nu.setNotice(notice);
					nuDao.add(nu);
				}
			}
		}
		msg.setMsg("success");
		msg.setNotice(notice);

		return msg;
	}

	@Override
	public Message updateNotice(Notice notice, String staffs) {
		Message msg = new Message();

		Notice n = nDao.findById(notice.getId());
		// �ж϶����Ƿ����
		if (n != null) {
			// �ж��Ƿ��޸�ֵ
			if (n.getTitle().equals(notice.getTitle()) && n.getContent().equals(notice.getContent())
					&& staffs == null) {
				msg.setMsg("��֪ͨ��û���޸ģ�");
				return msg;
			}

			// ����޸���֪ͨ���û�
			if (staffs != null) {
				// ��ȡ�û�id
				String[] uIds = staffs.split(",");
				for (String uId : uIds) {
					// �ҵ���Ӧ�û�
					User user = uDao.findById(uId);
					// �ж��Ƿ�����û�������֪ͨ��������͹���������
					if (user != null) {
						NoticeUser nu = nuDao.findByNoticeAndUser(notice.getId(), uId);
						// ��������û�id��֪ͨid�ҵ��˶�Ӧ���м�ʵ�壬���ʾ֪ͨ�����������������û�
						if (nu == null) {
							NoticeUser noticeUser = new NoticeUser();
							noticeUser.setId(UUID.randomUUID().toString().substring(0, 5));
							noticeUser.setNotice(notice);
							noticeUser.setNotice(notice);
							noticeUser.setUser(user);
							nuDao.add(noticeUser);
						}
					}
				}
			}
			// ʱ�䲻��
			notice.setTime(n.getTime());
			nDao.update(notice);
			msg.setNotice(notice);
			msg.setMsg("success");
		} else {
			msg.setMsg("�޸Ķ��󲻴��ڣ���ˢ�£�");
		}

		return msg;
	}

	@Override
	public Message deleteNotice(String id) {
		Message msg = new Message();
		Notice notice = nDao.findById(id);
		if(notice != null) {
			nDao.delete(notice);
			msg.setMsg("success");
		}
		
		return msg;
	}

}
