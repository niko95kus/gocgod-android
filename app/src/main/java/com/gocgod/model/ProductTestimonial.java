package com.gocgod.model;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ProductTestimonial implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("testimonial")
    @Expose
    private String testimonial;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
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
     * The testimonial
     */
    public String getTestimonial() {
        return testimonial;
    }

    /**
     *
     * @param testimonial
     * The testimonial
     */
    public void setTestimonial(String testimonial) {
        this.testimonial = testimonial;
    }

    public ProductTestimonial(Parcel in) {
        super();
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    private void readFromParcel(Parcel in) {
    }

    public static final Parcelable.Creator<ProductTestimonial> CREATOR = new Parcelable.Creator<ProductTestimonial>() {
        public ProductTestimonial createFromParcel(Parcel in) {
            return new ProductTestimonial(in);
        }

        public ProductTestimonial[] newArray(int size) {

            return new ProductTestimonial[size];
        }

    };
}