package net.r35.streamdecker.guiswing.lib;

import com.squareup.moshi.Json;

import java.util.Map;

public class HeroBuild {
    public Map<String, String> config;
    public String heroName;
    @Json(name="buildName") public String name;
    public String levelOnePick;
    public String levelThreePick;
    public String levelSevenPick;
    public String levelTenPick;
    public String levelThirteenPick;
    public String levelSixteenPick;
    public String levelTwentyPick;

    public String selfLink;

    public String getTopLineString() {
        return String.format("<html><font color=#ff0000>[ %s ]</font><font color=#00ff00>[ %s ]</font><font color=#0000ff>[ %s ]</font></html>", levelOnePick, levelThreePick, levelSevenPick);
    }

    public String getMiddleLineString() {
        return String.format("<html><font color=#ffff00>[ %s ]</font>", levelTenPick);
    }

    public String getBottomLineString() {
        return String.format("<html><font color=#0000ff>[ %s ]</font><font color=#00ff00>[ %s ]</font><font color=#ff0000>[ %s ]</font></html>", levelThirteenPick, levelSixteenPick, levelTwentyPick);
    }
}
