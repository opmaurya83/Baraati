package com.nectarbits.baraati.Singletone;

import com.nectarbits.baraati.Models.Address.ShippingAddress;
import com.nectarbits.baraati.Models.CartList.CartListModel;

/**
 * Created by root on 22/9/16.
 */
public class CartProcessUtil {
    private static CartProcessUtil ourInstance = new CartProcessUtil();
    ShippingAddress shippingAddress=null;
    CartListModel cartListModel=null;
    int setectedAddress=-1;
    public static CartProcessUtil getInstance() {
        return ourInstance;
    }

    private CartProcessUtil() {
    }

    public void Reset(){
        shippingAddress=null;
        cartListModel=null;
        setectedAddress=-1;
    }

    public void ResetAddress(){
        shippingAddress=new ShippingAddress();
    }
    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public CartListModel getCartListModel() {
        return cartListModel;
    }

    public void setCartListModel(CartListModel cartListModel) {
        this.cartListModel = cartListModel;
    }

    public int getSetectedAddress() {
        return setectedAddress;
    }

    public void setSetectedAddress(int setectedAddress) {
        this.setectedAddress = setectedAddress;
    }
}
