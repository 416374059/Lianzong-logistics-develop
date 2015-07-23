package com.lianzong.logistics.app.push;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

public class CustomActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources resource = this.getResources();
        String pkgName = this.getPackageName();

        setContentView(resource.getIdentifier("custom_activity", "layout",
                pkgName));
    }
}
