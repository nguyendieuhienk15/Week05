package com.example.rxbus.event;

import android.view.View;

//A sample bus event
public class ClickEvent {
    private View view;

    public ClickEvent(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
