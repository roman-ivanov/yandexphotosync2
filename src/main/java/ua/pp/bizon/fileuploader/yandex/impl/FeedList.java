package ua.pp.bizon.fileuploader.yandex.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.pp.bizon.fileuploader.yandex.Album;

import com.google.gson.Gson;

public class FeedList {

    private String id;
    private String url;
    private String token;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public FeedList(String href, String token) throws IOException {
        this.url = href;
        this.token = token;
        init(href, null);
    }
    
    public FeedList(String href, String token, FeedList parent) throws IOException {
        this.url = href;
        this.token = token;
        init(href, parent);
    }

    private void init(String uri, FeedList parent) throws IOException, HttpException {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(uri);
        method.addRequestHeader("Authorization", "OAuth " + token);
        method.addRequestHeader("Accept", "application/json; charset=\"utf-8\"");
        logger.trace("URL: " + uri);
        logger.trace("feed document responce code: " + client.executeMethod(method));
        logger.trace("encoding: "  + method.getResponseCharSet());
        String doc = method.getResponseBodyAsString();
        logger.trace("feed document responce text: " + doc);
        FeedData data = new Gson().fromJson(doc, FeedData.class);
        id = data.getId();
        if (id.contains("photos")) {
            for (EntryData e: data.getEntries()) {
                String albumlist = e.getLinks().get("album");
                Album a = parent.allAlbumsCachedByUrl.get(albumlist);
                if (a != null)
                a.addPhoto(e);
                else 
                    System.out.println(e);
            }
        } else {
            for (EntryData e : data.getEntries()) {
                Album a = new Album(e);
                if (a.getParentUrl() == null) {
                    albums.add(a);
                }
                allAlbumsCachedByUrl.put(a.getSelfUrl(), a);
            }
        }
        if (data.getLinks().containsKey("next")) {
            init(data.getLinks().get("next"), parent);
        } else {
            if (id.contains("photos")) {

            } else {
                for (Album e : allAlbumsCachedByUrl.values()) {
                    if (e.getParentUrl() != null) {
                        e.setParent(allAlbumsCachedByUrl.get(e.getParentUrl()));
                    }
                }
            }
        }
    }

    private Map<String, Album> allAlbumsCachedByUrl = new TreeMap<String, Album>();
    private List<Album> albums = new LinkedList<Album>();

    public Album getAlbumByPath(String to) {
        return getAlbumByPath(albums, to);
    }

    private static Album getAlbumByPath(List<Album> from, String to) {
        String foldername = to.split("\\\\", 1)[0];
        LoggerFactory.getLogger(FeedList.class).trace("search for album :" + foldername);
        for (Album a : from) {
            if (foldername.equals(a.getName())) {
                if (to.indexOf('\\') == -1) {
                    return a;
                } else
                    return getAlbumByPath(a.getChildren(), to.split("\\\\", 1)[1]);
            }
        }
        return null;
    }

    public Album getAlbumByPath(Album album, String f) {
        List<Album> children;
        if (album == null) {
            children = albums;
        } else {
            children = album.getChildren();
        }
        for (Album a : children) {
            if (a.getName().equals(f)) {
                return a;
            }
        }
        return null;
    }

    public String getUrl() {
        return url;
    }

    public Album load(String responseBodyAsString, Album parent) {
        Album a = new Album(new Gson().fromJson(responseBodyAsString, EntryData.class));
        if (parent == null)
            albums.add(a);
        else
            parent.getChildren().add(a);
        return a;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName()).append("[");
        buffer.append("id=\"").append(id);
        buffer.append("\",url=").append("url");
        buffer.append("\",albums=[\n");
        for (Album a : albums) {
            buffer.append(a).append(",\n");
        }
        buffer.append("]]");
        return buffer.toString();
    }
}
