package pl.edu.pb.todolist.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import pl.edu.pb.todolist.model.Task;
@Getter
public class TaskStorage {
    private static final TaskStorage taskStorage = new TaskStorage();
    public static final String DEFAULT_TASK_NAME = "Lorem ipsum";

    private final List<Task> tasks;

    public static TaskStorage getInstance() {return taskStorage;}

    private TaskStorage(){
        tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            task.setName(DEFAULT_TASK_NAME + " #" + (i+1));
            task.setDone((i+1)%3 == 0);
            tasks.add(task);
        }
    }

    public Task getTask(UUID taskId) {
        Integer indexOfTask = null;
        for (int i = 0; i < tasks.size(); i++)
            if (tasks.get(i).getId().equals(taskId)) indexOfTask = i;
        if(indexOfTask == null) return null;

        return tasks.get(indexOfTask);
    }

    public void addTask(Task aTask) {
        tasks.add(aTask);
    }
}
