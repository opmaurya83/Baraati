package com.nectarbits.baraati;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.nectarbits.baraati.Adapters.DashboardAdapter;
import com.nectarbits.baraati.Chat.gcm.GSMHelper;
import com.nectarbits.baraati.Fragment.AssignedCheckListFragment;
import com.nectarbits.baraati.Fragment.MyCheckListFragment;
import com.nectarbits.baraati.Interface.OnAssignedCheckListLoadedListener;
import com.nectarbits.baraati.Interface.OnDashBoardListeners;
import com.nectarbits.baraati.Interface.OnMyCheckListLoadedListener;
import com.nectarbits.baraati.Models.Logout.LogoutModel;
import com.nectarbits.baraati.Models.UpdateProfile.UpdateProfileModel;
import com.nectarbits.baraati.Models.UpdateProfileRequest.UpdateProfileRequestOverlay;
import com.nectarbits.baraati.Models.UserEvent.EventType;
import com.nectarbits.baraati.Models.UserEvent.SubCategory;
import com.nectarbits.baraati.Models.UserEvent.SubCategory_;
import com.nectarbits.baraati.Models.UserEvent.UserEventModel;
import com.nectarbits.baraati.Singletone.AddCheckListSigletone;
import com.nectarbits.baraati.Singletone.EventTypesSingletone;
import com.nectarbits.baraati.Singletone.UserCalenderEventsSingletone;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.Utils.ContactService;
import com.nectarbits.baraati.View.EditTextKozukaGothicProL;
import com.nectarbits.baraati.View.TextViewKozukaGothicProH;
import com.nectarbits.baraati.View.TextViewKozukaGothicProL;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.L;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.generalHelper.SP;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.orhanobut.hawk.Hawk;
import com.quickblox.q_municate_core.models.AppSession;
import com.quickblox.q_municate_db.managers.DataManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskGranted;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnDashBoardListeners, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    TextViewKozukaGothicProL tvTutorial, tvInspireYourSelf, tvShareApp, tvOrderHistory, tvPintrest, tvSettings,tvVendorInquiry;
    @Nullable
    @Bind(R.id.nav_view)
    NavigationView navigationHeader;
    @Nullable
    @Bind(R.id.nav_footer_view)
    NavigationView nav_footer_view;
    @Nullable
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Nullable
    @Bind(R.id.tabs)
    TabLayout tabs;
    DashboardAdapter adapter;
    ProgressDialog progressDialog;
    @Bind(R.id.sliding_layout)
    SlidingUpPanelLayout slidingLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ccFooter)
    RelativeLayout ccFooter;
    @Bind(R.id.ivBG)
    ImageView ivBG;
    @Bind(R.id.iv_choose_profile_bg)
    AppCompatImageView ivChooseProfileBg;
    @Bind(R.id.civ_profile_pic)
    CircularImageView civProfilePic;
    @Bind(R.id.iv_choose_profile)
    AppCompatImageView ivChooseProfile;
    @Bind(R.id.tvEdit)
    TextViewKozukaGothicProL tvEdit;
    @Bind(R.id.et_bride)
    EditTextKozukaGothicProL etBride;
    @Bind(R.id.et_bride_phone)
    EditTextKozukaGothicProL etBridePhone;
    @Bind(R.id.et_bride_email)
    EditTextKozukaGothicProL etBrideEmail;
    @Bind(R.id.et_groom)
    EditTextKozukaGothicProL etGroom;
    @Bind(R.id.et_groom_phone)
    EditTextKozukaGothicProL etGroomPhone;
    @Bind(R.id.et_groom_email)
    EditTextKozukaGothicProL etGroomEmail;
    @Bind(R.id.tv_wedding_date)
    TextViewKozukaGothicProL tvWeddingDate;
    @Bind(R.id.tvtDays)
    TextViewKozukaGothicProH tvtDays;
    @Bind(R.id.tvtHours)
    TextViewKozukaGothicProH tvtHours;
    @Bind(R.id.tvtMinutes)
    TextViewKozukaGothicProH tvtMinutes;
    @Bind(R.id.tvtSeconds)
    TextViewKozukaGothicProH tvtSeconds;
    @Bind(R.id.llTime)
    LinearLayout llTime;
    @Bind(R.id.ll_up)
    LinearLayout llUp;
    @Bind(R.id.llOverLay)
    LinearLayout llOverLay;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private Uri ProfilePicfileUri = Uri.EMPTY;
    private Uri ProfileBGfileUri = Uri.EMPTY;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String IMAGE_DIRECTORY_NAME = "Baraati";
    private File file;
    int PROFILE_PIC = 0, PROFILE_BG = 1;
    Boolean isProfilePIC = true;


    private int day;
    private int month;
    private int year;
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat fmt_date = new SimpleDateFormat("dd:MM:yyyy");
    Date weddingDate;
    long diffTime = 0;
    String date = "";

    /*Finish*/

    private Context mContext;
    List<SubCategory> list = new ArrayList<>();
    List<SubCategory_> subCategory_list = new ArrayList<>();
    Boolean isUpdate = false;
    FloatingActionButton floatingActionButton;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private OnMyCheckListLoadedListener onMyCheckListLoadedListener;
    private OnAssignedCheckListLoadedListener onAssignedCheckListLoadedListener;

    private GSMHelper gsmHelper;
    MenuItem menu_item_notification = null;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mContext = this;
        toolbar.setTitle(getString(R.string.str_home));
        toolbar.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(mContext);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        if (GH.isInternetOn(mContext))
            GetUserCheckList();
        setHeaderLayoutWithMenu();
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String permission = Manifest.permission.READ_CONTACTS;
                if (ContextCompat.checkSelfPermission(mContext, permission)
                        == PackageManager.PERMISSION_GRANTED) {
                    com.nectarbits.baraati.Chat.ui.activities.main.MainActivity.start(mContext);
                } else {
                    String[] permissionsToRequest = new String[]{Manifest.permission.READ_CONTACTS};
                    ActivityCompat.requestPermissions(MainActivity.this, permissionsToRequest, 1);
                }
            }
        });


     /*   if (TextUtils.isEmpty(SP.getPreferences(mContext, SP.FIRST_TIME_INSPIRE_SCREEN))) {
            Log.e(TAG, "Is First Time");
            SP.savePreferences(mContext, SP.FIRST_TIME_INSPIRE_SCREEN, "true");
            //slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        } else {
            //slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }*/
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState== SlidingUpPanelLayout.PanelState.COLLAPSED){
                    OpenHelp();
                }
            }
        });

        gsmHelper = new GSMHelper(this);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            if (gsmHelper.checkPlayServices()) {
                if (!gsmHelper.isDeviceRegisteredWithUser()) {
                    gsmHelper.registerInBackground();
                } else {
                    Log.e(TAG, "checkGCMRegistration: Registered...");
                }
            } else {
                Log.i(TAG, "No valid Google Play Services APK found.");
            }
        }

        //    Log.e(TAG, "onCreate: "+printKeyHash(MainActivity.this));
        GH.UpdateQuickBloxID(mContext);


        initnavFooter();
        initProfileOverLay();
    }


    void initProfileOverLay() {
        ivChooseProfileBg.setOnClickListener(onProfileClickListener);
        ivChooseProfile.setOnClickListener(onProfileClickListener);
        tvEdit.setOnClickListener(onProfileClickListener);
        tvWeddingDate.setOnClickListener(onProfileClickListener);
        setView();
    }

    void setView() {


        if (UserDetails.getInstance(mContext).getUser_picture().length() > 0) {
            Picasso.with(mContext)
                    .load(UserDetails.getInstance(mContext).getUser_picture())
                    .into(civProfilePic);
        }

        if (UserDetails.getInstance(mContext).getUser_cover().length() > 0) {
            Picasso.with(mContext)
                    .load(UserDetails.getInstance(mContext).getUser_cover())
                    .into(ivBG);
        }


        if (UserDetails.getInstance(mContext).getUser_wedding_date().length() > 0 && !UserDetails.getInstance(mContext).getUser_wedding_date().equalsIgnoreCase("0000-00-00")) {

            try {
                weddingDate = fmt.parse(UserDetails.getInstance(mContext).getUser_wedding_date());
                tvWeddingDate.setText(fmt_date.format(weddingDate));
                date = fmt.format(weddingDate);
                diffTime = weddingDate.getTime() - new Date().getTime();
                startTimer();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if (UserDetails.getInstance(mContext).getUser_groom().length() > 0) {
            etGroom.setText(UserDetails.getInstance(mContext).getUser_groom());

        }

        if (UserDetails.getInstance(mContext).getUser_bridal().length() > 0) {
            etBride.setText(UserDetails.getInstance(mContext).getUser_bridal());

        }


        if (UserDetails.getInstance(mContext).getUser_groom_email().length() > 0) {
            etGroomEmail.setText(UserDetails.getInstance(mContext).getUser_groom_email());
        }
        if (UserDetails.getInstance(mContext).getUser_groom_phone().length() > 0) {
            etGroomPhone.setText(UserDetails.getInstance(mContext).getUser_groom_phone());
        }

        if (UserDetails.getInstance(mContext).getUser_bridal_email().length() > 0) {
            etBrideEmail.setText(UserDetails.getInstance(mContext).getUser_bridal_email());
        }
        if (UserDetails.getInstance(mContext).getUser_bridal_phone().length() > 0) {
            etBridePhone.setText(UserDetails.getInstance(mContext).getUser_bridal_phone());
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

    View.OnClickListener onProfileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvEdit:
                    if (!tvEdit.isSelected()) {
                        profileEdit(true);
                        tvEdit.setSelected(true);
                        tvEdit.setText("Done");
                    } else {
                        profileEdit(false);
                        tvEdit.setSelected(false);
                        tvEdit.setText("Edit");
                    }
                    break;
                case R.id.iv_choose_profile:
                    ChoosePic(PROFILE_PIC);
                    break;
                case R.id.iv_choose_profile_bg:
                    ChoosePic(PROFILE_BG);
                    break;
                case R.id.tv_wedding_date:
                    DateDialog();
                    break;

            }
        }
    };


    void profileEdit(Boolean isUpdate) {
        if (isUpdate) {
            ivChooseProfileBg.setVisibility(View.VISIBLE);
            ivChooseProfile.setVisibility(View.VISIBLE);
            etBride.setFocusableInTouchMode(true);
            etBrideEmail.setFocusableInTouchMode(true);
            etBridePhone.setFocusableInTouchMode(true);
            etGroom.setFocusableInTouchMode(true);
            etGroomEmail.setFocusableInTouchMode(true);
            etGroomPhone.setFocusableInTouchMode(true);
            tvWeddingDate.setClickable(true);
        } else {
            onUpdateProfile();
            ivChooseProfileBg.setVisibility(View.GONE);
            ivChooseProfile.setVisibility(View.GONE);
            etBride.setFocusableInTouchMode(false);
            etBrideEmail.setFocusableInTouchMode(false);
            etBridePhone.setFocusableInTouchMode(false);
            etGroom.setFocusableInTouchMode(false);
            etGroomEmail.setFocusableInTouchMode(false);
            etGroomPhone.setFocusableInTouchMode(false);
            tvWeddingDate.setClickable(false);
        }
    }

    public void DateDialog() {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                tvWeddingDate.setText(dayOfMonth + ":" + monthOfYear + ":" + year);
                date = year + "-" + monthOfYear + "-" + dayOfMonth;

            }
        };
        long currentTime = new Date().getTime();
        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, year, month, day);
        dpDialog.getDatePicker().setMinDate(currentTime);
        dpDialog.show();

    }

    private void onUpdateProfile() {

        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        final Map<String, Object> requestbodyFiles = new HashMap<>();
        HashMap partMap = new HashMap();


        final UpdateProfileRequestOverlay updateProfileRequest = new UpdateProfileRequestOverlay();
        updateProfileRequest.setUserid(UserDetails.getInstance(mContext).getUser_id());
        updateProfileRequest.setBrideemail(etBrideEmail.getText().toString());
        updateProfileRequest.setBridename(etBride.getText().toString());
        updateProfileRequest.setBridephone(etBridePhone.getText().toString());
        updateProfileRequest.setGroomemail(etGroomEmail.getText().toString());
        updateProfileRequest.setGroomname(etGroom.getText().toString());
        updateProfileRequest.setGroomphone(etGroomPhone.getText().toString());
        updateProfileRequest.setWeddingdate(date);

        Log.e(TAG, "onUpdateProfile: " + updateProfileRequest.toString());
        requestbodyFiles.put("editRequest", updateProfileRequest);

        if (ProfilePicfileUri != Uri.EMPTY) {
            File file = new Compressor.Builder(mContext)
                    .setMaxWidth(600)
                    .setMaxHeight(600)
                    .setQuality(100)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .build()
                    .compressToFile(new File(ProfilePicfileUri.getPath()));
            partMap.put("user_profile_image\"; filename=\"" + new File(ProfilePicfileUri.getPath()).getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
        }

        if (ProfileBGfileUri != Uri.EMPTY) {
            File file = new Compressor.Builder(mContext)
                    .setMaxWidth(600)
                    .setMaxHeight(600)
                    .setQuality(100)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .build()
                    .compressToFile(new File(ProfileBGfileUri.getPath()));
            partMap.put("coverPhoto\"; filename=\"" + new File(ProfileBGfileUri.getPath()).getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
        }

        if (!progressDialog.isShowing())
            progressDialog.show();

        L.showError("Called::++" + updateProfileRequest);

        Call<UpdateProfileModel> call = RetrofitBuilder.getInstance().getRetrofit().GetUpdateProfileOverlay(requestbodyFiles, partMap);

        call.enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                UpdateProfileModel updateProfileModel = response.body();
                if (updateProfileModel.getStatus().equals("Success")) {
                    saveResponseToPreference(updateProfileModel);
                    setView();
                } else {
                    L.showToast(mContext, updateProfileModel.getMsg());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    private void saveResponseToPreference(UpdateProfileModel updateProfileModel) {
        SP.savePreferences(mContext, SP.USER_USERID, updateProfileModel.getUserDetail().getUserId());
        SP.savePreferences(mContext, SP.USER_EMAIL, "" + updateProfileModel.getUserDetail().getEmail());
        SP.savePreferences(mContext, SP.USER_NAME, "" + updateProfileModel.getUserDetail().getFirstName());
        SP.savePreferences(mContext, SP.USER_ADDRESS_1, "" + updateProfileModel.getUserDetail().getAddressLine1());
        SP.savePreferences(mContext, SP.USER_ADDRESS_2, "" + updateProfileModel.getUserDetail().getAddressline2());
        SP.savePreferences(mContext, SP.USER_COUNTRY, "" + updateProfileModel.getUserDetail().getCountry());
        SP.savePreferences(mContext, SP.USER_PHONE, "" + updateProfileModel.getUserDetail().getContact());
        SP.savePreferences(mContext, SP.USER_STATE, "" + updateProfileModel.getUserDetail().getState());
        SP.savePreferences(mContext, SP.USER_CITY, "" + updateProfileModel.getUserDetail().getCity());
        SP.savePreferences(mContext, SP.USER_WEDDING_DATE, "" + updateProfileModel.getUserDetail().getWeddingdate());
        SP.savePreferences(mContext, SP.USER_ZIP, "" + updateProfileModel.getUserDetail().getZipcode());
        SP.savePreferences(mContext, SP.USER_GENDER, "" + updateProfileModel.getUserDetail().getGendar());
        SP.savePreferences(mContext, SP.USER_AVTAR, "" + updateProfileModel.getUserDetail().getProfilePicture());
        SP.savePreferences(mContext, SP.USER_GROOM, "" + updateProfileModel.getUserDetail().getGroom());
        SP.savePreferences(mContext, SP.USER_BRIDAL, "" + updateProfileModel.getUserDetail().getBridal());
        SP.savePreferences(mContext, SP.USER_BRIDAL_PHONE, "" + updateProfileModel.getUserDetail().getBridephone());
        SP.savePreferences(mContext, SP.USER_BRIDAL_EMAIL, "" + updateProfileModel.getUserDetail().getBrideemail());
        SP.savePreferences(mContext, SP.USER_GROOM_EMAIL, "" + updateProfileModel.getUserDetail().getGroomemail());
        SP.savePreferences(mContext, SP.USER_GROOM_PHONE, "" + updateProfileModel.getUserDetail().getGroomphone());
        SP.savePreferences(mContext, SP.USER_BG, "" + updateProfileModel.getUserDetail().getCover());
        SP.savePreferences(mContext, SP.LOGIN_STATUS, SP.FLAG_YES);
    }

    AlertDialog alert = null;

    void ChoosePic(final int TYPE) {
        String[] imagechoose = new String[]{getString(R.string.str_camera), getString(R.string.str_gallary)};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.str_profile_picture));

        builder.setItems(imagechoose, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                if (TYPE == PROFILE_PIC)
                    isProfilePIC = true;
                else
                    isProfilePIC = false;

                alert.dismiss();
                if (item == 1) {
                    new ImagePicker.Builder(MainActivity.this)
                            .mode(ImagePicker.Mode.GALLERY)
                            .compressLevel(ImagePicker.ComperesLevel.NONE)
                            .directory(ImagePicker.Directory.DEFAULT)
                            .extension(ImagePicker.Extension.JPG)
                            // .scale(600, 600)

                            .allowMultipleImages(false)
                            .enableDebuggingMode(true)
                            .build();
                } else {
                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (PROFILE_PIC == TYPE) {
                            ProfilePicfileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, ProfilePicfileUri);
                            startActivityForResult(intent, AppUtils.REQUEST_CAMERA_IMAGE_FOR_PROFILE);
                        } else {
                            ProfileBGfileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, ProfileBGfileUri);
                            startActivityForResult(intent, AppUtils.REQUEST_CAMERA_IMAGE_FOR_PROFILE_BG);
                        }


                    } else {
                        Ask.on(MainActivity.this)
                                .forPermissions(Manifest.permission.CAMERA
                                        , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withRationales("Camera permission to capture profile picture",
                                        "In order to save file you will need to grant storage permission") //optional
                                .go();
                    }
                }
            }
        });
        alert = builder.create();
        alert.show();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (ProfilePicfileUri != null) {
            outState.putString("cameraImageUri", ProfilePicfileUri.toString());
        }
        if (ProfileBGfileUri != null) {
            outState.putString("cameraImageUriBG", ProfileBGfileUri.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("cameraImageUri")) {
            ProfilePicfileUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
        }

        if (savedInstanceState.containsKey("cameraImageUriBG")) {
            ProfileBGfileUri = Uri.parse(savedInstanceState.getString("cameraImageUriBG"));
        }
    }

    /**
     * returning image / video
     */
    private File getOutputMediaFile(int type) {

        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".png");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;



    }


    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        file = null;
        if (getOutputMediaFile(type) != null) {
            file = getOutputMediaFile(type);
            return Uri.fromFile(file);
        } else {
            return null;
        }

    }

    void OpenHelp() {
        if (!SP.getPreferences(mContext, AppUtils.IS_DONE_SHOWCASE).equals("true")) {
            new TapTargetSequence(this)
                    .targets(
                            TapTarget.forToolbarMenuItem(toolbar, R.id.action_next, "Add Events", "Add your Events").id(1).cancelable(false),
                            TapTarget.forToolbarMenuItem(toolbar, R.id.action_notifications, "Notifications", "See Notifications").id(2).cancelable(false),
                            TapTarget.forToolbarNavigationIcon(toolbar, "Menu", "Menus for managing events").id(3).cancelable(false),
                            TapTarget.forView(findViewById(R.id.fab), "Chat", "Chat with buddy").id(4).cancelable(false)
                    )
                    .listener(new TapTargetSequence.Listener() {

                        // This listener will tell us when interesting(tm) events happen in regards
                        // to the sequence
                        @Override
                        public void onSequenceFinish() {
                            // Yay
                            SP.savePreferences(mContext, AppUtils.IS_DONE_SHOWCASE, "true");
                        }

                        @Override
                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                            SP.savePreferences(mContext, AppUtils.IS_DONE_SHOWCASE, "true");
                        }


                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget) {
                            SP.savePreferences(mContext, AppUtils.IS_DONE_SHOWCASE, "true");
                        }
                    }).start();
        }
    }

    //optional
    @AskGranted(Manifest.permission.READ_CONTACTS)
    public void mapAccessGranted() {
        Log.i(TAG, "MAP GRANTED");
        Intent intent = new Intent(mContext, ContactService.class);
        startService(intent);
    }

    @AskGranted(Manifest.permission.READ_PHONE_STATE)
    public void readPhoneState() {
        Log.i(TAG, "MAP GRANTED");
        if (gsmHelper.checkPlayServices()) {
            if (!gsmHelper.isDeviceRegisteredWithUser()) {
                gsmHelper.registerInBackground();
            } else {
                Log.e(TAG, "checkGCMRegistration: Registered...");
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
    }

    @Override
    public void onMyCheckListSelect() {

    }

    @Override
    public void onAssignedCheckList() {

    }

    @Override
    public void onRefreshCheckList() {
        if (GH.isInternetOn(mContext))
            GetUserCheckList();
    }


    void initnavFooter() {
        View footerview = nav_footer_view.getHeaderView(0);
        AppCompatImageView iv_fb = (AppCompatImageView) footerview.findViewById(R.id.iv_fb);
        iv_fb.setOnClickListener(this);
        AppCompatImageView iv_twitter = (AppCompatImageView) footerview.findViewById(R.id.iv_twitter);
        iv_twitter.setOnClickListener(this);
        AppCompatImageView iv_youtube = (AppCompatImageView) footerview.findViewById(R.id.iv_youtube);
        iv_youtube.setOnClickListener(this);
        AppCompatImageView iv_insta = (AppCompatImageView) footerview.findViewById(R.id.iv_insta);
        iv_insta.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.iv_fb:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.facebook.com/BaraatiInc"));
                startActivity(i);
                break;
            case R.id.iv_twitter:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://twitter.com/baraatiinc"));
                startActivity(i);
                break;
            case R.id.iv_youtube:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.youtube.com/channel/UCAnyYuNoRpHEsSlRPgGctOw"));
                startActivity(i);
                break;
            case R.id.iv_insta:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.instagram.com/baraatiinc"));
                startActivity(i);
                break;

        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                Fragment fragment = MyCheckListFragment.newInstance("", "");
                onMyCheckListLoadedListener = (OnMyCheckListLoadedListener) fragment;
                return fragment;
            } else {
                Fragment fragment = AssignedCheckListFragment.newInstance("", "");
                onAssignedCheckListLoadedListener = (OnAssignedCheckListLoadedListener) fragment;
                return fragment;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.myplan);
                case 1:
                    return getString(R.string.sharedplan);
            }
            return null;
        }
    }

    public void setHeaderLayoutWithMenu() {
        /**
         * Get Child View From Navigation Header at index 0;
         */
        View headerLayout = navigationHeader.getHeaderView(0);
        TextView txtUserName = (TextView) headerLayout.findViewById(R.id.txtUserName);

        tvTutorial = (TextViewKozukaGothicProL) headerLayout.findViewById(R.id.tvTutorial);
        tvTutorial.setOnClickListener(menuclickListener);

        tvInspireYourSelf = (TextViewKozukaGothicProL) headerLayout.findViewById(R.id.tvInspireYourSelf);
        tvInspireYourSelf.setOnClickListener(menuclickListener);

        tvShareApp = (TextViewKozukaGothicProL) headerLayout.findViewById(R.id.tvShareThisApp);
        tvShareApp.setOnClickListener(menuclickListener);

        tvOrderHistory = (TextViewKozukaGothicProL) headerLayout.findViewById(R.id.tvOrderHistory);
        tvOrderHistory.setOnClickListener(menuclickListener);

        tvPintrest = (TextViewKozukaGothicProL) headerLayout.findViewById(R.id.tvPintrest);
        tvPintrest.setOnClickListener(menuclickListener);

        tvVendorInquiry = (TextViewKozukaGothicProL) headerLayout.findViewById(R.id.tvVendorInquiry);
        tvVendorInquiry.setOnClickListener(menuclickListener);

        tvSettings = (TextViewKozukaGothicProL) headerLayout.findViewById(R.id.tvSetttings);
        tvSettings.setOnClickListener(menuclickListener);

        CircularImageView circularImageView = (CircularImageView) headerLayout.findViewById(R.id.civProfilePicture);
        txtUserName.setText(UserDetails.getInstance(mContext).getUser_name());


        if (UserDetails.getInstance(mContext).getUser_picture().length() > 0) {
            Picasso.with(mContext)
                    .load(UserDetails.getInstance(mContext).getUser_picture())
                    .into(circularImageView);
        }

        /*headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileDetailActivity.class);
                intent.putExtra(AppUtils.ARG_IS_EDIT, false);
                startActivityForResult(intent, AppUtils.REQUEST_UPDATE_PROFILE);

            }
        });*/
        /**
         * TextView for Logout in NavigationView Header
         */
        headerLayout.findViewById(R.id.imgLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SP.getPreferences(mContext, SP.LOGIN_STATUS).equalsIgnoreCase(SP.FLAG_YES)) {
                    if (GH.isInternetOn(mContext))
                        OnUserLogout();
                } else {
                    //  startActivityForResult(new Intent(mContext,LoginActivity.class),AppUtils.REQUEST_LOGIN_FROM_DASHBOARD);
                }

            }
        });

        headerLayout.findViewById(R.id.imgPlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditProfileActivity.class);
                intent.putExtra(AppUtils.ARG_IS_EDIT, true);
                startActivityForResult(intent, AppUtils.REQUEST_UPDATE_PROFILE);

            }
        });

        headerLayout.findViewById(R.id.civProfilePicture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (EventDateResponsibilityActivity.IS_UPDATED) {
            EventDateResponsibilityActivity.IS_UPDATED = false;
            if (GH.isInternetOn(mContext))
                GetUserCheckList();

        }
        supportInvalidateOptionsMenu();
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
        MainActivity.this.menu = menu;
        menu_item_notification = (MenuItem) menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) menu_item_notification.getIcon();
        if ((String) Hawk.get(AppUtils.ARG_NOTIFICATION_COUNT) != null) {
            Log.e(TAG, "onCreateOptionsMenu: " + (String) Hawk.get(AppUtils.ARG_NOTIFICATION_COUNT));
            GH.setBadgeCount(mContext, icon, (String) Hawk.get(AppUtils.ARG_NOTIFICATION_COUNT));
        }


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_next) {
           /* Toast.makeText(mContext,"You clicked next",Toast.LENGTH_SHORT).show();*/

            startActivityForResult(new Intent(mContext, CategoryListActivity.class), AppUtils.REQUEST_CATEGORY);
        } else if (id == R.id.action_notifications) {
            startActivity(new Intent(mContext, NotificationActivity.class));
        }
        /*if (id == R.id.action_settings) {
            return true;
        }*/


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_odrers) {
            // Handle the camera action
            /* Divyaaa */
            Intent intent = new Intent(mContext, OrderListActivity.class);
            startActivity(intent);
            item.setCheckable(false);
        } else if (id == R.id.nav_blog) {
            item.setCheckable(false);
            Intent intent = new Intent(mContext, InspireYourSelfActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_inquiries) {
            item.setCheckable(false);
            Intent intent = new Intent(mContext, InquiryListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_pintrest) {
            item.setCheckable(false);
          /*  Intent intent=new Intent(mContext,InspireYourSelfActivity.class);
            startActivity(intent);*/
            Intent intent = new Intent(mContext, BlogActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_my_cart) {
            item.setCheckable(false);
            Intent intent = new Intent(mContext, CartActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_aboutus) {
            item.setCheckable(false);
            startActivity(new Intent(mContext, AboutUsActivity.class));
            // Toast.makeText(mContext,"You have clicked "+item,Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_contactus) {
            item.setCheckable(false);
            startActivity(new Intent(mContext, ContactUsActivity.class));
            // Toast.makeText(mContext,"You have clicked "+item,Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_instant_booking) {
            item.setCheckable(false);
            //  Toast.makeText(mContext,"You have clicked "+item,Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_privacy_policy) {
            item.setCheckable(false);
            startActivity(new Intent(mContext, PrivacyPolicyActivity.class));
            // Toast.makeText(mContext,"You have clicked "+item,Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_terms) {
            item.setCheckable(false);
            startActivity(new Intent(mContext, TermsOfUseActivity.class));
            //Toast.makeText(mContext,"You have clicked "+item,Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_account) {
            item.setCheckable(false);
            startActivityForResult(new Intent(mContext, AccountActivity.class), 123);
            //Toast.makeText(mContext,"You have clicked "+item,Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    View.OnClickListener menuclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();


            if (id == R.id.tvTutorial) {

            } else if (id == R.id.tvInspireYourSelf) {
                Intent intent = new Intent(mContext, InspireYourSelfActivity.class);
                startActivity(intent);

            } else if (id == R.id.tvShareThisApp) {


            } else if (id == R.id.tvOrderHistory) {
                Intent intent = new Intent(mContext, OrderListActivity.class);
                startActivity(intent);
            } else if (id == R.id.tvPintrest) {
                Intent intent = new Intent(mContext, BlogActivity.class);
                startActivity(intent);
            } else if (id == R.id.tvSetttings) {
                startActivityForResult(new Intent(mContext, AccountActivity.class), 123);

            }else if (id == R.id.tvVendorInquiry) {
                startActivity(new Intent(mContext, InquiryListActivity.class));

            }

        }
    };

    private void OnUserLogout() {
        if (!progressDialog.isShowing())
            progressDialog.show();

        Call<LogoutModel> call = RetrofitBuilder.getInstance().getRetrofit().Logout(RequestBodyBuilder.getInstanse().getRequestLogoutResponse(UserDetails.getInstance(mContext).getUser_id()));
        call.enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                final LogoutModel logoutResponseInfo = response.body();
                if (logoutResponseInfo.getStatus().equals("Success")) {

                    SP.clear(mContext);
                    SP.savePreferences(mContext, SP.LOGIN_STATUS, SP.FLAG_NO);
                    Hawk.deleteAll();

                    AppSession.getSession().closeAndClear();
                    DataManager.getInstance().clearAllTables();
                    //   QBLogoutCompositeCommand.start(MainActivity.this);

                    startActivity(new Intent(mContext, LoginSignUpActivity.class));
                    L.showToast(mContext, logoutResponseInfo.getMsg());
                    finish();
                } else {
                    L.showToast(mContext, logoutResponseInfo.getMsg());
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LogoutModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

                L.showToast(mContext, t.toString());
            }
        });
    }

    private void GetUserCheckList() {
        if (!progressDialog.isShowing() /*&& !Hawk.contains(AppUtils.OFFLINE_USERCHECK_LIST) && isUpdate*/)
            progressDialog.show();
        Call<UserEventModel> call = RetrofitBuilder.getInstance().getRetrofit().GetUserCheckList(RequestBodyBuilder.getInstanse().getRequestUserCheckListResponse(UserDetails.getInstance(mContext).getUser_id()));
        call.enqueue(new Callback<UserEventModel>() {
            @Override
            public void onResponse(Call<UserEventModel> call, Response<UserEventModel> response) {
                final UserEventModel userEventModel = response.body();
                if (userEventModel.getStatus().equals("Success")) {

                    Log.e(TAG, "onResponse: " + userEventModel.getUnReadNotification());

                    Hawk.put(AppUtils.ARG_NOTIFICATION_COUNT, userEventModel.getUnReadNotification());


                    list.clear();
                    subCategory_list.clear();
                    isUpdate = false;
                    Hawk.put(AppUtils.ARG_OFFLINE_USEREVENTS, userEventModel);
                    subCategory_list.addAll(userEventModel.getAssignChecklist().get(0).getSubCategory());
                    list.addAll(userEventModel.getMychecklist().get(0).getSubCategory());
                    List<String> selected_sucat = new ArrayList<String>();
                    List<String> selected_eventytypes = new ArrayList<String>();
                    List<Event> Userevents = new ArrayList<Event>();
                    List<EventType> eventTypeList = new ArrayList<EventType>();
                    List<EventType> eventTypeList_2 = new ArrayList<EventType>();
                    for (int i = 0; i < list.size(); i++) {
                        selected_sucat.add(list.get(i).getSubcategoryId());
                        for (int k = 0; k < list.get(i).getEvents().size(); k++) {
                            list.get(i).getEvents().get(k).setSub_categoryID(list.get(i).getSubcategoryId());
                            list.get(i).getEvents().get(k).setCategoryID(list.get(i).getCategoryId());
                            for (int l = 0; l < list.get(i).getEvents().get(k).getEventType().size(); l++) {
                                selected_eventytypes.add(list.get(i).getEvents().get(k).getEventType().get(l).getEventTypeEventId());
                                if (list.get(i).getEvents().get(k).getEventType().size() - 1 == l) {
                                    list.get(i).getEvents().get(k).getEventType().get(l).setLast(true);
                                }
                                eventTypeList.add(list.get(i).getEvents().get(k).getEventType().get(l));

                                if (!list.get(i).getEvents().get(k).getEventType().get(l).getStartDate().equalsIgnoreCase("0000-00-00") && !list.get(i).getEvents().get(k).getEventType().get(l).getEndDate().equalsIgnoreCase("0000-00-00")) {
                                    List<Date> list_dates = GH.getDates(list.get(i).getEvents().get(k).getEventType().get(l).getStartDate(), list.get(i).getEvents().get(k).getEventType().get(l).getEndDate());

                                    // String[] array = mContext.getResources().getStringArray(R.array.rainbow);
                                    // String randomStr = array[new Random().nextInt(array.length)];
                                    // Log.e(TAG, "onResponse: Color::"+randomStr);
                                /*    Random rand = new Random();
                                    int r = rand.nextInt(255);
                                    int g = rand.nextInt(255);
                                    int b = rand.nextInt(255);*/
                                    for (int m = 0; m < list_dates.size(); m++) {
                                        Userevents.add(new Event(Color.WHITE, list_dates.get(m).getTime(), list.get(i).getEvents().get(k).getEventType().get(l)));

                                    }
                                }

                            }
                        }
                    }


                    for (int i = 0; i < subCategory_list.size(); i++) {

                        for (int k = 0; k < subCategory_list.get(i).getEvents().size(); k++) {

                            for (int l = 0; l < subCategory_list.get(i).getEvents().get(k).getEventType().size(); l++) {

                                if (subCategory_list.get(i).getEvents().get(k).getEventType().size() - 1 == l) {
                                    subCategory_list.get(i).getEvents().get(k).getEventType().get(l).setLast(true);
                                }
                                eventTypeList_2.add(subCategory_list.get(i).getEvents().get(k).getEventType().get(l));

                            }
                        }
                    }


                    EventTypesSingletone.getInstance().setEventTypeList(eventTypeList);
                    EventTypesSingletone.getInstance().setEventTypeList_assigned(eventTypeList_2);


                    Log.e(TAG, "onResponse: Selected subcat ids::" + TextUtils.join(",", selected_sucat) + " Event Types::+" + TextUtils.join(",", selected_eventytypes));
                    AddCheckListSigletone.getInstance().setSelected_subCat(selected_sucat);
                    AddCheckListSigletone.getInstance().setSelected_eventTypes(selected_eventytypes);
                    UserCalenderEventsSingletone.getInstance().setUserEventlist(Userevents);

                    Hawk.put(AppUtils.OFFLINE_USERCHECK_LIST, list);
                    if (onMyCheckListLoadedListener != null && onAssignedCheckListLoadedListener != null) {
                        onMyCheckListLoadedListener.onMyCheckListLoaded(list);
                        onAssignedCheckListLoadedListener.onAssignedCheckListLoaed(subCategory_list);
                    }


                    //setRecyclerView();
/*
                    if (userEventModel.getDetailResult().size() == 0) {
                        recyclerView.setVisibility(View.GONE);
                        empty_text.setVisibility(View.VISIBLE);
                        empty_text.setText(getString(R.string.str_no_checklsit));
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        empty_text.setVisibility(View.GONE);
                    }*/
                } else {
                  /*  recyclerView.setVisibility(View.GONE);
                    empty_text.setVisibility(View.VISIBLE);
                    empty_text.setText(getString(R.string.str_no_checklsit));*/
                    onMyCheckListLoadedListener.onMyCheckListLoaded(list);
                    onAssignedCheckListLoadedListener.onAssignedCheckListLoaed(subCategory_list);
                }

                progressDialog.dismiss();

                supportInvalidateOptionsMenu();


            }

            @Override
            public void onFailure(Call<UserEventModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
              /*recyclerView.setVisibility(View.GONE);
                empty_text.setVisibility(View.VISIBLE);
                empty_text.setText(getString(R.string.str_no_checklsit));*/
                onMyCheckListLoadedListener.onMyCheckListLoaded(list);
                onAssignedCheckListLoadedListener.onAssignedCheckListLoaed(subCategory_list);
                L.showToast(mContext, t.toString());
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppUtils.REQUEST_CATEGORY || requestCode == AppUtils.REQUEST_UPDATE_USEREVENT) {
                isUpdate = true;
                GetUserCheckList();
            }

            if (requestCode == AppUtils.REQUEST_UPDATE_PROFILE) {
                setHeaderLayoutWithMenu();
                setView();
            }

            if (requestCode == AppUtils.REQUEST_EDITPROFILE) {
                Intent intent = new Intent(mContext, InspireYourSelfActivity.class);
                startActivity(intent);
            }
            if (requestCode == AppUtils.REQUEST_LOGOUT) {
                if (SP.getPreferences(mContext, SP.LOGIN_STATUS).equalsIgnoreCase(SP.FLAG_YES)) {
                    if (GH.isInternetOn(mContext))
                        OnUserLogout();
                }
            }
        }

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            if (isProfilePIC) {
                Uri fileUri = Uri.fromFile(new File(mPaths.get(0)));
                Picasso.with(mContext)
                        .load(fileUri)
                        .into(civProfilePic);
                ProfilePicfileUri = fileUri;
            } else {
                Uri fileUri = Uri.fromFile(new File(mPaths.get(0)));
                Picasso.with(mContext)
                        .load(fileUri)
                        .into(ivBG);
                ProfileBGfileUri = fileUri;
            }


        }

        if (requestCode == AppUtils.REQUEST_CAMERA_IMAGE_FOR_PROFILE && resultCode == RESULT_OK) {

            File file = new Compressor.Builder(mContext)
                    .setMaxWidth(600)
                    .setMaxHeight(600)
                    .setQuality(100)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .build()
                    .compressToFile(new File(ProfilePicfileUri.getPath()));
            Picasso.with(mContext)
                    .load(file)
                    .into(civProfilePic);
        }

        if (requestCode == AppUtils.REQUEST_CAMERA_IMAGE_FOR_PROFILE_BG && resultCode == RESULT_OK) {

            File file = new Compressor.Builder(mContext)
                    .setMaxWidth(600)
                    .setMaxHeight(600)
                    .setQuality(100)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .build()
                    .compressToFile(new File(ProfileBGfileUri.getPath()));
            Picasso.with(mContext)
                    .load(file)
                    .into(ivBG);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        GH.UpdateQuickBloxID(mContext);
    }

    /**
     * Load Product data
     */
/*    private void UpdateQuickBloxID() {


        Call<UpdateQuickBloxModel> call = RetrofitBuilder.getInstance().getRetrofit().UpdateQuickBloxID(RequestBodyBuilder.getInstanse().getRequestToUpdateQuickBlocID(UserDetails.getInstance(mContext).getUser_id(),""+AppSession.getSession().getUser().getId()));

        call.enqueue(new Callback<UpdateQuickBloxModel>() {
            @Override
            public void onResponse(Call<UpdateQuickBloxModel> call, Response<UpdateQuickBloxModel> response) {

            }

            @Override
            public void onFailure(Call<UpdateQuickBloxModel> call, Throwable t) {

            }
        });
    }*/



   /* void setRecyclerView(){
        Log.e(TAG, "setRecyclerView: "+list.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new DashboardAdapter(mContext, list, new OnUserCheckListSelect() {
            @Override
            public void onEventTypeSelect(int position) {

            }

            @Override
            public void OnUserSubcategoryAddSelect(int potition) {
                AddCheckListSigletone.getInstance().reset();
                AddCheckListSigletone.getInstance().setCategoryID(list.get(potition).getCategoryId());
                AddCheckListSigletone.getInstance().setSubcategoryID(list.get(potition).getSubcategoryId());
                List<String> selected_events = new ArrayList<String>();
                for (int i = 0; i < list.get(potition).getEvents().size(); i++) {
                    selected_events.add(list.get(potition).getEvents().get(i).getEventId());
                }
                AddCheckListSigletone.getInstance().setSelected_event(selected_events);
                Intent intent = new Intent(mContext, EventActivty.class);
                intent.putExtra(AppUtils.ARG_CATEGORY_ID, list.get(potition).getCategoryId());
                startActivityForResult(intent, AppUtils.REQUEST_UPDATE_USEREVENT);
            }

            @Override
            public void OnUserSubcategoryDeleteSelect(final int potition) {
                AlertsUtils.getInstance().showYesNoAlert(mContext, String.format(getString(R.string.str_are_you_sure_you_want_to_remove), list.get(potition).getSubcategory()), R.string.str_yes_caps, R.string.str_no_caps, new OnAlertDialogClicked() {
                    @Override
                    public void onPositiveClicked() {
                        DeleteUserEvents(list.get(potition).getUsersubCategoryId(), "0", "0");
                    }

                    @Override
                    public void onNagativeClicked() {

                    }
                });

            }

            @Override
            public void OnUserEventDeleteSelect(final Event event) {
                AlertsUtils.getInstance().showYesNoAlert(mContext, String.format(getString(R.string.str_are_you_sure_you_want_to_remove), event.getEvent()), R.string.str_yes_caps, R.string.str_no_caps, new OnAlertDialogClicked() {
                    @Override
                    public void onPositiveClicked() {
                        DeleteUserEvents("0", event.getUserEventId(), "0");
                    }

                    @Override
                    public void onNagativeClicked() {

                    }
                });

            }

            @Override
            public void OnUserEventAddSelect(Event event) {
                AddCheckListSigletone.getInstance().reset();
                List<String> selected_events = new ArrayList<String>();
                for (int i = 0; i < event.getEventType().size(); i++) {
                    selected_events.add(event.getEventType().get(i).getEventTypeEventId());
                }
                Log.e(TAG, "onResponse: Selected eventtypes::" + TextUtils.join(",", selected_events));

                AddCheckListSigletone.getInstance().setSelected_eventTypes(selected_events);


                AddCheckListSigletone.getInstance().setCategoryID(event.getCategoryID());
                AddCheckListSigletone.getInstance().setSubcategoryID(event.getSub_categoryID());
                AddCheckListSigletone.getInstance().setEventsID(event.getEventCategoryId());
                Intent intent = new Intent(mContext, EventTypeActivity.class);
                startActivityForResult(intent, AppUtils.REQUEST_UPDATE_USEREVENT);
            }

            @Override
            public void OnUserEventTypeDeleteSelect(final EventType eventType) {

                AlertsUtils.getInstance().showYesNoAlert(mContext, String.format(getString(R.string.str_are_you_sure_you_want_to_remove), eventType.getEventType()), R.string.str_yes_caps, R.string.str_no_caps, new OnAlertDialogClicked() {
                    @Override
                    public void onPositiveClicked() {
                        DeleteUserEvents("0", "0", eventType.getUserEventTypeEventsId());
                    }

                    @Override
                    public void onNagativeClicked() {

                    }
                });
            }

            @Override
            public void OnUserEventTypeCalenderSelect(EventType eventType) {
                String categoryID="",subcategory="";
                for (int i = 0; i <list.size(); i++) {
                    for (int j = 0; j < list.get(i).getEvents().size(); j++) {
                        for (int k = 0; k < list.get(i).getEvents().get(j).getEventType().size(); k++) {
                            if(eventType.getUserEventTypeEventsId().equalsIgnoreCase(list.get(i).getEvents().get(j).getEventType().get(k).getUserEventTypeEventsId())){
                                            categoryID=list.get(i).getCategoryId();
                                            subcategory=list.get(i).getSubcategoryId();
                            }
                        }
                    }
                }

                if(!eventType.getStartDate().toString().equalsIgnoreCase("0000-00-00") && !eventType.getEndDate().toString().equalsIgnoreCase("0000-00-00"))
                {
                    Intent intent = new Intent(mContext, CalendarActivity.class);
                    intent.putExtra(AppUtils.ARG_EVENTTYPE_ID, eventType.getUserEventTypeEventsId());
                    intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME, eventType.getEventType());
                    intent.putExtra(AppUtils.ARG_DATE_FROM, eventType.getStartDate());
                    intent.putExtra(AppUtils.ARG_DATE_TO, eventType.getEndDate());
                    intent.putExtra(AppUtils.ARG_CATEGORY_ID, categoryID);
                    intent.putExtra(AppUtils.ARG_SUBCATEGORY_ID,subcategory);

                    startActivityForResult(intent, AppUtils.REQUEST_UPDATE_USEREVENT);
                }else{
                    Intent intent = new Intent(mContext, EventDateActivity.class);
                    intent.putExtra(AppUtils.ARG_EVENTTYPE_ID, eventType.getUserEventTypeEventsId());
                    intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME, eventType.getEventType());
                    intent.putExtra(AppUtils.ARG_DATE_FROM, eventType.getStartDate());
                    intent.putExtra(AppUtils.ARG_DATE_TO, eventType.getEndDate());
                    intent.putExtra(AppUtils.ARG_CATEGORY_ID, categoryID);
                    intent.putExtra(AppUtils.ARG_SUBCATEGORY_ID,subcategory);
                    startActivityForResult(intent, AppUtils.REQUEST_UPDATE_USEREVENT);
                }


            }

            @Override
            public void OnUserEventTypeCartSelect(EventType eventType) {


                Intent intent = new Intent(mContext, VendorBazaarActivity.class);
                intent.putExtra(AppUtils.ARG_EVENTTYPE_ID, eventType.getEventTypeId());
                intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME, eventType.getEventType());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }*/
}
