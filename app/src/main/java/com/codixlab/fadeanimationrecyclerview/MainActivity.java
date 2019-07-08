package com.codixlab.fadeanimationrecyclerview;

import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.codixlab.fadeanimationrecyclerview.adapter.FadeAnimationAdapter;
import com.codixlab.fadeanimationrecyclerview.databinding.ActivityMainBinding;
import com.codixlab.fadeanimationrecyclerview.model.People;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bi;
    List<People> list;
    FadeAnimationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();

    }

    private void init() {
        list = getData();
        list.addAll(getData());
        list.addAll(getData());
        list.addAll(getData());
        list.addAll(getData());
        list.addAll(getData());

        initRecyclerView();
    }

    private void initRecyclerView() {

        adapter = new FadeAnimationAdapter(this, list);
        bi.myList.setLayoutManager(new LinearLayoutManager(this));
        bi.myList.setAdapter(adapter);

        adapter.setOnItemClickListener(new FadeAnimationAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, People people, int position) {


                Snackbar.make(view, "Item " + people.name + "clicked ", Snackbar.LENGTH_SHORT).show();
            }
        });

    }


    List<People> getData() {
        List<People> list = new ArrayList<>();
        TypedArray imagesArray = getResources().obtainTypedArray(R.array.people_images);
        String[] names = getResources().getStringArray(R.array.people_names);

        for (int i = 0; i < imagesArray.length(); i++) {
            People people = new People();
            people.name = names[i];
            people.image = imagesArray.getResourceId(i, -1);
            list.add(people);
        }
        Collections.shuffle(list);


        return list;

    }
}
