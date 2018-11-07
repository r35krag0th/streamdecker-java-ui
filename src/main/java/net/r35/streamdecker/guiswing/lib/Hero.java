package net.r35.streamdecker.guiswing.lib;

import java.util.List;
import java.util.Map;

public class Hero {
    public String name;
    public String slug;
    public HeroBuildsCollection builds = new HeroBuildsCollection();

    public Hero(String aName, String aSlug) {
        name = aName;
        slug = aSlug;
    }

    public boolean hasBuilds() {
        return builds.count() > 0;
    }
}
