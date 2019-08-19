package parking;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class InOrderParkingStrategyTest {

	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */
        Car mEddyCar = mock(Car.class);
        when(mEddyCar.getName()).thenReturn("EddyCar2");
        ParkingLot mEddyLot = mock(ParkingLot.class);
        when(mEddyLot.getName()).thenReturn("EddyLot2");

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        Receipt receipt = inOrderParkingStrategy.createReceipt(mEddyLot, mEddyCar);

        verify(mEddyCar, times(1)).getName();
        verify(mEddyLot, times(1)).getName();
        Assert.assertEquals("EddyCar2", receipt.getCarName());
        Assert.assertEquals("EddyLot2", receipt.getParkingLotName());
    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */
        Car mEddyCar = mock(Car.class);
        when(mEddyCar.getName()).thenReturn("EddyCar2");

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        Receipt receipt = inOrderParkingStrategy.createNoSpaceReceipt(mEddyCar);

        verify(mEddyCar, times(1)).getName();
        Assert.assertEquals("EddyCar2", receipt.getCarName());
        Assert.assertEquals(ParkingStrategy.NO_PARKING_LOT, receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){

	    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */
        Car eddyCar = new Car("EddyCar");

        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());
        spyInOrderParkingStrategy.park(null, eddyCar);

        verify(spyInOrderParkingStrategy).createNoSpaceReceipt(eddyCar);
    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */
        Car eddyCar = new Car("EddyCar");

        ParkingLot parkingLot = new ParkingLot("parking lot", 1);

        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = spyInOrderParkingStrategy.park(parkingLots, eddyCar);

        verify(spyInOrderParkingStrategy).createReceipt(parkingLot, eddyCar);
        Assert.assertEquals("EddyCar", receipt.getCarName());
        Assert.assertEquals("parking lot", receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */
        Car eddyCar = new Car("EddyCar");

        ParkingLot parkingLot = new ParkingLot("parking lot", 1);
        parkingLot.getParkedCars().add(new Car("stefanCar"));

        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = spyInOrderParkingStrategy.park(parkingLots, eddyCar);

        verify(spyInOrderParkingStrategy,times(0)).createReceipt(parkingLot, eddyCar);
        verify(spyInOrderParkingStrategy,times(1)).createNoSpaceReceipt(eddyCar);
        Assert.assertEquals("EddyCar", receipt.getCarName());
        Assert.assertEquals(ParkingStrategy.NO_PARKING_LOT, receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
        Car eddyCar = new Car("EddyCar");

        ParkingLot parkingLot1 = new ParkingLot("parking lot", 0);
        ParkingLot parkingLot2 = new ParkingLot("parking lot 2", 1);

        List<ParkingLot> parkingLots = Arrays.asList(parkingLot1, parkingLot2);
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = spyInOrderParkingStrategy.park(parkingLots, eddyCar);

        verify(spyInOrderParkingStrategy,times(1)).createReceipt(parkingLot2, eddyCar);
        verify(spyInOrderParkingStrategy,times(0)).createReceipt(parkingLot1, eddyCar);
        Assert.assertEquals("EddyCar", receipt.getCarName());
        Assert.assertEquals("parking lot 2", receipt.getParkingLotName());
    }
}
