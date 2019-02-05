/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import asgn2Aircraft.A380;
import asgn2Aircraft.Aircraft;
import asgn2Aircraft.AircraftException;
import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * @author Tristan
 *
 */
public class A380Test {

	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#Aircraft(java.lang.String, int, int, int, int, int)}.
	 */
	@Test (expected=AircraftException.class)
	public void testAircraftFlightCodeNotNull() throws AircraftException {
		new A380(null, 1, 1, 1, 1, 1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#Aircraft(java.lang.String, int, int, int, int, int)}.
	 */
	@Test (expected=AircraftException.class)
	public void testAircraftDepartureTimeNot0() throws AircraftException {
		new A380("test", 0, 1, 1, 1, 1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#Aircraft(java.lang.String, int, int, int, int, int)}.
	 */
	@Test (expected=AircraftException.class)
	public void testAircraftDepartureTimeNotLessThan0() throws AircraftException {
		new A380("test", -1, 1, 1, 1, 1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#Aircraft(java.lang.String, int, int, int, int, int)}.
	 */
	@Test (expected=AircraftException.class)
	public void testAircraftFirstNotLessThan0() throws AircraftException {
		new A380("test", 1, -1, 1, 1, 1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#Aircraft(java.lang.String, int, int, int, int, int)}.
	 */
	@Test (expected=AircraftException.class)
	public void testAircraftBusinessNotLessThan0() throws AircraftException {
		new A380("test", 1, 1, -1, 1, 1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#Aircraft(java.lang.String, int, int, int, int, int)}.
	 */
	@Test (expected=AircraftException.class)
	public void testAircraftPremiumNotLessThan0() throws AircraftException {
		new A380("test", 1, 1, 1, -1, 1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#Aircraft(java.lang.String, int, int, int, int, int)}.
	 */
	@Test (expected=AircraftException.class)
	public void testAircraftEconomyNotLessThan0() throws AircraftException {
		new A380("test", 1, 1, 1, 1, -1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#cancelBooking(asgn2Passengers.Passenger, int)}.
	 */
	@Test 
	public void testCancelBooking() throws AircraftException, PassengerException{
		Passenger passenger = new Economy(1,23);
		Aircraft aircraft = new A380("test", 24);
		aircraft.confirmBooking(passenger, 1);
		aircraft.cancelBooking(passenger,2);
		assertEquals(aircraft.getNumEconomy(),0);
	}
	

	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#cancelBooking(asgn2Passengers.Passenger, int)}.
	 */
	@Test (expected=AircraftException.class)
	public void testCancelBookingNotInSeating() throws AircraftException, PassengerException{
		Passenger passenger = new Economy(123321,134125323);
		Aircraft aircraft = new A380("test", 1);
		aircraft.cancelBooking(passenger,1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#confirmBooking(asgn2Passengers.Passenger, int)}.
	 */
	@Test
	public void testConfirmBooking() throws AircraftException, PassengerException{
		Passenger passenger = new Economy(5,12);
		Aircraft aircraft = new A380("test", 1);
		aircraft.confirmBooking(passenger, 1);
		assertEquals(aircraft.getNumEconomy(),1);
	}

	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#confirmBooking(asgn2Passengers.Passenger, int)}.
	 */
	@Test (expected=AircraftException.class) 
	public void testConfirmBookingNoSeatsEconomy() throws AircraftException, PassengerException{
		Passenger passenger = new Economy(5,12);
		Aircraft aircraft = new A380("test", 1, 1, 1, 1, 0);
		aircraft.confirmBooking(passenger, 1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#confirmBooking(asgn2Passengers.Passenger, int)}.
	 */
	@Test (expected=AircraftException.class) 
	public void testConfirmBookingNoSeatsPremium() throws AircraftException, PassengerException{
		Passenger passenger = new Premium(5,12);
		Aircraft aircraft = new A380("test", 1, 1, 1, 0, 1);
		aircraft.confirmBooking(passenger, 1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#confirmBooking(asgn2Passengers.Passenger, int)}.
	 */
	@Test (expected=AircraftException.class) 
	public void testConfirmBookingNoSeatsBusiness() throws AircraftException, PassengerException{
		Passenger passenger = new Business(5,12);
		Aircraft aircraft = new A380("test", 1, 1, 0, 1, 1);
		aircraft.confirmBooking(passenger, 1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#confirmBooking(asgn2Passengers.Passenger, int)}.
	 */
	@Test (expected=AircraftException.class) 
	public void testConfirmBookingNoSeatsFirst() throws AircraftException, PassengerException{
		Passenger passenger = new First(5,12);
		Aircraft aircraft = new A380("test", 1, 0, 1, 1, 1);
		aircraft.confirmBooking(passenger, 1);
	}

	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#flightEmpty()}.
	 */
	@Test
	public void testFlightEmpty() throws AircraftException {
		Aircraft aircraft = new A380("test", 1);
		assertTrue(aircraft.flightEmpty());
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#flightEmpty()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testFlightIsNotEmpty() throws AircraftException, PassengerException {
		Passenger passenger = new First(5,12);
		Aircraft aircraft = new A380("test", 1);
		aircraft.confirmBooking(passenger, 1);
		assertFalse(aircraft.flightEmpty());
	}

	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#flightFull()}. 
	 */
	@Test
	public void testFlightFull() throws PassengerException, AircraftException {
		Passenger passenger = new First(5,12);
		Aircraft aircraft = new A380("test", 1, 1, 0, 0, 0);
		aircraft.confirmBooking(passenger, 1);
		assertTrue(aircraft.flightFull());
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#flightFull()}. 
	 */
	@Test
	public void testFlightIsNotFull() throws PassengerException, AircraftException {
		Passenger passenger = new First(5,12);
		Aircraft aircraft = new A380("test", 1, 1, 1, 1, 1);
		aircraft.confirmBooking(passenger, 1);
		assertFalse(aircraft.flightFull());
	}


	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#flyPassengers(int)}.
	 */
	@Test 
	public void testFlyPassengers() throws PassengerException, AircraftException {
		Passenger passenger = new First(5,12);
		Aircraft aircraft = new A380("test", 12);
		aircraft.confirmBooking(passenger, 1);
		aircraft.flyPassengers(12);
		assertTrue(passenger.isFlown());
	}

	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#hasPassenger(asgn2Passengers.Passenger)}.
	 */
	@Test
	public void testHasPassenger() throws PassengerException, AircraftException {
		Passenger passenger = new First(2,3);
		Aircraft aircraft = new A380("test", 3);
		aircraft.confirmBooking(passenger, 1);
		assertTrue(aircraft.hasPassenger(passenger));
	}

	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#hasPassenger(asgn2Passengers.Passenger)}.
	 */
	@Test
	public void testDoesNotHavePassenger() throws PassengerException, AircraftException {
		Passenger passenger = new First(2,3);
		Aircraft aircraft = new A380("test", 3);
		assertFalse(aircraft.hasPassenger(passenger));
	}

	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#seatsAvailable(asgn2Passengers.Passenger)}.
	 */
	@Test
	public void testSeatsNotAvailable() throws AircraftException, PassengerException {
		Passenger passenger = new First(2,3);
		Aircraft aircraft = new A380("test", 3, 0, 1, 1, 1);
		assertFalse(aircraft.seatsAvailable(passenger));
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#seatsAvailable(asgn2Passengers.Passenger)}.
	 */
	@Test
	public void testSeatsAvailable() throws AircraftException, PassengerException {
		Passenger passenger = new First(2,3);
		Aircraft aircraft = new A380("test", 3, 1, 1, 1, 1);
		assertTrue(aircraft.seatsAvailable(passenger));
	}

	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#upgradeBookings()}.
	 */
	@Test
	public void testUpgradeBookingsEconomy() throws PassengerException, AircraftException {
		Passenger passenger = new Economy(2,3);
		Aircraft aircraft = new A380("test", 3);
		aircraft.confirmBooking(passenger, 1);
		aircraft.upgradeBookings();
		assertEquals(aircraft.getNumEconomy(), 0);
		assertEquals(aircraft.getNumPremium(), 1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#upgradeBookings()}.
	 */
	@Test
	public void testUpgradeBookingsPremium() throws PassengerException, AircraftException {
		Passenger passenger = new Premium(2,3);
		Aircraft aircraft = new A380("test", 3);
		aircraft.confirmBooking(passenger, 1);
		aircraft.upgradeBookings();
		assertEquals(aircraft.getNumPremium(), 0);
		assertEquals(aircraft.getNumBusiness(), 1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#upgradeBookings()}.
	 */
	@Test
	public void testUpgradeBookingsBusiness() throws PassengerException, AircraftException {
		Passenger passenger = new Business(2,3);
		Aircraft aircraft = new A380("test", 3);
		aircraft.confirmBooking(passenger, 1);
		aircraft.upgradeBookings();
		assertEquals(aircraft.getNumBusiness(), 0);
		assertEquals(aircraft.getNumFirst(), 1);
	}
	
	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#upgradeBookings()}.
	 */
	@Test
	public void testUpgradeBookingsFirst() throws PassengerException, AircraftException {
		Passenger passenger = new First(2,3);
		Aircraft aircraft = new A380("test", 3);
		aircraft.confirmBooking(passenger, 1);
		aircraft.upgradeBookings();
		assertEquals(aircraft.getNumFirst(), 1);
	}

}
