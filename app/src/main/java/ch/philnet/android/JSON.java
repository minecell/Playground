package ch.philnet.android;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import ch.philnet.foodlocation.FoodLocation;

public class JSON {
    JsonWriter writer;

    public void WriteJSON(OutputStream out, List<FoodLocation> foodLocations) throws IOException {
        /*JsonWriter */writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("    ");
        writer.beginArray();
        for(FoodLocation fl : foodLocations) {
            SetObject(writer, fl);
        }
        writer.endArray();
        writer.close();
    }

    private void SetObject(JsonWriter writer, FoodLocation foodLocation) throws IOException {
        writer.beginObject();
        writer.name("location").value(foodLocation.getLocation());
        writer.name("shop").value(foodLocation.getShop());
        writer.name("desc").value(foodLocation.getDesc());
        writer.name("favorite").value(foodLocation.getFavorite());
        writer.endObject();
    }
    public List<FoodLocation> ReadJSON(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            List<FoodLocation> foodLocations = new ArrayList<>();
            reader.beginArray();
            while (reader.hasNext()) {
                foodLocations.add(GetObject(reader));
            }
            reader.endArray();
            return foodLocations;
        } finally {
            reader.close();
        }
    }

    private FoodLocation GetObject(JsonReader reader) throws IOException {
        String shop = "";
        String desc = "";
        String location = "";
        boolean favorite = false;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("shop")) {
                shop = reader.nextString();
            } else if (name.equals("desc")) {
                desc = reader.nextString();
            } else if (name.equals("location")) {
                location = reader.nextString();
            } else if (name.equals("favorite")) {
                favorite = reader.nextBoolean();
            }
        }
        reader.endObject();
        return new FoodLocation(shop, desc, location, favorite);
    }

    public JsonWriter getWriter() {
        return this.writer;
    }
}
