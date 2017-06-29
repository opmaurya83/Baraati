package com.nectarbits.baraati;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nectarbits.baraati.Models.VendorBazaar.ProductAllAttribute;
import com.nectarbits.baraati.Models.VendorBazaar.VendorAllAttribute;
import com.nectarbits.baraati.Models.filter.FilterListInfo;
import com.nectarbits.baraati.Singletone.FilterController;
import com.nectarbits.baraati.generalHelper.AppUtils;

import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VendorFilterActivity extends AppCompatActivity {

    private static final String TAG = VendorFilterActivity.class.getSimpleName() ;
    private Context mContext;

    private List<ProductAllAttribute> allAttributeArrayList;
    private List<VendorAllAttribute> allVendroAttributeArrayList;

/*    private List<AttributeList> attributeListArrayList;
*/
    private List<FilterListInfo> filterListInfoArrayList = new ArrayList<FilterListInfo>();

    private JSONArray jsonArray;
    private JSONObject jsonObject;

    private String minPriceValue,minPriceValue_temp;
    private String maxPriceValue;
    Boolean IsProductFilter=true;




    @Bind(R.id.llFilterNameContainer)
    LinearLayout llFilterNameContainer;

    @Bind(R.id.llFilterValueContainer)
    LinearLayout llFilterValueContainer;

    @Bind(R.id.rangeSeekbar)
    RangeSeekBar<Integer> rangeSeekbar;

    @OnClick(R.id.btnCancel)
    void Cancel(){
        finish();
    }

    @OnClick(R.id.txtClear)
    void Clear(){
       llFilterNameContainer.removeAllViews();
        llFilterValueContainer.removeAllViews();

        minPriceValue=""+Integer.parseInt(getIntent().getStringExtra(AppUtils.ARG_MIN_PRICE_RANGE));
        maxPriceValue=""+Integer.parseInt(getIntent().getStringExtra(AppUtils.ARG_MAX_PRICE_RANGE));
        minPriceValue_temp=""+((int)Integer.parseInt(minPriceValue)-1);
        filterListInfoArrayList.clear();

        jsonArray = new JSONArray();
        for (int i = 0; i < filterListInfoArrayList.size(); i++) {
            try {
                jsonObject = new JSONObject();
                jsonObject.put("attributevalue", filterListInfoArrayList.get(i).getAttrsValue());
                jsonObject.put("attributename", filterListInfoArrayList.get(i).getAttrtsName());
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                jsonArray.put(jsonObject);
            }
        }

        if(IsProductFilter) {
            LoadFilter();
        }else {
            LoadFilterVendor();
        }
    }
    @OnClick(R.id.txtApply)
    void onFilterApply() {


        jsonArray = new JSONArray();
        for (int i = 0; i < filterListInfoArrayList.size(); i++) {
            try {
                jsonObject = new JSONObject();
                jsonObject.put("attributevalue", filterListInfoArrayList.get(i).getAttrsValue());
                jsonObject.put("attributename", filterListInfoArrayList.get(i).getAttrtsName());
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                jsonArray.put(jsonObject);
            }
        }

        if(IsProductFilter){
            FilterController.getInstance().setFilterListInfoList_Product(filterListInfoArrayList);
        }else {
            FilterController.getInstance().setFilterListInfoList_Vendor(filterListInfoArrayList);
        }



        Log.e(TAG, "onFilterApply: "+jsonArray.toString()+" IsProductFilter::"+IsProductFilter);

        Intent returnIntent = new Intent();
        //returnIntent.putExtra(AppUtils.ARG_IS_VALID_ACTIVITY_RESULT, AppUtils.ARG_IS_VALID_ACTIVITY_RESULT);
        if(jsonArray.length()>0) {
            returnIntent.putExtra(AppUtils.ARG_RETURN_ATTRIBUTES_LIST, jsonArray.toString());
        }else{
            returnIntent.putExtra(AppUtils.ARG_RETURN_ATTRIBUTES_LIST, "");
        }

        if(minPriceValue.equalsIgnoreCase(getIntent().getStringExtra(AppUtils.ARG_MIN_PRICE_RANGE)) && maxPriceValue.equalsIgnoreCase(getIntent().getStringExtra(AppUtils.ARG_MAX_PRICE_RANGE)))
        {
            returnIntent.putExtra(AppUtils.ARG_RETURN_PRICE_RANGE,"");
        }else{
            returnIntent.putExtra(AppUtils.ARG_RETURN_PRICE_RANGE, minPriceValue + "-" + maxPriceValue);
        }

        returnIntent.putExtra(AppUtils.ARG_IS_PRODUCT,IsProductFilter);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mContext = this;

        ButterKnife.bind(this);

        minPriceValue=""+Integer.parseInt(getIntent().getStringExtra(AppUtils.ARG_MIN_PRICE_RANGE));
        maxPriceValue=""+Integer.parseInt(getIntent().getStringExtra(AppUtils.ARG_MAX_PRICE_RANGE));
        minPriceValue_temp=""+((int)Integer.parseInt(minPriceValue)-1);
        IsProductFilter=getIntent().getBooleanExtra(AppUtils.ARG_IS_PRODUCT,true);
        rangeSeekbar.setRangeValues(Integer.parseInt(getIntent().getStringExtra(AppUtils.ARG_MIN_PRICE_RANGE))
                , Integer.parseInt(getIntent().getStringExtra(AppUtils.ARG_MAX_PRICE_RANGE)));
        rangeSeekbar.setSelectedMinValue(Integer.parseInt(getIntent().getStringExtra(AppUtils.ARG_MIN_PRICE_RANGE)));
        rangeSeekbar.setSelectedMaxValue(Integer.parseInt(getIntent().getStringExtra(AppUtils.ARG_MAX_PRICE_RANGE)));

        rangeSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                minPriceValue=""+minValue;
                maxPriceValue=""+maxValue;
            }
        });

        if(IsProductFilter){
            filterListInfoArrayList=    FilterController.getInstance().getFilterListInfoList_Product();
        }else {
            filterListInfoArrayList=  FilterController.getInstance().getFilterListInfoList_Vendor();
        }

        if(IsProductFilter) {
            allAttributeArrayList = FilterController.getInstance().getProductAllAttributes();
            LoadFilter();
        }else{
            allVendroAttributeArrayList = FilterController.getInstance().getVendorAllAttributes();
            LoadFilterVendor();
        }

    }


    private void LoadFilterVendor(){


        for (int i = 0; i < allVendroAttributeArrayList.size(); i++) {


            TextView textViewTitle = new TextView(mContext);
            LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textViewLayoutParams.setMargins(0, 0, 0, 0);
            textViewTitle.setLayoutParams(textViewLayoutParams);
            textViewTitle.setGravity(Gravity.LEFT);
            textViewTitle.setBackgroundColor(Color.WHITE);
            textViewTitle.setTextSize(20.0f);
            textViewTitle.setPadding(20, 10, 10, 10);
            textViewTitle.setTextColor(getResources().getColorStateList(R.color.color_red_white));
            textViewTitle.setBackgroundResource(R.drawable.selector_filter_category);
            textViewTitle.setText(allVendroAttributeArrayList.get(i).getAttributeName());
            textViewTitle.setTag(i);
            textViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=(int)v.getTag();
                    for (int i = 0; i < llFilterNameContainer.getChildCount(); i++) {
                        if((int)((TextView)llFilterNameContainer.getChildAt(i)).getTag()==position){
                            ((TextView)llFilterNameContainer.getChildAt(i)).setSelected(true);
                        }else{
                            ((TextView)llFilterNameContainer.getChildAt(i)).setSelected(false);
                        }
                    }


                    llFilterValueContainer.removeAllViews();
                    for (int k = 0; k < allVendroAttributeArrayList.get(position).getValue().size(); k++) {
                        final CheckedTextView textViewFilter = new CheckedTextView(mContext);
                        LinearLayout.LayoutParams textViewFilterLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        textViewFilterLayoutParams.setMargins(10, 5, 20, 5);
                        textViewFilter.setLayoutParams(textViewFilterLayoutParams);
                        textViewFilter.setGravity(Gravity.CENTER);
                        textViewFilter.setBackgroundColor(Color.WHITE);
                        textViewFilter.setTextSize(TypedValue.COMPLEX_UNIT_DIP,getResources().getDimension(R.dimen._5sdp));
                        textViewFilter.setSelected(false);
                        // textViewFilter.setTextColor(Color.DKGRAY);
                        textViewFilter.setTextColor(getResources().getColorStateList(R.color.colorPrimary));
                        textViewFilter.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_filter_check_uncheck,0,0,0);
                        // textViewFilter.setCheckMarkDrawable(R.drawable.selector_filter_check_uncheck);
                        textViewFilter.setPadding(5, 5, 5, 5);
                        textViewFilter.setId(Integer.parseInt(k + "" + position));
                        textViewFilter.setTag(allVendroAttributeArrayList.get(position).getAttributeName() + "," + allVendroAttributeArrayList.get(position).getValue().get(k));
                        // textViewFilter.setBackgroundResource(R.drawable.selector_corner_white_gray);
                        textViewFilter.setText(allVendroAttributeArrayList.get(position).getValue().get(k));

                        textViewFilter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FilterListInfo filterListInfo = new FilterListInfo();
                                if (textViewFilter.isSelected()) {
                                    textViewFilter.setSelected(false);

                                    if (filterListInfoArrayList.size() > 0) {
                                        for (int i = 0; i < filterListInfoArrayList.size(); i++) {
                                            if (filterListInfoArrayList.get(i).getAttrtsAt().equals(v.getId())) {
                                                filterListInfoArrayList.remove(i);
                                            }
                                        }
                                    }

                                } else {
                                    textViewFilter.setSelected(true);

                                    String tagIs = "" + v.getTag();
                                    String name = tagIs.substring(0, tagIs.indexOf(","));
                                    String value = tagIs.substring(tagIs.indexOf(",") + 1, tagIs.length());

                                    filterListInfo.setAttrtsAt(v.getId());
                                    filterListInfo.setAttrsValue(value.trim());
                                    filterListInfo.setAttrtsName(name.trim());
                                    filterListInfoArrayList.add(filterListInfo);
                                }
                            }
                        });

                        try{
                            if (filterListInfoArrayList.size() > 0) {
                                for (int i = 0; i < filterListInfoArrayList.size(); i++) {
                                    if (filterListInfoArrayList.get(i).getAttrtsAt().equals(Integer.parseInt(k + "" + position))) {
                                        textViewFilter.setSelected(true);
                                    }
                                }
                            }

                        }catch (Exception ex){

                        }
                        llFilterValueContainer.addView(textViewFilter);
                    }
                }
            });


            llFilterNameContainer.addView(textViewTitle);

        }

    }

    private void LoadFilter(){

        if(!minPriceValue.equalsIgnoreCase(maxPriceValue)) {
            addPriceFilterLabel();
        }
        for (int i = 0; i < allAttributeArrayList.size(); i++) {


            TextView textViewTitle = new TextView(mContext);
            LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textViewLayoutParams.setMargins(0, 0, 0, 0);
            textViewTitle.setLayoutParams(textViewLayoutParams);
            textViewTitle.setGravity(Gravity.LEFT);
            textViewTitle.setBackgroundColor(Color.WHITE);
            textViewTitle.setTextSize(20.0f);
            textViewTitle.setPadding(20, 10, 10, 10);
            textViewTitle.setTextColor(getResources().getColorStateList(R.color.color_red_white));
            textViewTitle.setBackgroundResource(R.drawable.selector_filter_category);
            textViewTitle.setText(allAttributeArrayList.get(i).getAttributeName());
            textViewTitle.setTag(i);
            textViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=(int)v.getTag();
                    for (int i = 0; i < llFilterNameContainer.getChildCount(); i++) {
                        if((int)((TextView)llFilterNameContainer.getChildAt(i)).getTag()==position){
                            ((TextView)llFilterNameContainer.getChildAt(i)).setSelected(true);
                        }else{
                            ((TextView)llFilterNameContainer.getChildAt(i)).setSelected(false);
                        }
                    }


                    llFilterValueContainer.removeAllViews();
                    for (int k = 0; k < allAttributeArrayList.get(position).getValue().size(); k++) {
                        final CheckedTextView textViewFilter = new CheckedTextView(mContext);
                        LinearLayout.LayoutParams textViewFilterLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        textViewFilterLayoutParams.setMargins(10, 5, 20, 5);
                        textViewFilter.setLayoutParams(textViewFilterLayoutParams);
                        textViewFilter.setGravity(Gravity.CENTER);
                        textViewFilter.setBackgroundColor(Color.WHITE);
                        textViewFilter.setTextSize(TypedValue.COMPLEX_UNIT_DIP,getResources().getDimension(R.dimen._5sdp));
                        textViewFilter.setSelected(false);
                        // textViewFilter.setTextColor(Color.DKGRAY);
                        textViewFilter.setTextColor(getResources().getColorStateList(R.color.colorPrimary));
                        textViewFilter.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_filter_check_uncheck,0,0,0);
                        // textViewFilter.setCheckMarkDrawable(R.drawable.selector_filter_check_uncheck);
                        textViewFilter.setPadding(5, 5, 5, 5);
                        textViewFilter.setId(Integer.parseInt(k + "" + position));
                        textViewFilter.setTag(allAttributeArrayList.get(position).getAttributeName() + "," + allAttributeArrayList.get(position).getValue().get(k));
                        // textViewFilter.setBackgroundResource(R.drawable.selector_corner_white_gray);
                        textViewFilter.setText(allAttributeArrayList.get(position).getValue().get(k));

                        textViewFilter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FilterListInfo filterListInfo = new FilterListInfo();
                                if (textViewFilter.isSelected()) {
                                    textViewFilter.setSelected(false);

                                    if (filterListInfoArrayList.size() > 0) {
                                        for (int i = 0; i < filterListInfoArrayList.size(); i++) {
                                            if (filterListInfoArrayList.get(i).getAttrtsAt().equals(v.getId())) {
                                                filterListInfoArrayList.remove(i);
                                            }
                                        }
                                    }

                                } else {
                                    textViewFilter.setSelected(true);

                                    String tagIs = "" + v.getTag();
                                    String name = tagIs.substring(0, tagIs.indexOf(","));
                                    String value = tagIs.substring(tagIs.indexOf(",") + 1, tagIs.length());

                                    filterListInfo.setAttrtsAt(v.getId());
                                    filterListInfo.setAttrsValue(value.trim());
                                    filterListInfo.setAttrtsName(name.trim());
                                    filterListInfoArrayList.add(filterListInfo);
                                }
                            }
                        });

                        try{
                            if (filterListInfoArrayList.size() > 0) {
                                for (int i = 0; i < filterListInfoArrayList.size(); i++) {
                                    if (filterListInfoArrayList.get(i).getAttrtsAt().equals(Integer.parseInt(k + "" + position))) {
                                        textViewFilter.setSelected(true);
                                    }
                                }
                            }

                        }catch (Exception ex){

                        }
                        llFilterValueContainer.addView(textViewFilter);
                    }
                }
            });


            llFilterNameContainer.addView(textViewTitle);

        }

    }


    private void addPriceFilterLabel(){
        TextView textViewTitle = new TextView(mContext);
        LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayoutParams.setMargins(0, 0, 0, 0);
        textViewTitle.setLayoutParams(textViewLayoutParams);
        textViewTitle.setGravity(Gravity.LEFT);
        textViewTitle.setBackgroundColor(Color.WHITE);
        textViewTitle.setTextSize(20.0f);
        textViewTitle.setPadding(20, 10, 10, 10);
        textViewTitle.setTextColor(getResources().getColorStateList(R.color.color_red_white));
        textViewTitle.setBackgroundResource(R.drawable.selector_filter_category);
        textViewTitle.setText(getString(R.string.str_price_filter));
        textViewTitle.setTag(-1);

        llFilterNameContainer.addView(textViewTitle);
        textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                for (int i = 0; i < llFilterNameContainer.getChildCount(); i++) {
                    if((int)((TextView)llFilterNameContainer.getChildAt(i)).getTag()==position){
                        ((TextView)llFilterNameContainer.getChildAt(i)).setSelected(true);
                    }else{
                        ((TextView)llFilterNameContainer.getChildAt(i)).setSelected(false);
                    }
                }


                llFilterValueContainer.removeAllViews();

                final RangeSeekBar rangeseekbar = new RangeSeekBar(mContext);
                LinearLayout.LayoutParams textViewFilterLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textViewFilterLayoutParams.setMargins(10, 20, 20, 10);
                rangeseekbar.setLayoutParams(textViewFilterLayoutParams);

                rangeseekbar.setBackgroundColor(Color.WHITE);

               /* rangeseekbar.setPadding(20, 20, 20, 20);*/
                rangeseekbar.setId(123456);


                rangeseekbar.setBackgroundResource(R.color.colorTransparent);
                rangeseekbar.setRangeValues(Integer.parseInt(minPriceValue_temp)
                        , Integer.parseInt(getIntent().getStringExtra(AppUtils.ARG_MAX_PRICE_RANGE)));
                rangeseekbar.setSelectedMinValue(Integer.parseInt(minPriceValue_temp)+1);
                rangeseekbar.setSelectedMaxValue(Integer.parseInt(maxPriceValue));
                rangeseekbar.setTextAboveThumbsColorResource(R.color.colorPrimary);
                rangeseekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                    @Override
                    public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                        if(minValue==(Integer.parseInt(minPriceValue_temp)))
                        {
                            minPriceValue=""+((int)Integer.parseInt(minPriceValue_temp)+1);
                        }else{
                            minPriceValue=""+minValue;
                        }

                        maxPriceValue=""+maxValue;
                    }
                });

                llFilterValueContainer.addView(rangeseekbar);

            }

        });

        textViewTitle.performClick();
    }
}
