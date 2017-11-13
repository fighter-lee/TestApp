package top.fighter_lee.testapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import top.fighter_lee.testapp.R;
import top.fighter_lee.testapp.base.BaseActivity;
import top.fighter_lee.testapp.info.MSG;
import top.fighter_lee.testapp.utils.SPUtils;


public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.iv_welcome)
    ImageView ivWelcome;

    @Override
    protected void initView(Bundle savedInstanceState) {
        BmobQuery<MSG> bmobQuery = new BmobQuery<MSG>();
        bmobQuery.getObject("s69JAAAG", new QueryListener<MSG>() {
            @Override
            public void done(MSG object, BmobException e) {
                if (e == null) {
                    if (object.getIsshow() && !TextUtils.isEmpty(object.getWeb()) && !TextUtils.isEmpty(object.getPay())) {
                        new SPUtils("Test").put(HomeActivity.IS_SHOW,true);
                    }
                }
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
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
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
