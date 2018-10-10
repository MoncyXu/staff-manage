package team.nobug.staffmanage.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team.nobug.staffmanage.pojo.User;

public class UserDto {

	private String id;

	private String name;

	private String position;

	private String tel;

	private Date time;

	private String permission;

	public UserDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.position = user.getPosition();
		this.tel = user.getTel();
		this.time = user.getTime();
		if (user.getPermission() != null)
			this.permission = user.getPermission().getName();
	}

	public UserDto() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public static List<UserDto> change(List<User> users) {
		List<UserDto> userDtos = new ArrayList<UserDto>();
		for (User user : users) {
			UserDto userDto = new UserDto(user);
			userDtos.add(userDto);
		}
		return userDtos;
	}
}
