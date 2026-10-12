package controlador;

import java.util.ArrayList;
import modelo.Cuenta;
import modelo.CuentaAhorros;
import modelo.CuentaCorriente;
import modelo.Fecha;

public class ControladorBanco {
    private ArrayList<Cuenta> cuentas = new ArrayList<>();

    // Crea una cuenta de ahorros. Devuelve false si el número ya existe
    public boolean crearCuentaAhorros(int numero, String titular, Fecha fecha, double saldo) {
        if (buscar(numero) != null) {
            return false;
        }
        cuentas.add(new CuentaAhorros(numero, titular, fecha, saldo));
        return true;
    }

    // Crea una cuenta corriente. Devuelve false si el número ya existe
    public boolean crearCuentaCorriente(int numero, String titular, Fecha fecha,
                                        double saldo, double cupoSobregiro) {
        if (buscar(numero) != null) {
            return false;
        }
        cuentas.add(new CuentaCorriente(numero, titular, fecha, saldo, cupoSobregiro));
        return true;
    }

    // Busca una cuenta por su número. Devuelve null si no existe
    public Cuenta buscar(int numero) {
        for (Cuenta c : cuentas) {
            if (c.getNumero() == numero) {
                return c;
            }
        }
        return null;
    }

    public boolean consignar(int numero, double valor) {
        Cuenta c = buscar(numero);
        if (c == null || valor <= 0) {
            return false;
        }
        c.consignar(valor);
        return true;
    }

    // Aquí actúa el polimorfismo: cada tipo de cuenta aplica su propia regla
    public boolean retirar(int numero, double valor) {
        Cuenta c = buscar(numero);
        if (c == null) {
            return false;
        }
        return c.retirar(valor);
    }

    // soloSaldo = true muestra solo el saldo; false muestra todos los datos
    public String consultar(int numero, boolean soloSaldo) {
        Cuenta c = buscar(numero);
        if (c == null) {
            return "Cuenta no encontrada";
        }
        return c.imprimir(soloSaldo);
    }
}