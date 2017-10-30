package top.fighter_lee.testapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import top.fighter_lee.testapp.R;
import top.fighter_lee.testapp.base.BaseActivity;

/**
 * @author fighter_lee
 * @date 2017/10/30
 */

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.iv_welcome)
    ImageView ivWelcome;

    @Override
    protected void initView(Bundle savedInstanceState) {
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
