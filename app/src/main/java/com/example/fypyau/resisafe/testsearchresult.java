package com.example.fypyau.resisafe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nick Yau on 3/2/2017.
 */

public class testsearchresult extends Fragment {
    private View MyView;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.testsearchresult_layout, container, false);
        return MyView;
    }
}
