package uam.aleksy.deansoffice.tour.summary;

import lombok.extern.java.Log;
import uam.aleksy.deansoffice.employee.enums.Activity;
import uam.aleksy.deansoffice.tour.summary.data.TourSummary;

import java.util.Map;

@Log
class TourSummaryLogger {

    private static final String lineSeparator = System.lineSeparator();

    static void logTourSummary(TourSummary summary) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("Summary of tour ")
                .append(summary.getTourId())
                .append(": ")
                .append(lineSeparator)
                .append("remaining in queue: ")
                .append(summary.getQueueSize())
                .append(lineSeparator)
                .append(createEmployeeActivityLogMessage(summary.getEmployeeNameToActivity()));

        log.info(stringBuilder.toString());
    }

    private static String createEmployeeActivityLogMessage(Map<String, Activity> employeeNameActivityMap) {

        StringBuilder stringBuilder = new StringBuilder();

        employeeNameActivityMap.forEach((employeeName, activity) -> stringBuilder
                .append("Employee ")
                .append(employeeName)
                .append("'s current activity: ")
                .append(activity)
                .append(lineSeparator));

        return stringBuilder.toString();
    }
}
