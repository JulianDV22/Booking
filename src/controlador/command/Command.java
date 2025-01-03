package controlador.command;

public interface Command<T> {
    T execute();
}