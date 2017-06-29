package com.nectarbits.baraati;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nectarbits.baraati.Adapters.AdapterForCityList;
import com.nectarbits.baraati.Adapters.AdapterForState;
import com.nectarbits.baraati.Interface.OnStateCitySelect;
import com.nectarbits.baraati.Models.CountryModel.CityInfo;
import com.nectarbits.baraati.Models.CountryModel.CountryInfo;
import com.nectarbits.baraati.Models.CountryModel.StateInfo;
import com.nectarbits.baraati.Models.UpdateProfile.UpdateProfileModel;
import com.nectarbits.baraati.Models.UpdateProfileRequest.UpdateProfileRequest;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.EditTextTitle;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.DBHelper;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.L;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.generalHelper.SP;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.squareup.picasso.Picasso;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskGranted;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = EditProfileActivity.class.getSimpleName();


    @Bind(R.id.civProfilePicture)
    ImageView civProfilePicture;
    @Bind(R.id.evName)
    EditTextTitle evName;
    @Bind(R.id.evEmail)
    EditTextTitle evEmail;
    @Bind(R.id.evPhone)
    EditTextTitle evPhone;
    @Bind(R.id.evAddress)
    EditTextTitle evAddress;
    @Bind(R.id.evAddress2)
    EditTextTitle evAddress2;
    @Bind(R.id.evPinCode)
    EditTextTitle evPinCode;

    @Bind(R.id.evGroom)
    EditTextTitle evGroom;
    @Bind(R.id.evBridal)
    EditTextTitle evBridal;

    @Bind(R.id.tvGender)
    TextViewTitle tvGender;

    @Bind(R.id.tvCountry)
    TextViewTitle tvCount;

    @Bind(R.id.tvtDays)
    TextViewTitle tvtDays;
    @Bind(R.id.tvtHours)
    TextViewTitle tvtHours;
    @Bind(R.id.tvtMinutes)
    TextViewTitle tvtMinutes;
    @Bind(R.id.tvtSeconds)
    TextViewTitle tvtSeconds;

    @Bind(R.id.tvState)
    TextViewTitle tvState;

    @Bind(R.id.tvCity)
    TextViewTitle tvCity;

    @Bind(R.id.tvWeddingDate)
    TextViewTitle tvWeddingDate;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.llTime)
    LinearLayout llTime;
    @Bind(R.id.ivChangeProfilePic)
    ImageView ivChangeProfilePic;
    @Bind(R.id.evGroomPhone)
    EditTextTitle evGroomPhone;
    @Bind(R.id.evGroomEmail)
    EditTextTitle evGroomEmail;
    @Bind(R.id.evBridalPhone)
    EditTextTitle evBridalPhone;
    @Bind(R.id.evBridalEmail)
    EditTextTitle evBridalEmail;
    @Bind(R.id.ttvChangePassword)
    TextViewTitle ttvChangePassword;
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
    Boolean isEdit = false;
    MenuItem item;

    @OnClick(R.id.civProfilePicture)
    void OnChooseImage() {
       /* new ImagePicker.Builder(EditProfileActivity.this)
                .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.NONE)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.JPG)
               // .scale(600, 600)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();*/
        fileUri = Uri.EMPTY;
        String[] imagechoose = new String[]{getString(R.string.str_camera), getString(R.string.str_gallary)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.str_profile_picture));
        builder.setItems(imagechoose, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                alert.dismiss();
                if (item == 1) {
                   /* Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image*//*");
                    startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.str_profile_picture)), AppUtils.REQUEST_GALLARY_IMAGE);*/
                    new ImagePicker.Builder(EditProfileActivity.this)
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
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                        if (fileUri != Uri.EMPTY)
                            startActivityForResult(intent, AppUtils.REQUEST_CAMERA_IMAGE);
                    } else {
                        Ask.on(EditProfileActivity.this)
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

    @AskGranted(Manifest.permission.CAMERA)
    public void fileAccessGranted() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (fileUri != Uri.EMPTY)
            startActivityForResult(intent, AppUtils.REQUEST_CAMERA_IMAGE);
    }

    @OnClick(R.id.ttvChangePassword)
    void onClickChangePassword() {
        Intent intent = new Intent(mContext, ChangePassword.class);
        startActivity(intent);
    }

    @OnClick(R.id.tvWeddingDate)
    void onClickWeddingDate() {
        DateDialog();
    }

    @OnClick(R.id.tvGender)
    void OnGender() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.str_select_gender));
        builder.setItems(mrOrMrsArray, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                alert.dismiss();
                gender = item;
                tvGender.setText(mrOrMrsArray[item]);
            }
        });
        alert = builder.create();
        alert.show();
    }


    @OnClick(R.id.tvState)
    void OnState() {
        openState();

    }


    @OnClick(R.id.tvCity)
    void OnCity() {
        if (stateInfo.getId() > 0) {
            openCity();
        } else {
            Toast.makeText(EditProfileActivity.this, getString(R.string.str_please_select_state_first), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);

        dbHelper = new DBHelper(this);
        try {
            dbHelper.copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        if(arrayListCountry!=null && !arrayListCountry.isEmpty()) {
            tvCount.setText(arrayListCountry.get(0).getName());
        }
        setView();
        setEnable(isEdit);

    }

    void setView() {
        evName.setText(UserDetails.getInstance(mContext).getUser_name());
        evEmail.setText(UserDetails.getInstance(mContext).getUser_emailid());

        if (UserDetails.getInstance(mContext).getUser_picture().length() > 0) {
            Picasso.with(mContext)
                    .load(UserDetails.getInstance(mContext).getUser_picture())
                    .into(civProfilePicture);
        }

        if (UserDetails.getInstance(mContext).getUser_emailid().length() > 0) {
            evPhone.setText(UserDetails.getInstance(mContext).getUser_phone());
        }

        if (UserDetails.getInstance(mContext).getUser_wedding_date().length() > 0 && !UserDetails.getInstance(mContext).getUser_wedding_date().equalsIgnoreCase("0000-00-00")) {
            tvWeddingDate.setText(UserDetails.getInstance(mContext).getUser_wedding_date());
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
            evAddress.setText(UserDetails.getInstance(mContext).getUser_address1());
        }

        if (UserDetails.getInstance(mContext).getUser_address2().length() > 0) {
            evAddress2.setText(UserDetails.getInstance(mContext).getUser_address2());
            Log.e(TAG, "setView: " + UserDetails.getInstance(mContext).getUser_address2());
        }

        if (UserDetails.getInstance(mContext).getUser_gender().length() > 0) {
            tvGender.setText(mrOrMrsArray[Integer.parseInt(UserDetails.getInstance(mContext).getUser_gender())]);
            gender = Integer.parseInt(UserDetails.getInstance(mContext).getUser_gender());
        }

        if (UserDetails.getInstance(mContext).getUser_zip().length() > 0) {
            evPinCode.setText(UserDetails.getInstance(mContext).getUser_zip());
        }

        if (UserDetails.getInstance(mContext).getUser_groom().length() > 0) {
            evGroom.setText(UserDetails.getInstance(mContext).getUser_groom());

        }

        if (UserDetails.getInstance(mContext).getUser_bridal().length() > 0) {
            evBridal.setText(UserDetails.getInstance(mContext).getUser_bridal());

        }

        if (UserDetails.getInstance(mContext).getUser_zip().length() > 0) {
            evPinCode.setText(UserDetails.getInstance(mContext).getUser_zip());
        }


        if (UserDetails.getInstance(mContext).getUser_groom_email().length() > 0) {
            evGroomEmail.setText(UserDetails.getInstance(mContext).getUser_groom_email());
        }
        if (UserDetails.getInstance(mContext).getUser_groom_phone().length() > 0) {
            evGroomPhone.setText(UserDetails.getInstance(mContext).getUser_groom_phone());
        }

        if (UserDetails.getInstance(mContext).getUser_bridal_email().length() > 0) {
            evBridalEmail.setText(UserDetails.getInstance(mContext).getUser_bridal_email());
        }
        if (UserDetails.getInstance(mContext).getUser_bridal_phone().length() > 0) {
            evBridalPhone.setText(UserDetails.getInstance(mContext).getUser_bridal_phone());
        }



        if (UserDetails.getInstance(mContext).getUser_zip().length() > 0) {
            evPinCode.setText(UserDetails.getInstance(mContext).getUser_zip());
        }
        if (UserDetails.getInstance(mContext).getUser_zip().length() > 0) {
            evPinCode.setText(UserDetails.getInstance(mContext).getUser_zip());
        }
        if (UserDetails.getInstance(mContext).getUser_zip().length() > 0) {
            evPinCode.setText(UserDetails.getInstance(mContext).getUser_zip());
        }

        if (UserDetails.getInstance(mContext).getUser_state().length() > 0) {
            for (int i = 0; i < stateInfoArrayList.size(); i++) {
                if (stateInfoArrayList.get(i).getId() == (Integer.parseInt(UserDetails.getInstance(mContext).getUser_state()))) {
                    tvState.setText(stateInfoArrayList.get(i).getName());
                    stateInfo.setId(stateInfoArrayList.get(i).getId());
                    stateInfo.setName(stateInfoArrayList.get(i).getName());
                    stateInfo.setCountry_id(stateInfoArrayList.get(i).getCountry_id());
                }
            }

        }

        if (UserDetails.getInstance(mContext).getUser_city().length() > 0) {
            getCityNameFromCountryId();
            for (int i = 0; i < cityInfoArrayList.size(); i++) {
                if (cityInfoArrayList.get(i).getId() == (Integer.parseInt(UserDetails.getInstance(mContext).getUser_city()))) {
                    tvCity.setText(cityInfoArrayList.get(i).getCityName());
                    cityInfo.setId(cityInfoArrayList.get(i).getId());
                    cityInfo.setCityName(cityInfoArrayList.get(i).getCityName());
                    cityInfo.setState_id(cityInfoArrayList.get(i).getState_id());
                }
            }

        }

    }

    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_update_profile));
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


                    if (evName.getText().toString().trim().equals("")) {
                        L.showToast(mContext, getString(R.string.str_name_must));
                    } else if (!GH.isValidName(evName.getText().toString().trim())) {
                        Toast.makeText(mContext, getString(R.string.str_name_range), Toast.LENGTH_SHORT).show();
                    } else if (evGroom.getText().toString().trim().equals("")) {
                        Toast.makeText(mContext, getString(R.string.str_groom_must), Toast.LENGTH_SHORT).show();
                    } else if (!GH.isValidEmail(evGroomEmail.getText().toString().trim())) {
                        Toast.makeText(mContext, R.string.vaild_groom_email, Toast.LENGTH_SHORT).show();
                    }
                    else if (evBridal.getText().toString().trim().equals("")) {
                        Toast.makeText(mContext, getString(R.string.str_bridal_must), Toast.LENGTH_SHORT).show();
                    }else if (!GH.isValidEmail(evBridalEmail.getText().toString().trim())) {
                        Toast.makeText(mContext, R.string.valid_bride_email, Toast.LENGTH_SHORT).show();
                    } else if (!GH.isValidName(evPhone.getText().toString().trim())) {
                        Toast.makeText(mContext, getString(R.string.str_phone_must_range), Toast.LENGTH_SHORT).show();
                    }/*else if(evPhone.getText().toString().length()<10 && evPhone.getText().toString().length()>12){
                        Toast.makeText(mContext, getString(R.string.str_phone_must_range), Toast.LENGTH_SHORT).show();
                    }*/ else if (evAddress.getText().toString().length() == 0) {
                        Toast.makeText(mContext, getString(R.string.str_address1_must), Toast.LENGTH_SHORT).show();
                    } else if (stateInfo.getId() == 0) {
                        Toast.makeText(mContext, getString(R.string.str_please_select_state_first), Toast.LENGTH_SHORT).show();
                    } else if (cityInfo.getId() == 0) {
                        Toast.makeText(mContext, getString(R.string.str_city_must), Toast.LENGTH_SHORT).show();
                    }/*else if(evPinCode.getText().toString().length()==0){
                        Toast.makeText(mContext, getString(R.string.str_pin_must), Toast.LENGTH_SHORT).show();
                    }*/ else if (evPinCode.getText().toString().length() != 6) {
                        Toast.makeText(mContext, getString(R.string.str_pin_must_range), Toast.LENGTH_SHORT).show();
                    } else {
                        if (GH.isInternetOn(mContext))
                            onUpdateProfile();
                    }
                }
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void DateDialog() {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                tvWeddingDate.setText(year + "-" + monthOfYear + "-" + dayOfMonth);

            }
        };
        long currentTime = new Date().getTime();
        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, year, month, day);
        dpDialog.getDatePicker().setMinDate(currentTime);
        dpDialog.show();

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


    private void onUpdateProfile() {

        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        final Map<String, Object> requestbodyFiles = new HashMap<>();
        HashMap partMap = new HashMap();

        final UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setAddressLine1(evAddress.getText().toString());
        Log.e(TAG, "onUpdateProfile: City ID::" + cityInfo.getId());
        updateProfileRequest.setCity("" + cityInfo.getId());
        updateProfileRequest.setState("" + stateInfo.getId());
        updateProfileRequest.setCountry("" + arrayListCountry.get(0).getId());
        updateProfileRequest.setAddressLine1(evAddress.getText().toString());
        updateProfileRequest.setAddressline2(evAddress2.getText().toString());
        updateProfileRequest.setFirstName(evName.getText().toString());
        updateProfileRequest.setLastName(evName.getText().toString());
        updateProfileRequest.setPhone(evPhone.getText().toString());
        updateProfileRequest.setUser_profile_image("");
        updateProfileRequest.setZipcode(evPinCode.getText().toString());
        updateProfileRequest.setGendar("" + gender);
        updateProfileRequest.setWeddingdate(tvWeddingDate.getText().toString());
        updateProfileRequest.setUserid(UserDetails.getInstance(mContext).getUser_id());
        updateProfileRequest.setGroom(evGroom.getText().toString());
        updateProfileRequest.setBridal(evBridal.getText().toString());
        updateProfileRequest.setBrideemail(evBridalEmail.getText().toString());
        updateProfileRequest.setBridephone(evBridalPhone.getText().toString());
        updateProfileRequest.setGroomemail(evGroomEmail.getText().toString());
        updateProfileRequest.setGroomphone(evGroomPhone.getText().toString());
        //updateProfileRequest.set(UserDetails.getInstance(mContext).getUser_id());

        Log.e(TAG, "onUpdateProfile: " + updateProfileRequest.toString());
        requestbodyFiles.put("Editprofile_Request", updateProfileRequest);

        if (fileUri != Uri.EMPTY) {
            File file = new Compressor.Builder(mContext)
                    .setMaxWidth(600)
                    .setMaxHeight(600)
                    .setQuality(100)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .build()
                    .compressToFile(new File(fileUri.getPath()));
            partMap.put("user_profile_image\"; filename=\"" + new File(fileUri.getPath()).getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
        }

        if (!progressDialog.isShowing())
            progressDialog.show();

        L.showError("Called::++" + updateProfileRequest);

        Call<UpdateProfileModel> call = RetrofitBuilder.getInstance().getRetrofit().GetUpdateProfile(requestbodyFiles, partMap);

        call.enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                updateProfileModel = response.body();
                if (updateProfileModel.getStatus().equals("Success")) {
                    setResult(RESULT_OK);
                    item.setTitle(getString(R.string.str_edit));
                    isEdit = false;
                    setEnable(isEdit);
                    L.showToast(mContext, getString(R.string.str_profile_updated_successfully));
                    saveResponseToPreference();
                    finish();
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


    private void saveResponseToPreference() {
       /* SP.savePreferences(mContext,SP.USER_NAME, updateProfileModel.getUserDetail().getFirstName());
        SP.savePreferences(mContext,SP.USER_ADDRESS_1,updateProfileModel.getUserDetail().getAddressLine1());
        SP.savePreferences(mContext,SP.USER_AVTAR,updateProfileModel.getUserDetail().getProfilePicture());*/
        //SP.savePreferences(mContext,SP.USER_GENDER,updateProfileModel.getUserDetail().get());
        //SP.savePreferences(mContext,SP.USER_WEDDING_DATE,updateProfileModel.getUserDetail().get());

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



        SP.savePreferences(mContext, SP.LOGIN_STATUS, SP.FLAG_YES);
    }

    private void openState() {
        stateInfosListTemp.clear();
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_list_item_for_country);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        EditText editText = (EditText) dialog.findViewById(R.id.edttxtCountry);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerViewForCountry);
        editText.addTextChangedListener(textWatcher_State);
        getCountryFromDB();

        adapterForState = new AdapterForState(stateInfoArrayList, onStateCitySelect_state);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterForState);
        dialog.show();
    }


    OnStateCitySelect onStateCitySelect_state = new OnStateCitySelect() {
        @Override
        public void onselected(int position) {
            dialog.dismiss();
            if (stateInfosListTemp.size() == 0) {
                stateInfo.setId(stateInfoArrayList.get(position).getId());
                stateInfo.setName(stateInfoArrayList.get(position).getName());
                stateInfo.setCountry_id(stateInfoArrayList.get(position).getCountry_id());
                tvState.setText(stateInfoArrayList.get(position).getName());
            } else {
                stateInfo.setId(stateInfosListTemp.get(position).getId());
                stateInfo.setName(stateInfosListTemp.get(position).getName());
                stateInfo.setCountry_id(stateInfosListTemp.get(position).getCountry_id());
                tvState.setText(stateInfosListTemp.get(position).getName());
            }

            cityInfo = new CityInfo();
            tvCity.setText(getString(R.string.str_city));
        }
    };
    TextWatcher textWatcher_State = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && s.length() > 0) {
                //  arrayListCountry =getFilteredCountries(s.toString().toLowerCase().trim());
                Log.e(TAG, "afterTextChanged: > 0");
                stateInfosListTemp = getFilteredStates(s.toString().toLowerCase().trim());
                AdapterForState adapterForCountry1 = new AdapterForState(stateInfosListTemp, onStateCitySelect_state);
                recyclerView.setAdapter(adapterForCountry1);
                adapterForCountry1.notifyDataSetChanged();

            } else {
                Log.e(TAG, "afterTextChanged: < 0");
                stateInfosListTemp.clear();
                getCountryFromDB();
                AdapterForState adapterForCountry1 = new AdapterForState(stateInfoArrayList, onStateCitySelect_state);

                recyclerView.setAdapter(adapterForCountry1);
                adapterForCountry1.notifyDataSetChanged();
            }
        }

    };

    private ArrayList<StateInfo> getFilteredStates(String trim) {
        ArrayList<StateInfo> arrayList1 = new ArrayList<StateInfo>();

        for (int i = 0; i < stateInfoArrayList.size(); i++) {
            if (stateInfoArrayList.get(i).getName().toLowerCase().contains(trim)) {
                arrayList1.add(stateInfoArrayList.get(i));
            }
        }

        return arrayList1;
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


    private void openCity() {
        cityInfoslistTemp.clear();
        getCityNameFromCountryId();
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_list_item_for_country);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        EditText editText = (EditText) dialog.findViewById(R.id.edttxtCountry);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerViewForCountry);
        editText.addTextChangedListener(textWatcher_City);
        getCountryFromDB();

        adapterForCityList = new AdapterForCityList(cityInfoArrayList, onStateCitySelect_city);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterForCityList);
        dialog.show();
    }

    OnStateCitySelect onStateCitySelect_city = new OnStateCitySelect() {
        @Override
        public void onselected(int position) {
            dialog.dismiss();
            if (cityInfoslistTemp.size() == 0) {
                cityInfo.setId(cityInfoArrayList.get(position).getId());

                cityInfo.setCityName(cityInfoArrayList.get(position).getCityName());

                tvCity.setText(cityInfoArrayList.get(position).getCityName());
            } else {
                cityInfo.setId(cityInfoslistTemp.get(position).getId());

                cityInfo.setCityName(cityInfoslistTemp.get(position).getCityName());

                tvCity.setText(cityInfoslistTemp.get(position).getCityName());
            }
        }
    };


    TextWatcher textWatcher_City = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && s.length() > 0) {
                //  arrayListCountry =getFilteredCountries(s.toString().toLowerCase().trim());
                cityInfoslistTemp = getFilteredCity(s.toString().toLowerCase().trim());
                AdapterForCityList adapterForCountry1 = new AdapterForCityList(cityInfoslistTemp, onStateCitySelect_city);
                recyclerView.setAdapter(adapterForCountry1);
                adapterForCountry1.notifyDataSetChanged();

            } else {
                cityInfoslistTemp.clear();
                getCityNameFromCountryId();
                AdapterForCityList adapterForCountry1 = new AdapterForCityList(cityInfoArrayList, onStateCitySelect_state);

                recyclerView.setAdapter(adapterForCountry1);
                adapterForCountry1.notifyDataSetChanged();
            }
        }

    };

    private ArrayList<CityInfo> getFilteredCity(String trim) {
        ArrayList<CityInfo> arrayList1 = new ArrayList<CityInfo>();

        for (int i = 0; i < cityInfoArrayList.size(); i++) {
            if (cityInfoArrayList.get(i).getCityName().toLowerCase().contains(trim)) {
                arrayList1.add(cityInfoArrayList.get(i));
            }
        }

        return arrayList1;
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

    private void getCityNameFromCountryIdAll() {

        String selectCity = "select * from cities";
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


    /**
     * returning image / video
     */
    private File getOutputMediaFile(int type) {

        // External sdcard location
/*

        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED ) {
*/


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

      /*  } else {
            //Toast.makeText(this, R.string.error_permission_map, Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(CreateMagazine.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITEXTERNALSTORAGE_IMAGE);

            return null;
        }*/


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

    void setEnable(Boolean enable) {

        evGroom.setEnabled(enable);
        evBridal.setEnabled(enable);
        evAddress.setEnabled(enable);
        // evEmail.setEnabled(enable);
        evAddress2.setEnabled(enable);
        evName.setEnabled(enable);
        //  evPhone.setEnabled(enable);
        evPinCode.setEnabled(enable);
        tvCity.setEnabled(enable);
        tvState.setEnabled(enable);
        tvCount.setEnabled(enable);
        tvGender.setEnabled(enable);
        tvWeddingDate.setEnabled(enable);
        civProfilePicture.setEnabled(enable);

        if (enable) {

            llTime.setVisibility(View.GONE);
        } else {
            llTime.setVisibility(View.VISIBLE);

        }
    }


}