package com.lckiss.settingfragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by root on 17-6-25.
 */

public class SettingListFragment extends Fragment {
    private View view;
    private TextView textView1;
    private TextView textView2;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settinglist, container, false);
        if (view != null) {
            initView();
        }
        setText(((MainActivity) getActivity()).getSettingText()[0]);
        return view;
    }

    public void initView() {
        textView1 = (TextView) view.findViewById(R.id.tv);
        textView2 = (TextView) view.findViewById(R.id.tv1);
    }

    public void setText(String[] text) {
        textView1.setText(text[0]);
        textView2.setText(text[1]);
    }
}
