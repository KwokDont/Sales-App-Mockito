package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

    SalesDao salesDao = new SalesDao();

    SalesReportDao salesReportDao = new SalesReportDao();

    EcmService ecmService = new EcmService();

	public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {
		
		if (salesId == null) { return; }
		
		Sales sales = getSalesDao().getSalesBySalesId(salesId);

        if (this.isNotEffective(sales)){ return; }
		
		List<SalesReportData> reportDataList = getSalesReportDao().getReportData(sales);

        List<String> headers = generateHeaders(isNatTrade);

        SalesActivityReport report = this.generateReport(headers, reportDataList);

		getEcmService().uploadDocument(report.toXml());
	}

    private List<String> generateHeaders(boolean isNatTrade) {
        List<String> headers;
        if (isNatTrade) {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
        } else {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
        }
        return headers;
    }

    protected boolean isNotEffective(Sales sales) {
        Date today = new Date();
        return today.after(sales.getEffectiveTo()) || today.before(sales.getEffectiveFrom());
    }

    protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return null;
	}

    public SalesDao getSalesDao() {
        return salesDao;
    }

    public SalesReportDao getSalesReportDao() {
        return salesReportDao;
    }

    public EcmService getEcmService() {
        return ecmService;
    }
}
