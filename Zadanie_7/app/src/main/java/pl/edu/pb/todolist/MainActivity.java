package pl.edu.pb.todolist;

import androidx.fragment.app.Fragment;

import java.util.UUID;

import pl.edu.pb.todolist.fragment.TaskFragment;
import pl.edu.pb.todolist.fragment.TaskListFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID taskId = (UUID) getIntent().getSerializableExtra(TaskListFragment.KEY_EXTRA_TASK_ID);
        return TaskFragment.newInstance(taskId);
    }
}