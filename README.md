# E-Charge - Demo

For the purposes of the demo, we have simplified some procedures and implementations.

We generate 4 demo types of plugs in the background on the first App start. Navigatoin and UI have been kept basic. Strings and dimensions have not been extracted. 
Proper architecture, navigation, menus, package structure, dependency injection, etc have not been used for the purpose of this demo.

Below the test assignment goals with feedback on the implementation:

> - create and delete new users and see a list of all users

The main screen show all the registered users. Creating a new user (Registration) is done without a password. Long click on a user from the user list opens the delete user dialog.

> - create charging stations for our user

Clicking on a user in the user list opens the station list of this user. Add a station with the +-fab button.

> - get a list of our charging stations and the charging stations of the other users

We have not implemented a login, so selecting a user from the user list brings up the charging station list of that user. 
A screen with an overview of all station from all users with sorting options would be an easy addition. This would only make sense if we also add proper navigation with Appbar/Menu...
