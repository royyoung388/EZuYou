package com.you.ezuyou.Rearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.you.ezuyou.R;


/**
 * Created by Administrator on 2017/2/28.
 */

public class Search extends AppCompatActivity {

    private Spinner spinner1, spinner2, spinner3;
    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter adapter;
    private String[] strings = {"1101", "1afdfd3215", "2546", "346456", "a", "ads", "axcv", "agkikh", "bkopgf", "ckwld", "ddsgj", "fwdsaf", "atdsfjla"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        initView();

        //加载适配器
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);



        listView.setAdapter(adapter);
        //设置过滤功能
        listView.setTextFilterEnabled(true);

        //设置搜索按键
        searchView.setSubmitButtonEnabled(true);

        //注册事件
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //Spinner spinner=(Spinner) parent;
                Toast.makeText(getApplicationContext(), "xxxx"+spinner1.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                if (spinner1.getItemAtPosition(position) != "尺寸") {
                    adapter.getFilter().filter(spinner1.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "没有改变的处理", Toast.LENGTH_LONG).show();
            }

        });

        //设置事件监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            //输入时触发
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    //清除ListView的过滤
                    listView.clearTextFilter();
                    //adapter.getFilter().filter("");

                }
                else {
                    //进行过滤
                    adapter.getFilter().filter(s.toString());
                    //listView.setFilterText(s);
                }
                return true;
            }

            @Override
            //点击搜索时触发
            public boolean onQueryTextSubmit(String s) {
                //实际查询功能
                Toast.makeText(Search.this, "您搜索了" + s, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void initView() {
        searchView = (SearchView) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.list);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);


        //取消spinner监听器默认执行一次
        spinner1.setSelection(0, true);
        spinner2.setSelection(0, true);
        spinner3.setSelection(0, true);
    }
}
