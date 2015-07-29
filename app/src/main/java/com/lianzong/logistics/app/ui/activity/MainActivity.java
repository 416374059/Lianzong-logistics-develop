package com.lianzong.logistics.app.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.lianzong.logistics.app.LogisticsApplication;
import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.push.PushDemoActivity;
import com.lianzong.logistics.app.ui.fragment.HelpFragment;
import com.lianzong.logistics.app.ui.fragment.LogisticsFragment;
import com.lianzong.logistics.app.ui.fragment.SettingFragment;
import com.lianzong.logistics.app.ui.view.fab.FBMainActivity;
import com.lianzong.logistics.app.ui.view.observableviews.fragment.BaseViewPagerParentFragment;
import com.lianzong.logistics.app.ui.view.observableviews.fragment.FlexibleSpaceWithImageWithToolBarFragment;
import com.lianzong.logistics.app.ui.view.pulltorefresh.XListViewActivity;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

public class MainActivity extends BaseActivity {
    private static final int PROFILE_SETTING = 0;

    // drawer item Identifier ID
    // primary app function item
    private static final int IDENTIFIER_PRIMARY_LOGISTICS = 1;
    private static final int IDENTIFIER_PRIMARY_IM = IDENTIFIER_PRIMARY_LOGISTICS + 1;
    private static final int IDENTIFIER_PRIMARY_MARKET = IDENTIFIER_PRIMARY_LOGISTICS + 2;
    private static final int IDENTIFIER_PRIMARY_JOBS = IDENTIFIER_PRIMARY_LOGISTICS + 3;
    // secondary item
    private static final int IDENTIFIER_SECONDARY_MIME = 50;
    private static final int IDENTIFIER_SECONDARY_SETTING= IDENTIFIER_SECONDARY_MIME + 1;
    // secondary item 2
    private static final int IDENTIFIER_SECONDARY_AUTO_INSURAMCE = 100;
    private static final int IDENTIFIER_SECONDARY_WEATHER= IDENTIFIER_SECONDARY_AUTO_INSURAMCE + 1;
    private static final int IDENTIFIER_SECONDARY_CALL_SERVICES= IDENTIFIER_SECONDARY_AUTO_INSURAMCE + 2;
    // debug item
    private static final int IDENTIFIER_DEBUG_PUSH = 1000;
    private static final int IDENTIFIER_DEBUG_REFRESH_LIST_VIEW = IDENTIFIER_DEBUG_PUSH + 1;
    private static final int IDENTIFIER_DEBUG_FLOATION_ACTION_WIDGETS = IDENTIFIER_DEBUG_PUSH + 2;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private IProfile defaultProfile;

    // fragment state
    private int mLastFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Remove line to test RTL support
        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (LogisticsApplication.sIsSuportAccount) {
            // Create a few sample profile
            // NOTE you have to define the loader logic too. See the LogisticsApplication for more details
            final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
            final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));
            final IProfile profile3 = new ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile2));
            final IProfile profile4 = new ProfileDrawerItem().withName("Felix House").withEmail("felix.house@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile3));
            final IProfile profile5 = new ProfileDrawerItem().withName("Mr. X").withEmail("mister.x.super@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile4)).withIdentifier(4);
            final IProfile profile6 = new ProfileDrawerItem().withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));
            defaultProfile = profile;

            // Create the AccountHeader
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.header)
                    .addProfiles(
                            profile
//                            profile2,
//                            profile3,
//                            profile4,
//                            profile5,
//                            profile6,
//                            //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
//                            new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(PROFILE_SETTING),
//                            new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                    )
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                            //sample usage of the onProfileChanged listener
                            //if the clicked item has the identifier 1 add a new profile ;)
                            if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                                IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));
                                if (headerResult.getProfiles() != null) {
                                    //we know that there are 2 setting elements. set the new profile above them ;)
                                    headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                                } else {
                                    headerResult.addProfiles(newProfile);
                                }
                            }

                            //false if you have not consumed the event and it should close the drawer
                            return false;
                        }
                    })
                    .withSavedInstance(savedInstanceState)
                    .build();
        } else {
            // Create the AccountHeader
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.header)
                    .withSavedInstance(savedInstanceState)
                    .build();
        }

        //  create the footer
