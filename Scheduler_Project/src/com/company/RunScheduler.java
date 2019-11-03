package com.company;

import java.io.File;

/* Class is designed to run both the basic (MVP) and advanced
   implementations of the scheduler.
 */
public class RunScheduler {

    /* Function takes in the input of the meetings file and then
       runs both of the implementations.
     */
    public static void main(String[] args) {
        // Checking to see if the file of meetings is valid, args[0] csv file
        File meetings = null;
        File holidays = null;
        int flag = -1;
        if (args.length != 1 && args.length != 3) {
            //Error message
            System.out.println("ERROR: ARGS ISSUE: " + args.length);
            return;
        } else if (args.length == 1){
            meetings = new File(args[0]);
            // Check if valid file
            if (!meetings.isFile()) {
                // Error message
                System.out.println("ERROR: FILE ISSUE");
                return;
            }
        } else {
            meetings = new File(args[0]);
            holidays = new File(args[1]);
            flag = Integer.valueOf(args[2]);
            // Check if valid file
            if (!meetings.isFile() || !holidays.isFile()) {
                // Error message
                System.out.println("ERROR: FILE ISSUE");
                return;
            }
        }

        // Implementation 1: MVP
        if (flag == -1) {
            MeetingSchedulerBasic schedulerMVP = new MeetingSchedulerBasic();
            schedulerMVP.ParseMeetings(meetings);
            schedulerMVP.PrintMeetingsReport();
        }

        // Implementation 2: Advanced
        MeetingSchedulerAdvanced schedulerAVD;
        if (flag == -1) {
            schedulerAVD = new MeetingSchedulerAdvanced();
            schedulerAVD.ParseMeetings(meetings);
        } else {
            schedulerAVD = new MeetingSchedulerAdvanced(holidays);
            if (flag == 0) {
                schedulerAVD.ParseMeetings(meetings);
            } else {
                schedulerAVD.ParseMeetings(meetings,flag);
            }
        }
        schedulerAVD.PrintMeetingsReport();
    }
}
