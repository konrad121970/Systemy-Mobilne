package pl.edu.pb.todolist.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.edu.pb.todolist.MainActivity;
import pl.edu.pb.todolist.R;
import pl.edu.pb.todolist.model.Task;
import pl.edu.pb.todolist.storage.TaskStorage;

public class TaskListFragment extends Fragment {
    public static final String SUBTITLE_VISIBLE_KEY = "subtitleVisible";
    public static final String KEY_EXTRA_TASK_ID = "KEY_EXTRA_TASK_ID";
    private RecyclerView recyclerView;
    private boolean subtitleVisible;
    private TaskAdapter adapter;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SUBTITLE_VISIBLE_KEY, subtitleVisible);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_task:
                Task task = new Task();
                TaskStorage.getInstance().addTask(task);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(KEY_EXTRA_TASK_ID, task.getId());
                startActivity(intent);
                return true;
            case R.id.show_and_hide_subtitle:
                subtitleVisible = !subtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.show_and_hide_subtitle);
        if (subtitleVisible) menuItem.setTitle(R.string.hide_subtitle);
        else menuItem.setTitle(R.string.show_subtitle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = view.findViewById(R.id.task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
            subtitleVisible = savedInstanceState.getBoolean(SUBTITLE_VISIBLE_KEY);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
        List<Task> tasks = TaskStorage.getInstance().getTasks();
        if (adapter == null) {
            adapter = new TaskAdapter(tasks);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    public void updateSubtitle() {
        List<Task> tasks = TaskStorage.getInstance().getTasks();
        int amountOfUndoneTasks = 0;
        for (Task task : tasks) {
            if (!task.isDone()) amountOfUndoneTasks++;
        }
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        String subtitle = getString(R.string.subtitle_format, amountOfUndoneTasks);
        if(!subtitleVisible) subtitle = null;
        appCompatActivity.getSupportActionBar().setSubtitle(subtitle);
    }


    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nameOfTaskTextView;
        private final TextView dateOfTaskTextView;
        private final ImageView doneOfTaskImageView;

        private Task task;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);

            nameOfTaskTextView = itemView.findViewById(R.id.task_item_name);
            dateOfTaskTextView = itemView.findViewById(R.id.task_item_date);
            doneOfTaskImageView = itemView.findViewById(R.id.task_item_done);
        }

        private void bind(Task task) {
            this.task = task;
            nameOfTaskTextView.setText(task.getName());
            if (task.isShowNameInDetailsInListView())
                dateOfTaskTextView.setText(task.getName() + " - " + task.getDate().toString());
            else dateOfTaskTextView.setText(task.getDate().toString());
            if (task.isDone()) doneOfTaskImageView.setImageResource(R.drawable.ic_completed_task);
            else doneOfTaskImageView.setImageResource(R.drawable.ic_uncompleted_task);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(KEY_EXTRA_TASK_ID, task.getId());
            startActivity(intent);
        }
    }


    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks) {
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = tasks.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }
}
