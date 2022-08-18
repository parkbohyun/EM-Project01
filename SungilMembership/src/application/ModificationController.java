package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModificationController implements Initializable {
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField useridTextField;
	@FXML
	private PasswordField password1PasswordField;
	@FXML
	private PasswordField password2PasswordField;
	@FXML
	private TextField hakTextField;
	@FXML
	private TextField banTextField;
	@FXML
	private TextField bunTextField;
	@FXML
	private Label modifyMessageLabel;
	@FXML
	private Button modifyButton;
	@FXML
	private Button resetButton;
	@FXML
	private Button closeButton;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		readMemberData();	
	}	
	
	@FXML
	void modifyButtonOnAction() {
		// �Է��� ���� üũ�մϴ�
		boolean checkEmpty = isCheckEmpty();
		boolean checkPasswordSame = isCheckPasswordSame();
		boolean checkNumbers = isCheckNumbers();
		
		if(
				checkEmpty == true
				&& checkPasswordSame == true
				&& checkNumbers == true
				) { // ��� üũ�� ������� ��� �����ͺ��̽��� �����Ѵ�
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("ȸ������ ���� ���");
			alert.setHeaderText("ȸ������ ����");
			alert.setContentText(useridTextField.getText() + " ���� ȸ�������� �����Ͻðڽ��ϱ�?");
			Optional<ButtonType> alertResult = alert.showAndWait();
			
			if(alertResult.get() == ButtonType.OK) {
				modifyMessageLabel.setText("ȸ�� ������ �����մϴ�...");
				
				DBConnection connNow = new DBConnection();
				Connection conn = connNow.getConnection();
				
				String sql = "UPDATE member_accounts "
						+ "SET "
						+ "user_name=?, "
						+ "user_password=?, "
						+ "user_hak=?, "
						+ "user_ban=?, "
						+ "user_bun=? "
						+ "WHERE user_id=?";
				
				try {
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, usernameTextField.getText());
					pstmt.setString(2, password1PasswordField.getText());
					pstmt.setString(3, hakTextField.getText());
					pstmt.setString(4, banTextField.getText());
					pstmt.setString(5, bunTextField.getText());
					pstmt.setString(6, useridTextField.getText());
					pstmt.executeUpdate();
					
					pstmt.close();
					conn.close();
					
					modifyMessageLabel.setText("ȸ�� ���� ������ �Ϸ��߽��ϴ�!");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if(checkEmpty == false) {
				modifyMessageLabel.setText("��� ������ �Է����ּ���!");
			} else if(checkPasswordSame == false) {
				modifyMessageLabel.setText("�Է��� ��ȣ�� �������� �ʽ��ϴ�. �ٽ� Ȯ�����ּ���!");
			} else if(checkNumbers == false) {
				modifyMessageLabel.setText("�г�, ��, ��ȣ�� �߸� �Է��߽��ϴ�!");
			}
		}
	}
	
	@FXML
	void resetButtonOnAction() {
		readMemberData();
	}
	
	@FXML
	void closeButtonOnAction() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
	
	boolean isCheckEmpty() { // ������ ������ üũ�Ѵ�
		boolean result = false;
		if(
			usernameTextField.getText().isEmpty() == false
			&& useridTextField.getText().isEmpty() == false
			&& password1PasswordField.getText().isEmpty() == false
			&& password2PasswordField.getText().isEmpty() == false
			&& hakTextField.getText().isEmpty() == false
			&& banTextField.getText().isEmpty() == false
			&& bunTextField.getText().isEmpty() == false
		) { // ������ ���ٸ�..
			result = true;
		}
		return result;
	}
		
	boolean isCheckPasswordSame() {
		boolean result = false;
		if(
			(password1PasswordField.getText().isEmpty() == false
			  && password2PasswordField.getText().isEmpty() == false)
			&&
			(password1PasswordField.getText().equals(password2PasswordField.getText()))
		) {
			result = true;
		}
		return result;
	}
	
	boolean isCheckNumbers() {
		boolean result = false;
		int hak = 0;
		int ban = 0;
		int bun = 0;
		
		try {
			hak = Integer.parseInt(hakTextField.getText());
			ban = Integer.parseInt(banTextField.getText());
			bun = Integer.parseInt(bunTextField.getText());
			
			if(
				(hak >= 1 && hak <= 3)
				&& (ban >= 1 && ban <= 15)
				&& (bun >= 1 && bun <= 31)
			) { // �г�, ��, ��ȣ�� ���ڸ� �˻��ؼ� ����� ���
				result = true;
			}
			
			return result;
		} catch(NumberFormatException e) {
			result = false;
			return result;
		}
	}
	
	void readMemberData() {
		if(
			Main.global_user_id.isEmpty() == false
			&&
			Main.global_user_id.length() > 0
		) {
			DBConnection connNow = new DBConnection();
			Connection conn = connNow.getConnection();
			
			String sql = "SELECT * FROM member_accounts "
					+ "WHERE user_id='" + Main.global_user_id + "'";
			
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next() ) {
					usernameTextField.setText(rs.getString("user_name"));
					useridTextField.setText(rs.getString("user_id"));
					password1PasswordField.setText(rs.getString("user_password"));
					password2PasswordField.setText(rs.getString("user_password"));
					hakTextField.setText(rs.getString("user_hak"));
					banTextField.setText(rs.getString("user_ban"));
					bunTextField.setText(rs.getString("user_bun"));
				}
				
				stmt.close();
				rs.close();
				conn.close();
			} catch(Exception e) {}
		}
	}
}
