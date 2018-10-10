package team.nobug.staffmanage.service;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.Punch;

/**
 * ��ҵ���ӿ�
 * @author ��ï��
 *
 */
public interface PunchService {

	Message findAll(int page, int pageSize);

	Message findByFuzzy(String name);

	Message addPunch(Punch punch, String id);

}
