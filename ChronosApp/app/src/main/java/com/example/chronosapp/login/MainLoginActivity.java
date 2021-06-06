package com.example.chronosapp.login;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.chronosapp.NotificationBuilder;
import com.example.chronosapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.concurrent.ExecutionException;
import static com.example.chronosapp.NotificationBuilder.CHANNEL_2_ID;


public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotPassword;
    private Button signButton;
    private EditText editTextLogin, editTextPassword;
    private ImageView passwordShown;
    private TextView login_error_message;
    private login_error_informations errors;
    private NotificationManagerCompat notificationManager;

    private Boolean isPasswordShown = false;
    //    private SignInButton signInButton;
//    private GoogleSignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    private String isUserInDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        notificationManager = NotificationManagerCompat.from(this);

        login_error_message = findViewById(R.id.login_error_message);
        passwordShown = (ImageView) findViewById(R.id.show_password_image);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.show_password);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordShown) {
                    passwordShown.setImageResource(R.drawable.login_eye);
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    passwordShown.setImageResource(R.drawable.login_close_32);
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                isPasswordShown = !isPasswordShown;
            }
        });

        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);*/


//        SignInButton signInButton = findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_WIDE);

//        GoogleSignInButton signInButton = findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_WIDE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
       /* if(account!=null){
            this.startActivity(new Intent(this, com.example.chronosapp.MainMainActivity.class));
        }*/

        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences = getSharedPreferences("userDataSharedPref", MODE_APPEND);
//<<<<<<< HEAD
        if(sharedPreferences!=null && !(sharedPreferences.getString("login","").equals("")))
        {
            com.example.chronosapp.login.BackgroundCheckUserTask backgroundCheckUserTask = new com.example.chronosapp.login.BackgroundCheckUserTask(this);
            String result = null;
            try {
                result = backgroundCheckUserTask.execute(sharedPreferences.getString("userid","")).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String[] resultRemote = result.split("\n");

            System.out.println("RESULT12: " + resultRemote[0]);

            if(resultRemote[0].equals("0")) {
                if (sharedPreferences.getString("is_verified", "").compareTo("0") != 0) {
                    startActivity(new Intent(this, com.example.chronosapp.MainMainActivity.class));
                } else {
                    startActivity(new Intent(this, com.example.chronosapp.login.VerifyActivity.class));
                }
                this.finish();
            }
//=======
//        if (sharedPreferences != null && !(sharedPreferences.getString("login", "").equals(""))) {

//>>>>>>> jk
        }

//        signInButton = findViewById(R.id.sign_in_button);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        editTextLogin = (EditText) findViewById(R.id.login);
        editTextPassword = (EditText) findViewById(R.id.password);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_click_fix);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);


        signButton = (Button) findViewById(R.id.signIn);
        signButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, com.example.chronosapp.login.RegisterUser.class));
                this.finish();
                break;
            case R.id.signIn:
               /* String title = "Jebanie Mantiuka";
                String message = "To czas jebania śmieci";

                Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManager.notify(1, notification);*/

                login();
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(MainLoginActivity.this, com.example.chronosapp.login.RestartPassword.class));
                this.finish();
                break;
            case R.id.sign_in_button:
                signInGoogle();
                break;
        }
    }

    private String login_error(String userName, String password) {
        if (userName.isEmpty() && password.isEmpty()) {
            editTextLogin.setError(errors.login_not_provided);
            editTextPassword.setError(errors.password_not_provided);

            editTextLogin.requestFocus();
            return errors.data_not_provided;
        }

        if (userName.isEmpty()) {
            editTextLogin.setError(errors.login_not_provided);

            editTextLogin.requestFocus();
            return errors.login_not_provided;
        }

        if (password.isEmpty()) {
            editTextPassword.setError(errors.password_not_provided);
            editTextPassword.requestFocus();
            return errors.password_not_provided;
        }

        return "";
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void login() {
        String username = (editTextLogin.getText().toString().trim());
        String password = (editTextPassword.getText().toString().trim());

        String msg = login_error(username, password);

        login_error_message.setText(msg);
        if (!msg.isEmpty())
            return;

        String type = "login";
//        com.example.chronosapp.login.BackgroundTask backgroundTask = new com.example.chronosapp.login.BackgroundTask(getApplicationContext());
        com.example.chronosapp.login.BackgroundTask backgroundTask = new com.example.chronosapp.login.BackgroundTask(this);
        backgroundTask.execute(type, username, password);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {

                String type = "GsignUp";
                BackgroundTask backgroundTask = new BackgroundTask(this);
                backgroundTask.execute(type, acct.getGivenName(), acct.getEmail());
                //com.example.chronosapp.login.BackgroundTask backgroundTask = new com.example.chronosapp.login.BackgroundTask(this);
                //backgroundTask.execute(type, acct.getGivenName(), acct.getEmail());

//                String personName = acct.getDisplayName();
//                String personGivenName = acct.getGivenName();
//                String personFamilyName = acct.getFamilyName();
//                String personEmail = acct.getEmail();
//                String personId = acct.getId();
//                Uri personPhoto = acct.getPhotoUrl();

                SharedPreferences sharedPreferences = this.getSharedPreferences("userDataSharedPref",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("login", acct.getGivenName());
                editor.putString("email", acct.getEmail());
                editor.putString("phone", "");
                editor.apply();

                this.startActivity(new Intent(this, com.example.chronosapp.MainMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                /*System.out.println("dane z google: " + personName + " " + personEmail);

                Toast.makeText(this, "User email :" + personEmail, Toast.LENGTH_SHORT).show();
                mGoogleSignInClient.signOut();*/

            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
            Log.d("wyjatek google", e.toString());
        }
    }
}