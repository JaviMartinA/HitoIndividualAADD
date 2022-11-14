package org;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
     static Conexion con1;
    public static void main(String[] args) throws SQLException, IOException {
        Scanner sc1=new Scanner(System.in);
        int respuesta;
        System.out.println("Elige 1.-MySQL o 2.-PostgreSQL");
        respuesta= sc1.nextInt();
        while(respuesta!=1 && respuesta!=2){
            System.out.println("Elige 1.-MySQL o 2.-PostgreSQL");
            respuesta= sc1.nextInt();
        }
        if(respuesta==1){
            con1=new Conexion("jdbc:mysql://localhost:3306/hitoindividual1","root","curso");
            con1.getConnection();
            System.out.println("La conexion ha sido realizada a MySQL");
        }else {
            con1 = new Conexion("jdbc:postgresql://localhost:5432/hitoindividual1", "postgres", "curso");
            con1.getConnection();
            System.out.println("La conexion ha sido realizada a PostgreSQL");
        }
        do{
            System.out.println("Que desea, 1.-insertar, 2.-ver, 3.-modificar, 4.-borrar, 5.-Exportar resultados, 6.- Importar datos, 7.- Salir");
            respuesta= sc1.nextInt();
            switch (respuesta){
                case 1 -> con1.insert();
                case 2 -> con1.show();
                case 3 -> con1.update();
                case 4 -> con1.delete();
                case 5 -> con1.exportarCSV();
                case 6 -> con1.importarCSV();
                case 7 -> {
                    System.out.println("Cerrando la base de datos");System.exit(0);
                }
                default -> System.out.println("Introduzca números del 1 al 7");
            }
        }while(true);
    }
}