package xavi.tech.springfood.model;

import lombok.Getter;

@Getter
public enum Role {

	CLIENT(0,"client"),
	WORKER(1,"worker");
	
	private int code;
	private String description;

	Role(int i, String description) {
		this.code = i;
		this.description = description;
	}

	public int getCode() {
		return this.code;
	}
	
}
