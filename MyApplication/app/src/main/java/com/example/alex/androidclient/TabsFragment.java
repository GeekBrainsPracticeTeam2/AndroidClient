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
        String nameFirstTab = getString(R.string.general_stats);
        String nameSecondTab = getString(R.string.daily_stats);
        //TODO перенести стринги вверх, после объявления класса. С контекстом и гетресурсом, валится.

        TabFragmentGeneralStat firstFragment = new TabFragmentGeneralStat();
        TabFragmentDailyStat secondFragment = new TabFragmentDailyStat();

        EasyTabsBuilder.with(easyTabs).addTabs(
                new TabItem(firstFragment, nameFirstTab),
                new TabItem(secondFragment, nameSecondTab)
        ).Build();
    }
}
