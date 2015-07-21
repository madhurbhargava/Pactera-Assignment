# Pactera-Assignment
Assignment for Pactera

An Android app which:
 https://dl.dropboxusercontent.com/u/746330/facts.json
 1. Ingests a json feed from 
The gson library can be used to parse this json if desired.
The feed contains a title and a list of rows

 2. Displays the content in a ListView
The title in the ActionBar should be updated from the json
Each row should be the right height to display its own content and no taller. No content should be clipped. This means some rows will be larger than others.

 3. Loads the images lazily
Don't download them all at once, but only as needed
 4. Refresh function
Either place a refresh button or use swipe to refresh.
Should not block UI when loading the data from the json feed.

App Design:

A.) App uses GSON Library to ingest a json from https://dl.dropboxusercontent.com/u/746330/facts.json. 
B.) The parsed JSON is converted to following class objects: FactsData.java(The JSON Itself), FactsListObject.java(Individual row item in JSON).
C.) List is fetched via ListFetcher.java which is a Async task. ListFetcher relies on LisatDataFetchListener.java to communicate with UI(MainActivity.java).
D.) ListItemAdapter.java  is respponsible for loading data into ListView. Images are loaded lazily in getView() method via BitmapDownloader.
E.) Refresh Button is present under Menu.
