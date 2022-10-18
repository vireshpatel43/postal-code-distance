/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.postalcodedistanceapp.models;

/**
 *
 * @author 2128027
 */
public class PostalCode {
    private int id;
    private String country;
    private String postalCode;
    private String province;
    private double latitude;
    private double longitude;

    public PostalCode(int id, String country, String postalCode, String province, double latitude, double longitude) {
        this.id = id;
        this.country = country;
        this.postalCode = postalCode;
        this.province = province;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    
    /**
     * getters and setters for each of the private variables
     * @return specified variable
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "PostalCode{" + "id=" + id + ", country=" + country + ", postalCode=" + postalCode + ", province=" + province + ", latitude=" + latitude + ", longitude=" + longitude + '}' + "\n";
    }
    
    
    
    
}
