package team.nobug.staffmanage.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "notice")
public class Notice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 20)
	private String id;

	@Column(length = 60)
	private String title;

	@Column(length = 300)
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time;

	@OneToMany(mappedBy = "notice")
	private List<NoticeUser> noticeUsers;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<NoticeUser> getNoticeUsers() {
		return noticeUsers;
	}

	public void setNoticeUsers(List<NoticeUser> noticeUsers) {
		this.noticeUsers = noticeUsers;
	}

}