//        ImageView footerView = new ImageView(MainActivity.this);
//        footerView.setImageResource(R.drawable.footer);

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
//                .withFooter(footerView)
//                .withFooterDivider(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_primary_logistics).withIcon(FontAwesome.Icon.faw_truck).withIdentifier(IDENTIFIER_PRIMARY_LOGISTICS),
//                        new PrimaryDrawerItem().withName(R.string.drawer_primary_im).withIcon(FontAwesome.Icon.faw_comments).withBadge("9").withIdentifier(IDENTIFIER_PRIMARY_IM).withVisibility(LogisticsApplication.sNonOnlineModuleVisibility),
//                        new PrimaryDrawerItem().withName(R.string.drawer_primary_market).withIcon(FontAwesome.Icon.faw_shopping_cart).withIdentifier(IDENTIFIER_PRIMARY_MARKET).withVisibility(LogisticsApplication.sNonOnlineModuleVisibility),
//                        new PrimaryDrawerItem().withName(R.string.drawer_primary_jobs).withIcon(FontAwesome.Icon.faw_group).withIdentifier(IDENTIFIER_PRIMARY_JOBS).withVisibility(LogisticsApplication.sNonOnlineModuleVisibility),
                        new DividerDrawerItem(),
//                        new DividerDrawerItem().withDividerHeight(50).withDividerColor(MainActivity.this.getResources().getColor(R.color.transparent)),
//                        new SecondaryDrawerItem().withName(R.string.drawer_secondary_auto_insurance).withIcon(FontAwesome.Icon.faw_inbox).withIdentifier(IDENTIFIER_SECONDARY_AUTO_INSURAMCE),
                        new SecondaryDrawerItem().withName(R.string.drawer_secondary_weather).withIcon(FontAwesome.Icon.faw_wechat).withIdentifier(IDENTIFIER_SECONDARY_WEATHER),
                        new SecondaryDrawerItem().withName(R.string.drawer_secondary_call_services).withIcon(FontAwesome.Icon.faw_yelp).withIdentifier(IDENTIFIER_SECONDARY_CALL_SERVICES),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_secondary_mine).withIcon(FontAwesome.Icon.faw_user).withIdentifier(IDENTIFIER_SECONDARY_MIME),
                        new SecondaryDrawerItem().withName(R.string.drawer_secondary_settings).withIcon(FontAwesome.Icon.faw_cogs).withIdentifier(IDENTIFIER_SECONDARY_SETTING)
//                        new DividerDrawerItem().withVisibility(LogisticsApplication.sDebugModuleVisibility),
//                        new SecondaryDrawerItem().withName(R.string.drawer_test_debug_push).withIcon(FontAwesome.Icon.faw_apple).withIdentifier(IDENTIFIER_DEBUG_PUSH).withVisibility(LogisticsApplication.sDebugModuleVisibility),
//                        new SecondaryDrawerItem().withName(R.string.drawer_test_debug_xlistview).withIcon(FontAwesome.Icon.faw_apple).withIdentifier(IDENTIFIER_DEBUG_REFRESH_LIST_VIEW).withVisibility(LogisticsApplication.sDebugModuleVisibility),
//                        new SecondaryDrawerItem().withName(R.string.drawer_test_debug_fab).withIcon(FontAwesome.Icon.faw_apple).withIdentifier(IDENTIFIER_DEBUG_FLOATION_ACTION_WIDGETS).withVisibility(LogisticsApplication.sDebugModuleVisibility)
                ) // add the items we want to use with our Drawer
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
//                        Toast.makeText(MainActivity.this, "onDrawerOpened", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
//                        Toast.makeText(MainActivity.this, "onDrawerClosed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            if (drawerItem instanceof Nameable) {
                                switch (drawerItem.getIdentifier()) {
                                    // primary items
                                    case IDENTIFIER_PRIMARY_LOGISTICS:
                                        getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, LogisticsFragment.newInstance()).commitAllowingStateLoss();
                                        mLastFragmentId = IDENTIFIER_PRIMARY_LOGISTICS;
                                        break;
                                    case IDENTIFIER_PRIMARY_IM:
                                        getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
//                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, VehiclesListFragment.newInstance()).commitAllowingStateLoss();
                                        mLastFragmentId = IDENTIFIER_PRIMARY_IM;
                                        break;
                                    case IDENTIFIER_PRIMARY_MARKET:
                                        getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
