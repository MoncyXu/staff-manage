package team.nobug.staffmanage.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import team.nobug.staffmanage.dao.PunchDao;
import team.nobug.staffmanage.dao.UserDao;
import team.nobug.staffmanage.dto.PunchDto;
import team.nobug.staffmanage.dto.UserDto;
import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.Page;
import team.nobug.staffmanage.pojo.Punch;
import team.nobug.staffmanage.pojo.User;
import team.nobug.staffmanage.service.PunchService;

/**
 * 打卡服务层实现
 * 
 * @author 徐茂鑫
 *
 */
@Service
@Transactional
public class PunchServiceImpl implements PunchService {

	@Resource
	private PunchDao punchDao;

	@Resource
	private UserDao userDao;

	@Override
	public Message findAll(int page, int pageSize) {
		Message msg = new Message();
		Page p = new Page(page, pageSize);

		int total = punchDao.findTotalCounts().intValue();
		p.setTotalPages(total);
		msg.setPage(p);

		List<Punch> punchList = punchDao.findStaffsByPage(p);
		msg.setPunchs(PunchDto.change(punchList));

		List<User> staffs = userDao.findAllStaffs();
		msg.setStaffs(UserDto.change(staffs));

		return msg;
	}

	@Override
	public Message findByFuzzy(String name) {
		Message msg = new Message();
		msg.setPunchs(PunchDto.change(punchDao.findPunchsByFuzzy(name)));
		List<User> staffs = userDao.findAllStaffs();
		msg.setStaffs(UserDto.change(staffs));

		return msg;
	}

	@Override
	public Message addPunch(Punch punch, String id) {
		Message msg = new Message();
		if (!punch.getDutytime().before(punch.getUndergo())) {
			msg.setMsg("请核对到岗时间和离岗时间！");
			return msg;
		}
		if (!id.equals("")) {
			User user = userDao.findById(id);
			if (user != null) {
				String pId = UUID.randomUUID().toString().substring(0, 5);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date time = sdf.parse(sdf.format(punch.getDutytime()));
					punch.setTime(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				punch.setUser(user);
				punch.setId(pId);
				punchDao.addPunch(punch);
				msg.setMsg("success");
				PunchDto dto = new PunchDto(punch);
				msg.setPunch(dto);
			} else {
				msg.setMsg("该员工不存在，请刷新！");
			}
		} else {
			msg.setMsg("您还没有选择用户！");
		}

		return msg;
	}

}
