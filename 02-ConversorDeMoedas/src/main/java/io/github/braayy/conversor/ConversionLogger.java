package io.github.braayy.conversor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConversionLogger {

    private String filename;

    public ConversionLogger(String filename) {
        this.filename = filename;
    }

    public void log(String from, String to, double amount, double convertedAmount) throws IOException {
        File file = new File(this.filename);
        FileWriter writer = new FileWriter(file, true);
        writer.append(String.format(
            "%.3f %s = %.3f %s\n",
            amount, from,
            convertedAmount, to
        ));
        writer.close();
    }

}
