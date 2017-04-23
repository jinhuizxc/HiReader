package com.example.jh.hireader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jh.hireader.R;
import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.builder.Item;
import com.vansuita.materialabout.views.AboutView;

import java.util.List;

/**
 * Created by jinhui  on 2017/4/23
 * 邮箱: 1004260403@qq.com
 *
 * 关于界面可以拓展，将每一个item进行监听！
 * 功能方面，例如更换头像，进行支付宝捐赠等。
 */

public class AboutActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        LinearLayout layoutParams = (LinearLayout)findViewById(R.id.aboutView);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        Intent intent = new Intent();
        AboutView view = AboutBuilder.with(this)
                .setBackgroundColor(R.color.mdtp_background_color)
                .setPhoto(R.drawable.tou)
                .setCover(R.mipmap.profile_cover)
                .setName("jinhuizxc")
                .setSubTitle("Android Develop")
                .setBrief("I'm warmed of mobile technologies. Ideas maker, curious and nature lover.")
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .setLinksColumnsCount(2)
                .addGitHubLink("jinhuizxc")
                .addEmailLink("1004260403@qq.com")
                .addIntroduceAction(intent)
                .addFiveStarsAction()
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .addMoreFromMeAction("user")
                .addDonateAction(intent)
                .addUpdateAction()
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .build();

        addContentView(view, p);


    }
}

