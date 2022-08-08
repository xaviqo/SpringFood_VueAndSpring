package xavi.tech.springfood.model;

import lombok.Getter;

@Getter
public enum Role {

	NO_ASG(0,"noasig","NA_"),
	CLIENT(1,"client","CL_"),
	WORKER(2,"worker","WK_");
	
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
