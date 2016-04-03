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
- Toolbar on CompanyList view to compare the image loading performance of Picasso (3rd party tool) and self-build AsyncTask + LruCache. Picasso seems to have better performance overall, but wanted to show a self-implemented solution without using a 3rd party tool. 
NOTE: This comparison is limited since the Picasso cache is not cleared. Thus, after opening the Picasso tab once, subsequent uses of the Picasso tab will maintain its current cache.
NOTE2: To allow for the comparison, the image/JSON caches will be cleared when switching tabs, causing a refresh fetch.
