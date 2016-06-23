package models;

//import library.MultiSelectItem;

import library.MultiSelectItem;

/**
 * Created by Vartulz on 4/15/2016.
 */

public class MultipleMessagePojo implements MultiSelectItem {


//public class MultipleMessagePojo implements  {

    private String signedUserId;
    private String userName;
    private String userId;
    private String email;
    private String profileImage;
    private String designation;
    private String followed;
    private String mReadableName;
    private String mId;


  /*  public MultipleBeans(String readableName){
        mReadableName = readableName;
        mId = String.valueOf(readableName.hashCode());
    }
*/

    public String getSignedUserId() {
        return signedUserId;
    }

    public void setSignedUserId(String signedUserId) {
        this.signedUserId = signedUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public String getmReadableName() {
        return mReadableName;
    }

    public void setmReadableName(String mReadableName) {
        this.mReadableName = mReadableName;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getReadableName() {
        return null;
    }
}
