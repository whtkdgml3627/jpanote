package com.example.jpanote.constants;

import lombok.Getter;

/**
 * packageName    : com.example.jpanote.constants
 * fileName       : ResultCodeType
 * author         : 조 상 희
 * date           : 2023-11-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-06       조 상 희         최초 생성
 */

@Getter
public enum ResultCodeType {
	SUCCESS("200","Success")

	;

	private final String code;
	private final String desc;

	ResultCodeType(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public String code(){
		return code;
	}

	public String desc(){
		return desc;
	}

	@Override
	public String toString() {
		return "ResultCodeType{" +
				"code='" + code + '\'' +
				", desc='" + desc + '\'' +
				'}';
	}
}
