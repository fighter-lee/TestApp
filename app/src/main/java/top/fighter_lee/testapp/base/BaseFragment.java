package top.fighter_lee.testapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    protected LoadService mBaseLoadService;
    /**
     * 获取布局ID
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 界面初始化
     */
    protected abstract void init(View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getActivity(), getContentViewLayoutID(), null);
        mBaseLoadService = LoadSir.getDefault().register(rootView, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onNetReload();
            }
        });
        return mBaseLoadService.getLoadLayout();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        init(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract void onNetReload();
}
