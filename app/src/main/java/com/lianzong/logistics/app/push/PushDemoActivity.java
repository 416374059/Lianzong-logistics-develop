package com.lianzong.logistics.app.push;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.lianzong.logistics.app.R;

import java.util.List;

public class PushDemoActivity extends Activity implements OnClickListener {

    private static final String TAG = PushDemoActivity.class.getSimpleName();
    RelativeLayout mainLayout = null;
    int akBtnId = 0;
    int initBtnId = 0;
    int richBtnId = 0;
    int setTagBtnId = 0;
    int delTagBtnId = 0;
    int clearLogBtnId = 0;
    int showTagBtnId = 0;
    int unbindBtnId = 0;
    int setunDisturbBtnId = 0;
    Button initButton = null;
    Button initWithApiKey = null;
    Button displayRichMedia = null;
    Button setTags = null;
    Button delTags = null;
    Button clearLog = null;
    Button showTags = null;
    Button unbind = null;
    Button setunDisturb = null;
    TextView logText = null;
    ScrollView scrollView = null;
    public static int initialCnt = 0;
    private boolean isLogin = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // checkApikey();
        Utils.logStringCache = Utils.getLogText(getApplicationContext());

        Resources resource = this.getResources();
        String pkgName = this.getPackageName();

        setContentView(resource.getIdentifier("main", "layout", pkgName));
        akBtnId = resource.getIdentifier("btn_initAK", "id", pkgName);
        initBtnId = resource.getIdentifier("btn_init", "id", pkgName);
        richBtnId = resource.getIdentifier("btn_rich", "id", pkgName);
        setTagBtnId = resource.getIdentifier("btn_setTags", "id", pkgName);
        delTagBtnId = resource.getIdentifier("btn_delTags", "id", pkgName);
        clearLogBtnId = resource.getIdentifier("btn_clear_log", "id", pkgName);
        showTagBtnId = resource.getIdentifier("btn_showTags", "id", pkgName);
        unbindBtnId = resource.getIdentifier("btn_unbindTags", "id", pkgName);
        setunDisturbBtnId = resource.getIdentifier("btn_setunDisturb", "id",
                pkgName);

        initWithApiKey = (Button) findViewById(akBtnId);
        initButton = (Button) findViewById(initBtnId);
        displayRichMedia = (Button) findViewById(richBtnId);
        setTags = (Button) findViewById(setTagBtnId);
        delTags = (Button) findViewById(delTagBtnId);
        clearLog = (Button) findViewById(clearLogBtnId);
        showTags = (Button) findViewById(showTagBtnId);
        unbind = (Button) findViewById(unbindBtnId);
        setunDisturb = (Button) findViewById(setunDisturbBtnId);

        logText = (TextView) findViewById(resource.getIdentifier("text_log",
                "id", pkgName));
        scrollView = (ScrollView) findViewById(resource.getIdentifier(
                "stroll_text", "id", pkgName));

        initWithApiKey.setOnClickListener(this);
        initButton.setOnClickListener(this);
        setTags.setOnClickListener(this);
        delTags.setOnClickListener(this);
        displayRichMedia.setOnClickListener(this);
        clearLog.setOnClickListener(this);
        showTags.setOnClickListener(this);
        unbind.setOnClickListener(this);
        setunDisturb.setOnClickListener(this);

