package com.gocgod.location;

import com.gocgod.model.LocationProvince;

import java.util.List;

/**
 * Created by Aurel on 05/09/2016.
 */
public class InstanceProvince {
    private static InstanceProvince instance;

    private List<LocationProvince> opProvince;

    private InstanceProvince() {}


    public List<LocationProvince> getOpProvince() {
        return opProvince;
    }

    public void setOpProvince(List<LocationProvince> opProvince) {
        this.opProvince = opProvince;
    }

    public static synchronized InstanceProvince getInstance() {
        if (instance == null) {
            instance = new InstanceProvince();
        }

        return instance;
    }
}
