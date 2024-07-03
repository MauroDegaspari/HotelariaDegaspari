package br.com.HotelariaDegaspari.infrastructure.exceptions.handle;

import java.time.LocalDateTime;

public class ErrosResponse {
	
	private String message;
	private LocalDateTime data;
	private int status;
	private String path;
	
	
	public ErrosResponse(String message, LocalDateTime data, int status, String path) {
		
	}
	public String getMessager() {
		return message;
	}
	public void setMessager(String message) {
		this.message = message;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	

}
