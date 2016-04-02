package bottlerocket.laurenyew.companylist.model;

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
    private String latitude;
    private String zipcode;
    private String storeLogoURL;
    private String phone;
    private String longitude;
    private String storeID;
    private String state;

    public CompanyDetail()
    {
        address = "";
        city = "";
        name = "";
        latitude = "";
        zipcode = "";
        storeLogoURL = "";
        phone = "";
        longitude = "";
        storeID = "";
        state = "";
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

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getStoreLogoURL() {
        return storeLogoURL;
    }

    public String getPhone() {
        return phone;
    }

    public String getStoreID() {
        return storeID;
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
