/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend_package;

import java.io.File;

/**
 *
 * @author C L
 */
public class Patient extends User{
    private String patient_ID;
    private String phone_no;
    private String Scanned_date;
    private String Appoinment;
    private String doctor_name;
    private String Blood_group;
    private String gender;
    private Features image;
    private final String imagePath;

    public Patient() {
        super(null, null, null);
        this.imagePath = null;
    }

    public Patient(String patient_ID, String phone_no, String Scanned_date, String Appoinment, String doctor_name, String Blood_group, String gender, String imagePath, String Name, String Address, String Age) {
        super(Name, Address, Age);
        this.patient_ID = patient_ID;
        this.phone_no = phone_no;
        this.Scanned_date = Scanned_date;
        this.Appoinment = Appoinment;
        this.doctor_name = doctor_name;
        this.Blood_group = Blood_group;
        this.gender = gender;
        this.imagePath = imagePath;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public String getPatient_ID() {
        return patient_ID;
    }

    public void setPatient_ID(String patient_ID) {
        this.patient_ID = patient_ID;
    }

    public Features getImage() {
        return image;
    }

    public void setImage(Features image) {
        this.image = image;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getScanned_date() {
        return Scanned_date;
    }

    public void setScanned_date(String Scanned_date) {
        this.Scanned_date = Scanned_date;
    }

    public String getAppoinment() {
        return Appoinment;
    }

    public void setAppoinment(String Appoinment) {
        this.Appoinment = Appoinment;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getBlood_group() {
        return Blood_group;
    }

    public void setBlood_group(String Blood_group) {
        this.Blood_group = Blood_group;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void DisplayInfo() {
        System.out.println("ID: "+patient_ID);
        System.out.println("Name: "+Name);
        System.out.println("Address: "+Address);
        System.out.println("Age: "+Age);
        System.out.println("Phone Number: "+ phone_no);
        System.out.println("Sacnned Date: "+Scanned_date);
        System.out.println("Appointment: "+Appoinment);
        System.out.println("Doctor Name: "+doctor_name);
        System.out.println("Blood Group: "+Blood_group);
        System.out.println("Gender: "+gender);
    }
    
}
