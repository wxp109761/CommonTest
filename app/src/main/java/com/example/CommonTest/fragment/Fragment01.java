package com.example.CommonTest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.CommonTest.R;

public class Fragment01 extends Fragment {
    View rootview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       rootview=inflater.inflate(R.layout.fg_01,container,false);
       initView();
       return rootview;
    }

    private void initView(){

        Button btn=rootview.findViewById(R.id.add_fg);
        final Fragment03 f03=new Fragment03();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!f03.isAdded())
                getChildFragmentManager().beginTransaction().add(R.id.container_child,f03).commit();

            }
        });
    }



}
