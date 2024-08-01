package persistence;

import org.json.JSONObject;


// Models code that can be found https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/Writable.java
public interface Writer {

    JSONObject toJson();
}
