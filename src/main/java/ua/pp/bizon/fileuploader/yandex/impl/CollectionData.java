package ua.pp.bizon.fileuploader.yandex.impl;

public class CollectionData {

	private String href;
	private String accept; 
	private String title;
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "CollectionData [href=" + href + ", accept=" + accept
				+ ", title=" + title + "]";
	}
	
}
