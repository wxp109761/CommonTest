package com.example.CommonTest.contentProvider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.CommonTest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentProviderActivity extends Activity {
    public static final String AUTHORITY = "com.example.contentProvider";
    private static final Uri STUDENT_URI = Uri.parse("content://" + AUTHORITY + "/test_db");

    @BindView(R.id.select)
    Button select;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.age)
    EditText age;
    @BindView(R.id.insert)
    Button insert;
    @BindView(R.id.delete_name)
    EditText deleteName;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.yuan_data)
    EditText yuanData;
    @BindView(R.id.new_data)
    EditText newData;
    @BindView(R.id.update)
    Button update;
    @BindView(R.id.select_data)
    TextView selectData;
    ContentResolver contentResolver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        ButterKnife.bind(this);
        contentResolver= getContentResolver();
    }


    @OnClick({R.id.select, R.id.insert, R.id.delete, R.id.update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select:
                select();
                break;
            case R.id.insert:
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", name.getText().toString());
                contentValues.put("age", age.getText().toString());
                contentResolver.insert(STUDENT_URI, contentValues);
                select();
                break;
            case R.id.delete:
                contentResolver.delete(STUDENT_URI,"name=?",new String[]{deleteName.getText().toString()});
                select();
                break;
            case R.id.update:
                ContentValues values1 = new ContentValues();
                values1.put("name",newData.getText().toString());
                contentResolver.update(STUDENT_URI,values1,"name=?",new String[]{yuanData.getText().toString()});
                select();
                break;
        }
    }


    void select(){
        Cursor cursor = contentResolver.query(STUDENT_URI, null, null, null, null, null);
        String tv_show_text = "";
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String age = cursor.getString(cursor.getColumnIndex("age"));
                // String desc = cursor.getString(cursor.getColumnIndex("desc"));
                tv_show_text += "name = " + name + ",age = " + age + "\n";
                Log.e(getClass().getSimpleName(), "Student:" + "name = " + name + ",age = " + age);

            }
            cursor.close();
        }
        selectData.setText(tv_show_text);
    }
}
