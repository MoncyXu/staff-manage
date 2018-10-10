package team.nobug.staffmanage.service;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.Vacate;

/**
 * ��ٷ����ӿ�
 * @author ��ï��
 *
 */
public interface VacateService {

	Message findAll(int page, int pageSize);

	Message findByFuzzy(String name);

	Message addVacate(Vacate vacate, String uId);

	Message changeState(String id,int state);

}
