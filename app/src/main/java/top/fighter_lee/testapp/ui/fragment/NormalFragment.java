package top.fighter_lee.testapp.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.fighter_lee.testapp.R;
import top.fighter_lee.testapp.base.BaseFragment;
import top.fighter_lee.testapp.callback.ErrorCallback;
import top.fighter_lee.testapp.callback.LoadingCallback;
import top.fighter_lee.testapp.engine.Network;
import top.fighter_lee.testapp.info.MuntiOpen;
import top.fighter_lee.testapp.info.TicketOpenData;
import top.fighter_lee.testapp.ui.adapter.TicketHistoryAdapter;
import top.fighter_lee.testapp.utils.TimeUtils;

/**
 * @author fighter_lee
 * @date 2017/11/6
 */
public class NormalFragment extends BaseFragment {
    public static final String TICKET_LISTS = "ticket_lists";
    @BindView(R.id.rv_normal)
    RecyclerView rvNormal;
    private int mPage = 1;
    private static final String TAG = "NormalFragment";
    private TicketHistoryAdapter historyAdapter;
    private static final int PRELOAD_SIZE = 6;
    private String endTime;
    private ArrayList<TicketOpenData> allData;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_normal;
    }

    @Override
    protected void init(View view) {
        allData = new ArrayList<>();
        historyAdapter = new TicketHistoryAdapter();
        rvNormal.setAdapter(historyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvNormal.setLayoutManager(linearLayoutManager);
        rvNormal.addOnScrollListener(getOnBottomListener(linearLayoutManager));
        request();
    }

    private RecyclerView.OnScrollListener getOnBottomListener(final LinearLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >=
                                historyAdapter.getItemCount() - PRELOAD_SIZE;
                if (isBottom) {
                    mPage += 1;
                    request();
                }
            }
        };
    }

    private void request() {
        if (mPage == 1) {
            endTime = TimeUtils.milliseconds2String(System.currentTimeMillis());
        }
        Network.getNetApi()
                .getMutiPeriodCheck("http://route.showapi.com/44-2", "cqssc", endTime, "10")
                //                .flatMap(new Function<MuntiOpen, ObservableSource<?>>() {
                //                    @Override
                //                    public ObservableSource<?> apply(MuntiOpen muntiOpen) throws Exception {
                //                        List<TicketOpenData> list = muntiOpen.getShowapi_res_body().list;
                //                        return null;
                //                    }
                //                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MuntiOpen>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MuntiOpen muntiOpen) {
                        mBaseLoadService.showSuccess();
                        ArrayList<TicketOpenData> arrayList = (ArrayList) muntiOpen.getShowapi_res_body().list;
                        allData.addAll(arrayList);
                        TicketOpenData lastData = arrayList.get(arrayList.size() - 1);
                        endTime = lastData.getTime().trim();
                        historyAdapter.setData(allData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mBaseLoadService.showCallback(ErrorCallback.class);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        //        Network.getNetApi().getTicketList("http://route.showapi.com/44-6",null)
        //                .flatMap(new Function<ShowapiRresBody, Observable<ArrayList<TicketType>>>() {
        //                    @Override
        //                    public Observable<ArrayList<TicketType>> apply(ShowapiRresBody showapiRresBody) throws Exception {
        //                        ArrayList<TicketType> areaTypeList = new ArrayList<TicketType>();
        //                        for (TicketType ticketType : showapiRresBody.getShowapi_res_body().list) {
        //                            if (!ticketType.getArea().equals("") && !ticketType.getIssuer().equals("境外")) {
        //                                areaTypeList.add(ticketType);
        //                            }
        //                        }
        //                        Collections.sort(areaTypeList);
        //                        return Observable.just(areaTypeList);
        //                    }
        //                })
        //                .subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe(new Observer<ArrayList<TicketType>>() {
        //                    @Override
        //                    public void onSubscribe(Disposable d) {
        //
        //                    }
        //
        //                    @Override
        //                    public void onNext(ArrayList<TicketType> ticketTypes) {
        //                        Trace.d(TAG, "onNext() "+ticketTypes.size());
        //                        historyAdapter.setData(ticketTypes);
        //                    }
        //
        //                    @Override
        //                    public void onError(Throwable e) {
        //
        //                    }
        //
        //                    @Override
        //                    public void onComplete() {
        //
        //                    }
        //                });
    }

    @Override
    protected void onNetReload() {
        mBaseLoadService.showCallback(LoadingCallback.class);
        request();
    }

}
