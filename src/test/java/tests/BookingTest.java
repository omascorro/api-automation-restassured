package tests;

import static io.restassured.RestAssured.*;

import api.booker.BookingAPI;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Booking;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingTest {

    private static final Logger logger = LoggerFactory.getLogger(BookingTest.class);


    static BookingAPI bookingAPI;
    static int bookingId;

    @BeforeAll
    static void beforeAll() {
        bookingAPI = new BookingAPI();

    }
    /*
    @Test
    void getBooking() {

        Response response = given().contentType(ContentType.JSON)
                .when().log().all().get("https://restful-booker.herokuapp.com/booking/1");
        logger.info(response.asString());

        Assertions.assertNotNull(response);

        Assertions.assertEquals(200,response.statusCode(),"Get Booking Status Code is not 200");


    }

     */

    @Test
    @Order(1)
    void createBooking() {

        Booking booking = new Booking("Teresa","Lopez",126,true,
                "2025-10-10","2025-10-17","Lunch");

        Response response = bookingAPI.createBooking(booking);

        Assertions.assertEquals(200,response.statusCode(),"Failed: Status code was not 200.");

        bookingId = response.jsonPath().getInt("bookingid");
        logger.info("bookingId: "+bookingId);
        Assertions.assertTrue(bookingId>0,"Failed: Booking Id should be greater than 0.");

        String firstName = response.jsonPath().getString("booking.firstname");
        logger.info("firstName: "+firstName);
        Assertions.assertEquals(booking.getFirstname(),firstName,"Failed: firstName is incorrect.");

        String lastName = response.jsonPath().getString("booking.lastname");
        logger.info("lastName: "+lastName);
        Assertions.assertEquals(booking.getLastname(),lastName,"Failed: lastName is incorrect.");

        int totalPrice = response.jsonPath().getInt("booking.totalprice");
        logger.info("totalPrice: "+totalPrice);
        Assertions.assertEquals(booking.getTotalprice(),totalPrice,"Failed: Price is incorrect.");
    }

    @Test
    @Order(2)
    void getBooking() {

        Response response = bookingAPI.getBooking(bookingId);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200,response.statusCode(),"Failed: Status code was not 200.");

        String firstName = response.jsonPath().getString("firstname");
        logger.info("firstname:" + firstName);
        Assertions.assertEquals("Teresa",firstName,"Failed: Firstname is not correct ");
    }
}
