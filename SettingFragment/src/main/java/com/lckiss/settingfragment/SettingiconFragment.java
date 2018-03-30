package com.lckiss.settingfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by root on 17-6-25.
 */

public class SettingiconFragment extends Fragment {
        private View view;
    private  int[] settingicon;
    private String[][] settingText;
    private ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_icons,container,false);
        MainActivity activity=(MainActivity)getActivity();
        settingicon=activity.getIcons();
        settingText=activity.getSettingText();
        if (view!=null){
            initView();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SettingListFragment listFragment=(SettingListFragment)((MainActivity)getActivity()).getFragmentManager().findFragmentById(R.id.settingcontent);
                listFragment.setText(settingText[i]);
            }
        });
        return view;
    }

    private void initView() {
        listView=(ListView)view.findViewById(R.id.settingicon);
        if (settingicon!=null){
            listView.setAdapter(new MyAdapter());
        }
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return settingicon.length;
        }

        @Override
        public Object getItem(int i) {
            return settingicon[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=View.inflate(getContext(),R.layout.item_list,null);
            ImageView nameTv=(ImageView)view.findViewById(R.id.settingicon_imgv);
            nameTv.setBackgroundResource(settingicon[i]);
            return view;
        }
    }
}
