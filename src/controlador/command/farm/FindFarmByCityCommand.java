package controlador.command.farm;

import controlador.command.Command;
import modelo.datos.FarmData;
import modelo.entidades.Farm;

import java.io.IOException;
import java.util.List;

public class FindFarmByCityCommand implements Command {
    private final String city;
    private List<Farm> farms;

    public FindFarmByCityCommand(String city) {
        this.city = city;
    }

    @Override
    public String execute() {
        try {
            farms = FarmData.findFarmsByCity(city);
            return "Fincas encontradas en " + city + ": " + farms.toString();
        } catch (IOException e) {
            return "Error al buscar fincas por ciudad: " + e.getMessage();
        }
    }

    public List<Farm> getFarms() {
        return farms;
    }
}
