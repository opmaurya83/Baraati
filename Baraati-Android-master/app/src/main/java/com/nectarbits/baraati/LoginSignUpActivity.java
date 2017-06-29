package com.nectarbits.baraati;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nectarbits.baraati.Chat.ui.activities.authorization.BaseAuthActivity;
import com.nectarbits.baraati.Chat.utils.KeyboardUtils;
import com.nectarbits.baraati.Models.Login.LoginModel;
import com.nectarbits.baraati.Models.PhoneUPdate.MobileUpdateModel;
import com.nectarbits.baraati.Singletone.LoginResponseSingletone;
import com.nectarbits.baraati.Utils.ContactService;
import com.nectarbits.baraati.View.TextViewKozukaGothicProH;
import com.nectarbits.baraati.generalHelper.AlertsUtils;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.L;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.generalHelper.SP;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.quickblox.q_municate_core.core.command.Command;
import com.quickblox.q_municate_core.models.AppSession;
import com.quickblox.q_municate_core.models.LoginType;
import com.quickblox.q_municate_core.qb.commands.QBUpdateUserCommand;
import com.quickblox.q_municate_core.qb.commands.rest.QBSignUpCommand;
import com.quickblox.q_municate_core.service.QBServiceConsts;
import com.quickblox.q_municate_db.managers.DataManager;
import com.quickblox.users.model.QBUser;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskGranted;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSignUpActivity extends BaseAuthActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = LoginSignUpActivity.class.getSimpleName();
    @Bind(R.id.txtterms)
    TextViewKozukaGothicProH txtterms;
    @Bind(R.id.txtprivacy)
    TextViewKozukaGothicProH txtprivacy;
    @Bind(R.id.btnFBLogin)
    ImageView btnFBLogin;
    @Bind(R.id.btnGLogin)
    ImageView btnGLogin;
    private Context mContext;


    /*Login with FB & Goolgle*/
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;
    String name = "", uid = "0";
    private QBUser qbUser;
    private static final int RC_SIGN_IN = 9001;
    ProgressDialog progressDialog;
    LoginModel loginResponse;
    LoginButton loginButton;
    private SignUpSuccessAction signUpSuccessAction;
    private UpdateUserSuccessAction updateUserSuccessAction;

    /************************/

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mContext = this;

        FacebookSdk.sdkInitialize(getApplicationContext());
        progressDialog = new ProgressDialog(mContext);
        qbUser = new QBUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_cliet_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(AppIndex.API).build();
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid() + " " + user.getDisplayName() + " " + user.getEmail() + " " + user.getProviderId());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                // updateUI(user);
                // [END_EXCLUDE]
            }
        };

        initializefacebook();
        LoginResponseSingletone.getInstance().reset();

    }

    //optional
    @AskGranted(Manifest.permission.READ_CONTACTS)
    public void mapAccessGranted() {
        Log.i(TAG, "MAP GRANTED");
        Intent intent = new Intent(mContext, ContactService.class);
        startService(intent);
    }

    void initializefacebook() {
        // [START initialize_fblogin]
        // Initialize Facebook Login button
        /*facebook id only mobile 9409129983*/
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                // updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                //  updateUI(null);
                // [END_EXCLUDE]
            }
        });
        // [END initialize_fblogin]

    }


    private void onLogin(final String mailid, String password, String id, final String name) {
        if (!progressDialog.isShowing())
            progressDialog.show();
        LoginResponseSingletone.getInstance().reset();
/*        Loginusing:0 means normal login
        Loginusing:1 means facebook login
        Loginusing:2 means google + login*/

        Call<LoginModel> call = RetrofitBuilder.getInstance().getRetrofit().Login(RequestBodyBuilder.getInstanse().get_Request_For_Login(mailid, password, id));
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                loginResponse = response.body();
                mAuth.signOut();

                if (loginResponse.getStatus().equals("Success")) {

                    Ask.on(LoginSignUpActivity.this)
                            .forPermissions(Manifest.permission.READ_CONTACTS)
                            .withRationales("Contact permission for chat") //optional
                            .go();

                    L.showToast(mContext, loginResponse.getMsg());
                    if (loginResponse.getUserDetail().getContact().length() > 0) {
                        setResult(RESULT_OK);
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                        saveResponseToPreference(loginResponse);
                        login(loginResponse.getUserDetail().getEmail(), loginResponse.getUserDetail().getEmail(), "");
                    } else {

                        LoginSignUpActivity.this.name = name;
                        loginResponse.getUserDetail().setFirstName(name);
                        LoginResponseSingletone.getInstance().setLoginResponse(loginResponse);
                        startActivityForResult(new Intent(mContext, MobileVerificationActivity.class), AppUtils.REQUEST_LOGIN);

                    }


                } else {
                    L.showToast(mContext, loginResponse.getMsg());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();
        mAuth.addAuthStateListener(mAuthListener);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.nectarbits.baraati/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.nectarbits.baraati/http/host/path")
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.disconnect();
    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.e(TAG, "onActivityResult: Fail" + result.getStatus());
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                //updateUI(null);
                // [END_EXCLUDE]
            }
        } else if (resultCode == RESULT_OK && requestCode == AppUtils.REQUEST_LOGIN) {

            setResult(RESULT_OK);
            if (GH.isInternetOn(mContext))
                UpdatePhoto();

        } else if ((requestCode == AppUtils.REQUEST_REGISTER || requestCode == AppUtils.REQUEST_LOGIN_FROM_LS) && resultCode == RESULT_OK) {
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.putExtra(AppUtils.IS_REGISTER, true);
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        if (!progressDialog.isShowing())
            progressDialog.show();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginSignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "onComplete: " + task.getResult().getUser().getDisplayName() + " " + task.getResult().getUser().getEmail() + " " + task.getResult().getUser().getUid() + " " + task.getResult().getUser().getProviderId());
                            onLogin(task.getResult().getUser().getEmail(), "", "2", task.getResult().getUser().getDisplayName());
                            login(task.getResult().getUser().getEmail(), task.getResult().getUser().getEmail(), "");
                        }

                        // [START_EXCLUDE]
                        progressDialog.dismiss();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]


    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        if (!progressDialog.isShowing())
            progressDialog.show();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginSignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "onComplete: " + task.getResult().getUser().getDisplayName() + " " + task.getResult().getUser().getEmail() + " " + task.getResult().getUser().getUid() + " " + task.getResult().getUser().getProviderId());
                            if (task.getResult().getUser().getEmail() != null) {
                                onLogin(task.getResult().getUser().getEmail(), "", "1", task.getResult().getUser().getDisplayName());
                                login(task.getResult().getUser().getEmail(), task.getResult().getUser().getEmail(), "");
                            } else {
                                AlertsUtils.getInstance().showOKAlert(mContext, getString(R.string
                                        .your_facebook_is_not_linked_with_mailid));
                                //FirebaseAuth.getInstance().signOut();
                                mAuth.signOut();
                                initializefacebook();
                                // loginButton.performClick();

                            }
                        }


                        // [START_EXCLUDE]
                        progressDialog.dismiss();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_facebook]

    private void UpdatePhoto() {
        if (!progressDialog.isShowing())
            progressDialog.show();
        Call<MobileUpdateModel> call = RetrofitBuilder.getInstance().getRetrofit().MobileUpdate(RequestBodyBuilder.getInstanse().Request_MobileUpdate(LoginResponseSingletone.getInstance().getLoginResponse().getUserDetail().getEmail(), LoginResponseSingletone.getInstance().getPhonenumber(), LoginResponseSingletone.getInstance().getLoginResponse().getUserDetail().getFirstName(), LoginResponseSingletone.getInstance().getLoginResponse().getUserDetail().getFirstName()));
        call.enqueue(new Callback<MobileUpdateModel>() {
            @Override
            public void onResponse(Call<MobileUpdateModel> call, Response<MobileUpdateModel> response) {
                MobileUpdateModel subCategoryModel = response.body();
                if (subCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)) {

                    saveResponseToPreference(LoginResponseSingletone.getInstance().getLoginResponse());

                   /* qbUser.setFullName(LoginResponseSingletone.getInstance().getLoginResponse().getUserDetail().getFirstName());
                    qbUser.setEmail(LoginResponseSingletone.getInstance().getLoginResponse().getUserDetail().getEmail());
                    qbUser.setPassword(LoginResponseSingletone.getInstance().getLoginResponse().getUserDetail().getEmail());
*/
                    qbUser.setFullName(name);
                    qbUser.setEmail(loginResponse.getUserDetail().getEmail());
                    qbUser.setPassword(loginResponse.getUserDetail().getEmail());
                    qbUser.setExternalId(loginResponse.getUserDetail().getUserId());
                    Log.e(TAG, "onResponse: " + LoginResponseSingletone.getInstance().getLoginResponse().getUserDetail().getContact());
                    qbUser.setPhone(LoginResponseSingletone.getInstance().getLoginResponse().getUserDetail().getContact());
                    appSharedHelper.saveUsersImportInitialized(false);
                    DataManager.getInstance().clearAllTables();
                    AppSession.getSession().closeAndClear();
                    QBSignUpCommand.start(mContext, qbUser, null);

                  /*
                    appSharedHelper.saveUsersImportInitialized(false);

                //    DataManager.getInstance().clearAllTables();
                //    AppSession.getSession().closeAndClear();
              //      QBSignUpCommand.start(LoginActivity.this, qbUser, null);
                    QBUpdateUserCommand.start(LoginActivity.this, qbUser, null);*/


                    startActivity(new Intent(mContext, MainActivity.class));
                    finish();

                } else {

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<MobileUpdateModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    private void login(String username, String password, String text) {
        KeyboardUtils.hideKeyboard(this);

        loginType = LoginType.EMAIL;


        appSharedHelper.saveSavedRememberMe(true);
        appSharedHelper.saveEnablePushNotifications(true);
        appSharedHelper.saveFirstAuth(true);

        boolean ownerUser = DataManager.getInstance().getUserDataManager().isUserOwner(username);
        if (!ownerUser) {
            DataManager.getInstance().clearAllTables();
        }

        login(username, password);

    }


    protected void performUpdateUserSuccessAction(Bundle bundle) {
        QBUser user = (QBUser) bundle.getSerializable(QBServiceConsts.EXTRA_USER);
        appSharedHelper.saveFirstAuth(true);
        appSharedHelper.saveSavedRememberMe(true);


    }

    private void performSignUpSuccessAction(Bundle bundle) {
        File image = (File) bundle.getSerializable(QBServiceConsts.EXTRA_FILE);
        QBUser user = (QBUser) bundle.getSerializable(QBServiceConsts.EXTRA_USER);
        QBUpdateUserCommand.start(LoginSignUpActivity.this, user, image);

    }

    private void saveResponseToPreference(LoginModel responseInfo) {
        SP.savePreferences(mContext, SP.USER_USERID, responseInfo.getUserDetail().getUserId());
        SP.savePreferences(mContext, SP.USER_EMAIL, "" + responseInfo.getUserDetail().getEmail());
        SP.savePreferences(mContext, SP.USER_NAME, "" + responseInfo.getUserDetail().getFirstName());
        SP.savePreferences(mContext, SP.USER_ADDRESS_1, "" + responseInfo.getUserDetail().getAddressLine1());
        SP.savePreferences(mContext, SP.USER_ADDRESS_2, "" + responseInfo.getUserDetail().getAddressline2());
        SP.savePreferences(mContext, SP.USER_COUNTRY, "" + responseInfo.getUserDetail().getCountry());
        SP.savePreferences(mContext, SP.USER_PHONE, "" + responseInfo.getUserDetail().getContact());
        SP.savePreferences(mContext, SP.USER_STATE, "" + responseInfo.getUserDetail().getState());
        SP.savePreferences(mContext, SP.USER_CITY, "" + responseInfo.getUserDetail().getCity());
        SP.savePreferences(mContext, SP.USER_WEDDING_DATE, "" + responseInfo.getUserDetail().getWeddingdate());
        SP.savePreferences(mContext, SP.USER_ZIP, "" + responseInfo.getUserDetail().getZipcode());
        SP.savePreferences(mContext, SP.USER_GENDER, "" + responseInfo.getUserDetail().getGendar());
        SP.savePreferences(mContext, SP.USER_GROOM, "" + responseInfo.getUserDetail().getGroom());
        SP.savePreferences(mContext, SP.USER_BRIDAL, "" + responseInfo.getUserDetail().getBridal());
        SP.savePreferences(mContext, SP.LOGIN_STATUS, SP.FLAG_YES);
    }



    private class SignUpSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) throws Exception {
            appSharedHelper.saveUsersImportInitialized(false);
            performSignUpSuccessAction(bundle);
        }
    }

    private class UpdateUserSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) throws Exception {
            performUpdateUserSuccessAction(bundle);
        }
    }


    private void addActions() {
        addAction(QBServiceConsts.SIGNUP_SUCCESS_ACTION, signUpSuccessAction);
        addAction(QBServiceConsts.UPDATE_USER_SUCCESS_ACTION, updateUserSuccessAction);

        updateBroadcastActionList();
    }

    private void removeActions() {
        removeAction(QBServiceConsts.SIGNUP_SUCCESS_ACTION);
        removeAction(QBServiceConsts.UPDATE_USER_SUCCESS_ACTION);

        updateBroadcastActionList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addActions();
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeActions();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_login_sign_up;
    }

    @OnClick(R.id.a_login_tv_sign_in)
    void b_signIn() {
        startActivityForResult(new Intent(mContext, LoginActivity.class), AppUtils.REQUEST_LOGIN_FROM_LS);
    }

    @OnClick(R.id.a_login_tv_sign_up)
    void b_sign_up() {
        startActivityForResult(new Intent(mContext, SignUp.class), AppUtils.REQUEST_REGISTER);
    }

    @OnClick({R.id.txtterms, R.id.txtprivacy,R.id.btnFBLogin, R.id.btnGLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtterms:
                startActivity(new Intent(mContext, TermsOfUseActivity.class));
                break;
            case R.id.txtprivacy:
                startActivity(new Intent(mContext, PrivacyPolicyActivity.class));
                break;
            case R.id.btnFBLogin:
                if (GH.isInternetOn(mContext))
                    loginButton.performClick();
                break;
            case R.id.btnGLogin:
                if (GH.isInternetOn(mContext))
                    signIn();
                break;
        }
    }
}
