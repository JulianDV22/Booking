package chainofresponsability.concrete;

import chainofresponsability.BaseValidator;
import chainofresponsability.BookingRequest;
import modelo.datos.FarmData;
import modelo.entidades.Farm;

import java.io.IOException;
import java.util.List;

public class AvailabilityValidator extends BaseValidator {
    @Override
    public boolean validate(BookingRequest request) {
        try {
            List<Farm> farmsInCity = FarmData.findFarmsByCity(request.getCity());
            if (farmsInCity.isEmpty()) {
                System.out.println("No se encontraron fincas disponibles en la ciudad: " + request.getCity());
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error al verificar disponibilidad: " + e.getMessage());
            return false;
        }
        return validateNext(request);
    }
}