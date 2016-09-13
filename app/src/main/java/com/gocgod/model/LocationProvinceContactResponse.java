package com.gocgod.model;

/**
 * Created by Aurel on 05/09/2016.
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class LocationProvinceContactResponse {

    @SerializedName("response")
    @Expose
    private LocationProvinceContactSuccess response;

    /**
     *
     * @return
     * The response
     */
    public LocationProvinceContactSuccess getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(LocationProvinceContactSuccess response) {
        this.response = response;
    }

}
