package com.intellect.financeTracker.dto;

import com.intellect.financeTracker.model.User;

public class AuthResponse {

	private String token;

	private String message;

	private User user;
	private Long adminId;
	private Long waiterId;

	public AuthResponse() {
	}

	public AuthResponse(String token, String message, User user, Long adminId, Long waiterId) {
		super();
		this.token = token;
		this.message = message;
		this.user = user;
		this.adminId = adminId;
		this.waiterId = waiterId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getWaiterId() {
		return waiterId;
	}

	public void setWaiterId(Long waiterId) {
		this.waiterId = waiterId;
	}

	@Override
	public String toString() {
		return "AuthResponse [token=" + token + ", message=" + message + ", user=" + user + ", adminId=" + adminId
				+ "]";
	}
}