package com.gocgod.model;

import android.location.Location;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Data {

    @SerializedName("productPagination")
    @Expose
    private ProductPagination productPagination;

    @SerializedName("productData")
    @Expose
    private ProductData productData;

    @SerializedName("productTestimonial")
    @Expose
    private ProductTestimonialPagination productTestimonialPagination;

    @SerializedName("agentLocation")
    @Expose
    private AgentLocationPagination agentLocationPagination;

    @SerializedName("city")
    @Expose
    private List<LocationCity> city = new ArrayList<LocationCity>();

    @SerializedName("login")
    @Expose
    private Login login;

    /**
     *
     * @return
     * The login
     */
    public Login getLogin() {
        return login;
    }

    /**
     *
     * @param login
     * The login
     */
    public void setLogin(Login login) {
        this.login = login;
    }
>>>>>>> master

    /**
     *
     * @return
     * The productData
     */
    public ProductData getProductData() {
        return productData;
    }

    /**
     *
     * @param productData
     * The productData
     */
    public void setProductData(ProductData productData) {
        this.productData = productData;
    }

    /**
     *
     * @return
     * The productTestimonial
     */
    public ProductTestimonialPagination getProductTestimonialPagination() {
        return productTestimonialPagination;
    }

    /**
     *
     * @param productTestimonial
     * The productTestimonial
     */
    public void setProductTestimonialPagination(ProductTestimonialPagination productTestimonialPagination) {
        this.productTestimonialPagination = productTestimonialPagination;
    }

    /**
     *
     * @return
     * The productPagination
     */
    public ProductPagination getProductPagination() {
        return productPagination;
    }

    /**
     *
     * @param productPagination
     * The productPagination
     */
    public void setProductPagination(ProductPagination productPagination) {
        this.productPagination = productPagination;
    }


    /**
     *
     * @return
     * The agent
     */
    public AgentLocationPagination getAgent() {
        return agentLocationPagination;
    }

    /**
     *
     * @param agent
     * The agent
     */
    public void setAgent(AgentLocationPagination agentLocationPagination) {
        this.agentLocationPagination = agentLocationPagination;
    }

    /**
     *
     * @return
     * The city
     */
    public List<LocationCity> getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(List<LocationCity> city) {
        this.city = city;
    }

}