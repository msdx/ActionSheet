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
     * Create menu, add a menu item but set it invisible.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("menu").setVisible(false);// Create one and set invisible then the menu icon would not show in actionbar.
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * intercept the open menu event to show the custom menu.
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
