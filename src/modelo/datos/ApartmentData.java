package modelo.datos;

import modelo.entidades.Apartment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApartmentData {

    private static final String FILE_PATH = "apartamentos.xlsx";
    private static final String SHEET_NAME = "Apartamentos";
    private static final List<String> HEADERS = List.of("Nombre", "Precio por Noche", "Tipo", "Ciudad", "Amueblado", "Características");
    private static final UtilExcel utilExcel = UtilExcel.getInstance();

    // Crear un apartamento
    public static void createApartment(Apartment apartment) throws IOException {
        executeWithWorkbook(FILE_PATH, workbook -> {
            Sheet sheet = utilExcel.ensureSheetExists(workbook, SHEET_NAME, HEADERS);
            int lastRow = sheet.getLastRowNum() + 1;
            utilExcel.writeRow(sheet, lastRow, apartment.toRow());
            utilExcel.saveWorkbook(workbook, FILE_PATH);
            return null; // Retorno explícito para cumplir con el tipo esperado
        });
    }

    // Leer todos los apartamentos
    public static List<Apartment> findApartments() throws IOException {
        return executeWithWorkbook(FILE_PATH, workbook -> {
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            List<Apartment> apartments = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                List<String> row = utilExcel.readRow(sheet.getRow(i));
                apartments.add(Apartment.fromRow(row));
            }
            return apartments;
        });
    }

    // Actualizar un apartamento
    public static void updateApartment(String name, String city, Apartment newApartment) throws IOException {
        modifyApartment(name, city, (sheet, index) -> {
            utilExcel.writeRow(sheet, index, newApartment.toRow());
            return null; // Retorno explícito
        });
    }

    // Eliminar un apartamento
    public static void deleteApartment(String name, String city) throws IOException {
        modifyApartment(name, city, (sheet, index) -> {
            sheet.removeRow(sheet.getRow(index));
            return null; // Retorno explícito
        });
    }

    private static void modifyApartment(String name, String city, ApartmentModification modification) throws IOException {
        int apartmentIndex = findApartmentIndex(name, city);
        if (apartmentIndex == -1) {
            throw new IOException("Apartamento no encontrado.");
        }
        executeWithWorkbook(FILE_PATH, workbook -> {
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            modification.modify(sheet, apartmentIndex + 1); // +1 para saltar el encabezado
            utilExcel.saveWorkbook(workbook, FILE_PATH);
            return null;
        });
    }

    private static int findApartmentIndex(String name, String city) throws IOException {
        List<Apartment> apartments = findApartments();
        for (int i = 0; i < apartments.size(); i++) {
            Apartment apartment = apartments.get(i);
            if (isaBoolean(name, city, apartment)) {
                return i;
            }
        }
        return -1;
    }

    private static <T> T executeWithWorkbook(String filePath, WorkbookExecutor<T> executor) throws IOException {
        Workbook workbook = utilExcel.loadWorkbook(filePath);
        try {
            return executor.execute(workbook);
        } finally {
            workbook.close();
        }
    }

    @FunctionalInterface
    private interface ApartmentModification {
        Void modify(Sheet sheet, int index) throws IOException;
    }

    @FunctionalInterface
    private interface WorkbookExecutor<T> {
        T execute(Workbook workbook) throws IOException;
    }

    // Buscar apartamentos por city
    public static List<Apartment> findApartmentPerCity(String city) throws IOException {
        return filtrateApartments(apartament -> apartament.getCity().equalsIgnoreCase(city));
    }

    // Buscar apartamento por name
    public static Apartment findPerName(String name) throws IOException {
        return findApartments().stream()
                .filter(apartament -> apartament.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IOException("Apartamento no encontrado."));
    }

    private static List<Apartment> filtrateApartments(ApartmentFiltrate filtrate) throws IOException {
        List<Apartment> apartments = findApartments();
        List<Apartment> result = new ArrayList<>();
        for (Apartment apartment : apartments) {
            if (filtrate.filtrate(apartment)) {
                result.add(apartment);
            }
        }
        return result;
    }

    private static boolean isaBoolean(String name, String city, Apartment apartment) {
        return apartment.getName().equalsIgnoreCase(name) && apartment.getCity().equalsIgnoreCase(city);
    }

    @FunctionalInterface
    private interface ApartmentFiltrate {
        boolean filtrate(Apartment apartment);
    }
}
