package com.nectarbits.baraati.Interface;

import com.nectarbits.baraati.Models.Event.EventList;
import com.nectarbits.baraati.Models.UserEvent.Event;
import com.nectarbits.baraati.Models.UserEvent.EventType;

/**
 * Created by root on 20/8/16.
 */
public interface OnUserCheckListSelect {
    public void onEventTypeSelect(int position);
    public void OnUserSubcategoryAddSelect(int potition);
    public void OnUserSubcategoryDeleteSelect(int potition);
    public void OnUserEventDeleteSelect(Event event);
    public void OnUserEventAddSelect(Event event);
    public void OnUserEventTypeDeleteSelect(EventType eventType);
    public void OnUserEventTypeCalenderSelect(EventType eventType);
    public void OnUserEventTypeCartSelect(EventType eventType);
    public void onCompleteEvent(Boolean aBoolean,EventType eventType);
}
