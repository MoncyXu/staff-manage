package team.nobug.staffmanage.dao;

import java.util.List;

import team.nobug.staffmanage.pojo.Page;
import team.nobug.staffmanage.pojo.Vacate;

/**
 * ������ݷ��ʽӿ�
 * @author ��ï��
 *
 */
public interface VacateDao {

	Long findTotalCounts();

	List<Vacate> findVacatesByPage(Page p);

	List<Vacate> findVacatesByFuzzy(String name);

	void add(Vacate vacate);

	Vacate findById(String id);

	void update(Vacate vacate);

	List<Vacate> findVacatesByUser(String id);

	void delete(Vacate vacate);

}
