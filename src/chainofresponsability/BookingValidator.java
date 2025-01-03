package chainofresponsability;

public interface BookingValidator {
    void setNext(BookingValidator next);
    boolean validate(BookingRequest request);
}

