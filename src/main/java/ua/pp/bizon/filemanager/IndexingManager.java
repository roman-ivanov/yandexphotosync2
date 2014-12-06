package ua.pp.bizon.filemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import ua.pp.bizon.filemanager.tasks.IndexingTask;

@Controller
public class IndexingManager {

	@SuppressWarnings("resource")
	public static void main(String args[]) {
		new AnnotationConfigApplicationContext(IndexingManager.class
				.getPackage().getName()).getBean(IndexingManager.class).index();
	}

	private void index() {
		IndexingTask task = getNewTask();
		task.setStartDirectory("z:\\");
		task.scan();
	}
	
	@Autowired
	private ApplicationContext context;

	public IndexingTask getNewTask() {
		return context.getBean(IndexingTask.class);
	}
}
