package co.edu.unbosque.services;

import co.edu.unbosque.dto.Colections;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ColectionService {

    public static List<Colections> getColections() throws IOException {
        List<Colections> colections;
        try(var inputStream = ColectionService.class.getClassLoader().getResourceAsStream("colections.csv")){
            HeaderColumnNameMappingStrategy<Colections> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Colections.class);

            try(var bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
                CsvToBean<Colections> csvToBean = new CsvToBeanBuilder<Colections>(bufferedReader).withType(Colections.class).withMappingStrategy(strategy).withIgnoreLeadingWhiteSpace(true).build();
                colections = csvToBean.parse();
            }
        }
        return colections;
    }

    public static  Colections createColection(String name, String category, String quantity){

    }
}
