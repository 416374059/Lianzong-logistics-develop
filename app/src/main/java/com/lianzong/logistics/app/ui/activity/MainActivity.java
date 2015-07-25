package com.lianzong.logistics.app.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lianzong.logistics.app.LogisticsApplication;
import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.push.PushDemoActivity;
import com.lianzong.logistics.app.ui.fragment.ContactFragment;
import com.lianzong.logistics.app.ui.fragment.GoodsListFragment;
import com.lianzong.logistics.app.ui.fragment.HelpFragment;
import com.lianzong.logistics.app.ui.fragment.MyVehiclesListFragment;
import com.lianzong.logistics.app.ui.fragment.SettingFragment;
import com.lianzong.logistics.app.ui.view.fab.FBMainActivity;
import com.lianzong.logistics.app.ui.view.pulltorefresh.XListViewActivity;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

public class MainActivity extends AppCompatActivity {
    private static final int PROFILE_SETTING = 1;

    // drawer item Identifier ID
    // app function item
    private static final int IDENTIFIER_GOODS = 1;
    private static final int IDENTIFIER_MY_VEHICLES = IDENTIFIER_GOODS + 1;
    // settings item
    private static final int IDENTIFIER_SETTING = 50;
    private static final int IDENTIFIER_HELP = IDENTIFIER_SETTING + 1;
    private static final int IDENTIFIER_CONTACT = IDENTIFIER_SETTING + 2;
    // debug item
    private static final int IDENTIFIER_DEBUG_PUSH = 100;
    private static final int IDENTIFIER_DEBUG_XLIST_VIEW = IDENTIFIER_DEBUG_PUSH + 1;
    private static final int IDENTIFIER_DEBUG_FLOAGION_ACTION_WIDGETS = IDENTIFIER_DEBUG_PUSH + 2;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private IProfile defaultProfile;

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
            defaultProfile = profile3;

            // Create the AccountHeader
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.header)
                    .addProfiles(
                            profile,
                            profile2,
                            profile3,
                            profile4,
                            profile5,
                            profile6,
                            //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                            new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(PROFILE_SETTING),
                            new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
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

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_goods).withIcon(FontAwesome.Icon.faw_cubes).withBadge("99").withIdentifier(IDENTIFIER_GOODS),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_my_vehicles).withIcon(FontAwesome.Icon.faw_truck).withBadge("9").withIdentifier(IDENTIFIER_MY_VEHICLES),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cogs).withIdentifier(IDENTIFIER_SETTING),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withIdentifier(IDENTIFIER_HELP),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_phone).withIdentifier(IDENTIFIER_CONTACT),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_debug_push).withIcon(FontAwesome.Icon.faw_automobile).withIdentifier(IDENTIFIER_DEBUG_PUSH).setEnabled(LogisticsApplication.sIsVersionDebug),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_debug_xlistview).withIcon(FontAwesome.Icon.faw_barcode).withIdentifier(IDENTIFIER_DEBUG_XLIST_VIEW),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_debug_fab).withIcon(FontAwesome.Icon.faw_apple).withIdentifier(IDENTIFIER_DEBUG_FLOAGION_ACTION_WIDGETS)
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
                                getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
                                switch (drawerItem.getIdentifier()) {
                                    case IDENTIFIER_GOODS:
                                        Fragment goodsListFragment = GoodsListFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, goodsListFragment).commit();
                                        break;
                                    case IDENTIFIER_MY_VEHICLES:
                                        Fragment myVehiclesListFragment = MyVehiclesListFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myVehiclesListFragment).commit();
                                        break;
                                    case IDENTIFIER_SETTING:
                                        Fragment settingFragment = SettingFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, settingFragment).commit();
                                        break;
                                    case IDENTIFIER_HELP:
                                        Fragment helpFragment = HelpFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, helpFragment).commit();
                                        break;
                                    case IDENTIFIER_CONTACT:
                                        Fragment contactFragment = ContactFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, contactFragment).commit();
                                        break;
                                    case IDENTIFIER_DEBUG_PUSH:
                                        Intent pushDemoActivity = new Intent(MainActivity.this, PushDemoActivity.class);
                                        MainActivity.this.startActivity(pushDemoActivity);
                                        break;
                                    case IDENTIFIER_DEBUG_XLIST_VIEW:
                                        Intent xlistviewDemoActivity = new Intent(MainActivity.this, XListViewActivity.class);
                                        MainActivity.this.startActivity(xlistviewDemoActivity);
                                        break;
                                    case IDENTIFIER_DEBUG_FLOAGION_ACTION_WIDGETS:
                                        Intent fBMainActivity = new Intent(MainActivity.this, FBMainActivity.class);
                                        MainActivity.this.startActivity(fBMainActivity);
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
        result.setSelectionByIdentifier(IDENTIFIER_GOODS);

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 11
            result.setSelectionByIdentifier(11, false);

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
}