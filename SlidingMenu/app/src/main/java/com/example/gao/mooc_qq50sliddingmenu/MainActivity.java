package com.example.gao.mooc_qq50sliddingmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.gao.mooc_qq50sliddingmenu.view.SliddingMenu;

/**
 * Created by gao on 2016/5/23.
 */
public class MainActivity extends Activity {
    private SliddingMenu mLeftMenu;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mLeftMenu=(SliddingMenu)findViewById(R.id.id_menu);
    }

    public void toggleMenu(View view){
        mLeftMenu.toggle();
    }

}
