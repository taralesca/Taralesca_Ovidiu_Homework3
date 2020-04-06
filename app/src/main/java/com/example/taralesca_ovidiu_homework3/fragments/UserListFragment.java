package com.example.taralesca_ovidiu_homework3.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.taralesca_ovidiu_homework3.R;
import com.example.taralesca_ovidiu_homework3.model.User;
import com.example.taralesca_ovidiu_homework3.util.RVAdapter;
import com.example.taralesca_ovidiu_homework3.util.RequestQueueSingleton;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class UserListFragment extends Fragment implements RVAdapter.OnTapListener {
    private List<User> users;
    private RVAdapter adapter = new RVAdapter(new String[]{}, this);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        users = new ArrayList<>();

        configureRecyclerView(view);

        final JsonArrayRequest jsonArrayRequest = getUserList();
        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

    private void configureRecyclerView(@NonNull View view) {
        final RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private JsonArrayRequest getUserList() {
        return new JsonArrayRequest(getString(R.string.users_endpoint_url),
                response -> {
                    for (int index = 0; index < response.length(); index++) {
                        try {
                            users.add(new User().fromJSON(response.getJSONObject(index)));
                        } catch (JSONException ex) {
                            Toast.makeText(getContext(), R.string.invalid_user_data, Toast.LENGTH_LONG).show();
                        }
                    }
                    updateRecyclerView();
                },
                error -> {
                    Toast.makeText(getContext(), R.string.users_endpoint_down, Toast.LENGTH_LONG).show();
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateRecyclerView() {
        adapter.swapDataSet(
                users.stream()
                        .map(user -> format(getString(R.string.user_card_format),
                                user.getName(), user.getUsername(), user.getEmail()))
                        .toArray(String[]::new)
        );
    }

    @Override
    public void onUserClick(int position) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new TodoListFragment(users.get(position)));
        fragmentTransaction.commit();
    }


}
