package TestModels;


import annotations.Selector;

import java.util.List;

@Selector(query = "#mw-content-text > div.mw-parser-output > div:nth-child(149)", root = true)
public class SopranosShow {

    @Selector(query = "table > tbody > tr:nth-child(3) > td > table > tbody > tr > td > div > ul > li")
    List<String> characters;

    @Selector (query = "table > tbody > tr:nth-child(2) > td > table > tbody > tr")
    List<Season> seasons;

    public SopranosShow() {
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }
}

