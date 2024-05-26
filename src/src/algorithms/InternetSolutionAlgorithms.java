package algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InternetSolutionAlgorithms {

    public String day_05_1_internet(String filename) throws IOException {
        StringBuilder topCrates = new StringBuilder();
        boolean readCreateSketch = true;
        List<List<String>> crateColumns = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (readCreateSketch) {
                    if (line.isEmpty()) {
                        readCreateSketch = false;
                        continue;
                    }

                    int crateRows = 9;
                    if (line.equals("")) {
                        crateRows = 0;
                    }

                    int position = 0;
                    List<String> crateRowsList = new ArrayList<>();
                    for (int i = 0; i < crateRows; i++) {
                        String substring;
                        if (i == (crateRows - 1)) {
                            substring = line.substring(position);
                        } else {
                            if (position >= line.length()) {
                                break; // Ensure we don't go out of bounds
                            }
                            substring = line.substring(position);
                            substring = substring.substring(0, Math.min(3, substring.length())); // Ensure substring length doesn't exceed line length
                            position += 4;
                        }
                        if (!Character.isDigit(substring.charAt(1))) {
                            crateRowsList.add(substring);
                        }
                    }

                    AtomicInteger rowCounter = new AtomicInteger();
                    crateRowsList.forEach(row -> {
                        if (crateColumns.isEmpty()) {
                            for (int i = 0; i < 9; i++) {
                                crateColumns.add(i, new ArrayList<>());
                            }
                        }
                        if (!row.equals("   ")) {
                            List<String> rowList = crateColumns.get(rowCounter.get());
                            rowList.add(row);
                        }
                        rowCounter.getAndIncrement();
                    });
                } else {
                    String[] parts = line.split(" ");
                    if (parts.length == 4 && parts[0].equals("move") && parts[2].equals("from") && parts[4].equals("to")) {
                        int moveValue = Integer.parseInt(parts[1]);
                        int fromValue = Integer.parseInt(parts[3]);
                        int toValue = Integer.parseInt(parts[5]);

                        for (int i = 0; i < moveValue; i++) {
                            List<String> fromList = crateColumns.get(fromValue - 1);
                            if (!fromList.isEmpty()) {
                                String movedValue = fromList.get(0);
                                List<String> toList = crateColumns.get(toValue - 1);
                                toList.add(0, movedValue);
                                fromList.remove(movedValue);
                            }
                        }
                    }
                }
            }
        }
        crateColumns.forEach(crateColumn -> topCrates.append(crateColumn.isEmpty() ? " " : crateColumn.get(0).replace("[", "").replace("]", "")));
        return topCrates.toString();
    }
}


