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

		List<String> headers = null;
		
		if (salesId == null) { return; }
		
		Sales sales = salesDao.getSalesBySalesId(salesId);

        if (this.isEffective(sales)){ return; }
		
		List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);

        List<SalesReportData> filteredReportDataList = generateFilteredReportDataList(isSupervisor, reportDataList);

        filteredReportDataList = generateTemplateList(maxRow, reportDataList);
		
		if (isNatTrade) {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		} else {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		}
		
		SalesActivityReport report = this.generateReport(headers, reportDataList);

		ecmService.uploadDocument(report.toXml());
		
	}

    protected List<SalesReportData> generateTemplateList(int maxRow, List<SalesReportData> reportDataList) {
        List<SalesReportData> tempList = new ArrayList<SalesReportData>();
        for (int i=0; i < reportDataList.size() || i < maxRow; i++) {
            tempList.add(reportDataList.get(i));
        }
        return tempList;
    }

    protected List<SalesReportData> generateFilteredReportDataList(boolean isSupervisor, List<SalesReportData> reportDataList) {
        List<SalesReportData> filteredReportDataList = new ArrayList<SalesReportData>();;
        for (SalesReportData data : reportDataList) {
            if ("SalesActivity".equalsIgnoreCase(data.getType())) {
                if (data.isConfidential()) {
                    if (isSupervisor) {
                        filteredReportDataList.add(data);
                    }
                }else {
                    filteredReportDataList.add(data);
                }
            }
        }
        return filteredReportDataList;
    }

    protected boolean isEffective(Sales sales) {
        Date today = new Date();
        return today.after(sales.getEffectiveTo()) || today.before(sales.getEffectiveFrom());
    }

    protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return null;
	}

}
