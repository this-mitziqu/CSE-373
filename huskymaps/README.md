# HuskyMaps Server

This assignment consists of three parts:\
### Rastering:
Rastering is the job of converting information into a pixel-by-pixel image. 
Given the coordinates of a rectangular region of the world and a requested depth, 
provide images of the appropriate resolution that cover that region.
### Street Map Graph:
`StreetMapGraph` implements `AStarGraph`. Each vertex of the graph is represented as a `Node` corresponding to a real, 
physical location in Seattle with a specific `long id`. Each Node has a latitude, longitude, and, optionally, a name. 
They represents both named places (such as the “University of Washington” light rail station) as well as spots along a
road (which can be unnamed). 
Many vertices don’t have edges, specifically those that correspond to places rather than spots along a road.\
There are three methods in the `StreetMapGraph`  class:
- `public long closest(double lon, double lat)`: 
finds the vertex nearest to where a user double-clicks on the map.
- `public List<String> getLocationsByPrefix(String prefix)`:
uses `BinaryRangeSearch` implementation to return autocomplete results.
- `public List<Location> getLocations(String locationName)`:
returns a list of locations that match the given `locationName`.
### Routing:

