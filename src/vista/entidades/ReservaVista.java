package vista.entidades;

import controlador.HabitacionControlador;
import controlador.HotelControlador;
import controlador.ReservaControlador;
import modelo.datos.ApartamentoDatos;
import modelo.datos.DiaDeSolDatos;
import modelo.datos.FincaDatos;
import modelo.datos.ReservaDatos;
import modelo.entidades.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static vista.Main.mostrarSubmenuAlojamientos;

public class ReservaVista {

    public static void menuReservarDiaDeSol(String emailCliente) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Paso 1: Preguntar por la ciudad
            System.out.print("Ingrese la ciudad donde desea reservar: ");
            String ciudad = scanner.nextLine();

            // Obtener los Días de Sol de la ciudad
            List<DiaDeSol> diasDeSolEnCiudad = DiaDeSolDatos.buscarPorCiudad(ciudad);

            if (diasDeSolEnCiudad.isEmpty()) {
                System.out.println("No se encontraron Días de Sol disponibles en la ciudad: " + ciudad);
                return;
            }

            // Mostrar los Días de Sol disponibles
            System.out.println("\nDías de Sol disponibles en " + ciudad + ":");
            for (int i = 0; i < diasDeSolEnCiudad.size(); i++) {
                System.out.println((i + 1) + ". " + diasDeSolEnCiudad.get(i).getNombre() + " - Precio: " + diasDeSolEnCiudad.get(i).getPrecioPorNoche() +
                        " - Actividades" + diasDeSolEnCiudad.get(i).getActividades() + " - Incluye almuerzo: " + diasDeSolEnCiudad.get(i).isIncluyeAlmuerzo() +
                        " - Incluye refrigerio: " + diasDeSolEnCiudad.get(i).isIncluyeRefrigerio());
            }

            // Elegir un Día de Sol
            System.out.print("Seleccione un Día de Sol (número): ");
            int diaDeSolIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consumir salto de línea

            if (diaDeSolIndex < 0 || diaDeSolIndex >= diasDeSolEnCiudad.size()) {
                System.out.println("Selección inválida.");
                return;
            }

            DiaDeSol diaDeSolSeleccionado = diasDeSolEnCiudad.get(diaDeSolIndex);

            // Paso 2: Ingresar la cantidad de personas
            System.out.print("Ingrese la cantidad de personas: ");
            int cantidadPersonas = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            // Paso 3: Ingresar la fecha de la reserva
            System.out.print("Ingrese la fecha de la reserva (YYYY-MM-DD): ");
            String fechaStr = scanner.nextLine();
            LocalDate fecha;

