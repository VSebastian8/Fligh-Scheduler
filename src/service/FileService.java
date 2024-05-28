package service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import models.Ticket;
import repository.Database;

import static java.nio.file.StandardOpenOption.APPEND;

public class FileService implements FileServiceInterface {
    private static final String FILE_PATH = "src/audit/data.txt";
    private final StringBuilder STRING_BUILDER = new StringBuilder();
    public static FileService instance;

    private FileService() {
    }

    public static FileService getFileService() {
        if (instance == null) {
            instance = new FileService();
        }
        return instance;
    }

    public void writeFile(String content) {
        Path path = Paths.get(FILE_PATH);
        try {
            Files.write(path, content.getBytes(), APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeObjectToFile(Ticket ticket) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH, true))) {
            objectOutputStream.writeObject(ticket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void generateReport(List<Ticket> tickets) {
        generateHeader();
        tickets.forEach(ticket -> {
            STRING_BUILDER.append(ticket.getID())
                    .append(",")
                    .append(ticket.getPrice())
                    .append(",")
                    .append(ticket.getSeat())
                    .append(",")
                    .append(ticket.getFlightName())
                    .append("\n");
        });
        try {
            Path reportsPath = Paths.get("src/audit/" + "Audit_" + Instant.now().getNano() + ".csv");
            Files.createFile(reportsPath);
            Files.write(reportsPath, STRING_BUILDER.toString().getBytes(), APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateHeader() {
        STRING_BUILDER
                .append("TICKET_ID")
                .append(",")
                .append("PRICE")
                .append(",")
                .append("SEAT")
                .append(",")
                .append("FLIGHT_NAME")
                .append("\n");
    }

}