//                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, VehiclesListFragment.newInstance()).commitAllowingStateLoss();
                                        mLastFragmentId = IDENTIFIER_PRIMARY_MARKET;
                                        break;
                                    case IDENTIFIER_PRIMARY_JOBS:
                                        getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
//                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, VehiclesListFragment.newInstance()).commitAllowingStateLoss();
                                        mLastFragmentId = IDENTIFIER_PRIMARY_JOBS;
                                        break;

                                    // secondary items
                                    case IDENTIFIER_SECONDARY_MIME:
                                        getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, HelpFragment.newInstance()).commitAllowingStateLoss();
                                        mLastFragmentId = IDENTIFIER_SECONDARY_MIME;
                                        break;
                                    case IDENTIFIER_SECONDARY_SETTING:
                                        getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SettingFragment.newInstance()).commitAllowingStateLoss();
                                        mLastFragmentId = IDENTIFIER_SECONDARY_SETTING;
                                        break;
                                    case IDENTIFIER_SECONDARY_AUTO_INSURAMCE:
                                        getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, HelpFragment.newInstance()).commitAllowingStateLoss();
                                        mLastFragmentId = IDENTIFIER_SECONDARY_MIME;
                                        break;
                                    case IDENTIFIER_SECONDARY_WEATHER:
                                        getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());

                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FlexibleSpaceWithImageWithToolBarFragment.newInstance()).commitAllowingStateLoss();
                                        mLastFragmentId = IDENTIFIER_SECONDARY_SETTING;
                                        break;
                                    case IDENTIFIER_SECONDARY_CALL_SERVICES:
                                        getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, BaseViewPagerParentFragment.newInstance()).commitAllowingStateLoss();
                                        mLastFragmentId = IDENTIFIER_SECONDARY_MIME;
                                        break;
//                                    case IDENTIFIER_CONTACT:
////                                        getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
//
////                                        Fragment contactFragment = ContactFragment.newInstance(title);
////                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, contactFragment).commit();
//
//                                        Intent intent = new Intent();
//                                        intent.setClass(MainActivity.this, VehicleListActivity.class);
//                                        intent.putExtra(BaseActivity.KEY_TITLE, getResources().getText(R.string.activity_title_my_vehicles));
//                                        MainActivity.this.startActivityForResult(intent, IDENTIFIER_CONTACT);
//                                        break;

                                    // debug items
                                    case IDENTIFIER_DEBUG_PUSH:
                                        MainActivity.this.startActivityForResult(new Intent(MainActivity.this, PushDemoActivity.class), IDENTIFIER_DEBUG_PUSH);
                                        break;
                                    case IDENTIFIER_DEBUG_REFRESH_LIST_VIEW:
                                        MainActivity.this.startActivityForResult(new Intent(MainActivity.this, XListViewActivity.class), IDENTIFIER_DEBUG_REFRESH_LIST_VIEW);
                                        break;
                                    case IDENTIFIER_DEBUG_FLOATION_ACTION_WIDGETS:
                                        MainActivity.this.startActivityForResult(new Intent(MainActivity.this, FBMainActivity.class), IDENTIFIER_DEBUG_FLOATION_ACTION_WIDGETS);
                                        break;
                                    default:
                                        break;
                                }
                            }

                            if (drawerItem instanceof Badgeable) {
                                Badgeable badgeable = (Badgeable) drawerItem;
                                if (badgeable.getBadge() != null) {
                                    //note don't do this if your badge contains a "+"
//                                    int badge = Integer.valueOf(badgeable.getBadge());
//                                    if (badge > 0) {
//                                        result.updateBadge(String.valueOf(badge - 1), position);
//                                    }
                                }
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();
        // first goto the goods item
        result.setSelectionByIdentifier(IDENTIFIER_PRIMARY_LOGISTICS);

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            result.setSelectionByIdentifier(IDENTIFIER_PRIMARY_LOGISTICS, false);

            //set the active profile
            if (LogisticsApplication.sIsSuportAccount) {
                headerResult.setActiveProfile(defaultProfile);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("wsl", "onActivityResult: mLastFragmentId = " + mLastFragmentId + ", requestCode = " + requestCode + ", resultCode = " + resultCode);
        result.setSelectionByIdentifier(mLastFragmentId);

        super.onActivityResult(requestCode, resultCode, data);
    }
}