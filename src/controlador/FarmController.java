package controlador;

import controlador.command.Command;
import java.util.ArrayList;
import java.util.List;

public class FarmController {

    private final List<Command<?>> commandHistory = new ArrayList<>();

    public <T> T executeCommand(Command<T> command) {
        T result = command.execute();
        commandHistory.add(command);
        return result;
    }

}
