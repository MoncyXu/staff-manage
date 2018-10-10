package team.nobug.staffmanage.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notice_user")
public class NoticeUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 20)
	private String id;

	@ManyToOne
	@JoinColumn(name = "notice_id")
	private Notice notice;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
