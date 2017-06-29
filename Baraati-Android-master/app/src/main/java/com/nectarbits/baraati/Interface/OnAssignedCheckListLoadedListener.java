package com.nectarbits.baraati.Interface;

import com.nectarbits.baraati.Models.UserEvent.SubCategory;
import com.nectarbits.baraati.Models.UserEvent.SubCategory_;

import java.util.List;

/**
 * Created by nectarbits on 23/08/16.
 */
public interface OnAssignedCheckListLoadedListener {

    void onAssignedCheckListLoaed(List<SubCategory_> subCategories);

}
