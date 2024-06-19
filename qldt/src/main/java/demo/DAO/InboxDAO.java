package demo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import demo.Data.DataBase;
import demo.Entity.Inbox;
import demo.Entity.Lecturer;
import demo.Entity.Student;

public class InboxDAO {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void addInbox(int senderID, int receiverID, String tiltle, String body, String classSectionID){
        try{
            connect = DataBase.connecDb();
            String sql = "INSERT INTO Email (senderID, receiverID, tiltle, body, classSectionID) VALUES (?, ?, ?, ?, ?)";
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, senderID);
            prepare.setInt(2, receiverID);
            prepare.setString(3, tiltle);
            prepare.setString(4, body);
            prepare.setString(5, classSectionID);

            prepare.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteInbox(int emailID){
        try{
            connect = DataBase.connecDb();
            String sql = "DELETE FROM Email WHERE emailID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, emailID);

            prepare.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getEmailID(int receiverID, String tiltle){
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT emailID FROM Email WHERE receiverID = ? AND tiltle = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, receiverID);
            prepare.setString(2, tiltle);

            result = prepare.executeQuery();
            while(result.next()){
                return result.getString("emailID");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void deleteInbox(int receiverID, String tiltle){
        String emailID = getEmailID(receiverID, tiltle);
        try{
            connect = DataBase.connecDb();
            String sql = "DELETE FROM Email WHERE emailID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, emailID);

            prepare.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public String getSenderName(int senderID){
        String type = new AccountDAO().checkTypeAccount(senderID);

        if(type.equals("Student")){
            Student student = (Student)(new AccountDAO().getInfoPerson(senderID));
            return student.getFirstName() + " " + student.getLastName();
        }else{
            Lecturer lecturer = (Lecturer)(new AccountDAO().getInfoPerson(senderID));
            return lecturer.getFirstName() + " " + lecturer.getLastName();
        }
    }

    public String getReceiverName(int receiverID){
        String type = new AccountDAO().checkTypeAccount(receiverID);

        if(type.equals("Student")){
            Student student = (Student)(new AccountDAO().getInfoPerson(receiverID));
            return student.getFirstName() + " " + student.getLastName();
        }else{
            Lecturer lecturer = (Lecturer)(new AccountDAO().getInfoPerson(receiverID));
            return lecturer.getFirstName() + " " + lecturer.getLastName();
        }
    }

    public List<Inbox> getReceivedInboxs(int receiverID){
        List<Inbox> list = new ArrayList<Inbox>();
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM Email WHERE receiverID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, receiverID);

            result = prepare.executeQuery();
            while(result.next()){
                Inbox inbox = new Inbox(result.getInt("emailID"), result.getInt("senderID"), result.getInt("receiverID"), result.getString("tiltle"), result.getString("body"), result.getString("classSectionID"));
                list.add(inbox);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<Inbox> getSentInboxs(int senderID){
        List<Inbox> list = new ArrayList<Inbox>();
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM Email WHERE senderID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, senderID);

            result = prepare.executeQuery();
            while(result.next()){
                Inbox inbox = new Inbox(result.getInt("emailID"), result.getInt("senderID"), result.getInt("receiverID"), result.getString("tiltle"), result.getString("body"), result.getString("classSectionID"));
                list.add(inbox);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public Inbox getReceiveInbox(int receiverID, String tiltle){
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM Email WHERE receiverID = ? AND tiltle = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, receiverID);
            prepare.setString(2, tiltle);

            result = prepare.executeQuery();
            while(result.next()){
                Inbox inbox = new Inbox(result.getInt("emailID"), result.getInt("senderID"), result.getInt("receiverID"), result.getString("tiltle"), result.getString("body"), result.getString("classSectionID"));
                return inbox;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Inbox getSendInbox(int sentID, String tiltle){
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM Email WHERE senderID = ? AND tiltle = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, sentID);
            prepare.setString(2, tiltle);

            result = prepare.executeQuery();
            while(result.next()){
                Inbox inbox = new Inbox(result.getInt("emailID"), result.getInt("senderID"), result.getInt("receiverID"), result.getString("tiltle"), result.getString("body"), result.getString("classSectionID"));
                return inbox;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int getNumberNotification(int receiverID){
        int count = 0;
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT COUNT(*) FROM Email WHERE receiverID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, receiverID);

            result = prepare.executeQuery();
            while(result.next()){
                count = result.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }
}
