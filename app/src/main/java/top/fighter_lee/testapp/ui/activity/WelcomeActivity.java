package top.fighter_lee.testapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.fighter_lee.testapp.R;
import top.fighter_lee.testapp.base.BaseActivity;
import top.fighter_lee.testapp.engine.Network;
import top.fighter_lee.testapp.info.CaiInfo;


public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.iv_welcome)
    ImageView ivWelcome;
    boolean isShow = false;

    @Override
    protected void initView(Bundle savedInstanceState) {

        Network.getNetApi()
                .getCaiInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CaiInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CaiInfo caiInfo) {

                        if ("1".equals(caiInfo.getData().getShow_url()) && !TextUtils.isEmpty(caiInfo.getData().getUrl())) {
                            isShow = true;
                        } else {
                            isShow = false;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        ivWelcome.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent;
                if (isShow){
                    intent = new Intent(WelcomeActivity.this, CaiActivity.class);
                }else{
                    intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                }

                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                WelcomeActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_welcome;
    }

}
