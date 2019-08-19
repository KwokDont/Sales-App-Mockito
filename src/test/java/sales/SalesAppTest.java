package sales;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {

	@Test
	public void testGenerateReport_given_sales_effective_and_salesId_not_null() {

		String salesId = "Eddy";
		List<String> headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		List<SalesReportData> reportDataList = new ArrayList<>();
		SalesReportData salesReportData = new SalesReportData();
		reportDataList.add(salesReportData);

		Sales sales = spy(new Sales());
		sales.setSupervisor(false);

		SalesDao salesDao = mock(SalesDao.class);
		given(salesDao.getSalesBySalesId(salesId)).willReturn(sales);

		SalesReportDao salesReportDao = mock(SalesReportDao.class);
		given(salesReportDao.getReportData(sales)).willReturn(reportDataList);

		SalesActivityReport report = mock(SalesActivityReport.class);
		given(report.toXml()).willReturn("XML String");

		EcmService ecmService = spy(new EcmService());

		SalesApp salesApp = spy(new SalesApp());
		doReturn(salesReportDao).when(salesApp).getSalesReportDao();
		doReturn(report).when(salesApp).generateReport(headers, reportDataList);
		doReturn(false).when(salesApp).isNotEffective(sales);
		doReturn(salesDao).when(salesApp).getSalesDao();
		doReturn(ecmService).when(salesApp).getEcmService();

		salesApp.generateSalesActivityReport("Eddy", 10, false, false);

		verify(salesDao, times(1)).getSalesBySalesId("Eddy");
		verify(salesApp, times(1)).isNotEffective(sales);
		verify(salesReportDao, times(1)).getReportData(sales);
		verify(salesApp, times(1)).generateReport(headers, reportDataList);
		verify(report, times(1)).toXml();
		verify(ecmService, times(1)).uploadDocument("XML String");
	}

	@Test
	public void testGenerateReport_given_sales_effective_and_salesId_null() {

		String salesId = null;
		List<String> headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		List<SalesReportData> reportDataList = new ArrayList<>();
		SalesReportData salesReportData = new SalesReportData();
		reportDataList.add(salesReportData);

		Sales sales = spy(new Sales());
		sales.setSupervisor(false);

		SalesDao salesDao = mock(SalesDao.class);
		given(salesDao.getSalesBySalesId(salesId)).willReturn(null);

		SalesReportDao salesReportDao = mock(SalesReportDao.class);
		given(salesReportDao.getReportData(sales)).willReturn(reportDataList);

		SalesActivityReport report = mock(SalesActivityReport.class);
		given(report.toXml()).willReturn("XML String");

		EcmService ecmService = spy(new EcmService());

		SalesApp salesApp = spy(new SalesApp());
		doReturn(salesReportDao).when(salesApp).getSalesReportDao();
		doReturn(report).when(salesApp).generateReport(headers, reportDataList);
		doReturn(false).when(salesApp).isNotEffective(sales);
		doReturn(salesDao).when(salesApp).getSalesDao();
		doReturn(ecmService).when(salesApp).getEcmService();

		salesApp.generateSalesActivityReport(null, 10, false, false);

		verify(salesDao, times(0)).getSalesBySalesId(salesId);
		verify(salesApp, times(0)).isNotEffective(sales);
		verify(salesReportDao, times(0)).getReportData(sales);
		verify(salesApp, times(0)).generateReport(headers, reportDataList);
		verify(report, times(0)).toXml();
		verify(ecmService, times(0)).uploadDocument("XML String");
	}

	@Test
	public void testGenerateReport_given_sales_not_effective_and_salesId_not_null() {

		String salesId = "Eddy";
		List<String> headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		List<SalesReportData> reportDataList = new ArrayList<>();
		SalesReportData salesReportData = new SalesReportData();
		reportDataList.add(salesReportData);

		Sales sales = spy(new Sales());
		sales.setSupervisor(false);

		SalesDao salesDao = mock(SalesDao.class);
		given(salesDao.getSalesBySalesId(salesId)).willReturn(sales);

		SalesReportDao salesReportDao = mock(SalesReportDao.class);
		given(salesReportDao.getReportData(sales)).willReturn(reportDataList);

		SalesActivityReport report = mock(SalesActivityReport.class);
		given(report.toXml()).willReturn("XML String");

		EcmService ecmService = spy(new EcmService());

		SalesApp salesApp = spy(new SalesApp());
		doReturn(salesReportDao).when(salesApp).getSalesReportDao();
		doReturn(report).when(salesApp).generateReport(headers, reportDataList);
		doReturn(true).when(salesApp).isNotEffective(sales);
		doReturn(salesDao).when(salesApp).getSalesDao();
		doReturn(ecmService).when(salesApp).getEcmService();

		salesApp.generateSalesActivityReport("Eddy", 10, false, false);

		verify(salesDao, times(1)).getSalesBySalesId(salesId);
		verify(salesApp, times(1)).isNotEffective(sales);
		verify(salesReportDao, times(0)).getReportData(sales);
		verify(salesApp, times(0)).generateReport(headers, reportDataList);
		verify(report, times(0)).toXml();
		verify(ecmService, times(0)).uploadDocument("XML String");
	}

	@Test
	public void testGenerateReport_given_isNatTrade_true() {

		String salesId = "Eddy";
		List<String> headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		List<SalesReportData> reportDataList = new ArrayList<>();
		SalesReportData salesReportData = new SalesReportData();
		reportDataList.add(salesReportData);

		Sales sales = spy(new Sales());
		sales.setSupervisor(false);

		SalesDao salesDao = mock(SalesDao.class);
		given(salesDao.getSalesBySalesId(salesId)).willReturn(sales);

		SalesReportDao salesReportDao = mock(SalesReportDao.class);
		given(salesReportDao.getReportData(sales)).willReturn(reportDataList);

		SalesActivityReport report = mock(SalesActivityReport.class);
		given(report.toXml()).willReturn("XML String");

		EcmService ecmService = spy(new EcmService());

		SalesApp salesApp = spy(new SalesApp());
		doReturn(salesReportDao).when(salesApp).getSalesReportDao();
		doReturn(report).when(salesApp).generateReport(headers, reportDataList);
		doReturn(false).when(salesApp).isNotEffective(sales);
		doReturn(salesDao).when(salesApp).getSalesDao();
		doReturn(ecmService).when(salesApp).getEcmService();

		salesApp.generateSalesActivityReport("Eddy", 10, true, false);

		verify(salesDao, times(1)).getSalesBySalesId(salesId);
		verify(salesApp, times(1)).isNotEffective(sales);
		verify(salesReportDao, times(1)).getReportData(sales);
		verify(salesApp, times(1)).generateReport(headers, reportDataList);
		verify(report, times(1)).toXml();
		verify(ecmService, times(1)).uploadDocument("XML String");
	}
}
