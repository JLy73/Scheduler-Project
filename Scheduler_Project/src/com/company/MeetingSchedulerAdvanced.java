package com.company;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/* Class is the advanced implementation of the problem. New ideas
   include:
    Files that allow meetings to be monthly, daily, biweekly, every third week, etc
    Holidays (company or personal) to be entered and taken account for

    Potential future ideas: Time of day and length of meeting
 */
public class MeetingSchedulerAdvanced extends MeetingScheduler {


    public MeetingSchedulerAdvanced(File holiday) {
        // create empty holidays and meetings
        meetings = new HashMap<>();
        holidays = new ArrayList<>();
        // get holidays
        // Read the file
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(holiday));
            // Go through each meeting in meeting
            String line = reader.readLine();
            while (line != null) {
                // Get day
                String day = line.strip();
                Date the_date = fullDate.parse(day);
                holidays.add(the_date);
                // next holiday
                line = reader.readLine();
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

    public MeetingSchedulerAdvanced() {
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
                    "These are the following dates:");
            for (int i = 0; i < count; ++i) {
                System.out.print("\t");
                System.out.println(dates.get(i));
            }
        }
    }

    /*
      Function allows for multiple file types to be explored:
           1: start date, end date, day of week, every x week
           2: start date, end date, day of week, every x days
           Potential future flags: start date, end date, day of week, day of week, ....
     */
    public void ParseMeetings(File allMeetings, int flag) {
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

                int multiplier;
                if (flag == 1) {
                    multiplier = Integer.valueOf(parts[3])*7;
                } else if (flag == 2) {
                    multiplier = Integer.valueOf(parts[3]);
                } else {
                    System.out.println("ERROR: Flag not identified.");
                    return;
                }

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
                                calendar.add(Calendar.DATE, multiplier);
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
                    calendar.add(Calendar.DATE, multiplier);
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

}
