package com.hc.meterialdesigntest.activity;

import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hc.meterialdesigntest.R;
import com.hc.meterialdesigntest.bean.TestBean;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lsh.XXRecyclerview.CommonRecyclerAdapter;
import com.lsh.XXRecyclerview.CommonViewHolder;

import junit.framework.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private RecyclerView mXrl;
    private CommonRecyclerAdapter<TestBean> mAdapter;

    private TestBean[] testBeen = {new TestBean("fruit", R.mipmap.fruit), new TestBean("fruit", R.mipmap.fruit),
            new TestBean("fruit", R.mipmap.fruit),
            new TestBean("fruit", R.mipmap.fruit),
            new TestBean("fruit", R.mipmap.fruit),
            new TestBean("fruit", R.mipmap.fruit),
            new TestBean("fruit", R.mipmap.fruit),
            new TestBean("fruit", R.mipmap.fruit)};
    private List<TestBean> datas = new ArrayList<>();
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.float_btn);
        mXrl = (RecyclerView) findViewById(R.id.recycler_view);
        refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Data deleted", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Data restored", Toast.LENGTH_SHORT).show();

                    }
                }).show();
            }
        });

        refresh.setColorSchemeResources(R.color.colorPrimary);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTest();
            }
        });
        initXRecyclerview();
    }

    private void refreshTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                }catch (Exception e){

                }
                //其实handler最好0-0
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "刷新", Toast.LENGTH_SHORT).show();
                        refresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


    private void initXRecyclerview() {

        for (int i = 0; i < testBeen.length; i++) {
            datas.add(testBeen[i]);
        }
        //设置相应的布局管理器
        mXrl.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommonRecyclerAdapter<TestBean>(this, datas, R.layout.fruit_item) {
            @Override
            public void convert(CommonViewHolder holder, TestBean o, int i, boolean b) {

                holder.setText(R.id.fruit_name, datas.get(i).getName());
                ImageView iv = (ImageView) holder.getView(R.id.fruit_image);
                Glide.with(MainActivity.this).load(datas.get(i).getImageId()).into(iv);
            }
        };

        mXrl.setAdapter(mAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    /**
     * menu中显示图标
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                } catch (Exception e) {
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Toast.makeText(this, "你点击了第一个按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu2:
                Toast.makeText(this, "你点击了第二个按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu3:
                Toast.makeText(this, "你点击了第三个按钮", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }


}
