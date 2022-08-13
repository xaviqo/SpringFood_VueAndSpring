package xavi.tech.springfood.model;

import lombok.Getter;

@Getter
public enum Role {

	NO_ASG(0,"NOASIG","NA_"),
	WORKER(1,"ROLE_ADMIN","WK_"),
	CLIENT(2,"ROLE_USER","CL_");
	
	private int code;
	private String description;
	private String prefix;

	Role(int i, String description, String prefix) {
		this.code = i;
		this.description = description;
		this.prefix = prefix;
	}

	public int getCode() {
		return this.code;
	}
	
}
