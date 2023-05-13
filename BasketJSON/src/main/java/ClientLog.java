package BasketJSON.src.main.java;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    protected List<String[]> log = new ArrayList<>();

    protected void log(int productNum, int amount) {
        log.add(new String[]{"" + productNum, "" + amount});
    }

    protected void exportAsCSV(File txtFile) {
        if (!txtFile.exists()) {
            log.add(0, new String[]{"productNum,amount"});
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
            writer.writeAll(log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



