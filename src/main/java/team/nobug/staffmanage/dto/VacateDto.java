package team.nobug.staffmanage.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team.nobug.staffmanage.pojo.Vacate;

public class VacateDto {

	private String id;

	private String user;

	private Date time;

	private String reason;

	private Date starttime;

	private Date endtime;

	private int approve;

	public VacateDto(Vacate vacate) {
		super();
		this.id = vacate.getId();
		if (vacate.getUser() != null)
			this.user = vacate.getUser().getName();
		this.time = vacate.getTime();
		this.reason = vacate.getReason();
		this.starttime = vacate.getStarttime();
		this.endtime = vacate.getEndtime();
		this.approve = vacate.getApprove();
	}

	public VacateDto() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public int getApprove() {
		return approve;
	}

	public void setApprove(int approve) {
		this.approve = approve;
	}

	public static List<VacateDto> change(List<Vacate> vacates) {
		List<VacateDto> vacateDtos = new ArrayList<VacateDto>();
		for (Vacate vacate : vacates) {
			VacateDto dto = new VacateDto(vacate);
			vacateDtos.add(dto);
		}

		return vacateDtos;
	}

}
