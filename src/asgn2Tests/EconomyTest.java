package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import asgn2Passengers.Economy;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;

public class EconomyTest {

    @Test
    public void testUpgrade() throws PassengerException {
        Passenger P = new Economy(10,20);
        P = P.upgrade();
        assertTrue(P.getPassID().contains("P(U)Y:"));
    }

    @Test
    public void testConstructor() throws PassengerException {
        Passenger P = new Economy(10,20);
        assertTrue(P.isNew());
        assertFalse(P.isConfirmed());
        assertFalse(P.isQueued());
        assertFalse(P.isFlown());
        assertFalse(P.isRefused());
        assertEquals(P.getBookingTime(),10);
        assertEquals(P.getDepartureTime(),20);
        assertEquals(P.getEnterQueueTime(),0);
        assertEquals(P.getExitQueueTime(),0);
        assertEquals(P.getConfirmationTime(),0);
        assertTrue(P.getPassID().contains("Y:"));
    }
    
    @Test(expected = PassengerException.class)
    public void testConstructorfail() throws PassengerException {
        Passenger P = new Economy(100,20);
    }

}
