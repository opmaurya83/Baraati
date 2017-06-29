package com.nectarbits.baraati.generalHelper;


import com.nectarbits.baraati.Models.RattingList.RatingDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 11/8/16.
 */
public class SingletonUtils {


    List<String> strings=new ArrayList<>();
    public RatingDetail ratingDetail;
    private static SingletonUtils ourInstance = new SingletonUtils();

    public static SingletonUtils getInstance() {
        return ourInstance;
    }

    private SingletonUtils() {
    }

    public RatingDetail getRatingDetail() {
        return ratingDetail;
    }

    public void setRatingDetail(RatingDetail ratingDetail) {
        this.ratingDetail = ratingDetail;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }
}
