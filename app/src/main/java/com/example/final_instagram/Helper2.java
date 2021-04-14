package com.example.final_instagram;

public class Helper2 {
    String Username,Userpass,Usernuber,Useremail,UserUid,Userdescp,profileurl,follower,following;
    String Post;
    Helper2(){}

    public Helper2(String username, String userpass, String usernuber, String useremail, String userUid, String userdescp, String profileurl, String follower, String following,String Post) {
        Username = username;
        Userpass = userpass;
        Usernuber = usernuber;
        Useremail = useremail;
        UserUid = userUid;
        Userdescp = userdescp;
        this.profileurl = profileurl;
        this.follower = follower;
        this.following = following;
        this.Post=Post;
    }

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserpass() {
        return Userpass;
    }

    public void setUserpass(String userpass) {
        Userpass = userpass;
    }

    public String getUsernuber() {
        return Usernuber;
    }

    public void setUsernuber(String usernuber) {
        Usernuber = usernuber;
    }

    public String getUseremail() {
        return Useremail;
    }

    public void setUseremail(String useremail) {
        Useremail = useremail;
    }

    public String getUserUid() {
        return UserUid;
    }

    public void setUserUid(String userUid) {
        UserUid = userUid;
    }

    public String getUserdescp() {
        return Userdescp;
    }

    public void setUserdescp(String userdescp) {
        Userdescp = userdescp;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
}
