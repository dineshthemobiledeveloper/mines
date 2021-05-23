package wahmi.project.alexanderstime.commonutils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wahmi.project.alexanderstime.AT_MainActivity;
import wahmi.project.alexanderstime.R;
import wahmi.project.alexanderstime.retrofit.pojo.GetCitiesByState;
import wahmi.project.alexanderstime.retrofit.pojo.GetStatesByCountry;
import wahmi.project.alexanderstime.retrofit.pojo.ResetPassword;
import wahmi.project.alexanderstime.retrofit.pojo.SaveMyPassword;
import wahmi.project.alexanderstime.retrofit.pojo.SendVerificationCodeToEmail;
import wahmi.project.alexanderstime.retrofit.pojo.SignIn;
import wahmi.project.alexanderstime.retrofit.pojo.UserSignUpSeller;
import wahmi.project.alexanderstime.retrofit.pojo.VerifyAccount;
import wahmi.project.alexanderstime.retrofit.util.RetrofitClient;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static androidx.core.app.ActivityCompat.startActivityForResult;

public class AlexandersTimeUtils
{
    private static ProgressDialog mProgressDialog;

    static String strEmailId,strRegisterPassword,strRegisterRePassword,strOTP,strRegisterEmail,strPassword,strForgotEmail,strForgotOTP,strChangePassword,strChangeRePassword;
    static String strSellerName,strSellerGender,strSellerStreet,strSellerCountry,strSellerState,strSellerCity,strSellerPincode,strSellerPhotoId,strSellerVatDetails,strSellerEmail,strSellerMobile,strSellerOtp,strSellerNewPassword,strSellerRePassword;
    static String whichImage;

    static TextView txtForgotPassword,txtExistingUser,txtRegister,txtSellerPhotoId;
    static EditText editEmail,editPassword,editForgotEmail,editForgotOTP,editChangePassword,editChangeRePassword,editRegisterEmail,editOTP,editNewPassword,editRePassword;
    static EditText editSellerName,editSellerGender,editSellerStreet,editSellerCountry,editSellerState,editSellerCity,editSellerPincode,editSellerVatDetails,editSellerEmail,editSellerMobile,editSellerOtp,editSellerNewPassword,editSellerRePassword;
    static Button btnLogin,btnSendOTP,btnForgotChangePassword,btnSaveNewPassword,btnRegister,btnCreatePassword,btnSaveRegister,btnSellerPhotoId,btn_sellerSignUp;
    static ImageView imgClose,imgForgot,imgRegister;
    static CheckBox checkboxTermsAndCondition;
    static Spinner spinnerSellerState,spinnerSellerCity;
    static LinearLayout layoutRegister,layoutCreateOTP,layoutCreatePassword,layoutForgotEmail,layoutVerifyOTP,layoutChangePassword,layoutSellerSignUp,layoutSellerOtp,layoutSellerCreatePassword;
    static File PhotoIdImage;
    static List<String> stateNameList = new ArrayList<>();
    static List<String> stateIdList = new ArrayList<>();
    static List<String> cityNameList = new ArrayList<>();
    static List<String> cityIdList = new ArrayList<>();
    static String strCityId,strStateId,strCountryId;



    public static void alexandersDisplayToast(Context currentactivityname, String messagetext)
    {
        Toast.makeText(currentactivityname, messagetext, Toast.LENGTH_SHORT).show();
    }

