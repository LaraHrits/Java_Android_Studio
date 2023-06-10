package com.example.lw_4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String NAME = "NAME";

    private SimpleExpandableListAdapter mAdapter;
    ExpandableListView simpleExpandableListView;
    private String groupItems[] = {"Phone", "Web", "Map"};
    private String[][] childItems = {{"Call"}, {"Search site"}, {"Search in Google Maps"}};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleExpandableListView = findViewById(R.id.ExpandableListView);

        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

        for (int i = 0; i < groupItems.length; i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(NAME, groupItems[i]);

            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < childItems[i].length; j++) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(NAME, childItems[i][j]);
            }
            childData.add(children);
        }

        String groupFrom[] = {NAME};
        int groupTo[] = {R.id.groupTitle};
        String childFrom[] = {NAME};
        int childTo[] = {R.id.childTitle};

        mAdapter = new SimpleExpandableListAdapter(this, groupData,
                R.layout.group_row,
                groupFrom, groupTo,
                childData, R.layout.child_row,
                childFrom, childTo);
        simpleExpandableListView.setAdapter(mAdapter);

        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                // display a toast with group name whenever a user clicks on a group item
                Toast.makeText(getApplicationContext(), groupItems[groupPosition], Toast.LENGTH_LONG).show();

                return false;
            }
        });

        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent;

                if (groupPosition == 0) {
                    intent = new Intent(MainActivity.this, WebActivity.class);
                } else if (groupPosition == 1) {
                    intent = new Intent(MainActivity.this, PhoneActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, MapActivity.class);
                }

                intent.putExtra("GROUP_POSITION", groupPosition);
                intent.putExtra("CHILD_POSITION", childPosition);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), childItems[groupPosition][childPosition], Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}