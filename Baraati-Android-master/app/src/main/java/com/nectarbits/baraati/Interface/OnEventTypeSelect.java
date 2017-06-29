package com.nectarbits.baraati.Interface;

import com.nectarbits.baraati.Models.EventType.EventList;
import com.nectarbits.baraati.Models.EventType.EventType;

/**
 * Created by root on 20/8/16.
 */
public interface OnEventTypeSelect {
    public void OnEventSelect(EventType eventType, Boolean aBoolean);
    public void OnEventSelectParent(EventList position);
}
