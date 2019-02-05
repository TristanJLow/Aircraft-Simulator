package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.First;

public class FirstTest {
    
    @Test
    public void testUpgrade() throws PassengerException {
        Passenger P = new First(10,20);
        P = P.upgrade();
        assertEquals("F",Character.toString(P.getPassID().charAt(0)));
    }

    @Test
    public void testConstructor() throws PassengerException {
        Passenger P = new First(10,20);
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
        assertEquals(Character.toString(P.getPassID().charAt(0)),"F");
    }
    
    @Test(expected = PassengerException.class)
    public void testConstructorfail_departureTime_Less_than_bookingTime() throws PassengerException {
        Passenger P = new First(100,20);
    }
    
    @Test(expected = PassengerException.class)
    public void testConstructorfail_departureTime_less_than_0() throws PassengerException {
        Passenger P = new First(100,-10);
    }
    
    @Test(expected = PassengerException.class)
    public void testConstructorfail_bookingTime_less_than_0() throws PassengerException {
        Passenger P = new First(-10,20);
    }

    @Test (expected = PassengerException.class)
    public void testCancelSeat_throws_if_new_state_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.cancelSeat(11);
    }
    
    @Test (expected = PassengerException.class)
    public void testCancelSeat_throws_if_inQueue_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.queuePassenger(11, 20);
        P.cancelSeat(12);
    }
    
    @Test (expected = PassengerException.class)
    public void testCancelSeat_throws_if_refused_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.refusePassenger(11);
        P.cancelSeat(12);
    }
    
    @Test (expected = PassengerException.class)
    public void testCancelSeat_throws_if_flown_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(12, 20);
        P.flyPassenger(18);
        P.cancelSeat(19);
    }
    
    @Test (expected = PassengerException.class)
    public void testCancelSeat_throws_if_cancellationTime_less_than_0() throws PassengerException {
        Passenger P = new First(10,20);
        P.cancelSeat(-1);
    }
    
    @Test (expected = PassengerException.class)
    public void testCancelSeat_throws_if_depatureTime_less_than_cancellationTime() throws PassengerException {
        Passenger P = new First(10,20);
        P.queuePassenger(11, 20);
        P.cancelSeat(21);
    }
    
    @Test
    public void testCancelSeat() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        P.cancelSeat(11);
        assertEquals(P.getBookingTime(),11);
        assertTrue(P.isNew());
        assertFalse(P.isConfirmed());
    }

    @Test (expected = PassengerException.class)
    public void testConfirmSeatfail_when_confirmed_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        P.confirmSeat(10, 20);
    }
    
    @Test (expected = PassengerException.class)
    public void testConfirmSeatfail_when_refused_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.refusePassenger(10);
        P.confirmSeat(10, 20);
    }
    
    @Test (expected = PassengerException.class)
    public void testConfirmSeatfail_when_flown_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.flyPassenger(10);
        P.confirmSeat(10, 20);
    }
    
    @Test (expected = PassengerException.class)
    public void testConfirmSeatfail_when_confirmationTime_less_than_0() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(-1, 20);
    }
    
    @Test (expected = PassengerException.class)
    public void testConfirmSeatfail_when_departureTime_less_than_confirmationTime() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(21, 20);
    }
    
    @Test
    public void testConfirmSeat_when_newState_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        assertFalse(P.isNew());
        assertFalse(P.isQueued());
        assertTrue(P.isConfirmed());
        assertEquals(P.getConfirmationTime(),10);
        assertEquals(P.getDepartureTime(),20);
    }
    
    @Test
    public void testConfirmSeat_when_inQueue_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.queuePassenger(10, 20);
        P.confirmSeat(10, 20);
        assertFalse(P.isNew());
        assertFalse(P.isQueued());
        assertTrue(P.isConfirmed());
        assertEquals(P.getConfirmationTime(),10);
        assertEquals(P.getDepartureTime(),20);
        assertEquals(P.getExitQueueTime(),10);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassengerfail_when_newState_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.flyPassenger(20);
    }

    @Test (expected = PassengerException.class)
    public void testFlyPassenger_throws_when_inQueue_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.queuePassenger(10, 20);
        P.flyPassenger(20);
    }
    
    @Test (expected = PassengerException.class)
    public void testFlyPassenger_throws_when_refused_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.refusePassenger(10);
        P.flyPassenger(20);
    }
    
    @Test (expected = PassengerException.class)
    public void testFlyPassenger_throws_when_flown_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        P.flyPassenger(20);
        P.flyPassenger(20);
    }
    
    @Test (expected = PassengerException.class)
    public void testFlyPassenger_throws_when_departureTime_less_than_0() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        P.flyPassenger(-1);
    }
    
    @Test (expected = PassengerException.class)
    public void testFlyPassenger_throws_when_departureTime_equals_0() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        P.flyPassenger(0);
    }
    
    @Test
    public void testFlyPassenger_success() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        P.flyPassenger(20);
        assertTrue(P.isFlown());
        assertFalse(P.isConfirmed());
        assertEquals(P.getDepartureTime(),20);
    }
    
    @Test (expected = PassengerException.class)
    public void testQueuePassenger_throws_when_inQueue_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.queuePassenger(10, 20);
        P.queuePassenger(10, 20);
    }
    
    @Test (expected = PassengerException.class)
    public void testQueuePassenger_throws_when_confirmed_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        P.queuePassenger(10, 20);
    }
    
    @Test (expected = PassengerException.class)
    public void testQueuePassenger_throws_when_refused_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.refusePassenger(10);
        P.queuePassenger(10, 20);
    }
    
    @Test (expected = PassengerException.class)
    public void testQueuePassenger_throws_when_flown_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        P.flyPassenger(20);
        P.queuePassenger(10, 20);
    }
    
    @Test (expected = PassengerException.class)
    public void testQueuePassenger_throws_when_queueTime_less_than_0() throws PassengerException {
        Passenger P = new First(10,20);
        P.queuePassenger(-1, 20);
    }
    
    @Test (expected = PassengerException.class)
    public void testQueuePassenger_throws_when_departureTime_less_thanQueueTime() throws PassengerException {
        Passenger P = new First(10,20);
        P.queuePassenger(10, 9);
    }
    
    @Test 
    public void testQueuePassenger_success() throws PassengerException {
        Passenger P = new First(10,20);
        P.queuePassenger(10, 20);
        assertTrue(P.isQueued());
        assertFalse(P.isNew());
        assertEquals(P.getEnterQueueTime(),10);
        assertEquals(P.getDepartureTime(),20);
    }

    @Test (expected = PassengerException.class)
    public void testRefusePassenger_throws_when_confirmed_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        P.refusePassenger(10);
    }
    
    @Test (expected = PassengerException.class)
    public void testRefusePassenger_throws_when_refused_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.refusePassenger(10);
        P.refusePassenger(10);
    }
    
    @Test (expected = PassengerException.class)
    public void testRefusePassenger_throws_when_flown_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        P.flyPassenger(20);
        P.refusePassenger(20);
    }
    
    @Test (expected = PassengerException.class)
    public void testRefusePassenger_throws_when_refusalTime_less_than_0() throws PassengerException {
        Passenger P = new First(10,20);
        P.refusePassenger(-1);
    }
    
    @Test (expected = PassengerException.class)
    public void testRefusePassenger_throws_when_refusalTime_less_than_bookingTime() throws PassengerException {
        Passenger P = new First(10,20);
        P.refusePassenger(9);
    }
    
    @Test
    public void testRefusePassenger_success_when_newState_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.refusePassenger(10);
        assertTrue(P.isRefused());
        assertFalse(P.isNew());
        assertFalse(P.isQueued());
    }
    
    @Test
    public void testRefusePassenger_success_when_inQueue_is_true() throws PassengerException {
        Passenger P = new First(10,20);
        P.queuePassenger(10, 20);
        P.refusePassenger(10);
        assertEquals(P.getExitQueueTime(),10);
        assertTrue(P.isRefused());
        assertFalse(P.isNew());
        assertFalse(P.isQueued());
    }

    @Test
    public void testWasConfirmed_true_currently_confirmed() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        assertTrue(P.wasConfirmed());
    }
    
    @Test
    public void testWasConfirmed_true_was_confirmed() throws PassengerException {
        Passenger P = new First(10,20);
        P.confirmSeat(10, 20);
        P.cancelSeat(10);
        assertTrue(P.wasConfirmed());
    }
    
    @Test
    public void testWasConfirmed_false() throws PassengerException {
        Passenger P = new First(10,20);
        assertFalse(P.wasConfirmed());
    }

    @Test
    public void testWasQueued_true_currently_queued() throws PassengerException {
        Passenger P = new First(10,20);
        P.queuePassenger(10, 20);
        assertTrue(P.wasQueued());
    }
    
    @Test
    public void testWasQueued_true_was_queued() throws PassengerException {
        Passenger P = new First(10,20);
        P.queuePassenger(10, 20);
        P.confirmSeat(10, 20);
        assertTrue(P.wasQueued());
    }
    
    @Test
    public void testWasQueued_false() throws PassengerException {
        Passenger P = new First(10,20);
        assertFalse(P.wasQueued());
    }

}
