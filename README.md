# Bottle Rocket Android Developer Test

High level requirements
* Fetch data from a web service and display the returned data in a ListView
* Display the full details of a selected store on a separate screen

#### List View Requirements
- [x] Consume and parse the data JSON feed from url http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json
- [x] Cache the data using a method of your choice
- [x] Display a message to the user if a data connection or cache is not available
- [x] Fetch the data asynchronously
- [x] Display the returned data in a ListView with a custom list item layout as per example
- [x] When user taps on list item, display a new screen that displays all of the details of the selected store

NOTE: The instructions requested a ListView, but used a RecyclerView instead.

Extra features:
- Phone number on detail page is linked to open native phone
- Displaying latitude/longitude in detail page was replaced with a button that opens Google Maps with the appropriate latitude/longitude

