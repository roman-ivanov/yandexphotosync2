package ua.pp.bizon.fileuploader.yandex.impl;

import com.google.gson.annotations.SerializedName;

public class CollectionListData {

	public CollectionData getPhotolist() {
		return photolist;
	}
	public void setPhotolist(CollectionData photolist) {
		this.photolist = photolist;
	}
	public CollectionData getAlbumlist() {
		return albumlist;
	}
	public void setAlbumlist(CollectionData albumlist) {
		this.albumlist = albumlist;
	}
	public CollectionData getTaglist() {
		return taglist;
	}
	public void setTaglist(CollectionData taglist) {
		this.taglist = taglist;
	}
	@SerializedName("photo-list")
	CollectionData photolist;

	@SerializedName("album-list")
	CollectionData albumlist;

	@SerializedName("tag-list")
	CollectionData taglist;
	@Override
	public String toString() {
		return "CollectionListData [photolist=" + photolist + ", albumlist="
				+ albumlist + ", taglist=" + taglist + "]";
	}
	
	
}
