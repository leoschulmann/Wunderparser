package TestModels;

import annotations.Selector;

import java.util.List;

@Selector(entity = true)
public class Season {
    @Selector(query = "td > div > ul > li > a")
    List<String> episodes;

    public List<String> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<String> episodes) {
        this.episodes = episodes;
    }
}
