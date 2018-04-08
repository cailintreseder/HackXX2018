package com.android.hackxx2018;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN";

    private FirebaseAuth mAuth;

    private EditText phoneNumber;
    private EditText email;
    private EditText code;

    private Button sendCode;
    private Button verifyCode;

    private String sentCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        phoneNumber = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        code = findViewById(R.id.verify_edit);

        sendCode = findViewById(R.id.sign_in);
        verifyCode = findViewById(R.id.verify_button);

        verifyCode.setEnabled(false);
        code.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();

        // Assign click listeners
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCode();
            }
        });

        verifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCode();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String val = prefs.getString("Code", null);
        if (val != null) {
            launchActivity();
        }
    }

    private void launchActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void sendCode() {

        String phone = "+1" + phoneNumber.getText().toString();
        String em = email.getText().toString();
        if (TextUtils.isEmpty(em)) {
            Toast.makeText(getApplicationContext(), "Please enter an email address", Toast.LENGTH_SHORT).show();
        }
        else if (phone.equals("+1")) {
            Toast.makeText(getApplicationContext(), "Please enter a phone number", Toast.LENGTH_SHORT).show();
        }
        else {

            String[] TO = {em};
            Random r = new Random();
            int c1 = r.nextInt(10);
            int c2 = r.nextInt(10);
            int c3 = r.nextInt(10);
            int c4 = r.nextInt(10);
            int c5 = r.nextInt(10);
            sentCode = "" + c1 + c2 + c3 + c4 + c5;
            String text = "Hello!\n\n" +
                    "Thank you for signing up for our HackXX messaging app.\n" +
                    "Please enter the code below into the verification box in the app." +
                    "\n\n" + sentCode;

            /*
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Verification Code for HackXX2018");
            emailIntent.putExtra(Intent.EXTRA_TEXT, text);

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                finish();
                Log.i("Finished sending email...", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(LoginActivity.this,
                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }*/
            String fromEmail = "cse110team35@gmail.com";
            String fromPassword = "softwareengineering";
            String emailSubject = "Verification Code for HackXX2018";

            try {
                SendMailTask sender = new SendMailTask(fromEmail, fromPassword);
                sender.sendMail(emailSubject, text, fromEmail, em);
            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
            }

            verifyCode.setEnabled(true);
            code.setEnabled(true);
        }
    }

    private void checkCode() {
        String val = code.getText().toString();
        if (val.equals(sentCode)) {
            Toast.makeText(LoginActivity.this, "User successfully verified",
                    Toast.LENGTH_LONG).show();
            SharedPreferences prefs = getSharedPreferences("UserInfo",MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("Code", sentCode);
            edit.apply();
            launchActivity();
        }
        else {
            Toast.makeText(LoginActivity.this, "Error: Wrong verification code",
                    Toast.LENGTH_LONG).show();
        }
    }

}
