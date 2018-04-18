package com.example.rh.newsapp.module.Hotchpotch.bing;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.example.rh.newsapp.adapter.BingPictureAdapter;
import com.example.rh.newsapp.base.BaseHotchpotchFragment;
import com.example.rh.newsapp.model.BingDailyBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2018/1/23
 */
public class BingPictureFragment extends BaseHotchpotchFragment<BingPicturePresenter> implements IBing.View {
    private List<BingDailyBean> bingDailyBeanList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private BingPictureAdapter bingPictureAdapter;
    private static BingPictureFragment bingPictureFragment;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static BingPictureFragment getInstance(){
        if (bingPictureFragment == null){
            bingPictureFragment = new BingPictureFragment();
        }
        return bingPictureFragment;
    }

 /*   @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bingpicture , container , false);
        //初始化控件
        initRecyclerView(view);
        //初始化图片
        initPicture(8);
        //初始化刷新控件
        initSwipeRefresh(view);
        return view;
    }*/

  /*  private void initSwipeRefresh(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(() -> new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getActivity().runOnUiThread(() -> {
                initPicture(8);
                swipeRefreshLayout.setRefreshing(false);
            });
        }).start());
    }*/

   /* private void initPicture(int number) {
        String bingPictureUrl = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=" + number;
        HttpUtils.sendOkHttpRequestWithGET(bingPictureUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                getActivity().runOnUiThread(() -> MyToast.systemshow("获取图片失败，请检查网络状态"));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final List<BingDailyBean> bingList = ParseJsonUtils.handleBingPicResponseWithGson(response.body().string());
                getActivity().runOnUiThread(() -> {
                    //刷新
                    freshPicture(bingList);
                });
            }
        });
    }*/

    /**
     * 刷新RecyclerView
     *
     */
    /*private void freshPicture(List<BingDailyBean> nowList) {
        bingDailyBeanList.clear();
        //list集合不能变，变了之后就用下面注释代码，重新绑定Adapter
        bingDailyBeanList.addAll(nowList);
        bingPictureAdapter.notifyDataSetChanged();
    }*/

   /* private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        bingPictureAdapter = new BingPictureAdapter(bingDailyBeanList);
        recyclerView.setAdapter(bingPictureAdapter);
    }*/

    @Override
    protected void setPresenter() {
        presenter = new BingPicturePresenter(compositeDisposable);
        presenter.attachView(this);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        bingPictureAdapter = new BingPictureAdapter(bingDailyBeanList);
        recyclerView.setAdapter(bingPictureAdapter);
    }

    @Override
    public void onLazyLoad() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        presenter.loadData();
    }

    @Override
    public void onUpdateUI(List<BingDailyBean> list) {
        bingDailyBeanList.clear();
        bingDailyBeanList.addAll(list);
        bingPictureAdapter.notifyDataSetChanged();
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
