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
 * ActionSheet for Android.
 * @author haohang (msdx.android@qq.com)
 * @version 0.2
 * @since 0.1
 */
public class ActionSheetDialog extends Dialog {
    private Button mCancel;
    private ListView mMenuItems;
    private ArrayAdapter<String> mAdapter;

    private MenuListener mMenuListener;
    private View mRootView;

    private MenuBackground mMenuBg = new MenuBackground(R.drawable.menu_item_top,
            R.drawable.menu_item_middle, R.drawable.menu_item_bottom, R.drawable.menu_item_single);
    private Animation mShowAnim;
    private Animation mDismissAnim;

    private boolean isDismissing;

    public ActionSheetDialog(Context context) {
        super(context, R.style.ActionSheetDialog);
        getWindow().setGravity(Gravity.BOTTOM);
        initView(context);
    }

    private void initView(Context context) {
        mRootView = View.inflate(context, R.layout.dialog_action_sheet, null);
        mCancel = (Button) mRootView.findViewById(R.id.menu_cancel);
        mCancel.setText(android.R.string.cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

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
                    view.setBackgroundResource(mMenuBg.single);
                } else if (position == 0) {
                    view.setBackgroundResource(mMenuBg.top);
                } else if (position == count - 1) {
                    view.setBackgroundResource(mMenuBg.bottom);
                } else {
                    view.setBackgroundResource(mMenuBg.middle);
                }
            }
        };
        mMenuItems.setAdapter(mAdapter);
        this.setContentView(mRootView);
        initAnim(context);
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
                if (mMenuListener != null) {
                    mMenuListener.onCancel();
                }
            }
        });
    }

    private void initAnim(Context context) {
        setShowAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_up));
        setDismissAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_down));
    }

    /**
     * @param animation Showing animation.
     * @since 0.2
     */
    public void setShowAnimation(Animation animation) {
        mShowAnim = animation;
    }

    /**
     * @param animation Dismissing animation.
     * @since 0.2
     */
    public void setDismissAnimation(Animation animation) {
        mDismissAnim = animation;
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

    /**
     * Add menu item.
     * @param item The text of the item be added.
     * @return
     * @since 0.1
     */
    public ActionSheetDialog addMenuItem(String item) {
        mAdapter.add(item);
        return this;
    }

    /**
     * Show or dismiss menu.
     * @since 0.1
     */
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

    /**
     * Return the menu listener.
     * @return
     */
    public MenuListener getMenuListener() {
        return mMenuListener;
    }

    /**
     * Set the menu listener
     * @param menuListener
     * @isnce 0.1
     */
    public void setMenuListener(MenuListener menuListener) {
        mMenuListener = menuListener;
    }

    /**
     * Set the menu item background.
     * @param top The top item's background.
     * @param middle The middle item's background.
     * @param bottom The bottom item's background.
     * @param single The background of the item, if there is only one in menu.
     * @since 0.2
     */
    public void setMenuBackground(int top, int middle, int bottom, int single) {
        mMenuBg.top = top;
        mMenuBg.middle = middle;
        mMenuBg.bottom = bottom;
        mMenuBg.single = single;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Menu listener.
     * @since 0.1
     */
    public interface MenuListener {
        /**
         * When one of the menu items is selected, this method is called.
         * @param position the position of the menu item
         * @param item the text of the menu item
         * @since 0.1
         */
        void onItemSelected(int position, String item);

        /**
         * This method is called when cancel the menu.
         * @since 0.1
         */
        void onCancel();
    }

    /**
     * The background of each item of menu.
     * @since 0.2
     */
    public static class MenuBackground {
        public int top;
        public int middle;
        public int bottom;
        public int single;

        public MenuBackground() {}

        public MenuBackground(int top, int middle, int bottom, int single) {
            this.top = top;
            this.middle = middle;
            this.bottom = bottom;
            this.single = single;
        }
    }
}
