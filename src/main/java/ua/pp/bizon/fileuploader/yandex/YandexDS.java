package ua.pp.bizon.fileuploader.yandex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class YandexDS {

    private String token;
    private ServiceDocument doc;

    protected YandexDS(String username) throws IOException {
        token = OAuthToken.getTokenByUserName(username);
        doc = new ServiceDocument(token);
    }

    public void uploadFile(File from, String to) throws IOException {
        Album album = doc.getAlbumList().getAlbumByPath(to);
        if (album == null) {
            Album parent = null;
            for (String f : to.split("\\\\")) {
                album = doc.getAlbumList().getAlbumByPath(album, f);
                if (album == null) {
                    album = createAlbum(parent, f);
                }
                parent = album;
            }
        }
        String photoname = from.getName();
        if (!album.isPhotoExist(photoname)) {
            doc.createPhoto(album, IOUtils.toByteArray(new FileInputStream(from)), photoname);
        } else {
            System.out.println("photo exist");
        }
    }

    private Album createAlbum(Album parent, String name) throws IOException {
        if (parent == null) {
            return doc.createAlbum(name);
        } else {
            return doc.createAlbum(name, parent);
        }
    }

}
