package team.nobug.staffmanage.pojo;

import java.io.Serializable;
import java.util.List;

import team.nobug.staffmanage.dto.PunchDto;
import team.nobug.staffmanage.dto.UserDto;
import team.nobug.staffmanage.dto.VacateDto;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private String msg;

	private UserDto user;

	private Page page;

	private List<UserDto> staffs;

	private PunchDto punch;

	private List<PunchDto> punchs;

	private VacateDto vacate;

	private List<VacateDto> vacates;

	private List<Notice> notices;

	private Notice notice;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<UserDto> getStaffs() {
		return staffs;
	}

	public void setStaffs(List<UserDto> staffs) {
		this.staffs = staffs;
	}

	public List<PunchDto> getPunchs() {
		return punchs;
	}

	public void setPunchs(List<PunchDto> punchs) {
		this.punchs = punchs;
	}

	public PunchDto getPunch() {
		return punch;
	}

	public void setPunch(PunchDto punch) {
		this.punch = punch;
	}

	public VacateDto getVacate() {
		return vacate;
	}

	public void setVacate(VacateDto vacate) {
		this.vacate = vacate;
	}

	public List<VacateDto> getVacates() {
		return vacates;
	}

	public void setVacates(List<VacateDto> vacates) {
		this.vacates = vacates;
	}

	public List<Notice> getNotices() {
		return notices;
	}

	public void setNotices(List<Notice> notices) {
		this.notices = notices;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

}
