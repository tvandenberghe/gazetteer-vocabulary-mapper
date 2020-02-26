/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.general;

import be.naturalsciences.bmdc.mapper.general.KnowledgeIndexMatcher;
import be.naturalsciences.bmdc.mapper.places.general.GazetteerMatcher;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 *
 * @author thomas
 */
public class Printer<L extends LocalEntry, R extends RemoteEntry> {

    private final KnowledgeIndexMatcher matcher;

    private final boolean append;
    private final Long timeStamp;

    public KnowledgeIndexMatcher getMatcher() {
        return matcher;
    }

    public Printer(KnowledgeIndexMatcher matcher, boolean append) {
        this.matcher = matcher;
        this.append = append;
        if (append) {
            this.timeStamp = null;
        } else {
            this.timeStamp = System.currentTimeMillis();
        }
    }

    public void printLineToFile(L localEntry, Set<R> remoteEntries) throws IOException {
    }

    public void printToFile() throws IOException {
        if (!getMatcher().getFeatures().isEmpty()) {
            System.out.println("-------------------PRINTING TO FILE-------------------");
            String apiName = getMatcher().getApi().getName();
            String fileName = "gazetteer_match_" + apiName + (this.timeStamp == null ? "" : "_" + this.timeStamp) + ".csv";
            FileWriter out = new FileWriter(fileName, append);

            try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.TDF)) { // try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.TDF.withHeader(HEADERS))) {
                boolean once = false;
                Map<L, Set<R>> features = getMatcher().getFeatures();
                for (Map.Entry<L, Set<R>> entry : features.entrySet()) {
                    L localEntry = entry.getKey();
                    Set<R> remoteEntries = entry.getValue();
                    List<String> localKeys = new ArrayList<>(localEntry.listFieldKeys());
                    List<String> localFields = new ArrayList<>(localEntry.listFieldValues());
                    if (remoteEntries != null) {

                        String collect = remoteEntries.stream().map(g -> g.toString()).collect(Collectors.joining("; "));
                        System.out.println("Writing to csv: " + localEntry.toString() + ": " + collect);

                        for (R remoteEntry : remoteEntries) {
                            try {
                                if (!once) {
                                    List<String> remoteKeys = new ArrayList<>(remoteEntry.listFieldKeys());
                                    localKeys.add("API");
                                    localKeys.addAll(remoteKeys);
                                    printer.printRecord(localKeys);
                                    once = true;
                                }

                                List<String> remoteFields = new ArrayList<>(remoteEntry.listFieldValues());
                                localFields.add(apiName);
                                localFields.addAll(remoteFields);
                                printer.printRecord(localFields);
                            } catch (Exception ex) {
                                Logger.getLogger(GazetteerMatcher.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
                            }
                        }
                    } else {
                        try {
                            printer.printRecord(localFields);
                            System.out.println(localEntry.toString() + ": nothing mapped");
                        } catch (Exception ex) {
                            Logger.getLogger(GazetteerMatcher.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
                        }
                    }
                }

                System.out.println("Saved csv to " + fileName);

            } catch (Exception ex) {
                Logger.getLogger(GazetteerMatcher.class.getName()).log(Level.SEVERE, "An exception occured.", ex); //normally shouldn't happen as long as file is named automatically and directory is writable
            }
        }
    }
}
