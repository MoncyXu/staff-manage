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
 * 通知服务层实现
 * 
 * @author 徐茂鑫
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
		// 如果没有用户，则单独插入通知
		// 添加通知
		String nId = UUID.randomUUID().toString().substring(0, 5);
		notice.setId(nId);
		notice.setTime(new Date());
		nDao.addNotice(notice);
		// 如果有用户
		if (staffs != null) {
			// 获取用户id
			String[] uIds = staffs.split(",");
			for (String id : uIds) {
				// 根据id查找用户
				User user = uDao.findById(id);

				// 判断用户是否为空，不为空就向中间表插入数据，为空就不处理
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
		// 判断对象是否存在
		if (n != null) {
			// 判断是否修改值
			if (n.getTitle().equals(notice.getTitle()) && n.getContent().equals(notice.getContent())
					&& staffs == null) {
				msg.setMsg("该通知并没有修改！");
				return msg;
			}

			// 如果修改了通知的用户
			if (staffs != null) {
				// 获取用户id
				String[] uIds = staffs.split(",");
				for (String uId : uIds) {
					// 找到对应用户
					User user = uDao.findById(uId);
					// 判断是否给该用户发过该通知，如果发送过，就跳过
					if (user != null) {
						NoticeUser nu = nuDao.findByNoticeAndUser(notice.getId(), uId);
						// 如果根据用户id和通知id找到了对应的中间实体，则表示通知过，否则就添加新增用户
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
			// 时间不变
			notice.setTime(n.getTime());
			nDao.update(notice);
			msg.setNotice(notice);
			msg.setMsg("success");
		} else {
			msg.setMsg("修改对象不存在，请刷新！");
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
