package parking;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class VipParkingStrategyTest {

	@Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */

        Car eddyCar = new Car("eddyCarA");
        ParkingLot parkingLot = new ParkingLot("parking lot", 1);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        doReturn(true).when(spyVipParkingStrategy).isAllowOverPark(eddyCar);

        Receipt receipt = spyVipParkingStrategy.park(parkingLots, eddyCar);
        Assert.assertEquals("eddyCarA", receipt.getCarName());
        Assert.assertEquals("parking lot", receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
        Car eddyCar = new Car("eddyCar");
        ParkingLot parkingLot = new ParkingLot("parking lot", 1);
        parkingLot.getParkedCars().add(new Car("stefanCar"));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());
        doReturn(false).when(spyVipParkingStrategy).isAllowOverPark(eddyCar);

        Receipt receipt = spyVipParkingStrategy.park(parkingLots, eddyCar);
        Assert.assertEquals("eddyCar", receipt.getCarName());
        Assert.assertEquals(ParkingStrategy.NO_PARKING_LOT, receipt.getParkingLotName());
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car eddyCar = new Car("eddyCarA");
        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        CarDao mCarDao = mock(CarDao.class);

        doReturn(mCarDao).when(spyVipParkingStrategy).getCarDao();
        when(mCarDao.isVip("eddyCarA")).thenReturn(true);

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(eddyCar);

        Assert.assertTrue(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car eddyCar = new Car("eddyCarA");
        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        CarDao mCarDao = mock(CarDao.class);

        doReturn(mCarDao).when(spyVipParkingStrategy).getCarDao();
        when(mCarDao.isVip("eddyCarA")).thenReturn(false);

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(eddyCar);

        Assert.assertFalse(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car eddyCar = new Car("eddyCar");
        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        CarDao mCarDao = mock(CarDao.class);

        doReturn(mCarDao).when(spyVipParkingStrategy).getCarDao();
        when(mCarDao.isVip("eddyCar")).thenReturn(false);

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(eddyCar);

        Assert.assertFalse(allowOverPark);
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
