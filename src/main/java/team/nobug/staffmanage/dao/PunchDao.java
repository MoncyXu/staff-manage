package team.nobug.staffmanage.dao;

import java.util.List;

import team.nobug.staffmanage.pojo.Page;
import team.nobug.staffmanage.pojo.Punch;

/**
 * 打卡数据访问层接口
 * @author 徐茂鑫
 *
 */
public interface PunchDao {

	List<Punch> findStaffsByPage(Page p);

	Long findTotalCounts();

	List<Punch> findPunchsByFuzzy(String name);

	void addPunch(Punch punch);

	List<Punch> findPunchsByUser(String uid);

	void deletePunch(Punch punch);

}
