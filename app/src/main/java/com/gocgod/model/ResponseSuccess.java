package com.gocgod.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ResponseSuccess {

    @SerializedName("success")
    @Expose
    private Success success;

    /**
     *
     * @return
     * The success
     */
    public Success getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(Success success) {
        this.success = success;
    }

}