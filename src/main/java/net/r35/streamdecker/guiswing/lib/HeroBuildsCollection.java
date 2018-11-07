package net.r35.streamdecker.guiswing.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HeroBuildsCollection {
    private Map<String, HeroBuild> storage;

    public HeroBuildsCollection() {
        storage = new HashMap<>();
    }

    public HeroBuildsCollection(List<HeroBuild> builds) {
        storage = new HashMap<>();
        merge(builds);
    }

    public void add(HeroBuild build) {
        if (storage.containsKey(build.name)) {
            return;
        }

        storage.put(build.name, build);
    }

    public void merge(List<HeroBuild> builds) {
        for (HeroBuild build : builds) {
            add(build);
        }
    }

    public void merge(HeroBuildsCollection collection) {
        for (String name : collection.getAllBuildNames()) {
            add(collection.getByName(name));
        }
    }

    public int count() {
        return storage.size();
    }

    public HeroBuild getByName(String buildName) {
        return storage.get(buildName);
    }

    public Set<String> getAllBuildNames() {
        return storage.keySet();
    }
}
