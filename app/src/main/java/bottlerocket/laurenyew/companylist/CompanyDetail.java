package bottlerocket.laurenyew.companylist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by laurenyew on 3/31/16.
 *
 * Holds company model information
 *
 * Ex:
 * address":"7804 Citrus Park Town Center Mall",
 "city":"Tampa",
 "name":"Target",
 "latitude":"28.078252",
 "zipcode":"33625",
 "storeLogoURL":"http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/images/target.jpeg",
 "phone":"813-926-7303",
 "longitude":"-82.583501",
 "storeID":"1240",
 "state":"FL"
 */
public class CompanyDetail {

    private String address;
    private String city;
    private String name;
    private double latitude;
    private int zipcode;
    private String storeLogoUrl;
    private String phone;
    private double longitude;
    private String storeId;
    private String state;

    public CompanyDetail()
    {

    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getStoreLogoUrl() {
        return storeLogoUrl;
    }

    public String getPhone() {
        return phone;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getState() {
        return state;
    }


    @Override
    public String toString()
    {
        return getName() + " [" + getPhone() + "]";
    }
}
