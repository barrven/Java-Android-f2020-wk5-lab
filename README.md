# Java-Android-f2020-wk5-lab
Week 5 lab - Using maps api and map activity

Start with google_maps_api.xml file. need to add api key here. This value is used in the manifest file.
Click on the link that is in this file and follow the link. Create, then copy and paste the api key into the xml file where it says YOUR KEY HERE

update long and lat values in the LatLng class being passed to the adMArker and MoveCamera methods
change camerageUpdateFactory method to newLatLngZoom then pass the LatLng object and a zoom level (higher is closer)

Modify layout - add layout resource file called main_layout
change this LinearLayout to ConstaintLayout
Copy activity_maps fragment and nest it into the main_layout xml 
