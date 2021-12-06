package pl.edu.pb.todolist.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

import pl.edu.pb.todolist.R;
import pl.edu.pb.todolist.model.Task;
import pl.edu.pb.todolist.storage.TaskStorage;

public class TaskFragment extends Fragment {
    public static final String ARG_TASK_ID = "ARG_TASK_ID";
    private Task task;
    private EditText nameField;
    private Button taskDateButton;
    private CheckBox showNameInDetailsInListViewCheckbox;
    private CheckBox taskDoneCheckbox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID taskId = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        task = TaskStorage.getInstance().getTask(taskId);
    }

    public static TaskFragment newInstance(UUID taskId){
     Bundle bundle = new Bundle();
     bundle.putSerializable(ARG_TASK_ID, taskId);
     TaskFragment taskFragment = new TaskFragment();
     taskFragment.setArguments(bundle);
     return taskFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        nameField = view.findViewById(R.id.task_name);
        taskDateButton = view.findViewById(R.id.task_date);
        taskDoneCheckbox = view.findViewById(R.id.task_done);
        showNameInDetailsInListViewCheckbox = view.findViewById(R.id.show_name_in_details);

        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        nameField.setText(task.getName());

        taskDateButton.setText(task.getDate().toString());
        taskDateButton.setEnabled(false);

        taskDoneCheckbox.setChecked(task.isDone());
        taskDoneCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> task.setDone(isChecked));

        showNameInDetailsInListViewCheckbox.setChecked(task.isShowNameInDetailsInListView());
        showNameInDetailsInListViewCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> task.setShowNameInDetailsInListView(isChecked));

        return view;
    }
}
