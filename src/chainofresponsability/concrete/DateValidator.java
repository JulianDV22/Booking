package chainofresponsability.concrete;

import chainofresponsability.BaseValidator;
import chainofresponsability.BookingRequest;

public class DateValidator extends BaseValidator {
    @Override
    public boolean validate(BookingRequest request) {
        if (request.getStartDate() == null || request.getFinishDate() == null || request.getStartDate().isAfter(request.getFinishDate())) {
            System.out.println("Fechas inválidas.");
            return false;
        }
        return validateNext(request);
    }
}
