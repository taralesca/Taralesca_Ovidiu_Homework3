package com.example.taralesca_ovidiu_homework3.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.taralesca_ovidiu_homework3.R;
import com.example.taralesca_ovidiu_homework3.model.Todo;
import com.example.taralesca_ovidiu_homework3.model.User;
import com.example.taralesca_ovidiu_homework3.util.RVAdapter;
import com.example.taralesca_ovidiu_homework3.util.RequestQueueSingleton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TodoListFragment extends Fragment implements RVAdapter.OnTapListener {
    private User user;
    private List<Todo> todos;
    RVAdapter mAdapter = new RVAdapter(new String[]{}, this);

    public TodoListFragment(User user) {
        this.user = user;
        todos = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final RecyclerView recyclerView = view.findViewById(R.id.todo_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);


        JsonArrayRequest jsonArrayRequest = getTodoList();
        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

    private JsonArrayRequest getTodoList() {
        String url = "https://jsonplaceholder.typicode.com/todos?userId=" + user.getId();
        return new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int index = 0; index < response.length(); index++) {
                            try {
                                Todo todo = new Todo().fromJSON(response.getJSONObject(index));
                                todos.add(todo);
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                        updateRecyclerView();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateRecyclerView() {
        mAdapter.swapDataSet(
                todos.stream()
                        .map(todo -> todo.getTitle()
                                + ": " + todo.isCompleted())
                        .toArray(String[]::new)
        );
    }

    @Override
    public void onUserClick(int position) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new TodoDetailsFragment(todos.get(position)));
        fragmentTransaction.commit();
    }
}
