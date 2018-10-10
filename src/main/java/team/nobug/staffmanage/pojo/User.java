package team.nobug.staffmanage.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 40)
	private String id;

	@Column(length = 15)
	private String tel;

	@Column(length = 30)
	private String name;

	@Column(length = 100)
	private String pwd;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time;

	@Column(length = 30)
	private String position;

	@ManyToOne
	@JoinColumn(name = "permission_id")
	private Permission permission;

	@OneToMany(mappedBy = "user")
	private List<Vacate> vacates;

	@OneToMany(mappedBy = "user")
	private List<Punch> punchs;

	@OneToMany(mappedBy = "user")
	private List<NoticeUser> noticeUsers;

	public User() {
	}

	public User(String id, String tel, String name, Date time, String position, Permission permission) {
		this.id = id;
		this.tel = tel;
		this.name = name;
		this.time = time;
		this.position = position;
		this.permission = permission;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public List<Vacate> getVacates() {
		return vacates;
	}

	public void setVacates(List<Vacate> vacates) {
		this.vacates = vacates;
	}

	public List<Punch> getPunchs() {
		return punchs;
	}

	public void setPunchs(List<Punch> punchs) {
		this.punchs = punchs;
	}

	public List<NoticeUser> getNoticeUsers() {
		return noticeUsers;
	}

	public void setNoticeUsers(List<NoticeUser> noticeUsers) {
		this.noticeUsers = noticeUsers;
	}

}
