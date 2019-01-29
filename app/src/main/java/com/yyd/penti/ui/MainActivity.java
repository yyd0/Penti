package com.yyd.penti.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yyd.penti.R;
import com.yyd.penti.ui.duanzi.DuanziFragment;
import com.yyd.penti.ui.lehuo.LehuoFragment;
import com.yyd.penti.ui.tugua.TuguaFragment;
import com.yyd.penti.ui.yitu.YituFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TUGUA = "TUGUA";
    private static final String YITU = "YITU";
    private static final String LEHUO = "LEHUO";
    private static final String DUANZI = "DUANZI";
    private static final String TITLE = "title";
    private TuguaFragment tuguaFragment;
    private LehuoFragment lhFrag;
    private DuanziFragment dzFrag;
    private Toolbar toolbar;
    private YituFragment yiTuFrag;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportActionBar().setTitle(R.string.tg);
            tuguaFragment = new TuguaFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, tuguaFragment)
                    .show(tuguaFragment)
                    .commit();
        } else {
            CharSequence charSequence = savedInstanceState.getCharSequence(TITLE);
            getSupportActionBar().setTitle(charSequence);
            tuguaFragment = (TuguaFragment) getSupportFragmentManager().getFragment(savedInstanceState, TUGUA);
            yiTuFrag = (YituFragment) getSupportFragmentManager().getFragment(savedInstanceState, YITU);
            lhFrag = (LehuoFragment) getSupportFragmentManager().getFragment(savedInstanceState, LEHUO);
            dzFrag = (DuanziFragment) getSupportFragmentManager().getFragment(savedInstanceState, DUANZI);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFrags(fragmentTransaction);
        if (id == R.id.nav_camera) {
            toolbar.setTitle(R.string.tg);
            // Handle the camera action
            fragmentTransaction.show(tuguaFragment);
        } else if (id == R.id.nav_gallery) {
            toolbar.setTitle(R.string.lh);
            if (lhFrag == null) {
                lhFrag = new LehuoFragment();
                fragmentTransaction.add(R.id.container, lhFrag);
            }
            fragmentTransaction.show(lhFrag);
        } else if (id == R.id.nav_slideshow) {
            toolbar.setTitle(R.string.yt);
            if (yiTuFrag == null) {
                yiTuFrag = new YituFragment();
                fragmentTransaction.add(R.id.container, yiTuFrag);
            }
            fragmentTransaction.show(yiTuFrag);
        } else if (id == R.id.nav_manage) {
            toolbar.setTitle(R.string.dz);
            if (dzFrag == null) {
                dzFrag = new DuanziFragment();
                fragmentTransaction.add(R.id.container, dzFrag);
            }
            fragmentTransaction.show(dzFrag);
        }
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        CharSequence title = toolbar.getTitle();
        outState.putCharSequence(TITLE, title);
        if (tuguaFragment != null)
            getSupportFragmentManager().putFragment(outState, TUGUA, tuguaFragment);
        if (yiTuFrag != null)
            getSupportFragmentManager().putFragment(outState, YITU, yiTuFrag);
        if (lhFrag != null)
            getSupportFragmentManager().putFragment(outState, LEHUO, lhFrag);
        if (dzFrag != null)
            getSupportFragmentManager().putFragment(outState, DUANZI, dzFrag);
        super.onSaveInstanceState(outState);
    }

    private void hideFrags(FragmentTransaction transaction) {
        if (tuguaFragment != null) {
            transaction.hide(tuguaFragment);
        }
        if (yiTuFrag != null) {
            transaction.hide(yiTuFrag);
        }
        if (lhFrag != null) {
            transaction.hide(lhFrag);
        }
        if (dzFrag != null) {
            transaction.hide(dzFrag);
        }
    }
}
