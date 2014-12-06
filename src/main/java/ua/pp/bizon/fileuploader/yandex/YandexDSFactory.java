package ua.pp.bizon.fileuploader.yandex;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class YandexDSFactory {
	
	private static Map<String, YandexDS> cache = new TreeMap<String, YandexDS>();
	
	public static YandexDS getYandexDS(String username) throws IOException{
		if (!cache.containsKey(username)){
			cache.put(username, new YandexDS(username));
		}
		return cache.get(username);
	}

}
