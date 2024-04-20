package com.maidcc.library.patron;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maidcc.library.borrowingrecord.BorrowRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;


@Entity
@Table
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PATRIN_ID")
    private Long patronId;
    @Size(max = 60, message = "Name must be less than 60 characters")
    private String fullName;
    @Column(unique = true)
    @NotBlank(message = "Phone is required")
    @Size(max = 10,min = 10, message = "Phone number must be less than 10 characters")
    private String phone;
    @Column(unique = true)
    @Email
    private String mail;
    @Size(max = 60, message = "Address must be less than 60 characters")
    private String address;

    @OneToMany(mappedBy = "patron",cascade = CascadeType.REMOVE)
    //@JsonIgnore
    private Set<BorrowRecord> borrowRecords;

    public Patron() {
    }

    public Patron(Long patronID, String fullName, String phone, String mail, String address) {
        this.patronId = patronID;
        this.fullName = fullName;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
    }

    public void setPatronID(Long patronID) {
        this.patronId = patronID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPatronID() {
        return patronId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public String getAddress() {
        return address;
    }
}
