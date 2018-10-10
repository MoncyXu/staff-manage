package team.nobug.staffmanage.service;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.Vacate;

/**
 * 请假服务层接口
 * @author 徐茂鑫
 *
 */
public interface VacateService {

	Message findAll(int page, int pageSize);

	Message findByFuzzy(String name);

	Message addVacate(Vacate vacate, String uId);

	Message changeState(String id,int state);

}
