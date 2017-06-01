package com.napolean.nonomanews.data.model;

import android.databinding.BindingAdapter;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Comment {

    public String text;
    public Long time;
    public String by;
    public Long id;
    public String type;
    public ArrayList<Long> kids;
    public ArrayList<Comment> comments;
    public int depth = 0;
    public boolean isTopLevelComment;

    public Comment() {
        comments = new ArrayList<>();
        isTopLevelComment = false;
    }

    @SuppressWarnings("deprecation")
    @BindingAdapter({"app:htmlText"})
    public static void toHtmlText(TextView iTextView, String iHtmlText) {
        String finalText = !TextUtils.isEmpty(iHtmlText) ? iHtmlText : "";
        iTextView.setText(Html.fromHtml(finalText));
    }

    @BindingAdapter({"app:text"})
    public static void convertLongToDateString(TextView iTextView, Long iLongText) {

        long millisecond = 0L;
        if (iLongText != null) millisecond = iLongText * 1000;

        final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm aaa",
                Locale.ENGLISH);

        String formattedDate = SIMPLE_DATE_FORMAT.format(new Date(millisecond));

        iTextView.setText(formattedDate);
    }

}
