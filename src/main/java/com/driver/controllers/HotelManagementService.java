package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class HotelManagementService {
    private HotelManagementRepository hotelManagementRepository=new HotelManagementRepository();

    public String addHotel(Hotel hotel) {
        if(Objects.isNull(hotel))
            return "FAILURE";
        if(hotel.getHotelName()==null ||  hotelManagementRepository.isPresent(hotel.getHotelName())){
            return "FAILURE";
        }
        hotelManagementRepository.addHotel(hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        return hotelManagementRepository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        List<String> hotelKey=hotelManagementRepository.getAllHotelKeys();
        String hotelName="";
        int max=0;
        for(String key:hotelKey){
            List<Facility> facilities=hotelManagementRepository.getHotelFacilities(key);
            if(max<facilities.size()){
                hotelName=key;
                max=facilities.size();
            }else if(max==facilities.size()){
                if(hotelName.compareTo(key)>0)
                    hotelName=key;
            }
        }
        return hotelName;

    }

    public int totalNumberOfBookingDoneByAPerson(Integer aadharCard) {
        List<String> bookingIdList=hotelManagementRepository.getAllBookingId();
        int count=0;
        for(String bookingId:bookingIdList){
            if(aadharCard== hotelManagementRepository.getAadharForBookingID(bookingId))
                count++;
        }
        return count;
    }

    public int createBooking(Booking booking) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        int noOfRoomAvailable= hotelManagementRepository.getnoOfRoomAvailable(booking.getHotelName());
        if(noOfRoomAvailable<booking.getNoOfRooms())
            return -1;
        hotelManagementRepository.bookRoom(uuidAsString,booking);
        int pricePerNight=hotelManagementRepository.getPrice(booking.getHotelName());
        int totalPrice=booking.getNoOfRooms()*pricePerNight;
        return totalPrice;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        List<Facility> oldFacilities=hotelManagementRepository.getHotelFacilities(hotelName);
        for(Facility facility:newFacilities){
            if(!oldFacilities.contains(facility))
                oldFacilities.add(facility);
        }
        return hotelManagementRepository.updateFacility(oldFacilities,hotelName);
    }
}
