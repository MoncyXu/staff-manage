package team.nobug.staffmanage.service;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.Punch;

/**
 * 打卡业务层接口
 * @author 徐茂鑫
 *
 */
public interface PunchService {

	Message findAll(int page, int pageSize);

	Message findByFuzzy(String name);

	Message addPunch(Punch punch, String id);

}
