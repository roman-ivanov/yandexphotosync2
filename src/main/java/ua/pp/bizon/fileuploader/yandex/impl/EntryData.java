package ua.pp.bizon.fileuploader.yandex.impl;

import java.util.Map;

public class EntryData {
	private String published, edited, updated, title, id, imageCount, summary;
	private Map<String, String> links;
	private Map<String, Map<String, String>> img;
	
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	public String getEdited() {
		return edited;
	}
	public void setEdited(String edited) {
		this.edited = edited;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImageCount() {
		return imageCount;
	}
	public void setImageCount(String imageCount) {
		this.imageCount = imageCount;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Map<String, String> getLinks() {
		return links;
	}
	public void setLinks(Map<String, String> links) {
		this.links = links;
	}
	
	public Map<String, Map<String, String>> getImg() {
        return img;
    }
	public void setImg(Map<String, Map<String, String>> img) {
        this.img = img;
    }
    @Override
    public String toString() {
        return "EntryData [published=" + published + ", edited=" + edited + ", updated=" + updated + ", title=" + title + ", id=" + id + ", imageCount="
                + imageCount + ", summary=" + summary + ", links=" + links + ", img=" + img + "]";
    }
}
