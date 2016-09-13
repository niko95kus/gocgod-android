package com.gocgod.model;

/**
 * Created by Aurel on 05/09/2016.
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class LocationProvinceContactSuccess {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("contact")
    @Expose
    private Contact contact;
    @SerializedName("province")
    @Expose
    private List<LocationProvince> province = new ArrayList<LocationProvince>();

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     *
     * @param contact
     * The contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     *
     * @return
     * The province
     */
    public List<LocationProvince> getProvince() {
        return province;
    }

    /**
     *
     * @param province
     * The province
     */
    public void setProvince(List<LocationProvince> province) {
        this.province = province;
    }

}