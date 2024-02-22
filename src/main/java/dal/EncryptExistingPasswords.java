package dal;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EncryptExistingPasswords extends DBContext{

    PreparedStatement ps = null;
    ResultSet rs = null;

    public void encryptPassWord(){
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement("SELECT userID, userPass FROM [User]");
            rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("userID");
                String plainPassword = rs.getString("userPass");
                // Mã hóa mật khẩu
                String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
                // Cập nhật lại cơ sở dữ liệu với mật khẩu đã mã hóa
                PreparedStatement updatePs = conn.prepareStatement("UPDATE [User] SET userPass = ? WHERE userID = ?");
                updatePs.setString(1, hashedPassword);
                updatePs.setInt(2, userId);
                updatePs.executeUpdate();
            }
            System.out.println("Mật khẩu đã được cập nhật thành công.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EncryptExistingPasswords a = new EncryptExistingPasswords();
        a.encryptPassWord();
    }
}
