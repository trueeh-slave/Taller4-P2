package co.edu.unbosque.services;

import co.edu.unbosque.dto.Collections;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ColectionService {

    public static List<Collections> getCollections() throws IOException {
        List<Collections> colections;
        try(var inputStream = ColectionService.class.getClassLoader().getResourceAsStream("colections.csv")){
            HeaderColumnNameMappingStrategy<Collections> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Collections.class);

            try(var bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
                CsvToBean<Collections> csvToBean = new CsvToBeanBuilder<Collections>(bufferedReader).withType(Collections.class).withMappingStrategy(strategy).withIgnoreLeadingWhiteSpace(true).build();
                colections = csvToBean.parse();
            }
        }
        return colections;
    }

    public static Collections createColection(String name, String category, String quantity, String path) throws IOException{
        String newLine = "\n"+ name + "," + category + "," + quantity;
        var outputStream  = new FileOutputStream(path + "WEB-INF/classes/"+ "collections", true);
        outputStream.write(newLine.getBytes());
        outputStream.close();
        return new Collections(name,category,quantity);
    }
}
