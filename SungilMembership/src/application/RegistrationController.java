package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistrationController {
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField useridTextField;
	@FXML
	private TextField hakTextField;
	@FXML
	private TextField banTextField;
	@FXML
	private TextField bunTextField;
	@FXML
	private PasswordField password1PasswordField;
	@FXML
	private PasswordField password2PasswordField;
	@FXML
	private Label registerMessageLabel;
	@FXML
	private Button submitButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button closeButton;
	
	@FXML
	void submitButtonOnAction() {
		// �Է��� ���� üũ�մϴ�
		boolean checkEmpty = isCheckEmpty();
		boolean checkDuplicatedId = isCheckDuplicatedId();
		boolean checkPasswordSame = isCheckPasswordSame();
		boolean checkNumbers = isCheckNumbers();
		
		if(
				checkEmpty == true
				&& checkDuplicatedId == true
				&& checkPasswordSame == true
				&& checkNumbers == true
				) { // ��� üũ�� ������� ��� �����ͺ��̽��� �����Ѵ�
			registerMessageLabel.setText("ȸ�� ������ �����ͺ��̽��� �����մϴ�...");
			
			DBConnection connNow = new DBConnection();
			Connection conn = connNow.getConnection();
			
			String sql = "INSERT INTO member_accounts "
					+ "(idx, user_name, user_id, user_password, user_hak, user_ban, user_bun) "
					+ "VALUES "
					+ "(member_idx_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
			
			try {
				// �����ͺ��̽��� ���� �����ϴ� SQL�� ����
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, usernameTextField.getText());
				pstmt.setString(2, useridTextField.getText());
				pstmt.setString(3, password1PasswordField.getText());
				pstmt.setString(4, hakTextField.getText());
				pstmt.setString(5, banTextField.getText());
				pstmt.setString(6, bunTextField.getText());
				pstmt.executeUpdate();
				
				pstmt.close();
				conn.close();
				
				registerMessageLabel.setText("�����ͺ��̽��� ȸ������ �Է� �Ϸ�!");
				usernameTextField.setText("");
				useridTextField.setText("");
				password1PasswordField.setText("");
				password2PasswordField.setText("");
				hakTextField.setText("");
				banTextField.setText("");
				bunTextField.setText("");
			}catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			if(checkEmpty == false) {
				registerMessageLabel.setText("��� ������ �Է����ּ���!");
			} else if(checkDuplicatedId == false) {
				registerMessageLabel.setText("���̵� �ߺ��˴ϴ�. �ٸ� ���̵� �Է����ּ���!");
			} else if(checkPasswordSame == false) {
				registerMessageLabel.setText("�Է��� ��ȣ�� �������� �ʽ��ϴ�. �ٽ� Ȯ�����ּ���!");
			} else if(checkNumbers == false) {
				registerMessageLabel.setText("�г�, ��, ��ȣ�� �߸� �Է��߽��ϴ�!");
			}
		}
	}
	
	@FXML
	void cancelButtonOnAction(ActionEvent e) {
		usernameTextField.setText("");
		useridTextField.setText("");
		hakTextField.setText("");
		banTextField.setText("");
		bunTextField.setText("");
		password1PasswordField.setText("");
		password2PasswordField.setText("");
	}
	
	@FXML
	void closeButtonOnAction(ActionEvent e) {
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
	
	boolean isCheckDuplicatedId() { // �����ͺ��̽����� ���̵� �ߺ��� üũ�Ѵ�
		boolean result = true;
		DBConnection connNow = new DBConnection();
		Connection conn = connNow.getConnection();
		
		String sql = "SELECT COUNT(1) FROM member_accounts "
				+ " WHERE user_id='" + useridTextField.getText() + "'";
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				if(rs.getInt(1) == 1) {
					result = false;
				}
			}
			
			stmt.close();
			conn.close();
		} catch(Exception e) {
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
}
