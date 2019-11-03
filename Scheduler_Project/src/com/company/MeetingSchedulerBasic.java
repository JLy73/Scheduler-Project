package com.company;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/* Class implements the MVP for the implementation */
public class MeetingSchedulerBasic extends MeetingScheduler {

    public MeetingSchedulerBasic() {
        // create empty holidays and meetings
        meetings = new HashMap<>();
        holidays = new ArrayList<>();
    }

    @Override
    public void PrintMeetingsReport() {
        // For meeting in meetings
        Iterator meetingItr = meetings.entrySet().iterator();
        while (meetingItr.hasNext()) {
            Map.Entry meet = (Map.Entry)meetingItr.next();
            String[] meeting = (String[]) meet.getKey();
            ArrayList<String> dates = (ArrayList<String>) meet.getValue();
            String day = meeting[2];
            String start = meeting[0];
            String end = meeting[1];
            int count= dates.size();
            String countPrint = String.valueOf(count);
            System.out.println("Meetings on " + day + "s from " + start + " to "
                    + end + " will have " +  String.valueOf(count) +
                    " occurrences.\n" +
                    "These are the following dates (check for " +
                    "holidays and vacations):");
            for (int i = 0; i < count; ++i) {
                System.out.print("\t");
                System.out.println(dates.get(i));
            }
        }
    }
}
