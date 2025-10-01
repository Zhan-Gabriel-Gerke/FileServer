package com.example.client.command;

import com.example.client.service.FileService;
import java.util.Scanner;

public interface Command {
    void execute(FileService service, Scanner sc);
}
