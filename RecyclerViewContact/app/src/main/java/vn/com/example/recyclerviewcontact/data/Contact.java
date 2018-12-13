package vn.com.example.recyclerviewcontact.data;

public class Contact {
    private String contactNumber;
    private String contactName;

    public Contact() {
    }

    public Contact(String contactNumber, String contactName) {
        this.contactNumber = contactNumber;
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
