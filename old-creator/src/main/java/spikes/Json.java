package spikes;

import com.badlogic.gdx.utils.JsonReader;

/**
 * Created by IntelliJ IDEA.
 * User: Rafael Alvarez
 * Date: 6/13/12
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Json {


    public static void main(String[] args) {
        JsonReader jsonReader = new JsonReader() {
            @Override
            protected void startObject(String name) {
                System.out.println("Json.startObject:19 - name = " + name);
                super.startObject(name);
            }

            @Override
            protected void startArray(String name) {
                System.out.println("Json.startArray:25 - name = " + name);
                super.startArray(name);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            protected void pop() {
                System.out.println("Json.pop:31");
                super.pop();    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            protected void string(String name, String value) {
                System.out.println("Json.string:37 - " +name + " = "+ value);
                super.string(name, value);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            protected void number(String name, float value) {
                System.out.println("Json.number:43 - " +name + " = "+ value);
                super.number(name, value);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            protected void bool(String name, boolean value) {
                System.out.println("Json.bool:49 - " +name + " = "+ value);
                super.bool(name, value);    //To change body of overridden methods use File | Settings | File Templates.
            }
        };


        Object parse = jsonReader.parse(TEST);
        System.out.println("Json.main:22 - parse = " + parse);
    }

    
    private static final String TEST="{\n" +
            "    \"name\":\"First Project\",\n" +
            "    \"tilesets\":[\n" +
            "        {\n" +
            "            \"A5\":\"Shop-TileA5.png\",\n" +
            "            \"stuff\":\"Town-Rural.png\",\n" +
            "            \"black\":\"Shop-Blacksmith.png\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"sceneries\":[\n" +
            "        {\n" +
            "            \"id\":\"1336686674947\",\n" +
            "            \"name\":\"Town\",\n" +
            "            \"size\":\"640x480\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\":\"1336692255933\",\n" +
            "            \"name\":\"Shop\",\n" +
            "            \"size\":\"288x256\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
