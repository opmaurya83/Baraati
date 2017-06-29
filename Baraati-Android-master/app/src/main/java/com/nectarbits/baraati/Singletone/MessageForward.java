package com.nectarbits.baraati.Singletone;

import com.quickblox.q_municate_core.models.CombinationMessage;

/**
 * Created by root on 11/15/16.
 */
public class MessageForward {
    enum Type {None,Text,Image,Video}


    CombinationMessage combinationMessage=null;
    Boolean isForward=false;
    Type type=Type.None;

    private static MessageForward ourInstance = new MessageForward();

    public static MessageForward getInstance() {
        return ourInstance;
    }

    private MessageForward() {
    }

    public CombinationMessage getCombinationMessage() {
        return combinationMessage;
    }

    public void setCombinationMessage(CombinationMessage combinationMessage) {
        this.combinationMessage = combinationMessage;
    }

    public Boolean getForward() {
        return isForward;
    }

    public void setForward(Boolean forward) {
        isForward = forward;
    }

    public  void reset()
    {
        isForward=false;
        combinationMessage=null;
        type=Type.None;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
