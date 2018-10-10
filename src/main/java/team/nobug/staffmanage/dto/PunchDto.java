package team.nobug.staffmanage.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team.nobug.staffmanage.pojo.Punch;

public class PunchDto {

	private String id;

	private String user;

	private Date time;

	private Date dutytime;

	private Date undergo;

	public PunchDto(Punch punch) {
		this.id = punch.getId();
		if(punch.getUser() != null)
			this.user = punch.getUser().getName();
		this.time = punch.getTime();
		this.dutytime = punch.getDutytime();
		this.undergo = punch.getUndergo();
	}

	public PunchDto() {
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

	public Date getDutytime() {
		return dutytime;
	}

	public void setDutytime(Date dutytime) {
		this.dutytime = dutytime;
	}

	public Date getUndergo() {
		return undergo;
	}

	public void setUndergo(Date undergo) {
		this.undergo = undergo;
	}
	
	public static List<PunchDto> change(List<Punch> punchs){
		List<PunchDto> punchDtos = new ArrayList<PunchDto>();
		
		for (Punch punch : punchs) {
			PunchDto punchDto = new PunchDto(punch);
			punchDtos.add(punchDto);
		}
		return punchDtos;
	}

}
