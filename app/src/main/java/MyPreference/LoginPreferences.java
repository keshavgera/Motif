package MyPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class LoginPreferences {
    private static LoginPreferences preferences = null;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;


    private String clientId = "clientId";
    private String userId = "userId";
    private String userType = "userType";
    private String userName = "userName";
    private String mobile = "mobile";
    private String email = "email";
    private String createdDate = "createdDate";
    private String updatedDate = "updatedDate";
    private String notificationCount = "notificationCount";

    private String gcmToken = "gcmToken";
    String profileImage = "profileImage";


    public String getProfileImage() {
        return mPreferences.getString(this.profileImage, "");
    }

    public void setProfileImage(String profileImage) {
        editor = mPreferences.edit();
        editor.putString(this.profileImage, profileImage);

        editor.commit();
    }



    public String getGcmToken() {
        return mPreferences.getString(this.gcmToken, "");
    }

    public void setGcmToken(String gcmToken) {
        editor = mPreferences.edit();
        editor.putString(this.gcmToken, gcmToken);
        editor.commit();
    }

    public String getNotificationCount() {
        return mPreferences.getString(this.notificationCount, "");
    }

    public void setNotificationCount(String notificationCount) {
        editor = mPreferences.edit();
        editor.putString(this.notificationCount, notificationCount);
        editor.commit();
    }

    public void setClientId(String clientId) {
        editor = mPreferences.edit();
        editor.putString(this.clientId, clientId);
        editor.commit();
    }

    public String getClientId() {
        return mPreferences.getString(this.clientId, "");
    }


    public void setUserId(String userId) {
        editor = mPreferences.edit();
        editor.putString(this.userId, userId);
        editor.commit();
    }

    public String getUserId() {
        return mPreferences.getString(this.userId, "");

    }

    public void setUserType(String userType) {
        editor = mPreferences.edit();
        editor.putString(this.userType, userType);
        editor.commit();
    }

    public String getUserType() {
        return mPreferences.getString(this.userType, "");

    }

    public void setUserName(String userName) {
        editor = mPreferences.edit();
        editor.putString(this.userName, userName);
        editor.commit();
    }

    public String getUserName() {
        return mPreferences.getString(this.userName, "");

    }

    public void setMobile(String mobile) {
        editor = mPreferences.edit();
        editor.putString(this.mobile, mobile);
        editor.commit();
    }

    public String getMobile() {
        return mPreferences.getString(this.mobile, "");
    }

    public void setCreatedDate(String createdDate) {
        editor = mPreferences.edit();
        editor.putString(this.createdDate, createdDate);
        editor.commit();
    }

    public String getCreatedDate() {
        return mPreferences.getString(this.createdDate, "");

    }

    public void setUpdatedDate(String updatedDate) {
        editor = mPreferences.edit();
        editor.putString(this.updatedDate, updatedDate);
        editor.commit();
    }

    public String getUpdatedDate() {
        return mPreferences.getString(this.updatedDate, "");
    }

    public void setEmail(String email) {
        editor = mPreferences.edit();
        editor.putString(this.email, email);
        editor.commit();
    }

    public String getEmail() {
        return mPreferences.getString(this.email, "");
    }

    public LoginPreferences(Context context) {
        setmPreferences(PreferenceManager.getDefaultSharedPreferences(context));
    }


    public SharedPreferences getmPreferences() {
        return mPreferences;
    }

    public void setmPreferences(SharedPreferences mPreferences) {
        this.mPreferences = mPreferences;
    }


    public static LoginPreferences getActiveInstance(Context context) {
        if (preferences == null) {
            preferences = new LoginPreferences(context);
        }
        return preferences;
    }
}