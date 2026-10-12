package modelo;

public abstract class Cuenta {
    protected int numero;
    protected String titular;
    protected Fecha fechaApertura;
    protected double saldo;

    // Constructor 1: con saldo inicial
    public Cuenta(int numero, String titular, Fecha fechaApertura, double saldo) {
        this.numero = numero;
        this.titular = titular;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
    }

    // Constructor 2 (SOBRECARGA): sin saldo inicial
    public Cuenta(int numero, String titular, Fecha fechaApertura) {
        this(numero, titular, fechaApertura, 0);
    }

    public void consignar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }

    // Cada subclase lo implementa a su manera (POLIMORFISMO)
    public abstract boolean retirar(double valor);

    // Impresión 1: todos los datos
    public String imprimir() {
        return "Cuenta N° " + numero
             + "\nTitular: " + titular
             + "\nFecha de apertura: " + fechaApertura
             + "\nSaldo: $" + saldo;
    }

    // Impresión 2 (SOBRECARGA): solo el saldo o todos los datos
    public String imprimir(boolean soloSaldo) {
        if (soloSaldo) {
            return "Saldo: $" + saldo;
        }
        return imprimir();
    }

    public int getNumero()     { return numero; }
    public String getTitular() { return titular; }
    public double getSaldo()   { return saldo; }
}