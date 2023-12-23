[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/XNhTFXWh)
# JournalApp
This Android App is a Journal App, in this app, you record all the activities that you have done throughout the day.

**Name of the Project:** Journal App

**Name:** Kinshuk Goel\
**ID:** 2021A7PS2726G\
**Email:** f20212726@goa.bits-pilani.ac.in

**Name:** Hardaat Singh Baath\
**ID:** 2021A7PS2662G\
**Email:** f20212662@goa.bits-pilani.ac.in

## Description
In this application, the user can keep a track of all the activities that he has done throughout the day. The name of the activity, the date and the starting and end time of the activity are taken by the user and saved in a database. The activities are available even after the application is closed and reopened. We can insert, update or delete the activities thta are shown. The activity can be shared and there is a functional info button on the home screen.
All the edge and corner cases were taken into account along with user's comfort and ease while  developing and implementing this application.

## Implementation
1. First we started by implementing the Nav Graphs Actions. The entryListFragment is the starting point for the nav graph. The entryDetailsFragment and infoFragment originate from this destination. The add new entry button will be used to add new entries in the application.
2. Then we implemented the code for changing the Database. Added INSERT, QUERY and UPDATE queries for insert, get and update functionalities in the database. These queries are called by relevant actions in the app such as clicking the save button or clicking the floating action button.
3. We then added the delete button on the task. Clicking this button prompts the user with a final warning before deleting the entry.
4. We then added the share button. The share button enables the user to share the activity and makes use of implicit intent.
5. We then added the iinfo button. The 'Info' button launches a new fragment by passing an intent and shows the information of the application to the user.

## Testing
For testing, we have used TDD. Test-Driven Development (TDD) is a software development approach where you write tests before writing the actual code. You start by creating a test case that defines the expected behavior of a specific piece of functionality. Then, you write just enough code to make that test pass. TDD promotes code quality, rapid feedback, and helps prevent regressions in your software as it evolves. It's a valuable practice for ensuring the reliability and robustness of your code, especially in the field of Software Development, where precision and correctness are crucial. We also tested espresso to check the UI.

For testing using Espresso, we created 5 test cases and were able to pass all of them successfully.

## Accessibility
While using the Accessibility Scanner, we faced the following errors:
1. Text Scaling: This was an error caused due to the size of the heading.
2. Text Contrast: The forground to background contrast ratio was not in an acceptable range.
3. Colour Contrast: The colour contrast of the fragments was not accepted by the scanner, but we were able to fix it in the .xml file.

### Talkback Experience
During the accessibility testing, we tried to run the app using only the talkback feature of android. The feature worked properly, and we were able to use the app with the eyes closed. One possible problem that we faced was the layout, that might be difficult to learn for first time users. Changing layout in portrait and landscape mode might be a problem for the visually impared when using for the first time. 

**Hours taken to complete the assignment:** Approximately 30-40 hours spread across the entire time duration between the release of 
the assignment and the deadline.

**Pair Programming Rating:** 5(Both the team members actively involved in the development of this application)

**Difficulty-wise Rating:** 10/10
