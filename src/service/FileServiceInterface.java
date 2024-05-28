package service;

import models.Ticket;

import java.util.List;

public interface FileServiceInterface {
    void writeFile(String content);

    void writeObjectToFile(Ticket ticket);

    void generateReport(List<Ticket> tickets);
}
