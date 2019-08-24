package com.example.mynoteapp.activity.main;

import com.example.mynoteapp.model.Note;

import java.util.List;

public interface MainView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<Note> notes);
    void  onErrorLoading(String message);
}
