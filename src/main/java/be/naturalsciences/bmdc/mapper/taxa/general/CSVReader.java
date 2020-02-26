/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.general;

import be.naturalsciences.bmdc.mapper.taxa.model.WormsCSVTaxon;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

/**
 *
 * @author thomas
 */
public class CSVReader {

    public static <C> Iterator<C> iterator(Path inputPath, Class<C> cls) throws IOException {
        Iterator<C> csvWormsIterator = null;
        if (inputPath != null) {
            String inputFileName = inputPath.getFileName().toString();
            if (inputFileName.contains(".csv")) {
                if (Files.exists(inputPath)) {

                    Reader reader = Files.newBufferedReader(inputPath);

                    CsvToBean<C> csvToBean = new CsvToBeanBuilder(reader)
                            .withType(cls)
                            .withIgnoreLeadingWhiteSpace(true).withSeparator('\t')
                            .build();

                    try {
                        csvWormsIterator = csvToBean.iterator();
                    } catch (Exception e) {
                        System.out.println("->Csv file must be tab-delimited and should contain some mandatory headers. Check the exception stack below for the header name.");
                        throw e;
                    }
                } else {
                    System.out.println("->File doesn't exist!");
                }
            } else {
                System.out.println("->File argument not a csv file!");
            }
        } else {
            System.out.println("No file argument provided!");
        }

        return csvWormsIterator;
    }
}
