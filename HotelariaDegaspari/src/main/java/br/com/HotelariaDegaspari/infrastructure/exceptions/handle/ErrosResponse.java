package br.com.HotelariaDegaspari.infrastructure.exceptions.handle;

import java.time.LocalDateTime;

public class ErrosResponse {
	
	private String messager;
	private LocalDateTime data;
	private int status;
	private String path;
	
	
	public ErrosResponse(String message, LocalDateTime data2, int value, String requestURI) {
		
	}
	public String getMessager() {
		return messager;
	}
	public void setMessager(String messager) {
		this.messager = messager;
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
