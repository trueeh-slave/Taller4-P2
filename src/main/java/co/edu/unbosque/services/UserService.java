package co.edu.unbosque.services;

import co.edu.unbosque.dto.User;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {

    public static List<User> getUsers() throws IOException {
        List<User> users;
        try (var inputStream = UserService.class.getClassLoader().getResourceAsStream("users.csv")) {
            HeaderColumnNameMappingStrategy<User> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(User.class);

            try (var bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(bufferedReader).withType(User.class).withMappingStrategy(strategy).withIgnoreLeadingWhiteSpace(true).build();
                users = csvToBean.parse();
            }
        }
        return users;
    }

    public static User createUser(String username, String password, String role, String path) throws IOException {
        String newLine = "\n" + username + "," + password + "," + role + ",0";
        var outputStream = new FileOutputStream(path + "WEB-INF/classes/" + "users.csv", true);
        outputStream.write(newLine.getBytes());
        outputStream.close();
        return new User(username, password, role);
    }
}
