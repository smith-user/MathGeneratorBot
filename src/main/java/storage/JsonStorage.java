package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import user.User;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class JsonStorage {
    private Map<String, User> usersMap;
    private Path path;
    private File file;
    private Reader reader;
    private Writer writer;
    private static final Type listType = new TypeToken<ArrayList<User>>(){}.getType();
    private Gson gson = new GsonBuilder().create();


    /**
     * @param pathname Путь к JSON файлу. Если файла нет, он будет создан.
     * @throws InvalidPathException Если строка {@code pathname} не является путем к файлу с расширением JSON.
     * @throws IllegalArgumentException Если поля представленных в файле экземпляров
     *      {@code Category} или {@code Record} имеют некорректные значения. Или массивы
     *      этих объектов представлены в неверном формате.
     */
    public JsonStorage(String pathname) throws InvalidPathException, IllegalArgumentException,
                                                IOException, JsonSyntaxException {
        //users = new Users();
        if(pathname.endsWith(".json") || pathname.endsWith(".JSON"))
            path = Paths.get(pathname);
        else
            throw new InvalidPathException(pathname, "Не является путем к JSON файлу");
        file = new File(pathname);
        updateUsersArray();
    }

    /**
     * @throws IllegalArgumentException Если поля представленных в файле экземпляров
     * {@code Category} или {@code Record} имеют некорректные значения. Или массивы
     * этих объектов имеют неверное представление.
     */
    public JsonStorage() throws IllegalArgumentException, IOException {
        this("./target/resources/categoryList.json");
    }

    private boolean WritingFileByUsingIO(String str) {
        byte[] data = str.getBytes();
        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(path, CREATE, TRUNCATE_EXISTING))) {
            out.write(data, 0, data.length);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void addUser(int userId) throws IOException{
        User user = new User(userId);
        if (usersMap.containsKey(Integer.toString(userId)))
            return;
        usersMap.put(Integer.toString(userId), user);
        writer = new FileWriter(file);
        writer.write(gson.toJson(usersMap));
        writer.close();
    }

    private void updateUsersArray() throws JsonSyntaxException , IOException{
        reader = new FileReader(file);
        usersMap = gson.fromJson(reader, new TypeToken<Map<String, User>>(){}.getType());
        if (usersMap == null)
            usersMap = new HashMap<String, User>();
        reader.close();
    }

    public static void main(String[] args) throws IOException {
        JsonStorage storage = new JsonStorage();
        storage.addUser(4);
        storage.addUser(3);
        for (User i : storage.usersMap.values()) {
            System.out.println(i.id);
        }
    }
}
