# Java-Android-f2020-wk5-lab
Week 5 lab - Using maps api and map activity

Start with google_maps_api.xml file. need to add api key here. This value is used in the manifest file.
Click on the link that is in this file and follow the link. Create, then copy and paste the api key into the xml file where it says YOUR KEY HERE

update long and lat values in the LatLng class being passed to the adMArker and MoveCamera methods
change camerageUpdateFactory method to newLatLngZoom then pass the LatLng object and a zoom level (higher is closer)

Modify layout - add layout resource file called main_layout
change this LinearLayout to ConstaintLayout
Copy activity_maps fragment and nest it into the main_layout xml 
Move the xmlns:map and xmlns:tools attributes from fragment to the constraint layout widget element body 

switch  back to design view then attach the layout to the top and sides with 0 margin

change layout in SetContentView of main class to use the main_layout.xml we just created

Run the app to test. If layout does not update on the design view, keep design view open and then ctrl + z then redo a couple times and the ide should update the design view

Add edit text element below the map

add button below the ET

Access the ET in the main program to get its value

in onCreate, access the button and define its onclick listener
this takes the text from the ET and checkes that it's not empty, then uses an instance of the Geocoder class to convert a location name into a list of addresses. This can generate an exception so must be surrounded by try-catch block

Then get an address from the list, make a new LatLng objext and pass it's lat and long to it from the address object. address.getLongitude(), etc
Then add a new mapMarker to the map and pass the position and name in a MarkerOptions object
then animate the camera

