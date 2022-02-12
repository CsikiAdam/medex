package com.trashcan.database;

import com.trashcan.account.User;

import javax.xml.transform.Result;
import java.sql.*;

/**
 * <h1>DB</h1>
 * <p>DB contains, creates and managers database operations</p>
 */
public class DB {
    private String link = "jdbc:mysql://localhost:3306/magmed";
    private String id = "medexUser";
    private String pass = "medexPass2001";
    public Connection dataBase;

    public DB() {
        try {
            this.dataBase = DriverManager.getConnection(this.link, this.id, this.pass);
            Statement statement = this.dataBase.createStatement();
            statement.execute("use magmed");
            statement.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DB(int admid) {
        try {
            this.dataBase = DriverManager.getConnection(this.link, this.id, this.pass);
            Statement statement = this.dataBase.createStatement();
            statement.execute("use magmed");
            if(this.getAdminPrivileges(admid)) {
                this.dataBase = DriverManager.getConnection(this.link, "medexAdm", "adminpassword2001");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h1>getAdminPrivileges()</h1>
     * <p>Returns a boolean if the current user has a valid admin id</p>
     * @param admid
     * @return
     * @throws SQLException
     */
    public boolean getAdminPrivileges(int admid) throws SQLException {
        PreparedStatement preparedStatement = dataBase.prepareStatement("select * from magmed.adm where admid=?");
        preparedStatement.setString(1, String.valueOf(admid));

        preparedStatement.executeQuery();

        return preparedStatement.getResultSet().next();
    }

    /**
     * <h1>registerCheck()</h1>
     * <p>Returns a boolean if the account being registered is valid</p>
     * @param name
     * @param mail
     * @return
     * @throws SQLException
     */
    public boolean registerCheck(String name, String mail) throws SQLException {
        boolean aux = false;

        if(!mail.contains("@")) {
            return false;
        }

        String[] arrayMail = mail.split("@");
        PreparedStatement preparedStatement = this.dataBase.prepareStatement("select uname, accname from magmed.usert where uname = ? and accname = ? and mdomain = ?");

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, arrayMail[0]);
        preparedStatement.setString(3, arrayMail[1]);
        preparedStatement.executeQuery();

        aux = preparedStatement.getResultSet().next();

        preparedStatement.close();

        return aux;
    }

    /**
     * <h1>setRegisteredUser()</h1>
     * <p>Sets the registered user in the database</p>
     * @param name
     * @param mail
     * @param pass
     * @return
     * @throws SQLException
     */
    public User setRegisteredUser(String name, String mail, String pass) throws SQLException {
        String[] arrayMail = mail.split("@");

        PreparedStatement preparedStatement = this.dataBase.prepareStatement("insert into magmed.usert (uname, accname, mdomain, upass) values (?, ?, ?, ?)");

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, arrayMail[0]);
        preparedStatement.setString(3, arrayMail[1]);
        preparedStatement.setString(4, pass);
        preparedStatement.executeUpdate();

        preparedStatement = this.dataBase.prepareStatement("select * from magmed.usert where uname = ? and accname = ? and mdomain = ? and upass = ?");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, arrayMail[0]);
        preparedStatement.setString(3, arrayMail[1]);
        preparedStatement.setString(4, pass);
        preparedStatement.executeQuery();

        ResultSet resultSet = preparedStatement.getResultSet();

        User user = new User();

        while(resultSet.next()) {
            user = new User(resultSet.getInt("id"), resultSet.getString("uname"), resultSet.getString("accname")+"@"+resultSet.getString("mdomain"));
        }

        resultSet.close();
        preparedStatement.close();

        System.out.println(user);

        return user;
    }

    /**
     * <h1>checkUser()</h1>
     * <p>Checks if the given User and it's Password is in database</p>
     * @param user
     * @param pass
     * @return
     * @throws SQLException
     */
    public boolean checkUser(User user, String pass) throws SQLException {
        boolean aux = false;

        PreparedStatement preparedStatement = this.dataBase.prepareStatement("select id, uname, accname, mdomain, upass from magmed.usert where id=? and uname=? and accname=? and mdomain=? and upass=?");

        preparedStatement.setInt(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getMail(0));
        preparedStatement.setString(4, user.getMail(1));
        preparedStatement.setString(5, pass);
        preparedStatement.executeQuery();

        aux = preparedStatement.getResultSet().next();

        preparedStatement.close();

        return aux;
    }

    /**
     * <h1>loginCheck()</h1>
     * <p>Returns a boolean if the account login was succesful or not</p>
     * @param mail
     * @param pass
     * @return
     * @throws SQLException
     */
    public boolean loginCheck(String mail, String pass) throws SQLException {
        boolean aux = false;

        if(!mail.contains("@")) {
            return false;
        }

        String[] mailArray = mail.split("@");
        PreparedStatement preparedStatement = this.dataBase.prepareStatement("select uname, accname from magmed.usert where accname = ? and mdomain = ? and upass = ?");

        preparedStatement.setString(1, mailArray[0]);
        preparedStatement.setString(2, mailArray[1]);
        preparedStatement.setString(3, pass);
        preparedStatement.executeQuery();

        aux = preparedStatement.getResultSet().next();

        preparedStatement.close();

        return aux;
    }

    /**
     * <h1>getUser()</h1>
     * <p>Returns the User from the database</p>
     * @param mail
     * @param pass
     * @return
     * @throws SQLException
     */
    public User getUser(String mail, String pass) throws SQLException {
        String[] arrayMail = mail.split("@");
        PreparedStatement preparedStatement = this.dataBase.prepareStatement("select * from magmed.usert where accname=? and mdomain=? and upass=?");

        preparedStatement.setString(1, arrayMail[0]);
        preparedStatement.setString(2, arrayMail[1]);
        preparedStatement.setString(3, pass);
        preparedStatement.executeQuery();

        ResultSet resultSet = preparedStatement.getResultSet();

        User user = new User();

        while(resultSet.next()) {
            user = new User(resultSet.getInt("id"), resultSet.getString("uname"), resultSet.getString("accname") + "@" + resultSet.getString("mdomain"), resultSet.getInt("admid"));
        }

        resultSet.close();
        preparedStatement.close();

        return user;
    }

    public void createTables() throws SQLException {
        DB db = new DB();
        Statement statement = db.dataBase.createStatement();
        statement.execute("use magmed");

        statement.execute("create table if not exists adm" +
                "(" +
                "    id    bigint default 0 not null," +
                "    admid bigint default 0 not null," +
                "    constraint adm_id_uindex" +
                "        unique (id)" +
                ");");

        statement.execute("create table if not exists usert" +
                "(" +
                "    id      bigint                 not null," +
                "    uname   varchar(32) default '' not null," +
                "    accname varchar(32) default '' not null," +
                "    mdomain varchar(32) default '' not null," +
                "    upass   varchar(32) default '' not null," +
                "    admid   bigint                 null," +
                "    constraint usert_id_uindex" +
                "        unique (id)," +
                "    constraint usert_adm_id_fk" +
                "        foreign key (id) references magmed.adm (id)" +
                ");");

        statement.execute("create table if not exists meds" +
                "(" +
                "    medName     varchar(30) default '' not null" +
                "        primary key," +
                "    firm        varchar(45)            null," +
                "    substActv   varchar(45)            null," +
                "    medType     varchar(10) default '' not null," +
                "    madeFor     varchar(40) default '' null," +
                "    adm         varchar(45)            null," +
                "    dosePerDay  int                    null," +
                "    description text                   null" +
                ");" +
                "create index meds_firm_index" +
                "    on meds (firm);");

        statement.execute("create table if not exists popularity" +
                "(" +
                "    medName varchar(45) default '' not null" +
                "        primary key," +
                "    count   int         default 0  not null," +
                "constraint popularity_meds_medName_fk\n" +
                "        foreign key (medName) references magmed.meds (medName)" +
                ");");

    }
}
