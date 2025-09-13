package api.booker;

import api.base.APIBase;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Booking;

import java.util.HashMap;
import java.util.Map;

public class BookingAPI extends APIBase {


    private static final Logger logger = LoggerFactory.getLogger(BookingAPI.class);

    private static final  String BASE_URL = "https://restful-booker.herokuapp.com";
    private static final  String AUTH_ENDPOINT = "/auth";
    private static final  String BOOKING_ENDPOINT = "/booking";

    private String token;


    public BookingAPI() {
        super(BASE_URL);
    }

    public Response createBooking (Booking booking){

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin",booking.getCheckin());
        bookingDates.put("checkout",booking.getCheckout());

        Map<String,Object> body = new HashMap<>();
        body.put("firstname" , booking.getFirstname());




        body.put("lastname" , booking.getLastname());
        body.put("totalprice" , booking.getTotalprice());
        body.put("depositpaid" , booking.isDepositpaid());
        body.put("bookingdates" , bookingDates);
        body.put("additionalneeds", booking.getAdditionalneeds());

        return post(BOOKING_ENDPOINT,body);

    }

    public String getToken (){

        Map<String,Object> body = new HashMap<>();
        body.put("username","admin");
        body.put("password","password123");

        Response response = post(AUTH_ENDPOINT,body);

        return response.jsonPath().getString("token");

    }
    public Response getBooking(int bookingId){
        return get(BOOKING_ENDPOINT+"/"+bookingId);
    }

    public Response updateBooking(Booking booking, int bookingId, String updateToken){

        Map<String,Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin",booking.getCheckin());
        bookingDates.put("checkout",booking.getCheckout());

        Map<String,Object> body = new HashMap<>();
        body.put("firstname",booking.getFirstname());
        body.put("lastname",booking.getLastname());
        body.put("totalprice",booking.getTotalprice());
        body.put("depositpaid",booking.isDepositpaid());
        body.put("bookingdates",bookingDates);
        body.put("additionalneeds",booking.getAdditionalneeds());

        return put(BOOKING_ENDPOINT+"/"+bookingId,body,updateToken);
    }

    public Response deleteBooking(int bookingId, String deleteToken){

        return delete(BOOKING_ENDPOINT+"/"+bookingId,deleteToken);

    }



}