            try {
                fecha = LocalDate.parse(fechaStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
                return;
            }

            // Crear la reserva
            String resultado = ReservaControlador.crearReservaDiaDeSol(emailCliente, diaDeSolSeleccionado.getNombre(), fecha, cantidadPersonas);
            System.out.println(resultado);

        } catch (IOException e) {
            System.out.println("Error al procesar la reserva: " + e.getMessage());
        }
    }

    public static void menuReservarFinca(String emailCliente) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Paso 1: Preguntar por la ciudad
            System.out.print("Ingrese la ciudad donde desea reservar: ");
            String ciudad = scanner.nextLine();

            // Obtener las fincas de la ciudad
            List<Finca> fincasEnCiudad = FincaDatos.buscarPorCiudad(ciudad);

            if (fincasEnCiudad.isEmpty()) {
                System.out.println("No se encontraron fincas disponibles en la ciudad: " + ciudad);
                return;
            }

            // Mostrar las fincas disponibles
            System.out.println("\nFincas disponibles en " + ciudad + ":");
            for (int i = 0; i < fincasEnCiudad.size(); i++) {
                System.out.println((i + 1) + ". " + fincasEnCiudad.get(i).getNombre() + " - Precio por noche: " + fincasEnCiudad.get(i).getPrecioPorNoche() +
                        " - Area (Hectareas): " + fincasEnCiudad.get(i).getArea());
            }

            // Elegir una finca
            System.out.print("Seleccione una finca (número): ");
            int fincaIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consumir salto de línea

            if (fincaIndex < 0 || fincaIndex >= fincasEnCiudad.size()) {
                System.out.println("Selección inválida.");
                return;
            }

            Finca fincaSeleccionada = fincasEnCiudad.get(fincaIndex);

            // Paso 2: Ingresar la fecha de inicio
            System.out.print("Ingrese la fecha de inicio de la reserva (YYYY-MM-DD): ");
            String fechaInicioStr = scanner.nextLine();
            LocalDate fechaInicio;

            try {
                fechaInicio = LocalDate.parse(fechaInicioStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
                return;
            }

            // Paso 3: Ingresar la fecha de fin
            System.out.print("Ingrese la fecha de fin de la reserva (YYYY-MM-DD): ");
            String fechaFinStr = scanner.nextLine();
            LocalDate fechaFin;

            try {
                fechaFin = LocalDate.parse(fechaFinStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
                return;
            }

            // Paso 4: Ingresar la cantidad de personas
            System.out.print("Ingrese la cantidad de personas: ");
            int cantidadPersonas = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea


            // Crear la reserva
            String resultado = ReservaControlador.crearReservaFinca(emailCliente, fincaSeleccionada.getNombre(), fechaInicio, fechaFin, cantidadPersonas);
            System.out.println(resultado);

        } catch (IOException e) {
            System.out.println("Error al procesar la reserva: " + e.getMessage());
        }
    }

    public static void menuReservarApartamento(String emailCliente) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Paso 1: Preguntar por la ciudad
            System.out.print("Ingrese la ciudad donde desea reservar el apartamento: ");
            String ciudad = scanner.nextLine();

            // Obtener los Apartamentos de la ciudad
            List<Apartamento> apartamentosEnCiudad = ApartamentoDatos.buscarPorCiudad(ciudad);

            if (apartamentosEnCiudad.isEmpty()) {
                System.out.println("No se encontraron apartamentos disponibles en la ciudad: " + ciudad);
                return;
            }

            // Mostrar los Apartamentos disponibles
            System.out.println("\nApartamentos disponibles en " + ciudad + ":");
            for (int i = 0; i < apartamentosEnCiudad.size(); i++) {
                Apartamento apartamento = apartamentosEnCiudad.get(i);
                System.out.println((i + 1) + ". " + apartamento.getNombre() + " - Precio: " + apartamento.getPrecioPorNoche() +
                        " - Amueblado: " + (apartamento.isAmueblado() ? "Sí" : "No") +
                        " - Características: " + String.join(", ", apartamento.getCaracteristicas()));
            }

            // Elegir un Apartamento
            System.out.print("Seleccione un apartamento (número): ");
            int apartamentoIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consumir salto de línea

            if (apartamentoIndex < 0 || apartamentoIndex >= apartamentosEnCiudad.size()) {
                System.out.println("Selección inválida.");
                return;
            }

            Apartamento apartamentoSeleccionado = apartamentosEnCiudad.get(apartamentoIndex);

            // Paso 2: Ingresar la cantidad de personas
            System.out.print("Ingrese la cantidad de personas: ");
            int cantidadPersonas = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            // Paso 3: Ingresar las fechas de inicio y fin
            System.out.print("Ingrese la fecha de inicio (YYYY-MM-DD): ");
            String fechaInicioStr = scanner.nextLine();
            LocalDate fechaInicio;

            try {
                fechaInicio = LocalDate.parse(fechaInicioStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
                return;
            }

            System.out.print("Ingrese la fecha de fin (YYYY-MM-DD): ");
            String fechaFinStr = scanner.nextLine();
            LocalDate fechaFin;

            try {
                fechaFin = LocalDate.parse(fechaFinStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
                return;
            }

            // Crear la reserva
            String resultado = ReservaControlador.crearReservaApartamento(emailCliente, apartamentoSeleccionado.getNombre(), fechaInicio, fechaFin, cantidadPersonas);
            System.out.println(resultado);

        } catch (IOException e) {
            System.out.println("Error al procesar la reserva: " + e.getMessage());
        }
    }

    public static void menuReservarHotel(String emailCliente) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Paso 1: Ingresar la ciudad
            System.out.print("Ingrese la ciudad donde desea reservar un hotel: ");
            String ciudad = scanner.nextLine();

            // Obtener los hoteles disponibles en la ciudad
            List<Hotel> hotelesEnCiudad = HotelControlador.buscarHotelesPorCiudad(ciudad);

            if (hotelesEnCiudad.isEmpty()) {
                System.out.println("No se encontraron hoteles disponibles en la ciudad: " + ciudad);
                return;
            }

            // Mostrar los hoteles disponibles
            System.out.println("Hoteles disponibles en " + ciudad + ":");
            for (int i = 0; i < hotelesEnCiudad.size(); i++) {
                System.out.println((i + 1) + ". " + hotelesEnCiudad.get(i).getNombre() + " - Precio por noche: " + hotelesEnCiudad.get(i).getPrecioPorNoche() +
                        " - Calificación: " + hotelesEnCiudad.get(i).getCalificacion() + " - Actividades: " + String.join(", ", hotelesEnCiudad.get(i).getActividades()));
            }

            // Paso 2: Seleccionar un hotel
            System.out.print("Seleccione un hotel (número): ");
            int hotelIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consumir salto de línea

            if (hotelIndex < 0 || hotelIndex >= hotelesEnCiudad.size()) {
                System.out.println("Selección de hotel inválida.");
                return;
            }

            Hotel hotelSeleccionado = hotelesEnCiudad.get(hotelIndex);

            // Paso 3: Ingresar la cantidad de adultos y niños
            System.out.print("Ingrese la cantidad de adultos: ");
            int cantidadAdultos = scanner.nextInt();

            System.out.print("Ingrese la cantidad de niños: ");
            int cantidadNinos = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            int cantidadPersonas = cantidadAdultos + cantidadNinos;

            // Filtrar habitaciones disponibles
            int capacidadRequerida = cantidadAdultos + cantidadNinos;
            List<Habitacion> habitacionesDisponibles = HabitacionControlador.filtrarHabitaciones(hotelSeleccionado.getNombre(), capacidadRequerida);

            if (habitacionesDisponibles.isEmpty()) {
                System.out.println("No hay habitaciones disponibles que cumplan con los requisitos.");
                return;
            }

            // Mostrar habitaciones disponibles
            System.out.println("Habitaciones disponibles en el hotel " + hotelSeleccionado.getNombre() + ":");
            for (int i = 0; i < habitacionesDisponibles.size(); i++) {
                Habitacion habitacion = habitacionesDisponibles.get(i);
                System.out.println((i + 1) + ". Tipo: " + habitacion.getTipo() + ", Precio: " + habitacion.getPrecio() + ", Características: " + String.join(", ", habitacion.getCaracteristicas()));
            }

            // Paso 4: Seleccionar habitación
            System.out.print("Seleccione una habitación (número): ");
            int habitacionIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consumir salto de línea

            if (habitacionIndex < 0 || habitacionIndex >= habitacionesDisponibles.size()) {
                System.out.println("Selección de habitación inválida.");
                return;
            }

            Habitacion habitacionSeleccionada = habitacionesDisponibles.get(habitacionIndex);

            // Paso 5: Ingresar fechas de reserva
            System.out.print("Ingrese la fecha de inicio de la reserva (YYYY-MM-DD): ");
            String fechaInicioStr = scanner.nextLine();

            System.out.print("Ingrese la fecha de fin de la reserva (YYYY-MM-DD): ");
            String fechaFinStr = scanner.nextLine();

            LocalDate fechaInicio;
            LocalDate fechaFin;

            try {
                fechaInicio = LocalDate.parse(fechaInicioStr);
                fechaFin = LocalDate.parse(fechaFinStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
                return;
            }

            // Crear la reserva directamente con los datos ya seleccionados
            String resultado = ReservaControlador.crearReservaHotel(emailCliente, hotelSeleccionado, habitacionSeleccionada, cantidadPersonas, fechaInicio, fechaFin);
            System.out.println(resultado);

        } catch (Exception e) {
            System.out.println("Error al realizar la reserva: " + e.getMessage());
        }
    }

    public static void menuActualizarReserva() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Ingrese su correo electrónico para autenticación: ");
            String email = scanner.nextLine();

            System.out.print("Ingrese su fecha de nacimiento (YYYY-MM-DD): ");
            String fechaNacimientoStr = scanner.nextLine();
            LocalDate fechaNacimiento;

            try {
                fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
                return;
            }

            // Obtener reservas del cliente autenticado
            List<Reserva> reservas = ReservaControlador.obtenerReservasPorCliente(email, fechaNacimiento);
            if (reservas.isEmpty()) {
                System.out.println("No se encontraron reservas asociadas a este cliente.");
                return;
            }

            System.out.println("Reservas encontradas:");
            for (int i = 0; i < reservas.size(); i++) {
                System.out.println((i + 1) + ". " + reservas.get(i));
            }

            System.out.print("Seleccione la reserva que desea actualizar (número): ");
            int reservaIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consumir salto de línea

            if (reservaIndex < 0 || reservaIndex >= reservas.size()) {
                System.out.println("Selección inválida.");
                return;
            }

            Reserva reservaSeleccionada = reservas.get(reservaIndex);

            System.out.println("¿Qué desea hacer?");
            System.out.println("1. Cambiar de habitación");
            System.out.println("2. Cambiar de alojamiento");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            if (opcion == 1) {
                // Cambio de habitación
                System.out.println("Habitaciones disponibles en " + reservaSeleccionada.getAlojamiento().getNombre() + ":");
                List<Habitacion> habitacionesDisponibles = ReservaControlador.obtenerHabitacionesDisponibles(
                        reservaSeleccionada.getAlojamiento().getNombre(), 1 // Modificar capacidad según necesidad
                );

                if (habitacionesDisponibles.isEmpty()) {
                    System.out.println("No hay habitaciones disponibles para este alojamiento.");
                    return;
                }

                for (int i = 0; i < habitacionesDisponibles.size(); i++) {
                    Habitacion habitacion = habitacionesDisponibles.get(i);
                    System.out.println((i + 1) + ". Tipo: " + habitacion.getTipo() + ", Precio: " + habitacion.getPrecio() + ", Características: " + String.join(", ", habitacion.getCaracteristicas()));
                }

                System.out.print("Seleccione la nueva habitación (número): ");
                int habitacionIndex = scanner.nextInt() - 1;
                scanner.nextLine(); // Consumir salto de línea

                if (habitacionIndex < 0 || habitacionIndex >= habitacionesDisponibles.size()) {
                    System.out.println("Selección de habitación inválida.");
                    return;
                }

                Habitacion nuevaHabitacion = habitacionesDisponibles.get(habitacionIndex);
                String resultado = ReservaControlador.actualizarReserva(email, fechaNacimiento, reservaSeleccionada, false, reservaSeleccionada.getAlojamiento().getNombre(), nuevaHabitacion.getTipo());
                System.out.println(resultado);

            } else if (opcion == 2) {
                // Cambio de alojamiento
                System.out.println("Ingrese el nombre del nuevo alojamiento: ");
                String nuevoAlojamientoNombre = scanner.nextLine();

                // Eliminar reserva existente
                String eliminacionResultado = ReservaControlador.eliminarReserva(reservaSeleccionada);
                System.out.println(eliminacionResultado);

                // Redirigir a creación de nueva reserva
                System.out.println("Ahora puede proceder a crear una nueva reserva para el alojamiento: " + nuevoAlojamientoNombre);
                mostrarSubmenuAlojamientos();

            } else {
                System.out.println("Opción no válida.");
            }

        } catch (Exception e) {
            System.out.println("Error al actualizar la reserva: " + e.getMessage());
        }
    }

}
