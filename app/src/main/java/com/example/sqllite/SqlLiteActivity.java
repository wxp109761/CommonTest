package com.example.sqllite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.CommonTest.R;

public class SqlLiteActivity extends Activity implements View.OnClickListener{
    EditText et_name,et_age,delete_name,yuan_data,up_data;
    TextView select_data;
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqllite);
        ininView();
    }

    private void ininView() {
        Button insert= (Button) findViewById(R.id.insert);
        Button delete= (Button) findViewById(R.id.delete);
        Button update= (Button) findViewById(R.id.update);
        Button select= (Button) findViewById(R.id.select);

        delete.setOnClickListener(this);
        select.setOnClickListener(this);
        update.setOnClickListener(this);
        insert.setOnClickListener(this);
        DatabaseHelper databaseHelper = new DatabaseHelper(this,"test_db",null,1);
        db = databaseHelper.getWritableDatabase();
        et_name=findViewById(R.id.name);
        et_age=findViewById(R.id.age);
        delete_name=findViewById(R.id.delete_name);
        yuan_data=findViewById(R.id.yuan_data);
        up_data=findViewById(R.id.new_data);
        select_data=findViewById(R.id.select_data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insert:
                ContentValues values = new ContentValues();
                values.put("name",et_name.getText().toString());
                values.put("age",et_age.getText().toString());
                db.insert("user",null,values);
                select();
                break;
            case R.id.delete:
                db.delete("user","name=?",new String[]{delete_name.getText().toString()});
                select();
                break;
            case R.id.update:
                ContentValues values2 = new ContentValues();
                values2.put("name", up_data.getText().toString());
                db.update("user", values2, "name = ?", new String[]{yuan_data.getText().toString()});
                select();
                break;
            case R.id.select:
                select();
                break;
        }
    }
    public void select(){
        //创建游标对象
        Cursor cursor = db.query("user", new String[]{"name","age"}, null, null, null, null, null);
        //利用游标遍历所有数据对象
        //为了显示全部，把所有对象连接起来，放到TextView中
        String textview_data = "";
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            textview_data = textview_data + "\n" + name+"  "+age;
        }
        select_data.setText(textview_data);
    }
}
