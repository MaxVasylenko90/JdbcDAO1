package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection())
        {
            // remove this
            try {
                try (Statement st = conn.createStatement()) {
                    st.execute("DROP TABLE IF EXISTS Clients");
                    //st.execute("CREATE TABLE Clients (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL, age INT)");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            ClientDAOImpl2 dao = new ClientDAOImpl2(conn, "Clients");
            dao.createTable(Client.class);

            Client a = new Client("test", 1);
            Client b = new Client("aaa", 2);
            Client c = new Client("bbb", 3);
            dao.add(a);
            dao.add(b);
            dao.add(c);

            List<Client> list = dao.getAll(Client.class);
            for (Client cli : list)
                System.out.println(cli);
            System.out.println();

            list.get(0).setAge(55);
            dao.update(list.get(0));
            for (Client cli : list)
                System.out.println(cli);
            System.out.println();

            List<Client> list4 = dao.getAll(Client.class, "name");
            for (Client cli : list4)
                System.out.println(cli);
            List<Client> list2 = dao.getAll(Client.class, "name", "age");
            for (Client cli : list2)
                System.out.println(cli);
            List<Client> list3 = dao.getAll(Client.class, "age");
            for (Client cli : list3)
                System.out.println(cli);
//
//
//            dao.delete(list.get(0));
        }
    }
}
