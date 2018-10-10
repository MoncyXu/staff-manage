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
 * ��ٷ����ʵ��
 * 
 * @author ��ï��
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
		// ������ҳʵ��
		Page p = new Page(page, pageSize);

		// ��ѯ������
		int total = vacateDao.findTotalCounts().intValue();
		p.setTotalPages(total);
		msg.setPage(p);

		// ��ѯ�����Ϣ
		List<Vacate> vacateList = vacateDao.findVacatesByPage(p);
		msg.setVacates(VacateDto.change(vacateList));

		// ��ѯԱ����Ϣ
		List<User> staffs = userDao.findAllStaffs();
		msg.setStaffs(UserDto.change(staffs));

		return msg;
	}

	@Override
	public Message findByFuzzy(String name) {
		Message msg = new Message();
		// �����û���ģ�����������Ϣ
		msg.setVacates(VacateDto.change(vacateDao.findVacatesByFuzzy(name)));
		List<User> staffs = userDao.findAllStaffs();
		msg.setStaffs(UserDto.change(staffs));

		return msg;
	}

	@Override
	public Message addVacate(Vacate vacate, String uId) {
		Message msg = new Message();
		// �ж��û�id�Ƿ���ֵ
		if (!uId.equals("")) {
			// ��ѯ��ǰ���������Ƿ������ݿ��д���
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
				msg.setMsg("�������û������ڣ���ˢ�£�");
			}
		} else {
			msg.setMsg("��û��ѡ���û���");
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
			msg.setMsg("��ǰ�������󲻴��ڣ���ˢ�£�");
		}

		return msg;
	}

}
