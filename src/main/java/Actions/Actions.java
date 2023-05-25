package Actions;

public enum Actions {
    GET_FILM_ACTION("getFilm"),
    LOAD_FILM_ACTION("loadFilm"),
    GET_TOP_ACTION("getTop"),
    LOAD_TOP_ACTION("loadTop");

    public String action;

    Actions(String action) {this.action = action;}
}
