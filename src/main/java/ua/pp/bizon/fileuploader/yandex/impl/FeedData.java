package ua.pp.bizon.fileuploader.yandex.impl;

import java.util.List;
import java.util.Map;

public class FeedData {
	
	private String id;
	private List<EntryData> entries;
	private Map<String, String> links;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<EntryData> getEntries() {
		return entries;
	}
	public void setEntries(List<EntryData> entries) {
		this.entries = entries;
	}
    public Map<String, String> getLinks() {
        return links;
    }
    public void setLinks(Map<String, String> links) {
        this.links = links;
    }
    @Override
    public String toString() {
        return "FeedData [id=" + id + ", entries=" + entries + ", links=" + links + "]";
    }
	

}
