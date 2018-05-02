package com.walidbarakat.newprogrammer.mazraaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class EnterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int[] menuform = {R.drawable.insert, R.drawable.followsheep, R.drawable.lamb, R.drawable.reports, R.drawable.alarm, R.drawable.gallery2};
    int[] menuform2 = {R.drawable.followsheep, R.drawable.lamb, R.drawable.delete};
    int[] menuform3 = {R.drawable.immunization, R.drawable.medicin, R.drawable.wean};
    String Indextype = "MainMenuItems";
    DB_Sqlite db = new DB_Sqlite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);


        final ListView list = (ListView) findViewById(R.id.ListViewShow);

        String[] menu = getResources().getStringArray(R.array.menu);
        final ArrayList<MenuItems> menuItemv = new ArrayList<MenuItems>();

        for (int i = 0; i < menu.length; i++) {
            menuItemv.add(new MenuItems(i, menuform[i], menu[i]));
        }

        ListAdaptor arryadp = new ListAdaptor(menuItemv);
        list.setAdapter(arryadp);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txttest = (TextView) view.findViewById(R.id.MenuTxt);
                //Toast.makeText(EnterActivity.this,txttest.getText(),Toast.LENGTH_SHORT).show();

                if (txttest.getText().equals("ادخال البيانات")) {
                    Intent insert = new Intent(EnterActivity.this, DataEntry.class);
                    startActivity(insert);
                } else if (txttest.getText().equals("متابعة الامهات")) {
                    Intent Mothers = new Intent(EnterActivity.this, FollowMotherActivity.class);
                    startActivity(Mothers);
                } else if (txttest.getText().equals("معرض الصور")) {
                    Intent Mothers = new Intent(EnterActivity.this, Gallery.class);
                    startActivity(Mothers);
                } else if (txttest.getText().equals("متابعة المواليد")) {
                    Intent Baby = new Intent(EnterActivity.this, FollowBabyActivity.class);
                    startActivity(Baby);
                } else if (txttest.getText().equals("التذكيرات")) {
                    if (Indextype.equals("MainMenuItems")) {
                        menuItemv.clear();
                        String[] menu = getResources().getStringArray(R.array.menu3);
                        ArrayList<MenuItems> menuItemv = new ArrayList<MenuItems>();

                        for (int i = 0; i < menu.length; i++) {
                            menuItemv.add(new MenuItems(i, menuform3[i], menu[i]));
                        }

                        ListAdaptor arryadp = new ListAdaptor(menuItemv);
                        list.setAdapter(arryadp);
                        Indextype = "SecondMenuItems2";
                    }
                } else if (txttest.getText().equals("التقارير")) {
                    if (Indextype.equals("MainMenuItems")) {
                        menuItemv.clear();
                        String[] menu = getResources().getStringArray(R.array.menu2);
                        ArrayList<MenuItems> menuItemv = new ArrayList<MenuItems>();

                        for (int i = 0; i < menu.length; i++) {
                            menuItemv.add(new MenuItems(i, menuform2[i], menu[i]));
                        }

                        ListAdaptor arryadp = new ListAdaptor(menuItemv);
                        list.setAdapter(arryadp);
                        Indextype = "SecondMenuItems";
                    }

                } else if (txttest.getText().equals("تقرير متابعة الامهات")) {
                    Intent Mothers = new Intent(EnterActivity.this, MotherReport.class);
                    startActivity(Mothers);
                    Indextype = "SecondMenuItems";
                } else if (txttest.getText().equals("تقرير متابعة المواليد")) {
                    Intent Baby = new Intent(EnterActivity.this, BabyReport.class);
                    startActivity(Baby);
                    Indextype = "SecondMenuItems";
                } else if (txttest.getText().equals("تقرير العزل")) {
                    Intent Delete = new Intent(EnterActivity.this, DeleteReport.class);
                    startActivity(Delete);
                    Indextype = "SecondMenuItems";
                } else if (txttest.getText().equals("التحصينات")) {
                    Intent Tahsyn = new Intent(EnterActivity.this, TahsynActivity.class);
                    startActivity(Tahsyn);
                    Indextype = "SecondMenuItems2";
                } else if (txttest.getText().equals("العلاج")) {
                    Intent Medicin = new Intent(EnterActivity.this, MedicinActivity.class);
                    startActivity(Medicin);
                    Indextype = "SecondMenuItems2";
                } else if (txttest.getText().equals("الفطام")) {
                    Intent Endb = new Intent(EnterActivity.this, EndBaby.class);
                    startActivity(Endb);
                    Indextype = "SecondMenuItems2";
                }
            }

        });

        //--------------------------------------------------------------------------------
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        } catch (Exception k) {
            Log.e("", k.getMessage());
        }
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        */
        if (Indextype.equals("MainMenuItems")) {
            super.onBackPressed();
        } else if (Indextype.equals("SecondMenuItems")) {
            Indextype = "MainMenuItems";
            ListView list = (ListView) findViewById(R.id.ListViewShow);
            String[] menu = getResources().getStringArray(R.array.menu);
            final ArrayList<MenuItems> menuItemv = new ArrayList<MenuItems>();

            for (int i = 0; i < menu.length; i++) {
                menuItemv.add(new MenuItems(i, menuform[i], menu[i]));
            }

            ListAdaptor arryadp = new ListAdaptor(menuItemv);
            list.setAdapter(arryadp);
        } else if (Indextype.equals("SecondMenuItems2")) {
            Indextype = "MainMenuItems";
            ListView list = (ListView) findViewById(R.id.ListViewShow);
            String[] menu = getResources().getStringArray(R.array.menu);
            final ArrayList<MenuItems> menuItemv = new ArrayList<MenuItems>();

            for (int i = 0; i < menu.length; i++) {
                menuItemv.add(new MenuItems(i, menuform[i], menu[i]));
            }

            ListAdaptor arryadp = new ListAdaptor(menuItemv);
            list.setAdapter(arryadp);
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    private class ListAdaptor extends BaseAdapter {
        ArrayList<MenuItems> menuitems = new ArrayList<MenuItems>();

        ListAdaptor(ArrayList<MenuItems> menuitems) {
            this.menuitems = menuitems;
        }

        @Override
        public int getCount() {
            return menuitems.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return menuitems.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();//to get enter_menu_item.xml file
            View view = layoutInflater.inflate(R.layout.enter_menu_form, null);

            TextView id = (TextView) view.findViewById(R.id.Txtid);
            TextView text = (TextView) view.findViewById(R.id.MenuTxt);
            ImageView image = (ImageView) view.findViewById(R.id.MenuImage);

            id.setText(String.valueOf(menuitems.get(position).id));
            image.setImageResource(menuitems.get(position).img);
            text.setText(menuitems.get(position).name);


            return view;
        }

    }

    //------------------------------------------------------------------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Menu_Insert) {
            Intent insert = new Intent(EnterActivity.this, DataEntry.class);
            startActivity(insert);
        } else if (id == R.id.Menu_FollowSheep) {
            Intent Mothers = new Intent(EnterActivity.this, FollowMotherActivity.class);
            startActivity(Mothers);
        } else if (id == R.id.Menu_FollowBaby) {
            Intent Baby = new Intent(EnterActivity.this, FollowBabyActivity.class);
            startActivity(Baby);
        } else if (id == R.id.Menu_Alarm) {
            if (Indextype.equals("MainMenuItems")) {
                ListView list = (ListView) findViewById(R.id.ListViewShow);

                String[] menu = getResources().getStringArray(R.array.menu3);
                ArrayList<MenuItems> menuItemv = new ArrayList<MenuItems>();

                for (int i = 0; i < menu.length; i++) {
                    menuItemv.add(new MenuItems(i, menuform3[i], menu[i]));
                }

                ListAdaptor arryadp = new ListAdaptor(menuItemv);
                list.setAdapter(arryadp);
                Indextype = "SecondMenuItems2";
            }
        } else if (id == R.id.Menu_Reports) {
            if (Indextype.equals("MainMenuItems")) {
                ListView list = (ListView) findViewById(R.id.ListViewShow);

                String[] menu = getResources().getStringArray(R.array.menu2);
                ArrayList<MenuItems> menuItemv = new ArrayList<MenuItems>();

                for (int i = 0; i < menu.length; i++) {
                    menuItemv.add(new MenuItems(i, menuform2[i], menu[i]));
                }

                ListAdaptor arryadp = new ListAdaptor(menuItemv);
                list.setAdapter(arryadp);
                Indextype = "SecondMenuItems";
            }
        } else if (id == R.id.Menu_Exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Index) {
            // Handle the camera action
        } else if (id == R.id.Rating) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