        // Push: ???apikey?????????????????????????????????Activity???onCreate??????
        // ?????????apikey?????????manifest???????????????????????????????????????
        // ????????????????????????????????????????????????????????????????????????Utils.getMetaValue(PushDemoActivity.this,
        // "api_key")
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                Utils.getMetaValue(PushDemoActivity.this, "api_key"));
        // Push: ????????????????????????????????????????????????????????????????????????????????????
        // PushManager.enableLbs(getApplicationContext());

        // Push: ???????????????????????????????????????API??????????????????????????????????????????????????????????????????????????????
        // ??????????????????????????????????????????->???????????????->??????????????????????????????????????????1???
        // ?????????????????? PushManager.setNotificationBuilder(this, 1, cBuilder)???????????????????????????
        CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
                resource.getIdentifier(
                        "notification_custom_builder", "layout", pkgName),
                resource.getIdentifier("notification_icon", "id", pkgName),
                resource.getIdentifier("notification_title", "id", pkgName),
                resource.getIdentifier("notification_text", "id", pkgName));
        cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        cBuilder.setNotificationDefaults(Notification.DEFAULT_VIBRATE);
        cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
        cBuilder.setLayoutDrawable(resource.getIdentifier(
                "simple_notification_icon", "drawable", pkgName));
        cBuilder.setNotificationSound(Uri.withAppendedPath(
                Audio.Media.INTERNAL_CONTENT_URI, "6").toString());
        // ??????????????????????????????????????????????????????ID
        PushManager.setNotificationBuilder(this, 1, cBuilder);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == akBtnId) {
            initWithApiKey();
        } else if (v.getId() == initBtnId) {
            initWithBaiduAccount();
        } else if (v.getId() == richBtnId) {
            openRichMediaList();
        } else if (v.getId() == setTagBtnId) {
            setTags();
        } else if (v.getId() == delTagBtnId) {
            deleteTags();
        } else if (v.getId() == clearLogBtnId) {
            Utils.logStringCache = "";
            Utils.setLogText(getApplicationContext(), Utils.logStringCache);
            updateDisplay();
        } else if (v.getId() == showTagBtnId) {
            showTags();
        } else if (v.getId() == unbindBtnId) {
            unBindForApp();
        } else if (v.getId() == setunDisturbBtnId) {
            setunDistur();
        } else {

        }

    }

    // ???????????????????????????
    private void openRichMediaList() {
        // Push: ???????????????????????????
        Intent sendIntent = new Intent();
        sendIntent.setClassName(getBaseContext(),
                "com.baidu.android.pushservice.richmedia.MediaListActivity");
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PushDemoActivity.this.startActivity(sendIntent);
    }

    // ??????tag??????
    private void deleteTags() {
        LinearLayout layout = new LinearLayout(PushDemoActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText textviewGid = new EditText(PushDemoActivity.this);
        textviewGid.setHint("?????????????????????????????????????????????");
        layout.addView(textviewGid);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                PushDemoActivity.this);
        builder.setView(layout);
        builder.setPositiveButton("????????????",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Push: ??????tag????????????
                        List<String> tags = Utils.getTagsList(textviewGid
                                .getText().toString());
                        PushManager.delTags(getApplicationContext(), tags);
                    }
                });
        builder.show();
    }

    // ????????????,?????????????????????
    private void setTags() {
        LinearLayout layout = new LinearLayout(PushDemoActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText textviewGid = new EditText(PushDemoActivity.this);
        textviewGid.setHint("?????????????????????????????????????????????");
        layout.addView(textviewGid);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                PushDemoActivity.this);
        builder.setView(layout);
        builder.setPositiveButton("????????????",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Push: ??????tag????????????
                        List<String> tags = Utils.getTagsList(textviewGid
                                .getText().toString());
                        PushManager.setTags(getApplicationContext(), tags);
                    }

                });
        builder.show();
    }

    // ???apikey???????????????
    private void initWithApiKey() {
        // Push: ????????????????????????api key??????
        // checkApikey();
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                Utils.getMetaValue(PushDemoActivity.this, "api_key"));
    }

    // ??????????????????????????????access token?????????
    private void initWithBaiduAccount() {
        if (isLogin) {
            // ??????????????????Cookie, access token, ??????????????????
            CookieSyncManager.createInstance(getApplicationContext());
            CookieManager.getInstance().removeAllCookie();
            CookieSyncManager.getInstance().sync();

            isLogin = false;
            // initButton.setText("??????????????????");
        }
        // ??????????????????????????????activity
        Intent intent = new Intent(PushDemoActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    // ??????
    private void unBindForApp() {
        // Push: ??????
        PushManager.stopWork(getApplicationContext());
    }

    // ??????tag??????
    private void showTags() {
        PushManager.listTags(getApplicationContext());
    }

    // ?????????????????????
    private void setunDistur() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.bpush_setundistur_time);

        final TimePicker start_picker = (TimePicker) window
                .findViewById(R.id.start_time_picker);
        final TimePicker end_picker = (TimePicker) window
                .findViewById(R.id.end_time_picker);
        start_picker.setIs24HourView(true);
        end_picker.setIs24HourView(true);
        start_picker
                .setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        end_picker
                .setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

        Button set = (Button) window.findViewById(R.id.btn_set);
        set.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                int startHour = start_picker.getCurrentHour();
                int startMinute = start_picker.getCurrentMinute();
                int endHour = end_picker.getCurrentHour();
                int endMinute = end_picker.getCurrentMinute();

                if (startHour == 0 && startMinute == 0 && endHour == 0
                        && endMinute == 0) {
                    Toast.makeText(getApplicationContext(), "????????? ?????????????????????",
                            Toast.LENGTH_SHORT).show();
                } else if (startHour > endHour
                        || (startHour == endHour && startMinute > endMinute)) {
                    setToastText("????????????" + startHour + ":" + startMinute, "????????????"
                            + endHour + ":" + endMinute);
                } else {
                    setToastText(startHour + ":" + startMinute, endHour + ":"
                            + endMinute);
                }

                // Push: ?????????????????????
                // startHour startMinute????????? ?????? ???24???????????????????????? 0~23 0~59
                // endHour endMinute????????? ?????? ???24???????????????????????? 0~23 0~59
                PushManager.setNoDisturbMode(getApplicationContext(),
                        startHour, startMinute, endHour, endMinute);

                alertDialog.cancel();
            }

        });
        Button guide = (Button) window.findViewById(R.id.btn_guide);
        guide.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String str = "?????????????????????,?????????????????????,????????????????????????,?????????????????????????????????????????????????????????.\n\n????????????:\n1.?????????????????????????????????????????????????????????????????????????????????????????????.\n2.??????????????????????????????????????????????????????????????????????????????????????????????????????.\n3.????????????????????????????????????????????????00:00???,???????????????????????????.\n4.?????????????????????????????????????????????????????????????????????????????????????????????23:00???????????????7:00.\n";
                new AlertDialog.Builder(PushDemoActivity.this)
                        .setTitle("?????????????????????????????????").setMessage(str)
                        .setPositiveButton("??????", null).show();
            }
        });

        Button cancel = (Button) window.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                alertDialog.cancel();
            }

        });

    }

    private void setToastText(String start, String end) {
        String text = getString(R.string.text_toast, start, end);
        Log.i("tangshi", text);
        int indexTotal = 13 + start.length();
        int indexPosition = indexTotal + 3 + end.length();
        SpannableString s = new SpannableString(text);
        s.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.red)),
                13, indexTotal, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        s.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.red)),
                indexTotal + 3, indexPosition,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "??????").setIcon(
                android.R.drawable.ic_menu_info_details);

        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "??????").setIcon(
                android.R.drawable.ic_menu_help);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (Menu.FIRST + 1 == item.getItemId()) {
            showAbout();
            return true;
        }
        if (Menu.FIRST + 2 == item.getItemId()) {
            showHelp();
            return true;
        }

        return false;
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;
    }

    // ??????
    private void showAbout() {
        Dialog alertDialog = new AlertDialog.Builder(PushDemoActivity.this)
                .setTitle("??????").setMessage(R.string.text_about)
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                    }

                }).create();
        alertDialog.show();
    }

    // ??????
    private void showHelp() {
        Dialog alertDialog = new AlertDialog.Builder(PushDemoActivity.this)
                .setTitle("??????").setMessage(R.string.text_help)
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                    }

                }).create();
        alertDialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
        updateDisplay();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();

        if (Utils.ACTION_LOGIN.equals(action)) {
            // Push: ???????????????????????????access token??????
            String accessToken = intent
                    .getStringExtra(Utils.EXTRA_ACCESS_TOKEN);
            PushManager.startWork(getApplicationContext(),
                    PushConstants.LOGIN_TYPE_ACCESS_TOKEN, accessToken);

            isLogin = true;
            initButton.setText("??????????????????");
        }

        updateDisplay();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Utils.setLogText(getApplicationContext(), Utils.logStringCache);
        super.onDestroy();
    }

    // ????????????????????????
    private void updateDisplay() {
        Log.d(TAG, "updateDisplay, logText:" + logText + " cache: "
                + Utils.logStringCache);
        if (logText != null) {
            logText.setText(Utils.logStringCache);
        }
        if (scrollView != null) {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

}
