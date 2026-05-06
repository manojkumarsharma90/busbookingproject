package com.busbooking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    @Column(nullable = false)
    private String name;

    private Integer age;

    private String gender;

    @Column(nullable = false)
    private String seatNo;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public Passenger() {}

    public Passenger(Long passengerId, String name, Integer age, String gender,
                     String seatNo, Booking booking) {
        this.passengerId = passengerId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seatNo = seatNo;
        this.booking = booking;
    }

    public Long getPassengerId() { return passengerId; }
    public void setPassengerId(Long passengerId) { this.passengerId = passengerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getSeatNo() { return seatNo; }
    public void setSeatNo(String seatNo) { this.seatNo = seatNo; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
}
