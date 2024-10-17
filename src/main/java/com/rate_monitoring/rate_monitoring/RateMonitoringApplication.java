package com.rate_monitoring.rate_monitoring;
import com.rate_monitoring.rate_monitoring.controller.IWTX;
import com.rate_monitoring.rate_monitoring.controller.Jumeirah;
import com.rate_monitoring.rate_monitoring.controller.TBO;
import com.rate_monitoring.rate_monitoring.dao.excelSheetUpdate;
import com.rate_monitoring.rate_monitoring.dao.sqlConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.rate_monitoring.rate_monitoring.services.queryStringGenerate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableScheduling
public class RateMonitoringApplication {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public static void main(String[] args) {SpringApplication.run(RateMonitoringApplication.class, args);}

	@Scheduled(cron = "0 30 5 * * *")
	public void run() {
		System.out.println("Report Generating");
		try {
			sqlConnector cursor = new sqlConnector(jdbcTemplate);
			String filePath = "rateMonitoringReport.xlsx";
			excelSheetUpdate excelSheet = new excelSheetUpdate(filePath);

			for (int i = 0; i < cursor.booking_hotel_details_API.size(); i++) {
				cursor.fetchData(i);
				cursor.booking_hotel_details.getFirst().setPartyname(cursor.booking_hotel_details.getFirst().getPartyname() + " - " + cursor.booking_hotel_details.getFirst().getRoomDisplayString());
				double percent=0;
				try {
					if (Objects.equals(cursor.booking_hotel_details_API.get(i).getApichannelid(), "Jumeirah")) {
						Jumeirah.jumeirahCostAsPerResponse(cursor.booking_hotel_details_API.get(i), cursor.booking_hotel_details.getFirst(), cursor.booking_guests.getFirst());
						cursor.booking_hotel_details.getFirst().setSource("RO/JUM");
						percent = 0.01;
					} else if (Objects.equals(cursor.booking_hotel_details_API.get(i).getApichannelid(), "iWTX")) {
						IWTX.iwtxCostAsPerResponse(cursor.booking_hotel_details_API.get(i), cursor.booking_hotel_details.getFirst());
						cursor.booking_hotel_details.getFirst().setSource("RO/IWTX");
						percent = 0.04;
					} else {
						TBO.tboCostAsPerResponse(cursor.booking_hotel_details_API.get(i), cursor.booking_hotel_details.getFirst());
						cursor.booking_hotel_details.getFirst().setSource("RO/TBO");
						percent = 0.02;
					}
				}catch (Exception e){
					System.out.println("Api response is not received because of "+e);System.exit(0);
				}
				cursor.booking_hotel_details.getFirst().setActual_markup(cursor.booking_hotel_details.getFirst().getCostvalue() * percent);

				double calculated_sale = cursor.booking_hotel_details.getFirst().getActual_markup() + cursor.booking_hotel_details.getFirst().getCost_as_per_response();
				double difference = cursor.booking_hotel_details.getFirst().getSalevalue() - calculated_sale;
				cursor.booking_hotel_details.getFirst().setDifference_sale(difference);

				cursor.booking_hotel_details.getFirst().setResult("FAIL");
				if ((difference >= 0) && (difference) <= 1) {
					cursor.booking_hotel_details.getFirst().setResult("PASS");
				}
				List<Object> arr = new ArrayList<>();
				String query = queryStringGenerate.getString(cursor.booking_hotel_details.getFirst(), cursor.booking_hotel_details_API.get(i), arr);
				try {
					cursor.execute(query);
					excelSheet.addSheet(arr, cursor.booking_hotel_details_API.get(i).getApichannelid());
				} catch (Exception e){
					System.out.println("Data is not uploaded because of "+e);
				}
			}
		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception is encountered");
		}
		System.out.println("Report Generated");
    }

}