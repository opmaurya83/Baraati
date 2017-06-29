package com.nectarbits.baraati.Interface;

import com.nectarbits.baraati.Models.UserEvent.SubCategory;

import java.util.List;

/**
 * Created by nectarbits on 23/08/16.
 */
public interface OnMyCheckListLoadedListener {

    void onMyCheckListLoaded(List<SubCategory> subCategories);

}
