package com.company;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
  Interface for a meeting scheduler and the functions needed to create, modify,
  and report meetings that are in occurrence at a particular company.
 */
public abstract class MeetingScheduler {

    // Variables
    HashMap<String[], ArrayList<String>> meetings;
    SimpleDateFormat fullDate = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat fullDay = new SimpleDateFormat("EEEE");
    Calendar calendar = Calendar.getInstance();
    ArrayList<Date> holidays;
    /*
      Function prints a general report of the current meetings scheduled.
     */
    abstract void PrintMeetingsReport();

    /*
      Function adds each meeting in a file of meetings.
     */
    void ParseMeetings(File allMeetings) {
        // Read the file
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(allMeetings));
            // Go through each meeting in meeting
            String meeting = reader.readLine();
            while (meeting != null) {
                // Get start date, end date, and day of week
                String[] parts = meeting.split(",");
                for (int i = 0; i < parts.length; ++i) {
                    parts[i] = parts[i].strip();
                }

                Date startDate = fullDate.parse(parts[0]);
                Date endDate = fullDate.parse(parts[1]);
                Date currDate = startDate;
                String currDay = fullDay.format(currDate);
                calendar.setTime(startDate);

                // Get first meeting
                int max = 8;
                boolean quit = false;
                while (!currDay.equals(parts[2])) {
                    // add day
                    calendar.add(Calendar.DATE, 1);
                    currDate = calendar.getTime();
                    currDay = fullDay.format(currDate);
                    max --;
                    if (max <= 0) {
                        System.out.println("ERROR: Invalid day of week");
                        quit = true;
                        break;
                    }
                }
                if (quit) {
                    meeting = reader.readLine();
                    continue;
                }

                // Get all remaining meetings
                ArrayList<String> allDates = new ArrayList<String>();
                boolean skip = false;
                while (currDate.compareTo(endDate) <= 0) {
                    if (holidays.size() > 0) {
                        for (int i = 0; i < holidays.size(); ++i) {
                            if (currDate.compareTo(holidays.get(i)) == 0) {
                                calendar.add(Calendar.DATE, 7);
                                currDate = calendar.getTime();
                                skip = true;
                                break;
                            }
                        }
                        if (skip) {
                            skip = false;
                            continue;
                        }
                    }
                    allDates.add(fullDate.format(currDate));
                    calendar.add(Calendar.DATE, 7);
                    currDate = calendar.getTime();
                }

                //Add meeting to meetings
                meetings.put(parts, allDates);

                // Next meeting
                meeting = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*
      Function counts the number of occurrences in a given meeting
     */
    int countMeeting(String meeting) {
        // Parse the meeting
        String[] parts = meeting.split(",");
        for (int i = 0; i < parts.length; ++i) {
            parts[i] = parts[i].strip();
        }

        // Find meeting in parsed meetings
        Iterator meetingItr = meetings.entrySet().iterator();
        while (meetingItr.hasNext()) {
            Map.Entry meet = (Map.Entry)meetingItr.next();
            String[] curr_meet = (String[]) meet.getKey();
            // if meet get count
            if (curr_meet[0].equals(parts[0]) && curr_meet[1].equals(parts[1]) && curr_meet[2].equals(parts[2])) {
                ArrayList<String> dates = (ArrayList<String>) meet.getValue();
                return dates.size();
            }
        }

        // If not found return error
        return -1;
    }
}
