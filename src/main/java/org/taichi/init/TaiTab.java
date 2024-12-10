package org.taichi.init;

public enum TaiTab {
    Curios("curios"),
    Ingredients("ingredients"),
    Misc("misc"),
    ;

    private final String name;
    public String getName() {
        return name;
    }

    TaiTab(String name) {
        this.name = name;
    }
}
