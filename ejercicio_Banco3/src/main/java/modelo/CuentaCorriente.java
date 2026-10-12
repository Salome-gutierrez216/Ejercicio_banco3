package modelo;

public class CuentaCorriente extends Cuenta {
    private double cupoSobregiro;

    public CuentaCorriente(int numero, String titular, Fecha fechaApertura,
                           double saldo, double cupoSobregiro) {
        super(numero, titular, fechaApertura, saldo);
        this.cupoSobregiro = cupoSobregiro;
    }

    public CuentaCorriente(int numero, String titular, Fecha fechaApertura,
                           double cupoSobregiro) {
        super(numero, titular, fechaApertura);
        this.cupoSobregiro = cupoSobregiro;
    }

    // Permite retirar hasta saldo + cupo de sobregiro (el saldo puede quedar negativo)
    @Override
    public boolean retirar(double valor) {
        if (valor <= 0) {
            return false;
        }
        if (valor <= saldo + cupoSobregiro) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    @Override
    public String imprimir() {
        return "[CUENTA CORRIENTE]\n" + super.imprimir()
             + "\nCupo de sobregiro: $" + cupoSobregiro;
    }

    public double getCupoSobregiro() { return cupoSobregiro; }
}