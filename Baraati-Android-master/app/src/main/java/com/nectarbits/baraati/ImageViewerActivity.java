package com.nectarbits.baraati;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nectarbits.baraati.Adapters.ImageSliderViewPagerAdapter;
import com.nectarbits.baraati.Fragment.ImageViewerFragment;
import com.nectarbits.baraati.Models.VendorDetail.Moreimage;
import com.nectarbits.baraati.Singletone.GeneralSingletone;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nectarbits on 20/05/16.
 */
public class ImageViewerActivity extends AppCompatActivity {
    private Context mContext;

    @Bind(R.id.vpImageSlider)
     ViewPager vpImageSlider;

    private LinearLayout llMedia;
    @Bind(R.id.imgBack)
    ImageView imgBack;
    @Bind(R.id.tvCount)
    TextViewTitle tvCount;

    private String mImgIndex;
    private ArrayList<Fragment> fragmentsArray;
    private List<Moreimage> imageArrayList;

    int selected_position;


   /* @Bind(R.id.llImageCapsule)
    LinearLayout llImageCapsule;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        mContext = this;

        initialiseView();

        ButterKnife.bind(this);

       // implementToolbar();

       imageArrayList = GeneralSingletone.getInstance().getMoreimageList();
        selected_position=getIntent().getExtras().getInt(AppUtils.ARG_MORE_POSITION);
        /**
         *create Fragment
         */
        createNewFragment(imageArrayList);
        vpImageSlider.setAdapter(new ImageSliderViewPagerAdapter(getSupportFragmentManager(), fragmentsArray));


        /**
         *ImageViewer with Viewpager Listeners
         */
        vpImageSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                vpImageSlider.setCurrentItem(position);
                tvCount.setText((position+1)+"/"+imageArrayList.size());
                for (int j = 0; j < fragmentsArray.size(); j++) {
                  /*  TextView txtSelector = (TextView) llImageCapsule.getChildAt(j);
                    if (j == position) {
                        txtSelector.setSelected(true);
                    } else {
                        txtSelector.setSelected(false);
                    }*/
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        vpImageSlider.setCurrentItem(selected_position);
        /*setCapsuleForImageViewer(fragmentsArray);
        TextView txtDefaultCapsule=(TextView)llImageCapsule.getChildAt(0);
        txtDefaultCapsule.setSelected(true);*/


        //imgBack
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvCount.setText((selected_position+1)+"/"+imageArrayList.size());
    }

   /* private void setCapsuleForImageViewer(ArrayList<Fragment> fragmentsArrayList) {
        if (fragmentsArrayList.size() > 0) {
            for (int i = 0; i < fragmentsArray.size(); i++) {

                TextView txtView = new TextView(mContext);
                LinearLayout.LayoutParams paramsForImgViewBackGrounds = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.sixteen_dip), (int) getResources().getDimension(R.dimen.six_dip));
                paramsForImgViewBackGrounds.setMargins(6, 0, 6, 0);
                txtView.setLayoutParams(paramsForImgViewBackGrounds);
                txtView.setId(i);
                txtView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_image_viewer_capsule));

                final int finalI = i;
                txtView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = v.getId();
                        for (int j = 0; j < fragmentsArray.size(); j++) {
                            TextView txtSelector = (TextView) llImageCapsule.getChildAt(j);
                            if (j == position) {
                                txtSelector.setSelected(true);
                            } else {
                                txtSelector.setSelected(false);
                            }
                        }

                        vpImageSlider.setCurrentItem(finalI);
                    }
                });

                llImageCapsule.addView(txtView);

            }
        }
    }*/


  /*  private void implementToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        getSupportActionBar().setTitle(bundle.getString(AppUtils.ARG_TOOLBAR_TITLE));
        toolbar.setTitle(bundle.getString(AppUtils.ARG_TOOLBAR_TITLE));


    }*/

    private void initialiseView() {
        vpImageSlider = (ViewPager) findViewById(R.id.vpImageSlider);
        imgBack = (ImageView) findViewById(R.id.imgBack);
    }

    private void createNewFragment(List<Moreimage> mArrayList) {
        fragmentsArray = new ArrayList<Fragment>();
        for (int i = 0; i < mArrayList.size(); i++) {
            ImageViewerFragment fragment = new ImageViewerFragment(mContext, mArrayList, "" + i);
            fragmentsArray.add(fragment);
        }
    }

}
