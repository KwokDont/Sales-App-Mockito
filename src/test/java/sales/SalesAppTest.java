package sales;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class SalesAppTest {

	@Test
	public void testGenerateReport() {

		String salesId = "Eddy";
		Sales sales = spy(new Sales());
		SalesApp salesApp = spy(new SalesApp());
		sales.setSupervisor(false);

		SalesDao salesDao = mock(SalesDao.class);
		given(salesDao.getSalesBySalesId(salesId)).willReturn(sales);

		doReturn(true).when(salesApp).isEffective(any());

		List<SalesReportData> reportDataList = new ArrayList<>();
		SalesReportData salesReportData = new SalesReportData();
		reportDataList.add(salesReportData);

		SalesReportDao salesReportDao = mock(SalesReportDao.class);
		given(salesReportDao.getReportData(sales)).willReturn(reportDataList);

		List<String> headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");

		SalesActivityReport report = mock(SalesActivityReport.class);
		given(report.toXml()).willReturn("XML String");

		doReturn(report).when(salesApp).generateReport(headers, reportDataList);

		salesApp.generateSalesActivityReport("Eddy", 10, false, false);

		EcmService ecmService = spy(new EcmService());

		verify(salesDao, times(1)).getSalesBySalesId("Eddy");
		verify(salesApp, times(1)).isEffective(sales);
		verify(salesReportDao, times(1)).getReportData(sales);
		verify(salesApp, times(1)).generateReport(headers, reportDataList);
		verify(report, times(1)).toXml();
		verify(ecmService, times(1)).uploadDocument("XML String");
		
	}
}
