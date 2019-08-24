package com.example.mynoteapp.activity.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mynoteapp.R;
import com.example.mynoteapp.activity.editor.EditorActivity;
import com.example.mynoteapp.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {
    private static final int INTENT_EDIT = 200;
    private static final int INTENT_ADD = 100;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    MainPresenter presenter;
    MainAdapter adapter;
    MainAdapter.ItemClickListener itemClickListener;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.add);
        fab.setOnClickListener(view ->
                startActivityForResult(new Intent(this, EditorActivity.class), INTENT_ADD)
        );

        presenter = new MainPresenter(this);
        presenter.getData();

        swipeRefreshLayout.setOnRefreshListener(
                () -> presenter.getData()
        );

        itemClickListener = ((view, position) -> {
            int id = notes.get(position).getId();
            String title = notes.get(position).getTitle();
            String note = notes.get(position).getNote();
            int color =  notes.get(position).getColor();

            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("title", title);
            intent.putExtra("note", note);
            intent.putExtra("color", color);
            startActivityForResult(intent , INTENT_EDIT);

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == INTENT_ADD && resultCode == RESULT_OK){
            presenter.getData();; // 데이터 다시불러오기
        }else if(requestCode == INTENT_EDIT && resultCode == RESULT_OK){
            presenter.getData();
        }
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Note> notes) {
        adapter = new MainAdapter(this, notes, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        this.notes = notes;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
