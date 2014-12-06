package ua.pp.bizon.filemanager.tasks;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import ua.pp.bizon.filemanager.IndexingManager;
import ua.pp.bizon.filemanager.data.jpa.FileEntity;
import ua.pp.bizon.filemanager.data.jpa.FileRepository;


@Repository
@Scope("prototype")
public class IndexingTask {
	
	@Autowired
	private IndexingManager manager;
	
	@Autowired
	private FileRepository repository;
	
	private File file;
	public void setStartDirectory(String string) {
		 file = new File(string);

	}

	public void scan() {
		repository.save(new FileEntity());
		System.out.println(repository.findOne(0L));
		
		if (file.isDirectory()) {
			for (File f: file.listFiles()){
		//		manager.getNewTask().setStartFolder(f).scan();
			}
		} else {
			System.out.println(repository.findOne(0L));
			System.out.println(file);
		}
	}

	private IndexingTask setStartFolder(File f) {
		file = f;
		return this;
	}

}
