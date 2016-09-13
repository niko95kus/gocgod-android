package com.gocgod.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Aurel on 09/09/2016.
 */
@Generated("org.jsonschema2pojo")
public class LocationCity {

    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("province_id")
    @Expose
    private Integer provinceId;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    /**
     *
     * @return
     * The cityId
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     *
     * @param cityId
     * The city_id
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     *
     * @return
     * The provinceId
     */
    public Integer getProvinceId() {
        return provinceId;
    }

    /**
     *
     * @param provinceId
     * The province_id
     */
    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    /**
     *
     * @return
     * The cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     *
     * @param cityName
     * The city_name
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The deletedAt
     */
    public Object getDeletedAt() {
        return deletedAt;
    }

    /**
     *
     * @param deletedAt
     * The deleted_at
     */
    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

}