package modelo;

public class CuentaAhorros extends Cuenta {

    public CuentaAhorros(int numero, String titular, Fecha fechaApertura, double saldo) {
        super(numero, titular, fechaApertura, saldo);
    }

    public CuentaAhorros(int numero, String titular, Fecha fechaApertura) {
        super(numero, titular, fechaApertura);
    }

    // Solo permite retirar si hay saldo suficiente
    @Override
    public boolean retirar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    @Override
    public String imprimir() {
        return "[CUENTA DE AHORROS]\n" + super.imprimir();
    }
}