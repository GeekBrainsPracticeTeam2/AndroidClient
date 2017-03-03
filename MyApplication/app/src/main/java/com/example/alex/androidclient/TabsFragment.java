package com.example.alex.androidclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import goldzweigapps.tabs.Builder.EasyTabsBuilder;
import goldzweigapps.tabs.Items.TabItem;
import goldzweigapps.tabs.View.EasyTabs;

/**
 * Created by alex on 03.03.17.
 */

public class TabsFragment extends Fragment {
    EasyTabs easyTabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabs_fragment_layout, container, false);

        initViews(view);

        setTabs();

        return  view;
    }

    private void initViews(View view){
        easyTabs = (EasyTabs)view.findViewById(R.id.easyTabs);
    }

    private void setTabs(){
        TabFragment firstFragment = new TabFragment();
        TabFragment secondFragment = new TabFragment();

        EasyTabsBuilder.with(easyTabs).addTabs(
                new TabItem(firstFragment, "Первый таб"),
                new TabItem(secondFragment, "Второй таб")
        ).Build();
    }
}
