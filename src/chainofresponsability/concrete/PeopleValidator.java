package chainofresponsability.concrete;

import chainofresponsability.BaseValidator;
import chainofresponsability.BookingRequest;

public class PeopleValidator extends BaseValidator {
    @Override
    public boolean validate(BookingRequest request) {
        if (request.getNumberOfPeople() <= 0) {
            System.out.println("El número de personas debe ser mayor a cero.");
            return false;
        }
        return validateNext(request);
    }
}
