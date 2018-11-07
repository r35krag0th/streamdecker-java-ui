package net.r35.streamdecker.guiswing.lib;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import net.r35.streamdecker.guiswing.HeroFragment;
import okhttp3.*;

public class StreamDeckerApi {
    private final OkHttpClient client = new OkHttpClient();
    private final Moshi moshi = new Moshi.Builder().build();
    private String baseUri;

    private final JsonAdapter<HeroesResponse> heroesResponseJsonAdapter = moshi.adapter(HeroesResponse.class);

    public StreamDeckerApi() {
        baseUri = "http://10.0.42.210:3123";
    }

    public StreamDeckerApi(String uri) {
        baseUri = uri;
    }

    public List<HeroFragment> getHeroes() throws IOException {
        Request request = new Request.Builder()
                .url(baseUri + "/heroes")
                .build();
        try (Response response = client.newCall(request).execute()) {
//            return response.body().json();
            Type type = Types.newParameterizedType(List.class, HeroFragment.class);
            JsonAdapter<List<HeroFragment>> adapter = moshi.adapter(type);

            List<HeroFragment> output = adapter.fromJson(response.body().source());

            for (HeroFragment fragment : output) {
                System.out.println(String.format("name=%s, slug=%s", fragment.name, fragment.slug));
            }
            return output;
        }
    }

    public HeroBuildsCollection getBuilds(Hero hero) throws IOException {
        String finalUrl = String.format("%s/heroes/%s", baseUri, hero.slug);
        System.out.println(String.format("=== [ URL | %s", finalUrl));
        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            Type type = Types.newParameterizedType(List.class, HeroBuild.class);
            JsonAdapter<List<HeroBuild>> adapter = moshi.adapter(type);

            List<HeroBuild> builds = adapter.fromJson(response.body().source());
            return new HeroBuildsCollection(builds);
        }
    }

    public void setAsCurrentBuild(Hero hero, String buildName) throws IOException {
        String finalUrl = String.format("%s/build/current", baseUri);
        System.out.println(String.format("=== [ URL | %s", finalUrl));

        HeroBuild build = hero.builds.getByName(buildName);

        SetCurrentBuildPayload payload = new SetCurrentBuildPayload();
        payload.hero = hero.slug;
        payload.build = build.name;

        JsonAdapter<SetCurrentBuildPayload> adapter = moshi.adapter(SetCurrentBuildPayload.class);


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, adapter.toJson(payload));
        Request request = new Request.Builder()
                .url(finalUrl)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.code());
        }
    }
}
