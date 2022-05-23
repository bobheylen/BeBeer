package be.kuleuven.bebeer.activities;

public class Pickup {
    String pickupID, pickupUsername, pickupAddress, pickupQuantity, pickupDate, pickupTime;

    public Pickup(String pickupID, String pickupUsername, String pickupAddress, String pickupQuantity, String pickupDate, String pickupTime) {
        this.pickupID = pickupID;
        this.pickupUsername = pickupUsername;
        this.pickupAddress = pickupAddress;
        this.pickupQuantity = pickupQuantity;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
    }

    public String getPickupID() {
        return pickupID;
    }

    public String getPickupUsername() {
        return pickupUsername;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public String getPickupQuantity() {
        return pickupQuantity;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public String getPickupTime() {
        return pickupTime;
    }
}
