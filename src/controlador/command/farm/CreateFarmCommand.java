package controlador.command.farm;

import controlador.command.Command;
import modelo.datos.FarmData;
import modelo.entidades.Farm;

import java.io.IOException;

public class CreateFarmCommand implements Command {
    private final String name;
    private final Double pricePerNight;
    private final String city;
    private final Double area;

    public CreateFarmCommand(String name, Double pricePerNight, String city, Double area) {
        this.name = name;
        this.pricePerNight = pricePerNight;
        this.city = city;
        this.area = area;
    }

    @Override
    public String execute() {
        try {
            Farm farm = Farm.build(name, pricePerNight, city, area);
            FarmData.createFarm(farm);
            return "Finca creada exitosamente.";
        } catch (IOException e) {
            return "Error al crear la finca: " + e.getMessage();
        }
    }
}
