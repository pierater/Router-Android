package edu.csumb.cst438.router;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Profile extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private UserServices userServices;
    private Button mSaveButton;
    private Button mMetric;
    private Button mImperial;
    private Button mPublic;
    private Button mPrivate;
    private EditText mUsername;
    private EditText mEmail;
    private EditText mBio;
    private Boolean isPrivate;
    private Boolean isMetric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userServices = Application.userService;
        mSaveButton = (Button)findViewById(R.id.ProfileSaveButton);
        mMetric = (Button)findViewById(R.id.DistanceUnitsButton_Kilometers);
        mImperial = (Button)findViewById(R.id.DistanceUnitsButton_Miles);
        mPublic = (Button)findViewById(R.id.ProfilePrivacyButton_Public);
        mPrivate = (Button)findViewById(R.id.ProfilePrivacyButton_Private);
        mUsername = (EditText)findViewById(R.id.UsernameEditText);
        mEmail = (EditText)findViewById(R.id.EmailTextEdit);
        mBio = (EditText)findViewById(R.id.ProfileBioEditText);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.profile_drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.profile_left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.menu_string_array)));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getProfile();
    }

    public void getProfile() {
        EditText mUsername = (EditText) findViewById(R.id.UsernameEditText);
        mUsername.setHint(userServices.getUserName());

        EditText email = (EditText) findViewById(R.id.EmailTextEdit);
        email.setHint(userServices.getUserEmail());

        EditText bio = (EditText) findViewById(R.id.ProfileBioEditText);
        bio.setHint(userServices.getUserBio());
        if(userServices.getUserPrivacy().toUpperCase().equals("PUBLIC")) {
            setPublic(mPublic);
        } else {
            setPrivate(mPrivate);
        }
        if(userServices.getUserUnits().toUpperCase().equals("IMPERIAL")) {
            setImperial(mImperial);
        } else {
            setMetric(mMetric);
        }
    }

    public void setPublic(View view) {
        isPrivate = false;
        mPublic.setBackgroundColor(0xff5a595b);
        mPrivate.setBackgroundColor(000000);
    }

    public void setPrivate(View view) {
        isPrivate = true;
        mPrivate.setBackgroundColor(0xff5a595b);
        mPublic.setBackgroundColor(000000);
    }

    public void setImperial(View view) {
        isMetric = false;
        mImperial.setBackgroundColor(0xff5a595b);
        mMetric.setBackgroundColor(000000);
    }

    public void setMetric(View view) {
        isMetric = true;
        mMetric.setBackgroundColor(0xff5a595b);
        mImperial.setBackgroundColor(000000);
    }

    public void saveProfile(View view) {

        if(!TextUtils.isEmpty(mBio.getText().toString())) {
            userServices.updateUserBio(mBio.getText().toString());
        }
        if(!TextUtils.isEmpty(mUsername.getText().toString())) {
            userServices.updateUserName(mUsername.getText().toString());
        }
        if(!TextUtils.isEmpty(mEmail.getText().toString())) {
            userServices.updateUserEmail(mEmail.getText().toString());
        }

        if(isPrivate){
            userServices.updateUserPrivacy("PRIVATE");
        } else {
            userServices.updateUserPrivacy("PUBLIC");
        }

        if(isMetric){
            userServices.updateUserUnits("METRIC");
        } else {
            userServices.updateUserUnits("IMPERIAL");
        }
    }

    public void logOut(View view) {
        userServices.deleteLocalUser();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
