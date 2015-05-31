# ActionSheet
ActionSheet for Android.

##For non-Gradle project:

It's interesting.

##For Gradle project:
### Add dependencies

```Gradle
    compile 'com.githang:actionsheet:0.1@aar'
```

### Usage
```java
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
```