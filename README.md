# ActionSheet
ActionSheet for Android.

 [ ![Download](https://api.bintray.com/packages/msdx/maven/ActionSheet/images/download.svg) ](https://bintray.com/msdx/maven/ActionSheet/_latestVersion)

## Installation
### For non-Gradle project:

It's interesting.

###For Gradle project:

Include it into the dependencies section in your project's `build.gradle`:

```Gradle
    compile 'com.githang:actionsheet:0.2@aar'
```

## Usage
```java
    /**
     * Create menu, add a menu item but set it invisible.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("menu").setVisible(false);// Create one and set invisible then the menu icon would not show in actionbar.
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Intercept the open menu event to show the custom menu.
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

You need to merge the node into your `AppTheme` in styles.xml:
```xml
        <item name="ActionSheetList">@style/ActionSheetList</item>
        <item name="ActionSheetItem">@style/ActionSheetItem</item>
        <item name="ActionSheetCancel">@style/ActionSheetCancel</item>
```
