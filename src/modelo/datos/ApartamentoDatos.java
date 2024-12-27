package modelo.datos;

import modelo.entidades.Apartamento;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.UtilExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApartamentoDatos {

    private static final String FILE_PATH = "apartamentos.xlsx";
    private static final String SHEET_NAME = "Apartamentos";
    private static final List<String> HEADERS = List.of("Nombre", "Precio por Noche", "Tipo", "Ciudad", "Amueblado", "Características");

    // Crear un apartamento
    public static void crearApartamento(Apartamento apartamento) throws IOException {
        UtilExcel.ensureFileExists(FILE_PATH, SHEET_NAME, HEADERS);
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        int lastRow = sheet.getLastRowNum() + 1; // Inserta en la última fila
        UtilExcel.writeRow(sheet, lastRow, apartamento.toRow());
        UtilExcel.saveWorkbook(workbook, FILE_PATH);
    }

    // Leer todos los apartamentos
    public static List<Apartamento> leerApartamentos() throws IOException {
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        List<Apartamento> apartamentos = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            List<String> row = UtilExcel.readRow(sheet.getRow(i));
            apartamentos.add(Apartamento.fromRow(row));
        }
        return apartamentos;
    }

    // Actualizar un apartamento
    public static void actualizarApartamento(String nombre, String ciudad, Apartamento nuevoApartamento) throws IOException {
        List<Apartamento> apartamentos = leerApartamentos();
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        for (int i = 0; i < apartamentos.size(); i++) {
            Apartamento apartamento = apartamentos.get(i);
            if (apartamento.getNombre().equalsIgnoreCase(nombre) && apartamento.getCiudad().equalsIgnoreCase(ciudad)) {
                UtilExcel.writeRow(sheet, i + 1, nuevoApartamento.toRow()); // +1 porque salta el encabezado
                UtilExcel.saveWorkbook(workbook, FILE_PATH);
                return;
            }
        }
        throw new IOException("Apartamento no encontrado para actualizar.");
    }

    // Eliminar un apartamento
    public static void eliminarApartamento(String nombre, String ciudad) throws IOException {
        List<Apartamento> apartamentos = leerApartamentos();
        Workbook workbook = UtilExcel.loadWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        for (int i = 0; i < apartamentos.size(); i++) {
            Apartamento apartamento = apartamentos.get(i);
            if (apartamento.getNombre().equalsIgnoreCase(nombre) && apartamento.getCiudad().equalsIgnoreCase(ciudad)) {
                sheet.removeRow(sheet.getRow(i + 1)); // +1 porque salta el encabezado
                UtilExcel.saveWorkbook(workbook, FILE_PATH);
                return;
            }
        }
        throw new IOException("Apartamento no encontrado para eliminar.");
    }

    // Buscar apartamentos por ciudad
    public static List<Apartamento> buscarPorCiudad(String ciudad) throws IOException {
        List<Apartamento> apartamentos = leerApartamentos();
        List<Apartamento> resultado = new ArrayList<>();

        for (Apartamento apartamento : apartamentos) {
            if (apartamento.getCiudad().equalsIgnoreCase(ciudad)) {
                resultado.add(apartamento);
            }
        }
        return resultado;
    }

    // Buscar apartamento por nombre
    public static Apartamento buscarPorNombre(String nombre) throws IOException {
        List<Apartamento> apartamentos = leerApartamentos();

        for (Apartamento apartamento : apartamentos) {
            if (apartamento.getNombre().equalsIgnoreCase(nombre)) {
                return apartamento;
            }
        }
        throw new IOException("Apartamento no encontrado.");
    }
}
