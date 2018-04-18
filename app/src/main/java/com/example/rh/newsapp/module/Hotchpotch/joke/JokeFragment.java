package com.example.rh.newsapp.module.Hotchpotch.joke;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.rh.newsapp.adapter.JokeAdapter;
import com.example.rh.newsapp.base.BaseHotchpotchFragment;
import com.example.rh.newsapp.model.JokeBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2018/3/31
 */
public class JokeFragment extends BaseHotchpotchFragment<JokePresenter> implements IJokeArticle.View {
    private static final String TAG = "JokeFragment";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private JokeAdapter jokeAdapter;
    private static JokeFragment jokeFragment;
    private List<JokeBean.JokeDataDataEntity> jokeDataDataGroupEntityList = new ArrayList<>();

    public static JokeFragment getInstance() {
        if (jokeFragment == null) {
            jokeFragment = new JokeFragment();
        }
        return jokeFragment;
    }

    @Override
    protected void setPresenter() {
        presenter = new JokePresenter(compositeDisposable);
        presenter.attachView(this);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        jokeAdapter = new JokeAdapter(jokeDataDataGroupEntityList);
        recyclerView.setAdapter(jokeAdapter);
    }



    @Override
    public void onLazyLoad() {
        Log.e(TAG, "fetchData: ");
        onRefresh();
    }
    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        presenter.loadData();
    }

    @Override
    public void onUpdateUI(List<JokeBean.JokeDataDataEntity> list) {
        jokeDataDataGroupEntityList.addAll(0,list);
        jokeAdapter.notifyDataSetChanged();
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //切断所有订阅事件，防止内存泄漏。
        compositeDisposable.clear();
        //取消Fragment和Presenter之间的关联
        if (presenter != null) {
            presenter.detachView();
        }
    }

}
