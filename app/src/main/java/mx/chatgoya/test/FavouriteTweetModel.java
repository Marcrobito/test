package mx.chatgoya.test;

import io.realm.RealmObject;

public class FavouriteTweetModel extends RealmObject {

    private Integer twitterId;
    private String twitterDate;
    private String twitterText;
    private Integer userId;
    private String userName;

    private String userPhotoUrl;

    public Integer getTwitterId() {
        return twitterId;
    }

    public String getTwitterText() {
        return twitterText;
    }

    public String getTwitterDate() {
        return twitterDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setTwitterId(Integer twitterId) {
        this.twitterId = twitterId;
    }

    public void setTwitterDate(String twitterDate) {
        this.twitterDate = twitterDate;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public void setTwitterText(String twitterText) {
        this.twitterText = twitterText;
    }


}
