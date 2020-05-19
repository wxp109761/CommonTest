package com.example.CommonTest.contentProvider;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.CommonTest.R;
import com.example.CommonTest.sqllite.DatabaseHelper;

public class contentProvider extends ContentProvider {
    private DatabaseHelper databaseHelper;
    public static final String AUTHORITY="com.example.contentProvider";


    private static UriMatcher uriMatcher;
    public static final int USER_DIR = 0;
    public static final int USER_ITEM = 1;
    //创建静态代码
    static {
        //实例化UriMatcher对象
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //可以实现匹配URI的功能
        //参数1：authority 参数2：路径 参数3：自定义代码
        uriMatcher.addURI(AUTHORITY, "test_db", USER_DIR);
        uriMatcher.addURI(AUTHORITY, "test_db/#", USER_ITEM);
    }
    @Override
    public boolean onCreate() {
        databaseHelper=new DatabaseHelper(getContext(),"test_db", null, 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db =databaseHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case USER_DIR:
                //参数1：表名  其他参数可借鉴上面的介绍
                cursor = db.query("test_db", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case USER_ITEM:
                String queryId = uri.getPathSegments().get(1);
                cursor = db.query("test_db", projection, "id=?", new String[]{queryId}, null, null, sortOrder);
                break;
            default:
        }
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db=databaseHelper.getWritableDatabase();
        int deleteInt=0;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                deleteInt=db.delete("userdemo",selection, selectionArgs);
                break;
            case USER_ITEM:
                String deleteId=uri.getPathSegments().get(1);
                deleteInt=db.delete("userdemo","id=?",new String[]{deleteId});
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
