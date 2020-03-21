package TestModels;

import annotations.MapSelector;
import annotations.Selector;

import java.util.Map;
import java.util.Set;

@Selector(query = "div > table.wikitable.sortable > tbody", root = true)
public class SpbMetroWiki {

    @MapSelector(keyQuery = "tr > td:nth-child(2) > a",
            valueQuery = "tr > td:nth-child(6) > small > span > span")
    public Map<String, Coordinate> stations;

    @Selector(query = "tr")
   public Set<SpbMetroLine> lines;

    public void setStations(Map<String, Coordinate> stations) {
        this.stations = stations;
    }

    public void setLines(Set<SpbMetroLine> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "TestModels.SpbMetroWiki{" +
                "stations=" + stations +
                ", \nlines=" + lines +
                '}';
    }
}
