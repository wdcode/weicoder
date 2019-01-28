package com.weicoder.oauth;

/**
 * 授权信息
 * @author WD
 */
public class OAuthInfo {
	// openid
	private String	openid;
	// 类型
	private String	type;
	// 返回的json信息
	private String	data;
	// 保存unionid
	private String	unionid;
	// 昵称
	private String	nickname;
	// 头像
	private String	head;
	// 性别
	private int		sex;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
}