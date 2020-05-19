package com.example.CommonTest.Search;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.CommonTest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class searchActivity extends Activity {
    @BindView(R.id.search_txt)
    EditText searchTxt;
    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.lab_title)
    TextView labTitle;
    @BindView(R.id.lab_list)
    ListView labList;
    @BindView(R.id.record_title)
    TextView recordTitle;
    @BindView(R.id.record_list)
    ListView recordList;
    @BindView(R.id.user_title)
    TextView userTitle;
    @BindView(R.id.user_list)
    ListView userList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.search_btn)
    public void onViewClicked() {



       // labList.setAdapter(new ArrayAdapter<String>());
    }
}
