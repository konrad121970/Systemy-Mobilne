package pl.edu.pb.todolist.model;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task {
    private UUID id;
    private String name;
    private Date date;
    private boolean isDone;
    private boolean showNameInDetailsInListView;

    public Task() {
        id = UUID.randomUUID();
        date = new Date();
    }

    public Task(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
    }
}
