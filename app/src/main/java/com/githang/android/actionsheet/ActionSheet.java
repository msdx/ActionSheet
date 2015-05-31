package com.githang.android.actionsheet;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * @author haohang
 * @version 15-5-24.
 */
public class ActionSheet extends Dialog {
    private Button mCancel;
    private ListView mMenuItems;
    private ArrayAdapter<String> mAdapter;

    private MenuListener mMenuListener;
    private View mRootView;

    private Animation mShowAnim;
    private Animation mDismissAnim;

    private boolean isDismissing;

    public ActionSheet(Context context) {
        super(context, R.style.ActionSheetDialog);
        getWindow().setGravity(Gravity.BOTTOM);
        initView(context);
    }

    private void initView(Context context) {
        mRootView = View.inflate(context, R.layout.dialog_action_sheet, null);
        mCancel = (Button) mRootView.findViewById(R.id.menu_cancel);
        mMenuItems = (ListView) mRootView.findViewById(R.id.menu_items);
        mAdapter = new ArrayAdapter<String>(context, R.layout.menu_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                setBackground(position, view);
                return view;
            }

            private void setBackground(int position, View view) {
                int count = getCount();
                if (count == 1) {
                    view.setBackgroundResource(R.drawable.menu_item_single);
                } else if (position == 0) {
                    view.setBackgroundResource(R.drawable.menu_item_top);
                } else if (position == count - 1) {
                    view.setBackgroundResource(R.drawable.menu_item_bottom);
                } else {
                    view.setBackgroundResource(R.drawable.menu_item_middle);
                }
            }
        };
        mMenuItems.setAdapter(mAdapter);
        this.setContentView(mRootView);
        initAnim(context);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        mMenuItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mMenuListener != null) {
                    mMenuListener.onItemSelected(position, mAdapter.getItem(position));
                    dismiss();
                }
            }
        });
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(mMenuListener != null) {
                    mMenuListener.onCancel();
                }
            }
        });
    }

    private void initAnim(Context context) {
        mShowAnim = AnimationUtils.loadAnimation(context, R.anim.translate_up);
        mDismissAnim = AnimationUtils.loadAnimation(context, R.anim.translate_down);
        mDismissAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismissMe();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public ActionSheet addMenuItem(String items) {
        mAdapter.add(items);
        return this;
    }

    public void toggle() {
        if (isShowing()) {
            dismiss();
        } else {
            show();
        }
    }

    @Override
    public void show() {
        mAdapter.notifyDataSetChanged();
        super.show();
        mRootView.startAnimation(mShowAnim);
    }

    @Override
    public void dismiss() {
        if(isDismissing) {
            return;
        }
        isDismissing = true;
        mRootView.startAnimation(mDismissAnim);
    }

    private void dismissMe() {
        super.dismiss();
        isDismissing = false;
    }

    public MenuListener getMenuListener() {
        return mMenuListener;
    }

    public void setMenuListener(MenuListener menuListener) {
        mMenuListener = menuListener;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    interface MenuListener {
        void onItemSelected(int position, String item);

        void onCancel();
    }
}