    public static Boolean alexandersOnCheckingInternetConnection(Context currentactivityname)
    {
        Boolean status = null;
        ConnectivityManager connec = (ConnectivityManager)currentactivityname.getSystemService(Context.CONNECTIVITY_SERVICE);
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED )
        {
            return status=true;
        }
        else if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  )
        {
            return status=false;
        }
        return status;
    }

    public static void alexandersNavigation(Context currentactivityname, Class<?> nextactivityname)
    {
        Intent i = new Intent(currentactivityname,nextactivityname);
        currentactivityname.startActivity(i);
    }

    public static void hideActionBar(Activity activity)
    {
        // Call before calling setContentView();
        if (activity != null) {
            activity.getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            if (activity.getActionBar() != null) {
                activity.getActionBar().hide();
            }
        }
    }

    public static void setFullScreen(Activity activity)
    {
        // Call before calling setContentView();
        activity.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void showAlertDialog(Context context, String title, String body)
    {
        showAlertDialog(context, title, body, null);
    }

    public static void showAlertDialog(Context context, String title, String body, DialogInterface.OnClickListener okListener)
    {
        if (okListener == null)
        {
            okListener = new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            };
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(body).setPositiveButton("OK", okListener);
        if (!TextUtils.isEmpty(title))
        {
            builder.setTitle(title);
        }
        builder.show();
    }

    public static void showProgressDialog(Context ctx, String title, String body, boolean isCancellable)
    {
        showProgressDialog(ctx, title, body, null, isCancellable);
    }

    public static void showProgressDialog(Context ctx, String title, String body, Drawable icon, boolean isCancellable)
    {

        if (ctx instanceof Activity)
        {
            if (!((Activity) ctx).isFinishing())
            {
                mProgressDialog = ProgressDialog.show(ctx, title, body, true);
                mProgressDialog.setIcon(icon);
                mProgressDialog.setCancelable(isCancellable);
            }
        }
    }

    public static boolean isProgressDialogVisible()
    {
        return (mProgressDialog != null);
    }

    public static void dismissProgressDialog()
    {
        if (mProgressDialog != null)
        {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }

    public static void onLoadSplashScreenTimer(Context context)
    {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent=new Intent(context, AT_MainActivity.class);
                context.startActivity(intent);
            }
        },9500);
    }

    public static String capitalizeString(String string)
    {
        if (string == null)
        {
            return null;
        }
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i]))
            {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            }
            else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'')
            { // You
                // can add other
                // chars here
                found = false;
            }
        } // end for
        return String.valueOf(chars);
    }

    public static String capitalizeString(String string, int start, int offset)
    {
        if (TextUtils.isEmpty(string))
        {
            return null;
        }
        String formattedString = string.substring(start, offset).toUpperCase() + string.substring(offset, string.length());
        return formattedString;
    }

    public static void onLoadingLoginDialog(Context myContext)
    {
        final Dialog dialog = new Dialog(myContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_loginactivity);
        dialog.setTitle("Title...");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        editEmail = dialog.findViewById(R.id.edit_emailAddress);
        editPassword = dialog.findViewById(R.id.edit_password);
        btnLogin = dialog.findViewById(R.id.btn_logIn);
        imgClose = dialog.findViewById(R.id.img_closeBack);
        txtForgotPassword = dialog.findViewById(R.id.txt_forgot);
        txtRegister = dialog.findViewById(R.id.txt_register);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strEmailId = editEmail.getText().toString().trim();
                strPassword = editPassword.getText().toString().trim();

                if(strEmailId.length()!=0 && Patterns.EMAIL_ADDRESS.matcher(strEmailId).matches())
                {
                    if(strPassword.length()!=0)
                    {
                        Call<SignIn> call = RetrofitClient.getInstance().getMyApi().signIn(strEmailId,strPassword);
                        call.enqueue(new Callback<SignIn>() {
                            @Override
                            public void onResponse(Call<SignIn> call, Response<SignIn> response) {
                                if (response.isSuccessful()) {
                                    List<SignIn.Userresult> UserResults = response.body().getUserresults();
                                    SignIn message = response.body();
                                    if (response.body().getSuccess() == true) {
                                        Toast.makeText(myContext, "LogIn Successfully", Toast.LENGTH_SHORT).show();


                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareUserName, (String) UserResults.get(0).getUsersName());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareGender, (String) UserResults.get(0).getUsersGender());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareEmail,UserResults.get(0).getUsersEmailaddress());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareStreet, (String) UserResults.get(0).getUsersStreet());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareCountry, (String) UserResults.get(0).getUsersCountry());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareState, (String) UserResults.get(0).getUsersState());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareCity, (String) UserResults.get(0).getUsersCity());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.sharePincode, (String) UserResults.get(0).getUsersPincode());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareMobile, (String) UserResults.get(0).getUsersMobilenumber());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareUserType,UserResults.get(0).getUsersUsertype());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareCityName, (String) UserResults.get(0).getCityName());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareStateName, (String) UserResults.get(0).getStateName());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareCountryName,UserResults.get(0).getCountryName());
                                        SharedPrefsUtils.putString(SharedPrefsUtils.PREF_KEY.shareVATDetails, (String) UserResults.get(0).getUsersVatdetails());

                                        dialog.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(myContext, message.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<SignIn> call, Throwable t) {
                            }
                        });
                    }
                    else
                    {
                        editPassword.setError("Please Enter Correct Password");
                    }

                }
                else
                {
                    editEmail.setError("Please Enter Registered Email Address");
                }


            }
        });

        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                editEmail.setError(null);
            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                editPassword.setError(null);
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View reg) {

                final Dialog dialog = new Dialog(myContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_registeractivity);
                dialog.setTitle("Title...");

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                editRegisterEmail = dialog.findViewById(R.id.edit_RegisterEmail);
                editOTP = dialog.findViewById(R.id.edit_otp);
                editNewPassword = dialog.findViewById(R.id.edit_newPassword);
                editRePassword = dialog.findViewById(R.id.edit_rePassword);
                checkboxTermsAndCondition = dialog.findViewById(R.id.checkbox_t_and_c);
                btnRegister = dialog.findViewById(R.id.btn_register);
                btnCreatePassword = dialog.findViewById(R.id.btn_createPassword);
                btnSaveRegister = dialog.findViewById(R.id.btn_saveRegister);
                imgRegister = dialog.findViewById(R.id.img_registerBack);
                txtExistingUser = dialog.findViewById(R.id.txt_existingUser);
                layoutRegister = dialog.findViewById(R.id.layout_register);
                layoutCreateOTP = dialog.findViewById(R.id.layout_createOTP);
                layoutCreatePassword = dialog.findViewById(R.id.layout_createPassword);

                txtExistingUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


                btnRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        strRegisterEmail = editRegisterEmail.getText().toString().trim();

                        if(strRegisterEmail.length()!=0 && Patterns.EMAIL_ADDRESS.matcher(strRegisterEmail).matches())
                        {

                            if(checkboxTermsAndCondition.isChecked())
                            {

                                Call<SendVerificationCodeToEmail> call = RetrofitClient.getInstance().getMyApi().sendVerificationCodeToEmail(strRegisterEmail);
                                call.enqueue(new Callback<SendVerificationCodeToEmail>() {
                                    @Override
                                    public void onResponse(Call<SendVerificationCodeToEmail> call, Response<SendVerificationCodeToEmail> response) {
                                        if(response.isSuccessful()) {
                                            SendVerificationCodeToEmail Results = response.body();
                                            if(response.body().getSuccess())
                                            {
                                                Toast.makeText(myContext, Results.getMessage(), Toast.LENGTH_SHORT).show();
                                                layoutRegister.setVisibility(View.GONE);
                                                layoutCreateOTP.setVisibility(View.VISIBLE);
                                            }
                                            else
                                            {
                                                Toast.makeText(myContext, Results.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<SendVerificationCodeToEmail> call, Throwable t) {
                                    }
                                });

                            }
                            else
                            {
                                Toast.makeText(myContext, "Please Accept Terms and Conditions", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            editRegisterEmail.setError("Enter Valid Email Address");
                        }

                    }
                });

                editRegisterEmail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        editRegisterEmail.setError(null);
                    }
                });

                btnCreatePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        strOTP = editOTP.getText().toString().trim();

                        if(strOTP.length()!=0 && strOTP.length()==6)
                        {

                            Call<VerifyAccount> call = RetrofitClient.getInstance().getMyApi().verifyAccount(strEmailId,strOTP);
                            call.enqueue(new Callback<VerifyAccount>() {
                                @Override
                                public void onResponse(Call<VerifyAccount> call, Response<VerifyAccount> response) {
                                    if(response.isSuccessful()) {
                                        VerifyAccount Results = response.body();
                                        if(response.body().getSuccess())
                                        {
                                            Toast.makeText(myContext, Results.getMessage(), Toast.LENGTH_SHORT).show();
                                            layoutCreateOTP.setVisibility(View.GONE);
                                            layoutCreatePassword.setVisibility(View.VISIBLE);
                                        }
                                        else {
                                            Toast.makeText(myContext, Results.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<VerifyAccount> call, Throwable t) {
                                }
                            });

                        }
                        else
                        {
                            editOTP.setError("Please Enter Valid OTP");
                        }

                    }
                });

                editOTP.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        editOTP.setError(null);
                    }
                });

                btnSaveRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        strRegisterPassword = editNewPassword.getText().toString().trim();
                        strRegisterRePassword = editRePassword.getText().toString().trim();

                        if(strRegisterPassword.length()!=0 && strRegisterRePassword.length()!=0 && strRegisterPassword.contentEquals(strRegisterRePassword))
                        {

                            Call<SaveMyPassword> call = RetrofitClient.getInstance().getMyApi().saveMyPassword(strEmailId,strRegisterPassword);
                            call.enqueue(new Callback<SaveMyPassword>() {
                                @Override
                                public void onResponse(Call<SaveMyPassword> call, Response<SaveMyPassword> response) {
                                    if(response.isSuccessful()) {
                                        SaveMyPassword Results = response.body();
                                        if(response.body().getSuccess())
                                        {
                                            Toast.makeText(myContext, Results.getMessage(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        else
                                        {
                                            Toast.makeText(myContext, Results.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<SaveMyPassword> call, Throwable t) {
                                }
                            });

                        }
                        else
                        {
                            editRePassword.setError("Password does not Match");
                        }
                    }
                });

                editRePassword.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        editRePassword.setError(null);
                    }
                });

                imgRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });


        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(myContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_forgotactivity);
                dialog.setTitle("Title...");

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//                layoutForgotEmail,layoutVerifyOTP,layoutChangePassword
//                strForgotEmail,strForgotOTP,strChangePassword,strChangeRePassword;

                editForgotEmail = dialog.findViewById(R.id.edit_forgotEmail);
                editForgotOTP = dialog.findViewById(R.id.edit_forgotOTP);
                editChangePassword = dialog.findViewById(R.id.edit_ChangePassword);
                editChangeRePassword = dialog.findViewById(R.id.edit_changeRePassword);
                btnSendOTP = dialog.findViewById(R.id.btn_sendOTP);
                btnForgotChangePassword = dialog.findViewById(R.id.btn_ForgotChangePassword);
                btnSaveNewPassword = dialog.findViewById(R.id.btn_saveNewPassword);
                layoutForgotEmail = dialog.findViewById(R.id.layout_forgotEmail);
                layoutVerifyOTP = dialog.findViewById(R.id.layout_verifyOTP);
                layoutChangePassword = dialog.findViewById(R.id.layout_changePassword);
                imgForgot = dialog.findViewById(R.id.img_forgotBack);



                btnSendOTP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        strForgotEmail = editForgotEmail.getText().toString().trim();

                        if(strForgotEmail.length()!=0 && Patterns.EMAIL_ADDRESS.matcher(strForgotEmail).matches())
                        {

                            Call<ResetPassword> call = RetrofitClient.getInstance().getMyApi().resetPassword(strForgotEmail);
                            call.enqueue(new Callback<ResetPassword>() {
                                @Override
                                public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                                    if(response.isSuccessful()) {
                                        ResetPassword Results = response.body();
                                        if(response.body().getSuccess())
                                        {
                                            Toast.makeText(myContext, Results.getMessage(), Toast.LENGTH_SHORT).show();
                                            layoutForgotEmail.setVisibility(View.GONE);
                                            layoutVerifyOTP.setVisibility(View.VISIBLE);
                                        }
                                        else
                                        {
                                            Toast.makeText(myContext, Results.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<ResetPassword> call, Throwable t) {
                                }
                            });

                        }
                        else
                        {
                            editForgotEmail.setError("Enter Valid Email Address");
                        }


                    }
                });

                editForgotEmail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        editForgotEmail.setError(null);
                    }
                });

                btnForgotChangePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        strForgotOTP = editForgotOTP.getText().toString().trim();

                        if(strForgotOTP.length()!=0 && strForgotOTP.length()==6)
                        {
                            Call<VerifyAccount> call = RetrofitClient.getInstance().getMyApi().verifyAccount(strForgotEmail,strForgotOTP);
                            call.enqueue(new Callback<VerifyAccount>() {
                                @Override
                                public void onResponse(Call<VerifyAccount> call, Response<VerifyAccount> response) {
                                    if(response.isSuccessful()) {
                                        VerifyAccount Results = response.body();
                                        if(response.body().getSuccess())
                                        {
                                            Toast.makeText(myContext, Results.getMessage(), Toast.LENGTH_SHORT).show();
                                            layoutVerifyOTP.setVisibility(View.GONE);
                                            layoutChangePassword.setVisibility(View.VISIBLE);
                                        }
                                        else {
                                            Toast.makeText(myContext, Results.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<VerifyAccount> call, Throwable t) {
                                }
                            });
                        }
                        else
                        {
                            editForgotOTP.setError("Please Enter Valid OTP");
                        }
                    }
                });

                editForgotOTP.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        editForgotOTP.setError(null);

                    }
                });

                btnSaveNewPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        strChangePassword = editChangePassword.getText().toString().trim();
                        strChangeRePassword = editChangePassword.getText().toString().trim();

                        if(strChangePassword.length()!=0 && strChangeRePassword.length()!=0 && strChangePassword.contentEquals(strChangeRePassword))
                        {
                            Call<SaveMyPassword> call = RetrofitClient.getInstance().getMyApi().saveMyPassword(strForgotEmail,strChangePassword);
                            call.enqueue(new Callback<SaveMyPassword>() {
                                @Override
                                public void onResponse(Call<SaveMyPassword> call, Response<SaveMyPassword> response) {
                                    if(response.isSuccessful()) {
                                        SaveMyPassword Results = response.body();
                                        if(response.body().getSuccess())
                                        {
                                            Toast.makeText(myContext, Results.getMessage(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        else
                                        {
                                            Toast.makeText(myContext, Results.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<SaveMyPassword> call, Throwable t) {
                                }
                            });
                        }
                        else
                        {
                            editChangePassword.setError("Password does not Match");
                        }

                    }
                });

                editChangePassword.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        editChangePassword.setError(null);

                    }
                });

                imgForgot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });



                dialog.show();
            }
        });

        dialog.show();

    }


}
