package ua.pp.bizon.filemanager.data.jpa;

import org.springframework.data.repository.CrudRepository;

public interface FileRepository  extends CrudRepository<FileEntity, Long> {
	
	

}
