package core.FuzzyPetriLogic.PetriNet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.Util;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;

public class PetriNetJsonSaver<PetriType> {

  public static void saveFuzzyPetriNetToJsonFile(FuzzyPetriNet netToSave, String fileName) {
    (new PetriNetJsonSaver<FuzzyPetriNet>()).save(netToSave, fileName);
  }

  public static FuzzyPetriNet loadFromJsonFile(String fileName) {
    return (new PetriNetJsonSaver<FuzzyPetriNet>()).load(fileName, FuzzyPetriNet.class);
  }

  Gson mySerilaizer;

  public PetriNetJsonSaver() {
    mySerilaizer = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization()
        .registerTypeAdapter(ITable.class, new CustomSerDe<>())
        .registerTypeAdapter(IUnifiedTable.class, new CustomSerDeAgaing<>())
        .setPrettyPrinting().create();
  }

  public void save(PetriType netToSave, String fileName) {
    String jsonStr = mySerilaizer.toJson(netToSave);
    Util.writeToFile(fileName, jsonStr);
  }

  public String makeString(PetriType net) {
    return mySerilaizer.toJson(net);
  }

  public PetriType loadFromJsonString(String jsonString, Class<PetriType> clazz) {
    return mySerilaizer.fromJson(jsonString, clazz);
  }

  public PetriType load(String fileName, Class<PetriType> clazz) {
    Reader reader;
    try {
      reader = new FileReader(fileName);
      return mySerilaizer.fromJson(reader, clazz);
    } catch (FileNotFoundException e) {
      System.err.println("!!!!!PROABLY WRONG FILENAME!!!!!!");
      e.printStackTrace();
    }
    throw new RuntimeException("something when wrong with file readin");
  }

  public static class CustomSerDe<T extends Object> implements JsonSerializer<T>, JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      final JsonObject container = (JsonObject) json;
      final JsonElement typeName = getValueGivenKey(container, "type");
      final JsonElement data = getValueGivenKey(container, "data");
      final Type instanceType = getType(typeName);
      return context.deserialize(data, instanceType);
    }

    private JsonElement getValueGivenKey(final JsonObject container, String key) {
      final JsonElement elem = container.get(key);
      if (elem == null) {
        throw new JsonParseException("Key : '" + key + "' Not Found. Cannot Deserialize");
      }
      return elem;
    }

    private Type getType(final JsonElement typeElem) {
      String type = typeElem.getAsString();
      if (type.contains("1x1"))
        return OneXOneTable.class;
      if (type.contains("1x2"))
        return OneXTwoTable.class;
      if (type.contains("2x1"))
        return TwoXOneTable.class;
      if (type.contains("2x2"))
        return TwoXTwoTable.class;
      throw new RuntimeException("Wrong type value " + this.getClass().getName());
    }

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
      final JsonObject wrapper = new JsonObject();
      String name = makeEasyReadble(src.getClass().getName());
      wrapper.addProperty("type", name);
      wrapper.add("data", context.serialize(src));
      return wrapper;
    }

    private String makeEasyReadble(String name) {
      if (name.contains("OneXOneTable"))
        return "1x1";
      if (name.contains("OneXTwoTable"))
        return "1x2";
      if (name.contains("TwoXOneTable"))
        return "2x1";
      if (name.contains("TwoXTwoTable"))
        return "2x2";
      throw new RuntimeException("not registerde table type " + this.getClass().getName());
    }
  }

  public static class CustomSerDeAgaing<T extends Object> implements JsonSerializer<T>, JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      final JsonObject container = (JsonObject) json;
      final JsonElement typeName = getValueGivenKey(container, "unfiedType");
      final JsonElement data = getValueGivenKey(container, "unifiedData");
      final Type instanceType = getType(typeName);
      return context.deserialize(data, instanceType);
    }

    private JsonElement getValueGivenKey(final JsonObject container, String key) {
      final JsonElement elem = container.get(key);
      if (elem == null) {
        throw new JsonParseException("Key : '" + key + "' Not Found. Cannot Deserialize");
      }
      return elem;
    }

    private Type getType(final JsonElement typeElem) {
      String type = typeElem.getAsString();
      if (type.contains("u1x1"))
        return UnifiedOneXOneTable.class;
      if (type.contains("u1x2"))
        return UnifiedOneXTwoTable.class;
      if (type.contains("u2x1"))
        return UnifiedTwoXOneTable.class;
      if (type.contains("u2x2"))
        return UnifiedTwoXTwoTable.class;
      throw new RuntimeException("Wrong type value " + this.getClass().getName());
    }

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
      final JsonObject wrapper = new JsonObject();
      String name = makeEasyReadble(src.getClass().getName());
      wrapper.addProperty("unfiedType", name);
      wrapper.add("unifiedData", context.serialize(src));
      return wrapper;
    }

    private String makeEasyReadble(String name) {
      if (name.contains("UnifiedOneXOneTable"))
        return "u1x1";
      if (name.contains("UnifiedOneXTwoTable"))
        return "u1x2";
      if (name.contains("UnifiedTwoXOneTable"))
        return "u2x1";
      if (name.contains("UnifiedTwoXTwoTable"))
        return "u2x2";
      throw new RuntimeException("not registerde table type " + this.getClass().getName());
    }
  }
}
