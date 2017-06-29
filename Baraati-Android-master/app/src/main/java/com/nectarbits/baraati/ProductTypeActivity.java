package com.nectarbits.baraati;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nectarbits.baraati.Adapters.ProductAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nectarbits on 23/08/16.
 */
public class ProductTypeActivity extends AppCompatActivity {

    @Bind(R.id.recyclerviewProductTypes)
    RecyclerView recyclerviewProductTypes;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    Context mContext;

    ArrayList<String> mArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_types);

        ButterKnife.bind(this);
        mContext = this;
        implementToolbar();

        mArrayList = new ArrayList<>();
        mArrayList.add("Atrangi Matka");
        mArrayList.add("Atrangi Matka 1");
        mArrayList.add("Atrangi Matka 2");
        mArrayList.add("Atrangi Matka 3");
        mArrayList.add("Atrangi Matka 4");

        recyclerviewProductTypes.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerviewProductTypes.setItemAnimator(new DefaultItemAnimator());
        recyclerviewProductTypes.setAdapter(new ProductAdapter(mContext,mArrayList));
    }

    private void implementToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
