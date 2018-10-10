package team.nobug.staffmanage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import team.nobug.staffmanage.dao.UserDao;
import team.nobug.staffmanage.dao.VacateDao;
import team.nobug.staffmanage.dto.UserDto;
import team.nobug.staffmanage.dto.VacateDto;
import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.Page;
import team.nobug.staffmanage.pojo.User;
import team.nobug.staffmanage.pojo.Vacate;
import team.nobug.staffmanage.service.VacateService;

/**
 * 请假服务层实现
 * 
 * @author 徐茂鑫
 *
 */
@Service
@Transactional
public class VacateServiceImpl implements VacateService {

	@Resource
	private VacateDao vacateDao;

	@Resource
	private UserDao userDao;

	@Override
	public Message findAll(int page, int pageSize) {
		Message msg = new Message();
		// 创建分页实体
		Page p = new Page(page, pageSize);

		// 查询总条数
		int total = vacateDao.findTotalCounts().intValue();
		p.setTotalPages(total);
		msg.setPage(p);

		// 查询请假信息
		List<Vacate> vacateList = vacateDao.findVacatesByPage(p);
		msg.setVacates(VacateDto.change(vacateList));

		// 查询员工信息
		List<User> staffs = userDao.findAllStaffs();
		msg.setStaffs(UserDto.change(staffs));

		return msg;
	}

	@Override
	public Message findByFuzzy(String name) {
		Message msg = new Message();
		// 按照用户名模糊查找请假信息
		msg.setVacates(VacateDto.change(vacateDao.findVacatesByFuzzy(name)));
		List<User> staffs = userDao.findAllStaffs();
		msg.setStaffs(UserDto.change(staffs));

		return msg;
	}

	@Override
	public Message addVacate(Vacate vacate, String uId) {
		Message msg = new Message();
		// 判断用户id是否有值
		if (!uId.equals("")) {
			// 查询当前操作对象是否在数据库中存在
			User user = userDao.findById(uId);
			if (user != null) {
				vacate.setUser(user);
				String id = UUID.randomUUID().toString().substring(0, 5);
				vacate.setId(id);
				vacate.setApprove(0);
				vacate.setTime(new Date());
				vacateDao.add(vacate);
				msg.setMsg("success");
				VacateDto vacateDto = new VacateDto(vacate);
				msg.setVacate(vacateDto);
			} else {
				msg.setMsg("操作的用户不存在，请刷新！");
			}
		} else {
			msg.setMsg("您没有选择用户！");
		}

		return msg;
	}

	@Override
	public Message changeState(String id,int state) {
		Message msg = new Message();
		Vacate vacate = vacateDao.findById(id);
		if (vacate != null) {
			vacate.setApprove(state);
			vacateDao.update(vacate);
			msg.setVacate(new VacateDto(vacate));
			msg.setMsg("success");
		} else {
			msg.setMsg("当前操作对象不存在，请刷新！");
		}

		return msg;
	}

}
