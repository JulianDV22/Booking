package chainofresponsability;

public abstract class BaseValidator implements BookingValidator {
    protected BookingValidator next;

    @Override
    public void setNext(BookingValidator next) {
        this.next = next;
    }

    protected boolean validateNext(BookingRequest request) {
        if (next == null) {
            return true;
        }
        return next.validate(request);
    }
}

