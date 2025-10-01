package com.example.client.command;

import com.example.client.service.FileService;

import java.util.Scanner;

public class CommandController {

    private final FileService service;

    public CommandController(FileService service) {
        this.service = service;
    }

    public void runCommand(Command command, Scanner sc) {
        command.execute(service, sc);
    }
}
