package com.regiocom.newsWebapp.contents;


import java.util.List;
import com.regiocom.newsWebapp.contents.pictures.Picture;


public class News {
	private int id;
	private String title;
	private String text;
	private String creationDate;
	private String validFrom;
	private String validTo;
	private int creadtedBy;
	private boolean isPublish;
	private List<Picture> pictures;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
	
	public int getCreadtedBy() {
		return creadtedBy;
	}

	public void setCreadtedBy(int creadtedBy) {
		this.creadtedBy = creadtedBy;
	}

	public boolean isPublish() {
		return isPublish;
	}

	public void setPublish(boolean isPublish) {
		this.isPublish = isPublish;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

}
