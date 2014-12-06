package ua.pp.bizon.fileuploader.yandex;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.pp.bizon.fileuploader.yandex.impl.EntryData;
import ua.pp.bizon.fileuploader.yandex.impl.FeedList;
import ua.pp.bizon.fileuploader.yandex.impl.ServiceDocumentData;

import com.google.gson.Gson;

public class ServiceDocument {

    private ServiceDocumentData data;
    private volatile String token;

    private FeedList albumsList;

    private FeedList photosList;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public ServiceDocument(String token) throws IOException {
        this.token = token;
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod("http://api-fotki.yandex.ru/api/me/");
        method.addRequestHeader("Authorization", "OAuth " + token);
        method.addRequestHeader("Accept", "application/json");
        
        int responceCode = client.executeMethod(method);
        String doc = method.getResponseBodyAsString();
        if (responceCode != 200) {
            logger.error("GET service document responce code: " + responceCode);
            logger.error("service document responce text: " + doc);
            throw new IOException(doc);
                
        }
        logger.trace("GET service document responce code: " + responceCode);
        logger.trace("service document responce text: " + doc);
        data = new Gson().fromJson(doc, ServiceDocumentData.class);
        albumsList = new FeedList(data.getCollections().getAlbumlist().getHref(), token);
        photosList = new FeedList(data.getCollections().getPhotolist().getHref(), token, albumsList);
        logger.trace("albums list: " + albumsList);

    }

    public FeedList getAlbumList() {
        return albumsList;
    }
    
    public FeedList getPhotosList() {
        return photosList;
    }

    public Album createAlbum(String name) throws IOException {
        return createAlbum(name, null);
    }

    public Album createAlbum(String name, Album parent) throws IOException {
        PostMethod method = new PostMethod(data.getCollections().getAlbumlist().getHref());
        method.addRequestHeader("Authorization", "OAuth " + token);
        method.addRequestHeader("Accept", "application/json");
        method.addRequestHeader("Content-Type", "application/json; charset=utf-8; type=\"entry\"");
        String entry = " {\"title\": \"" + name + "\"";
        if (parent != null) {
            entry += ", \"links\" : { \"album\" :\"" + parent.getSelfUrl() + "\"}";
        }
        entry += "}";
        method.setRequestEntity(new StringRequestEntity(entry, "application/json; charset=utf-8; type=\"entry\"", "UTF-8"));
        logger.trace("POST create album has being execuded");
        int httpResponceCode = new HttpClient().executeMethod(method);
        String responce = method.getResponseBodyAsString();
        if (httpResponceCode != 201) {
            logger.warn("POST create album responce code: " + httpResponceCode);
            logger.warn("POST create album responce message: " + responce);
            throw new IOException("album is not created, responce code: " + httpResponceCode);
        }
        logger.trace("encoding: " + method.getResponseCharSet());
        logger.trace("POST create album responce code: " + httpResponceCode);
        logger.trace("POST create album responce message: " + responce);
        return albumsList.load(responce, parent);
    }

    public void createPhoto(Album album, byte[] byteArray, String photoName) throws IOException{
        String uri = album.getPhotosUrl();
        PostMethod method = new PostMethod(uri);
        method.addRequestHeader("Authorization", "OAuth " + token);
        method.addRequestHeader("Accept", "application/json");
        method.setRequestEntity(new ByteArrayRequestEntity(byteArray, "image/jpeg"));
        logger.trace("POST upload photo has being execuded");
        int httpResponceCode = new HttpClient().executeMethod(method);
        String responce = method.getResponseBodyAsString();
        if (httpResponceCode != 201) {
            logger.warn("POST create photo responce code: " + httpResponceCode);
            logger.warn("POST create photo responce message: " + responce);
            throw new IOException("photo is not created, responce code: " + httpResponceCode);
        }
        logger.trace("POST create photo responce code: " + httpResponceCode);
        logger.trace("POST create photo responce message: " + responce);
        EntryData data = new Gson().fromJson(responce, EntryData.class);
        PutMethod putMethod = new PutMethod(data.getLinks().get("edit"));
        putMethod.addRequestHeader("Authorization", "OAuth " + token);
        putMethod.addRequestHeader("Accept", "application/json");
        putMethod.addRequestHeader("Content-Type", "application/json; charset=utf-8; type=\"entry\"");
        data.setTitle(photoName);
        data.setSummary(photoName);
        String entry = new Gson().toJson(data);
                //" {\"title\": \"image (8).jpg\", \"summary\":\"image (8).jpg\"}";
        logger.trace("PUT create photo data: " + entry);
        logger.trace("PUT create photo url: " + putMethod.getURI());
        putMethod.setRequestEntity(new StringRequestEntity(entry, "application/json; charset=utf-8; type=\"entry\"", "UTF-8"));
        httpResponceCode = new HttpClient().executeMethod(putMethod);
        responce = putMethod.getResponseBodyAsString();
        if (httpResponceCode != 200) {
            logger.warn("PUT create photo responce code: " + httpResponceCode);
            logger.warn("PUT create photo responce message: " + responce);
            throw new IOException("photo is not edited, responce code: " + httpResponceCode);
        }
        logger.trace("PUT create photo responce code: " + httpResponceCode);
        logger.trace("PUT create photo responce message: " + responce);
    }

}
