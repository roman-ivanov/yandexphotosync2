package ua.pp.bizon.fileuploader.yandex;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ua.pp.bizon.fileuploader.yandex.impl.EntryData;

public class Album {
	
	private String parentUrl;
	private String selfUrl;
	private String alternateUrl;
	private String photosUrl;
	private List<Album> children = new LinkedList<Album>();
	private Map<String, EntryData> photosByName = new HashMap<String, EntryData>();
	private String name;

	public Album(EntryData e) {
		parentUrl = e.getLinks().get("album");
		selfUrl = e.getLinks().get("self");
		alternateUrl = e.getLinks().get("alternate");
		photosUrl = e.getLinks().get("photos");
		name = e.getTitle();
	}

	public String getParentUrl() {
		return parentUrl;
	}

	public String getSelfUrl() {	
		return selfUrl;
	}
	
	public String getAlternateUrl() {
        return alternateUrl;
    }
	
	 public String getPhotosUrl() {
        return photosUrl;
    }

	public void setParent(Album album) {
		album.children.add(this);	
	}

	public String getName() {
		return name;
	}

	public List<Album> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return "Album [parentUrl=" + parentUrl + ", selfUrl=" + selfUrl
				+ ", children=" + children + ", name=" + name + "]";
	}

    public boolean isPhotoExist(String photoname) {
        return photosByName.containsKey(photoname);
    }
    

    public void addPhoto(EntryData e) {
        photosByName.put(e.getTitle(), e);
    }

}
