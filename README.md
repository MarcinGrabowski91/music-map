## **Application shows places used for performing or producing music.**
### Goals
- Based on MusicBrainz API and MapBox SDK
- Displayed places should be open from 1990
- Every pin has a lifespan, meaning after it expires, pin should be removed from the map. 
Lifespan calculation: open_year - 1990 = lifespan_in_seconds. Example: 2017 - 1990 = 27s
- Places returned per request should be limited, but all places must be displayed on map.
 For example there 100 places for search term, but limit is 20, so application need to do 5 requests 
 to get all the places
- MusicBrainz API has a limit of server requests. After 15 requests, server returns error 503, 
so huge responses could be cut

### How to build

To build the project:
- create a `secret.properties` file in the project's main folder and paste this line:

`MapBoxAccessToken=YourAccessToken`

- Open Android Studio preferences -> Build, Execution, Deployment -> Compiler -> Turn off `Configure on demand`. 

[Gradle 4.6 users: If you're using either Android Plugin for Gradle 3.0.1 or 3.1.0 with Gradle 4.6, you should disable configuration on demand to avoid some unpredictable build errors. This issue should be fixed in a future version of the plugin.] Source: https://developer.android.com/studio/build/optimize-your-build
