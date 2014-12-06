package ua.pp.bizon.fileuploader.yandex.impl;


public class ServiceDocumentData {

	private CollectionListData collections;

	public CollectionListData getCollections() {
		return collections;
	}

	public void setCollections(CollectionListData collections) {
		this.collections = collections;
	}

	@Override
	public String toString() {
		return "ServiceDocumentData [collections=" + collections + "]";
	}

}
