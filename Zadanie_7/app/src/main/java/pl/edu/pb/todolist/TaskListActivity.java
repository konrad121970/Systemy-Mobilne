package pl.edu.pb.todolist;

import androidx.fragment.app.Fragment;

import pl.edu.pb.todolist.fragment.TaskListFragment;

public class TaskListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TaskListFragment();
    }
}