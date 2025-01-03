package chainofresponsability;

import java.time.LocalDate;

public class BookingRequest {
    private String clientEmail;
    private String city;
    private String accommodationName;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int numberOfPeople;

    public BookingRequest(String clientEmail, String city, String accommodationName, LocalDate startDate, LocalDate finishDate, int numberOfPeople) {
        this.clientEmail = clientEmail;
        this.city = city;
        this.accommodationName = accommodationName;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.numberOfPeople = numberOfPeople;
    }

    // Getters
    public String getClientEmail() {
        return clientEmail;
    }

    public String getCity() {
        return city;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }
}