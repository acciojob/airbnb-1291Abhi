package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HotelManagementRepository {
    private Map<String, Hotel> hotelMap=new HashMap<>();
    private Map<Integer,User> userMap=new HashMap<>();
    private Map<String, Booking> bookingMap=new HashMap<>();

    public boolean isPresent(String hotelName) {
        if(hotelMap.containsKey(hotelName))
            return true;
        return false;
    }

    public void addHotel(Hotel hotel) {
        hotelMap.put(hotel.getHotelName(), hotel);
    }

    public Integer addUser(User user) {
        userMap.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }


    public List<String> getAllHotelKeys() {
        List<String> list=new ArrayList<>();
        for(String name:hotelMap.keySet())
            list.add(name);
        return list;
    }

    public List<Facility> getHotelFacilities(String key) {
        return hotelMap.get(key).getFacilities();
    }

    public List<String> getAllBookingId() {
        List<String> bookingIdList=new ArrayList<>();

        for(String bookingid:bookingMap.keySet()){
            bookingIdList.add(bookingid);
        }
        return bookingIdList;
    }

    public Integer getAadharForBookingID(String bookingId) {
        return bookingMap.get(bookingId).getBookingAadharCard();
    }

    public int getnoOfRoomAvailable(String hotelName) {
        return hotelMap.get(hotelName).getAvailableRooms();
    }

    public void bookRoom(String uuidAsString, Booking booking) {
        bookingMap.put(uuidAsString,booking);
    }

    public int getPrice(String hotelName) {
        return hotelMap.get(hotelName).getPricePerNight();
    }

    public Hotel updateFacility(List<Facility> oldFacilities, String hotelName) {
        Hotel hotel=hotelMap.get(hotelName);
        hotel.setFacilities(oldFacilities);
        hotelMap.put(hotelName,hotel);
        return hotel;
    }
}
