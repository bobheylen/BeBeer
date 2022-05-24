package be.kuleuven.bebeer.myPickups;

public class MyPickup {

    String myPickupID, myPickupUsername, myPickupFirstname, myPickupLastname, myPickupDate, myPickupTime, myPickupAddress, myPickupQuantity, myPickupPhonenumber;

    public MyPickup(String myPickupID, String myPickupUsername, String myPickupFirstname, String myPickupLastname, String myPickupDate, String myPickupTime, String myPickupAddress, String myPickupQuantity, String myPickupPhonenumber) {
        this.myPickupID = myPickupID;
        this.myPickupUsername = myPickupUsername;
        this.myPickupFirstname = myPickupFirstname;
        this.myPickupLastname = myPickupLastname;
        this.myPickupDate = myPickupDate;
        this.myPickupTime = myPickupTime;
        this.myPickupAddress = myPickupAddress;
        this.myPickupQuantity = myPickupQuantity;
        this.myPickupPhonenumber = myPickupPhonenumber;
    }

    public String getMyPickupID() {
        return myPickupID;
    }

    public String getMyPickupUsername() {
        return myPickupUsername;
    }

    public String getMyPickupFirstname() {
        return myPickupFirstname;
    }

    public String getMyPickupLastname() {
        return myPickupLastname;
    }

    public String getMyPickupDate() {
        return myPickupDate;
    }

    public String getMyPickupTime() {
        return myPickupTime;
    }

    public String getMyPickupAddress() {
        return myPickupAddress;
    }

    public String getMyPickupQuantity() {
        return myPickupQuantity;
    }

    public String getMyPickupPhonenumber() {
        return myPickupPhonenumber;
    }
}
