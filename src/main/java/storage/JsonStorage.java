package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import user.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private Gson gson = new GsonBuilder().create();


    /**
     * @param pathname Путь к JSON файлу. Если файла нет, он будет создан.
     * @throws InvalidPathException Если строка {@code pathname} не является путем к файлу с расширением JSON.
     * @throws IOException          Если возникла ошибка при попытке считать данные из JSON файла.
     * @throws JsonSyntaxException  Если уже существующий словарь пользователей в файле представлен
     *          в неккоректном виде {@code usersMap}
     */
    public JsonStorage(String pathname) throws InvalidPathException,
            IOException, JsonSyntaxException {
        if(pathname.endsWith(".json") || pathname.endsWith(".JSON"))
            path = Paths.get(pathname);
        else
            throw new InvalidPathException(pathname, "Не является путем к JSON файлу");
        file = new File(pathname);
        updateUsersArray();
    }

    public JsonStorage() throws IllegalArgumentException, IOException {
        this("./target/resources/dataStorage.json");
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

    /**
     * Добавить пользователя в {@code usersMap}, если его там еще нет, а затем записать в JSON файл
     * @param userId id пользователя
     * @throws IOException Если возникла ошибка ввода-вывода при попытке записать данные в файл
     */
    public void addUser(int userId) throws IOException{
        User user = new User(userId);
        if (usersMap.containsKey(Integer.toString(userId)))
            return;
        usersMap.put(Integer.toString(userId), user);
        updateJSONFile();
    }

    /**
     * обновляет количество сгенерированных пользователем задач в файле Json
     * @param userId id пользовтеля
     * @param numberOfTasks количество сгенерированных пользователем задач
     * @throws IOException Если при попытке записать данные в файл Json возникла ошибка
     */
    public void updateUsersGeneratedTasks(int userId, int numberOfTasks) throws IOException {
        usersMap.get(Integer.toString(userId)).addGeneratedTasks(numberOfTasks);
        updateJSONFile();
    }
    /**
     * обновляет количество решенных пользователем задач в файле Json
     * @param userId id пользовтеля
     * @param numberOfTasks количество решенных пользователем задач
     * @throws IOException Если при попытке записать данные в файл Json возникла ошибка
     */
    public void updateUsersSolvedTasks(int userId, int numberOfTasks) throws IOException {
        usersMap.get(Integer.toString(userId)).addSolvedTasks(numberOfTasks);
        updateJSONFile();
    }

    /**
     * возвращает объект пользователя {@code User}
     * @param userId id пользователя
     * @return объект пользователя {@code User}
     */
    public User getUserById(int userId) {
        return usersMap.get(Integer.toString(userId));
    }
    /**
     * Перезаписать JSON файл текущими данными, которые храняться в {@code usersMap}
     * @throws IOException Если возникла ошибка ввода-вывода при попытке записать данные в файл
     */
    private void updateJSONFile() throws IOException {
        writer = new FileWriter(file);
        writer.write(gson.toJson(usersMap));
        writer.close();
    }
    /**
     * Запись в {@code usersMap} данных, которые находятся в JSON файле.
     * @throws IOException          Если возникла ошибка при попытке считать данные из JSON файла.
     * @throws JsonSyntaxException  Если уже существующий словарь пользователей в файле представлен
     *                              в неккоректном виде {@code usersMap}
     */
    private void updateUsersArray() throws JsonSyntaxException , IOException{
        reader = new FileReader(file);
        usersMap = gson.fromJson(reader, new TypeToken<Map<String, User>>(){}.getType());
        if (usersMap == null)
            usersMap = new HashMap<String, User>();
        reader.close();
    }

    public static void main(String[] args) throws IOException {
        JsonStorage storage = new JsonStorage();
        for (User i : storage.usersMap.values()) {
            System.out.println(i.getGeneratedTasks());
        }
    }
}
