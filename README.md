# Scheduler-Project
Subset of a create meeting project to help count meetings with certain parameters.

# Guideline Responses
Estimated time working on the project: 6 hours (4 hour bulk and 2 hour test and clean-up)

No resources were used (minus the forgetful syntax look-ups).

# Design
Users give the scheduler a file with all meetings that they plan to schedule. The scheduler takes these meetings and puts them in a map, where the key is parameters of the meeting (limited to start date, end date and day of week) and its value is a list of all the days that fit in its parameters. Users have the ability to print a report showing these dates, or they can look up a specific date to find its meeting count.

# Implementation
The MVP/Basic solution simply keeps all dates in the range. It does not count for holidays but reminds the user to do so.

The Advanced solution gives the option to have a list of holidays that will automatically be removed from the count and potential dates. It also has the ability to pick a meeting every couple of days or weeks, which is determined by a flag the user sets.

# Input

Basic (which Advanced can do as well): input.csv

  input.csv:
+ start, end, day of week
+ Example line: 2018-05-02, 2018-12-31, Wednesday

Advanced: meetings.csv holidays.csv flag

  meetings.csv: 
+ start, end, day of week, every x days/weeks
+ Example line: 2018-05-02, 2018-12-31, Wednesday, 3

  holidays.csv:
+ date
+ Example line: 2018-12-25

  flags:
+ 0 - do normal implementation with holidays
+ 1 - every x weeks
+ 2 - every x days
