package model;

import annotations.FieldSelector;
import annotations.RootClassSelector;

import java.util.Map;
import java.util.Set;

@RootClassSelector(query = "div > table.wikitable.sortable > tbody")
public class SpbMetroWiki {

    @FieldSelector(
            query = " tr > td:nth-child(2) > a,  tr > td:nth-child(6) > small > span > span")
    public Map<String, Coordinate> stations;

    @FieldSelector(query = "tr")
    Set<SpbMetroLine> lines;

    public void setStations(Map<String, Coordinate> stations) {
        this.stations = stations;
    }

    public void setLines(Set<SpbMetroLine> lines) {
        this.lines = lines;
    }
}
