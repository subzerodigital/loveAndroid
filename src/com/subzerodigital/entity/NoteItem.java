package com.subzerodigital.entity;

import java.io.Serializable;
import java.util.Date;

public class NoteItem implements Serializable {
	
	private static final long serialVersionUID = -289677682208765221L;
	
	private String title;
	private Date date;
	private String text;
	
	public NoteItem(String title, Date date, String text) {
		super();
		this.title = title;
		this.date = date;
		this.text = text;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
