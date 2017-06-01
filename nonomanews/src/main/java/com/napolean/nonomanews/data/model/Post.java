package com.napolean.nonomanews.data.model;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Post implements Parcelable {

    public Long id;
    public String by;
    public Long time;
    public ArrayList<Long> kids;
    public String url;
    public Long score;
    public String title;
    public String text;

    @SerializedName("type")
    public PostType postType;


    public enum PostType {
        @SerializedName("story")
        STORY("story"),
        @SerializedName("ask")
        ASK("ask"),
        @SerializedName("job")
        JOB("job");

        private String string;

        PostType(String string) {
            this.string = string;
        }

        public static PostType fromString(String string) {
            if (string != null) {
                for (PostType postType : PostType.values()) {
                    if (string.equalsIgnoreCase(postType.string)) return postType;
                }
            }
            return null;
        }
    }

    public Post() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.by);
        dest.writeValue(this.id);
        dest.writeValue(this.time);
        dest.writeSerializable(this.kids);
        dest.writeString(this.url);
        dest.writeValue(this.score);
        dest.writeString(this.title);
        dest.writeString(this.text);
        dest.writeInt(this.postType == null ? -1 : this.postType.ordinal());
    }

    private Post(Parcel in) {
        this.by = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.time = (Long) in.readValue(Long.class.getClassLoader());
        this.kids = (ArrayList<Long>) in.readSerializable();
        this.url = in.readString();
        this.score = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.text = in.readString();
        int tmpStoryType = in.readInt();
        this.postType = tmpStoryType == -1 ? null : PostType.values()[tmpStoryType];
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };


    @BindingAdapter({"app:text"})
    public static void convertLongToString(TextView iTextView, Long iLongText) {
        String text = iLongText != null ? String.valueOf(iLongText) : "";
        iTextView.setText(text);
    }

    //***************************** Way one ************************************

    /**
     * Following variable will hold the WebView's loading state
     */
    public ObservableField<Boolean> hideProgress = new ObservableField<>();


    public WebViewClient getWebViewClient() {
        return webViewClient;
    }

    private WebViewClient webViewClient = new WebViewClient(){

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            hideProgress.set(false);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideProgress.set(true);
        }
    };


    @SuppressLint("SetJavaScriptEnabled")
    @BindingAdapter({"app:loadUrl", "app:setWebViewClient"})
    public static void configureWebView(WebView iWebView, String iUrl, WebViewClient iWebViewClient) {
        String finalUrl = "www.google.com";

        // Check for url
        if (!TextUtils.isEmpty(iUrl)) finalUrl = iUrl;

        // Configure WebView
        iWebView.getSettings().setJavaScriptEnabled(true);
        iWebView.setWebViewClient(iWebViewClient);

        iWebView.loadUrl(finalUrl);
    }

    //***************************** Way two ************************************

//
//    @BindingAdapter({"setWebViewClient"})
//    public static void setWebViewClient(WebView view, WebViewClient client) {
//        view.setWebViewClient(client);
//    }
//
//    @BindingAdapter({"loadUrl"})
//    public static void loadUrl(WebView view, String url) {
//        view.loadUrl(url);
//    }
//
//    /**
//     * This is to bind progressbar visibility with WebView
//     */
//    public ObservableBoolean hideProgress = new ObservableBoolean();
//
//    private class Client extends WebViewClient {
//        @Override
//        public void onReceivedError(WebView view, WebResourceRequest request,
//                                    WebResourceError error) {
//            super.onReceivedError(view, request, error);
//            Toast.makeText(view.getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
//            hideProgress.set(true);
//            setHideProgress(hideProgress);
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//
//            hideProgress.set(false);
//            setHideProgress(hideProgress);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//
//            hideProgress.set(true);
//            setHideProgress(hideProgress);
//        }
//    }
//
//    public WebViewClient getWebViewClient() {
//        return new Client();
//    }
//
//    public ObservableBoolean getHideProgress() {
//        return hideProgress;
//    }
//
//    public void setHideProgress(ObservableBoolean hideProgress) {
//        this.hideProgress.set(hideProgress.get());
//    }
//


    //***************************** Way three ************************************

//    // Note : Following code will also work when we will be taking it as
//    @Bindable
//    public boolean isHideProgress() {
//        return hideProgress;
//    }

//    private void setHideProgress(boolean hideProgress) {
//        this.hideProgress = hideProgress;
//        notifyPropertyChanged(BR.hideProgress);
//    }

}

