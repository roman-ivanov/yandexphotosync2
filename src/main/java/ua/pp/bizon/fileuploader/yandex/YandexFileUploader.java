package ua.pp.bizon.fileuploader.yandex;

import java.io.File;
import java.io.IOException;


public class YandexFileUploader {

	public static void main(String args[]) throws IOException{
		new YandexFileUploader().uploadRecurcively(new File("C:\\Users\\Roman\\Pictures\\10384128_10204066971825846_3872854824775217578_n.jpg"), "ivanov.roman.kr.ua", "тест\\фотки");
	}
	
	private void uploadRecurcively(File from,String username,  String to) throws IOException {
		if (from.isDirectory()) {
			for (File f: from.listFiles()){
			    if (f.isDirectory())
			        uploadRecurcively(f, username, to + "\\" + f.getName());
			    else 
			        upload(f, username, to);
			}
		} else upload(from, username, to);
		
	}
	
	private void upload(File from, String username, String to) throws IOException {
		YandexDSFactory.getYandexDS(username).uploadFile(from, to);
		
	}
}
