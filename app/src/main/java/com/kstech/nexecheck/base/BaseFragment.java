package com.kstech.nexecheck.base;

import android.app.Activity;
import android.app.Fragment;

import com.kstech.nexecheck.utils.Globals;

/**
 * Created by lijie on 2017/5/27.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity activity;

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    //注册实时显示参数
    public void registRealTimeListener(){
        for (int i = 0; i < Globals.CheckItemRealtimeViews.size(); i++) {
            String name = Globals.CheckItemRealtimeViews.get(i).getRealTimeParamVO().getName();
            Globals.getModelFile().getDataSetVO().getDSItem(name).addListener(Globals.CheckItemRealtimeViews.get(i));
        }
    }

    //取消注册实时显示参数
    public void unRegistRealTimeListener(){
        for (int i = 0; i < Globals.CheckItemRealtimeViews.size(); i++) {
            String name = Globals.CheckItemRealtimeViews.get(i).getRealTimeParamVO().getName();
            Globals.getModelFile().getDataSetVO().getDSItem(name).removeListener(Globals.CheckItemRealtimeViews.get(i));
        }
    }

    protected abstract BaseFragment getFragment();

    public void updateFragment(){
        //子类自由实现 而且并不强制实现
    }
}
