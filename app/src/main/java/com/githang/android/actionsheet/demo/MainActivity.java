package com.githang.android.actionsheet.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.Toast;

import com.githang.android.actionsheet.ActionSheetDialog;


public class MainActivity extends ActionBarActivity {
    private ActionSheetDialog mActionSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 创建MENU
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("menu").setVisible(false);// 必须创建一项,设为false之后ActionBar上不会出现菜单按钮。
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 拦截MENU事件，显示自己的菜单
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (mActionSheetDialog == null) {
            mActionSheetDialog = new ActionSheetDialog(this);
            mActionSheetDialog.addMenuItem("Test1").addMenuItem("Test2");
            mActionSheetDialog.setMenuListener(new ActionSheetDialog.MenuListener() {
                @Override
                public void onItemSelected(int position, String item) {
                    Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                }
            });
        }
        mActionSheetDialog.show();
        return true;
    }

}
