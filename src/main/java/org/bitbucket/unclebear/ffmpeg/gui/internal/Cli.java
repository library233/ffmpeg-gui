package org.bitbucket.unclebear.ffmpeg.gui.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cli {
    private static final Logger log = LoggerFactory.getLogger(Cli.class);
    private final List<String> commandLine;
    private final CliExceptionHandler cliExceptionHandler;
    private Process process;
    private boolean started = false;

    public Cli(String command, List<String> args, CliExceptionHandler cliExceptionHandler) {
        List<String> tmp = new ArrayList<>();
        tmp.add(command);
        tmp.addAll(args);
        commandLine = List.copyOf(tmp);
        this.cliExceptionHandler = cliExceptionHandler;
    }

    public void start() {
        try {
            process = Runtime.getRuntime().exec(commandLine.toArray(new String[]{}));
            started = true;
        } catch (IOException e) {
            log.error(String.format("Cannot execute command line: %s - %s", commandLine, e.getMessage()), e);
            CliException cliException = new CliException();
            cliException.initCause(e);
            cliExceptionHandler.handle(cliException);
        }
    }

    public boolean isStarted() {
        return started;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return super.toString();
        }
    }
}
