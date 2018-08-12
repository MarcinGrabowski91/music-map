## **Application shows places used for performing or producing music.**
#### Goals
- Based on MusicBrainz API and MapBox SDK
- Displayed places should be open from 1990
- Every pin has a lifespan, meaning after it expires, pin should be removed from the map. Lifespan calculation: open_year - 1990 = lifespan_in_seconds. Example: 2017 - 1990 = 27s

### How to build

To build the project, create a `secret.properties` file in the project's main folder and paste this line:

`MapBoxAccessToken=YourAccessToken`

| Property             | External property name | Environment variable |
|----------------------|------------------------|----------------------|
| MapBox Access Token  | MapBoxAccessToken      | MAP_BOX_ACCESS_TOKEN |
