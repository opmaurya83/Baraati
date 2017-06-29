package com.nectarbits.baraati.Interface;

import com.nectarbits.baraati.Models.InquiryModel.List.AssignInqiry;
import com.nectarbits.baraati.Models.OrderList.List.AssigneeOrder;

import java.util.List;

/**
 * Created by nectarbits on 23/08/16.
 */
public interface OnAssignedOrderListener {

    void onAssignedOrderListener(List<AssigneeOrder> subCategories);

}
