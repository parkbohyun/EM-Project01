package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MembershipController implements Initializable {
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
	private Label membershipMessageLabel;
	@FXML
	private Button updateButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button readlistButton;
	@FXML
	private Button closeButton;
	
	// TableView
	@FXML
	private TableView<Member> membershipTableView;
	@FXML
	private TableColumn<Member, String> userNameTableColumn;
	@FXML
	private TableColumn<Member, String> userIdTableColumn;
	@FXML
	private TableColumn<Member, String> userPasswordTableColumn;
	@FXML
	private TableColumn<Member, String> userHakTableColumn;
	@FXML
	private TableColumn<Member, String> userBanTableColumn;
	@FXML
	private TableColumn<Member, String> userBunTableColumn;

	@FXML
	void updateButtonOnAction() {
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
					alert.setTitle("������ ��� : ȸ������ ����");
					alert.setHeaderText("ȸ������ ����");
					alert.setContentText(useridTextField.getText() + " ���� ȸ�������� �����Ͻðڽ��ϱ�?");
					Optional<ButtonType> alertResult = alert.showAndWait();
					
					if(alertResult.get() == ButtonType.OK) {
						membershipMessageLabel.setText("ȸ�� ������ �����մϴ�...");
						
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
							
							membershipMessageLabel.setText("ȸ�� ���� ������ �Ϸ��߽��ϴ�!");
							
							readlistButtonOnAction();
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					if(checkEmpty == false) {
						membershipMessageLabel.setText("��� ������ �Է����ּ���!");
					} else if(checkPasswordSame == false) {
						membershipMessageLabel.setText("�Է��� ��ȣ�� �������� �ʽ��ϴ�. �ٽ� Ȯ�����ּ���!");
					} else if(checkNumbers == false) {
						membershipMessageLabel.setText("�г�, ��, ��ȣ�� �߸� �Է��߽��ϴ�!");
					}
				}
	}
	
	@FXML
	void deleteButtonOnAction() {
		if(isCheckEmpty()  == true) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("������ ��� : ȸ������ ����");
			alert.setHeaderText("ȸ������ ����");
			alert.setContentText(useridTextField.getText() + " ���� ȸ�������� �����Ͻðڽ��ϱ�?");
			Optional<ButtonType> alertResult = alert.showAndWait();
			if(alertResult.get() == ButtonType.OK) {
				DBConnection connNow = new DBConnection();
				Connection conn = connNow.getConnection();
				
				String sql = "DELETE FROM member_accounts "
						+ "WHERE user_id=?";
				
				try {
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, useridTextField.getText());
					pstmt.executeUpdate();
					
					pstmt.close();
					conn.close();
					
					membershipMessageLabel.setText("ȸ�� ���� ������ �Ϸ��߽��ϴ�!");
					
					userIdTableColumn.setText("");
					userPasswordTableColumn.setText("");
					userHakTableColumn.setText("");
					userBanTableColumn.setText("");
					userBunTableColumn.setText("");
					
					readlistButtonOnAction();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@FXML
	void readlistButtonOnAction() {
		DBConnection connNow = new DBConnection();
		Connection conn = connNow.getConnection();
		
		String sql = "SELECT * FROM member_accounts "
				+ "ORDER BY idx";
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ObservableList<Member> dataList = FXCollections.observableArrayList(); // �ڹ� ���׸�(Generic)
			
			while(rs.next()) {
				dataList.add(
					new Member(
							rs.getString("user_name"),
							rs.getString("user_id"),
							rs.getString("user_password"),
							rs.getString("user_hak"),
							rs.getString("user_ban"),
							rs.getString("user_bun")
					)
				);
			}
			
			membershipTableView.setItems(dataList);
		} catch(Exception e) {}
	}
	
	@FXML
	void closeButtonOnAction() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	void membershipTableViewOnAction() {
		if(membershipTableView.getSelectionModel().getSelectedItem() != null) {
			usernameTextField.setText(membershipTableView.getSelectionModel().getSelectedItem().getUserName());
			useridTextField.setText(membershipTableView.getSelectionModel().getSelectedItem().getUserId());
			password1PasswordField.setText(membershipTableView.getSelectionModel().getSelectedItem().getUserPassword());
			password2PasswordField.setText(membershipTableView.getSelectionModel().getSelectedItem().getUserPassword());
			hakTextField.setText(membershipTableView.getSelectionModel().getSelectedItem().getUserHak());
			banTextField.setText(membershipTableView.getSelectionModel().getSelectedItem().getUserBan());
			bunTextField.setText(membershipTableView.getSelectionModel().getSelectedItem().getUserBun());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//System.out.println("����� ��� â�� ������ �ʱ�ȭ �Լ�(initialize)�� �����߽��ϴ�.");
		userNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
		userIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		userPasswordTableColumn.setCellValueFactory(new PropertyValueFactory<>("userPassword"));
		userHakTableColumn.setCellValueFactory(new PropertyValueFactory<>("userHak"));
		userBanTableColumn.setCellValueFactory(new PropertyValueFactory<>("userBan"));
		userBunTableColumn.setCellValueFactory(new PropertyValueFactory<>("userBun"));
		
		readlistButtonOnAction();
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
}
