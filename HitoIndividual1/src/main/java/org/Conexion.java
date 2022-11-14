package org;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Conexion {
        public String driver;
        public String usuario;
        public String pass;
        public Connection con;
        Scanner sc1=new Scanner(System.in);
        PreparedStatement st;

        public Conexion(String driver, String usuario, String pass) {
            this.driver = driver;
            this.usuario = usuario;
            this.pass = pass;
        }
        public void getConnection() throws SQLException {
            con= DriverManager.getConnection(driver, usuario, pass);
        }
        public void insert() throws SQLException {
            System.out.println("Quieres insertar marca o coche");
            String respuesta= sc1.next();
            if(respuesta.equalsIgnoreCase("marca")){
                System.out.println("id de la marca");
                int id=sc1.nextInt();
                System.out.println("nombre de la marca");
                String nombre=sc1.next();
                System.out.println("beneficios de la marca");
                double benef=sc1.nextDouble();
                System.out.println("procedencia de la marca");
                String procedencia=sc1.next();
                st=con.prepareStatement("insert into marca values (?,?,?,?);");
                st.setInt(1,id);
                st.setString(2,nombre);
                st.setDouble(3,benef);
                st.setString(4,procedencia);
                st.executeUpdate();
                System.out.println("Marca guardada correctamente");
            }else if(respuesta.equalsIgnoreCase("coche")){
                System.out.println("id del coche");
                int id=sc1.nextInt();
                System.out.println("modelo del coche");
                String modelo=sc1.next();
                System.out.println("color del coche");
                String color=sc1.next();
                System.out.println("combustible del coche");
                String combus=sc1.next();
                System.out.println("valoracion del coche");
                int valoracion=sc1.nextInt();
                System.out.println("precio del coche");
                double precio=sc1.nextDouble();
                System.out.println("id de la marca del coche");
                int id_marca=sc1.nextInt();
                st=con.prepareStatement("insert into coche values (?,?,?,?,?,?,?);");
                st.setInt(1,id);
                st.setString(2,modelo);
                st.setString(3,color);
                st.setString(4,combus);
                st.setInt(5,valoracion);
                st.setDouble(6,precio);
                st.setInt(7,id_marca);
                st.executeUpdate();
                System.out.println("Coche guardado correctamente");
            }else{
                System.out.println("Escriba bien marca o coche");
            }

        }
        public void show() throws SQLException {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from marca;");
            while(rs.next()){
                String id=rs.getString(1);
                String nombre=rs.getString(2);
                String benef=rs.getString(3);
                String procedencia=rs.getString(4);
                System.out.println("ID de marca: "+id+" nombre: "+nombre+" beneficios de la marca: "+benef+" procedencia: "+procedencia);
            }
            st= con.createStatement();
            rs=st.executeQuery("select * from coche;");
            while(rs.next()){
                String id=rs.getString(1);
                String modelo=rs.getString(2);
                String color=rs.getString(3);
                String combustible=rs.getString(4);
                String valoracion=rs.getString(5);
                String precio=rs.getString(6);
                String id_marca=rs.getString(7);
                System.out.println("ID del coche: "+id+" modelo: "+modelo+" color: "+color+" combustible "+combustible+" valoracion: "+valoracion+" precio: "+precio+" ID marca del coche: "+id_marca);
            }
        }
        public void delete() throws SQLException {
            System.out.println("Indique el modelo del coche que quiere eliminar");
            String borrar=sc1.next();
            st= con.prepareStatement("delete from coche where modelo= ?;");
            st.setString(1,borrar);
            st.executeUpdate();
            System.out.println("Eliminado el coche "+borrar+" correctamente");
        }
        public void update() throws SQLException {
            int id=0;
            System.out.println("Dime el modelo del coche a actualizar");
            String modelo=sc1.next();
            st=con.prepareStatement("select id_coche from coche where modelo=?");
            st.setString(1,modelo);
            ResultSet rs= st.executeQuery();
            while(rs.next()){
                id=rs.getInt(1);
            }
            System.out.println("Id del coche "+id);
            System.out.println("¿Que quieres modificar: color,tipo de motor, valoracion, o precio?");
            String respuesta= sc1.next();
            if(respuesta.equalsIgnoreCase("color")){
                System.out.println("Dime el color nuevo");
                String color=sc1.next();
                st=con.prepareStatement("Update coche set color=? where id_coche=?;");
                st.setString(1,color);
                st.setInt(2,id);
                st.executeUpdate();
                System.out.println("Cambios guardados correctamente");
            }else if(respuesta.equalsIgnoreCase("motor")){
                System.out.println("Dime el tipo de motor");
                String motor=sc1.next();
                st=con.prepareStatement("Update coche set combustible=? where id_coche=?;");
                st.setString(1,motor);
                st.setInt(2,id);
                st.executeUpdate();
                System.out.println("Cambios guardados correctamente");
            }else if(respuesta.equalsIgnoreCase("valoracion")){
                System.out.println("Dime la nueva valoracion");
                int valo=sc1.nextInt();
                st=con.prepareStatement("Update coche set valoracion=? where id_coche=?;");
                st.setInt(1,valo);
                st.setInt(2,id);
                st.executeUpdate();
                System.out.println("Cambios guardados correctamente");
            }else if(respuesta.equalsIgnoreCase("precio")){
                System.out.println("Dime el nuevo precio");
                double precio=sc1.nextDouble();
                st=con.prepareStatement("Update coche set precio=? where id_coche=?;");
                st.setDouble(1,precio);
                st.setInt(2,id);
                st.executeUpdate();
                System.out.println("Cambios guardados correctamente");
            }else{
                System.out.println("Indique que quiere modificar correctamente");
            }
        }
        public void exportarCSV() throws FileNotFoundException, SQLException {
            PrintWriter pw=new PrintWriter(new File("exportar.csv"));
            StringBuilder sb=new StringBuilder();
            ResultSet rs= (ResultSet) con.prepareStatement("select * from marca");
            while(rs.next()){
                sb.append(rs.getInt(1));
                sb.append(";");
                sb.append(rs.getInt(2));
                sb.append(";");
                sb.append(rs.getInt(3));
                sb.append(";");
                sb.append(rs.getInt(4));
                sb.append(";");
                sb.append("\r\n");
            }
            ResultSet rs2=(ResultSet) con.prepareStatement("select * from coche");
            while(rs2.next()){
                sb.append(rs.getInt(1));
                sb.append(";");
                sb.append(rs.getInt(2));
                sb.append(";");
                sb.append(rs.getInt(3));
                sb.append(";");
                sb.append(rs.getInt(4));
                sb.append(";");
                sb.append(rs.getInt(5));
                sb.append(";");
                sb.append(rs.getInt(6));
                sb.append(";");
                sb.append(rs.getInt(7));
                sb.append(";");
                sb.append("\r\n");
            }
            pw.write(sb.toString());
            pw.close();
        }
        public void importarCSV() throws SQLException, IOException {
            con.setAutoCommit(false);
            String path="importar.csv";
            int batchSize=20;
            PreparedStatement st=con.prepareStatement("insert into marca values(?,?,?,?);");
            BufferedReader lector=new BufferedReader(new FileReader(path));
            String lineText=null;
            int contador=0;
            while((lineText=lector.readLine())!=null){
                String[] datos=lineText.split(";");
                String idmarca=datos[0];
                String nombre=datos[1];
                String benec=datos[2];
                String proc=datos[3];
                int id_marca=Integer.parseInt(idmarca);
                double benecif=Double.parseDouble(benec);
                st.setInt(1,id_marca);
                st.setString(2,nombre);
                st.setDouble(3,benecif);
                st.setString(4,proc);
                st.addBatch();
                if(contador%batchSize==0){
                    st.executeBatch();
                }
            }
            lector.close();
            st.executeBatch();
            con.commit();
        }
}
