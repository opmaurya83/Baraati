package com.nectarbits.baraati;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nectarbits.baraati.Adapters.AdapterForCityList;
import com.nectarbits.baraati.Adapters.AdapterForState;
import com.nectarbits.baraati.Models.CountryModel.CityInfo;
import com.nectarbits.baraati.Models.CountryModel.CountryInfo;
import com.nectarbits.baraati.Models.CountryModel.StateInfo;
import com.nectarbits.baraati.Models.UpdateProfile.UpdateProfileModel;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.DBHelper;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.squareup.picasso.Picasso;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;

public class ProfileDetailActivity extends AppCompatActivity {

    private static final String TAG = ProfileDetailActivity.class.getSimpleName();


    @Bind(R.id.civProfilePicture)
    ImageView civProfilePicture;


    @Bind(R.id.tvtDays)
    TextViewTitle tvtDays;
    @Bind(R.id.tvtHours)
    TextViewTitle tvtHours;
    @Bind(R.id.tvtMinutes)
    TextViewTitle tvtMinutes;
    @Bind(R.id.tvtSeconds)
    TextViewTitle tvtSeconds;


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.txtDate)
    TextViewTitle txtDate;
    @Bind(R.id.llTime)
    LinearLayout llTime;
    @Bind(R.id.tvGroome)
    TextViewTitle tvGroome;
    @Bind(R.id.tvBridal)
    TextViewTitle tvBridal;
    @Bind(R.id.tvd_address)
    TextViewDescription tvdAddress;
    @Bind(R.id.tvd_phone)
    TextViewDescription tvdPhone;
    @Bind(R.id.tvd_email)
    TextViewDescription tvdEmail;
    @Bind(R.id.tvName)
    TextViewTitle tvName;
    @Bind(R.id.llAddress)
    LinearLayout llAddress;


    private ArrayList<CountryInfo> arrayListCountry;
    private ArrayList<CityInfo> cityInfoArrayList;
    private ArrayList<StateInfo> stateInfoArrayList;
    private AdapterForState adapterForState;
    private AdapterForCityList adapterForCityList;

    ArrayList<CityInfo> cityInfoslistTemp = new ArrayList<>();
    ArrayList<StateInfo> stateInfosListTemp = new ArrayList<>();

    StateInfo stateInfo = new StateInfo();
    CityInfo cityInfo = new CityInfo();
    CountryInfo countryInfo;

    RecyclerView recyclerView;
    private DBHelper dbHelper;
    private Context mContext;
    ProgressDialog progressDialog;
    AlertDialog alert = null;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    //List<String> mPaths=new ArrayList<>();
    private String[] mrOrMrsArray;
    UpdateProfileModel updateProfileModel = null;
    Dialog dialog;
    int gender = -1;
    private Uri fileUri = Uri.EMPTY;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String IMAGE_DIRECTORY_NAME = "Baraati";
    private File file;
    Date weddingDate;
    long diffTime = 0;
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat fmt_date = new SimpleDateFormat("yyyy-MMM-dd");

    Boolean isEdit = false;
    MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);

        dbHelper = new DBHelper(this);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isEdit = bundle.getBoolean(AppUtils.ARG_IS_EDIT);
        }
        mrOrMrsArray = new String[]{getString(R.string.str_male), getString(R.string.str_female)};
        implementToolbar();
        getCountryFromDB();
        getStateFromDB();

        //tvCount.setText(arrayListCountry.get(0).getName());
        setView();
        //setEnable(isEdit);

    }

    void setView() {
        String address = "";
        tvName.setText(UserDetails.getInstance(mContext).getUser_name());
        tvdEmail.setText(UserDetails.getInstance(mContext).getUser_emailid());

        if (UserDetails.getInstance(mContext).getUser_picture().length() > 0) {
            Picasso.with(mContext)
                    .load(UserDetails.getInstance(mContext).getUser_picture())
                    .into(civProfilePicture);
        }

        if (UserDetails.getInstance(mContext).getUser_emailid().length() > 0) {
            tvdPhone.setText(UserDetails.getInstance(mContext).getUser_phone());
        }

        if (UserDetails.getInstance(mContext).getUser_wedding_date().length() > 0 && !UserDetails.getInstance(mContext).getUser_wedding_date().equalsIgnoreCase("0000-00-00")) {

            try {
                Date weddingdate = fmt.parse(UserDetails.getInstance(mContext).getUser_wedding_date());
                String date = fmt_date.format(weddingdate);
                String month = date.split("-")[1];
                String year = date.split("-")[0];
                String day = date.split("-")[2];
                String suffix = getDateSuffix(Integer.parseInt(day));
                txtDate.setText(Html.fromHtml(day + "<sup><small>" + suffix + "</small></sup>" + " " + month + "-" + year));
                //  txtDate.setText(Html.fromHtml("X<sup>2</sup>"));
            } catch (Exception ex) {

            }


            //  tvWeddingDate.setText(UserDetails.getInstance(mContext).getUser_wedding_date());

            try {
                weddingDate = fmt.parse(UserDetails.getInstance(mContext).getUser_wedding_date());
                diffTime = weddingDate.getTime() - new Date().getTime();
                Log.e(TAG, "setView: diff time" + diffTime);
                startTimer();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (UserDetails.getInstance(mContext).getUser_address1().length() > 0) {
            Log.e(TAG, "setView: " + UserDetails.getInstance(mContext).getUser_address1());
            //evAddress.setText(UserDetails.getInstance(mContext).getUser_address1());
            address += "" + UserDetails.getInstance(mContext).getUser_address1();
        }

        if (UserDetails.getInstance(mContext).getUser_address2().length() > 0) {
            //evAddress2.setText(UserDetails.getInstance(mContext).getUser_address2());
            address += "," + UserDetails.getInstance(mContext).getUser_address2();
            Log.e(TAG, "setView: " + UserDetails.getInstance(mContext).getUser_address2());
        }

      /*  if (UserDetails.getInstance(mContext).getUser_gender().length() > 0) {
            tvGender.setText(mrOrMrsArray[Integer.parseInt(UserDetails.getInstance(mContext).getUser_gender())]);
            gender = Integer.parseInt(UserDetails.getInstance(mContext).getUser_gender());
        }*/

        if (UserDetails.getInstance(mContext).getUser_city().length() > 0) {
            getCityNameFromCountryId();
            for (int i = 0; i < cityInfoArrayList.size(); i++) {
                if (cityInfoArrayList.get(i).getId() == (Integer.parseInt(UserDetails.getInstance(mContext).getUser_city()))) {
                    //tvCity.setText(cityInfoArrayList.get(i).getCityName());
                    address += " " + cityInfoArrayList.get(i).getCityName();
                  /*  cityInfo.setId(cityInfoArrayList.get(i).getId());
                    cityInfo.setCityName(cityInfoArrayList.get(i).getCityName());
                    cityInfo.setState_id(cityInfoArrayList.get(i).getState_id());*/
                }
            }

        }

        if (UserDetails.getInstance(mContext).getUser_state().length() > 0) {
            for (int i = 0; i < stateInfoArrayList.size(); i++) {
                if (stateInfoArrayList.get(i).getId() == (Integer.parseInt(UserDetails.getInstance(mContext).getUser_state()))) {
                    //   tvState.setText(stateInfoArrayList.get(i).getName());
                  /*  stateInfo.setId(stateInfoArrayList.get(i).getId());
                    stateInfo.setName(stateInfoArrayList.get(i).getName());
                    stateInfo.setCountry_id(stateInfoArrayList.get(i).getCountry_id());*/
                    address += " " + stateInfoArrayList.get(i).getName();
                }
            }

        }


        if (UserDetails.getInstance(mContext).getUser_state().length() > 0) {
            for (int i = 0; i < stateInfoArrayList.size(); i++) {
                if (stateInfoArrayList.get(i).getId() == (Integer.parseInt(UserDetails.getInstance(mContext).getUser_state()))) {
                    //   tvState.setText(stateInfoArrayList.get(i).getName());
                  /*  stateInfo.setId(stateInfoArrayList.get(i).getId());
                    stateInfo.setName(stateInfoArrayList.get(i).getName());
                    stateInfo.setCountry_id(stateInfoArrayList.get(i).getCountry_id());*/
                    address += " " + arrayListCountry.get(0).getName();
                }
            }

        }


        if (UserDetails.getInstance(mContext).getUser_zip().length() > 0) {
            // evPinCode.setText(UserDetails.getInstance(mContext).getUser_zip());
            address += "-" + UserDetails.getInstance(mContext).getUser_zip();
        }

        if (UserDetails.getInstance(mContext).getUser_groom().length() > 0) {
            tvGroome.setText(UserDetails.getInstance(mContext).getUser_groom());

        }

        if (UserDetails.getInstance(mContext).getUser_bridal().length() > 0) {
            tvBridal.setText(UserDetails.getInstance(mContext).getUser_bridal());

        }


        tvdAddress.setText(address);
        if(address.length()==0){
            llAddress.setVisibility(View.GONE);
        }else{
            llAddress.setVisibility(View.VISIBLE);
        }


       /* if (UserDetails.getInstance(mContext).getUser_zip().length() > 0) {
            evPinCode.setText(UserDetails.getInstance(mContext).getUser_zip());
        }*/


    }

    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_profile));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
                overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);
        if (isEdit) {
            menu.findItem(R.id.action_done).setTitle(getString(R.string.str_done));
        } else {
            menu.findItem(R.id.action_done).setTitle(getString(R.string.str_edit));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_done:
                if (!isEdit) {
/*                    item.setTitle(getString(R.string.str_done));
                    isEdit=true;
                    setEnable(isEdit);*/
                    Intent intent = new Intent(mContext, EditProfileActivity.class);
                    intent.putExtra(AppUtils.ARG_IS_EDIT, true);

                    startActivityForResult(intent, AppUtils.REQUEST_EDIT_PROFILE);
                    overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
                } else {
                    this.item = item;


                }
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            fileUri = Uri.fromFile(new File(mPaths.get(0)));
            Picasso.with(mContext)
                    .load(fileUri)
                    .into(civProfilePicture);

            Log.e(TAG, "onActivityResult: " + fileUri.getPath());
            //Your Code
        }
      /*  if (requestCode == AppUtils.REQUEST_GALLARY_IMAGE && resultCode == RESULT_OK) {
            // mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            fileUri=data.getData();
            Picasso.with(mContext)
                    .load(fileUri)
                    .into(civProfilePicture);

            Log.e(TAG, "onActivityResult: "+fileUri.getPath());

        }else */
        if (requestCode == AppUtils.REQUEST_CAMERA_IMAGE && resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult: " + fileUri.getPath());

            File file = new Compressor.Builder(mContext)
                    .setMaxWidth(600)
                    .setMaxHeight(600)
                    .setQuality(100)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .build()
                    .compressToFile(new File(fileUri.getPath()));
            Picasso.with(mContext)
                    .load(file)
                    .into(civProfilePicture);
        }


        if (requestCode == AppUtils.REQUEST_EDIT_PROFILE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            setView();
        }
    }


    private void getCountryFromDB() {

        String selectQuery = "select * from countries  where id=101 order by name";
        Cursor cursor = dbHelper.executeSelectQuery(selectQuery);
        arrayListCountry = new ArrayList<CountryInfo>();
        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                CountryInfo countryInfo = new CountryInfo();
                countryInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
                countryInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                countryInfo.setShortName(cursor.getString(cursor.getColumnIndex("sortname")));
                arrayListCountry.add(countryInfo);
                cursor.moveToNext();
            }
        }
    }


    private void getStateFromDB() {

        String selectQuery = "select * from states  where country_id=101 order by name";
        Cursor cursor = dbHelper.executeSelectQuery(selectQuery);
        stateInfoArrayList = new ArrayList<StateInfo>();
        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                StateInfo countryInfo = new StateInfo();

                countryInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
                countryInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                countryInfo.setCountry_id(cursor.getInt(cursor.getColumnIndex("country_id")));
                stateInfoArrayList.add(countryInfo);
                cursor.moveToNext();
            }
        }
    }

    private void getCityNameFromCountryId() {

        String selectCity = "select * from cities where state_id=" + stateInfo.getId() + " order by name";
        Cursor cursor = dbHelper.executeSelectQuery(selectCity);
        cityInfoArrayList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                CityInfo cityInfo = new CityInfo();
                cityInfo.setCityName(cursor.getString(cursor.getColumnIndex("name")));
                cityInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                cityInfoArrayList.add(cityInfo);
                cursor.moveToNext();
            }

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fileUri != null) {
            outState.putString("cameraImageUri", fileUri.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("cameraImageUri")) {
            fileUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
        }
    }

    void startTimer() {


        CountDownTimer countDownTimer = new CountDownTimer(diffTime, 1000) {

            public void onTick(long millisUntilFinished) {
                Date date = new Date();
                long diffTime = weddingDate.getTime() - date.getTime();
                Log.e(TAG, "onTick: " + millisUntilFinished);
                if (diffTime > 0) {
                    long x = diffTime / 1000;
                    String seconds = "" + x % 60;
                    x /= 60;
                    String minutes = "" + x % 60;
                    x /= 60;
                    String hours = "" + x % 24;
                    x /= 24;
                    String days = "" + x;

                    tvtDays.setText(days);
                    tvtMinutes.setText(minutes);
                    tvtHours.setText(hours);
                    tvtSeconds.setText(seconds);
                }
            }

            public void onFinish() {

            }

        }.start();
    }


    public String getDateSuffix(int day) {
        switch (day) {
            case 1:
            case 21:
            case 31:
                return ("st");

            case 2:
            case 22:
                return ("nd");

            case 3:
            case 23:
                return ("rd");

            default:
                return ("th");
        }
    }

}