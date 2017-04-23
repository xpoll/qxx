package cn.blmdz.book.g703;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StudentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	JLabel noLabel;//学号标签
	JLabel nameLabel;//姓名标签
	JLabel genderLabel;//性别标签
	JLabel birthLabel;//出生年月标签
	JLabel addressLabel;//家庭住址标签
	JLabel telLabel;//电话标签
	
	JTextField noField;//学号输入框
	JTextField nameField;//姓名输入框
	JTextField genderField;//性别输入框
	JTextField birthField;//出生年月输入框
	JTextField addressField;//家庭住址输入框
	JTextField telField;//电话输入框

	public StudentPanel() {
		setGUiComponent();
	}
	
	private void setGUiComponent() {
		//初始化组件
		noLabel = new JLabel("学号");
		noField = new JTextField(10);
		nameLabel = new JLabel("姓名");
		nameField = new JTextField(10);
		genderLabel = new JLabel("性别");
		genderField = new JTextField(10);
		birthLabel = new JLabel("出生年月");
		birthField = new JTextField(10);
		addressLabel = new JLabel("家庭住址");
		addressField = new JTextField(10);
		telLabel = new JLabel("电话");
		telField = new JTextField(10);
		//设置组件
		this.setLayout(new GridLayout(3, 4));//排列方式
		this.add(noLabel);
		this.add(noField);
		this.add(nameLabel);
		this.add(nameField);
		this.add(genderLabel);
		this.add(genderField);
		this.add(birthLabel);
		this.add(birthField);
		this.add(addressLabel);
		this.add(addressField);
		this.add(telLabel);
		this.add(telField);
		
		this.setSize(300, 300);
		this.setVisible(true);
	}
	
	public void clearContent() {
		noField.setText(null);
		nameField.setText(null);
		genderField.setText(null);
		birthField.setText(null);
		addressField.setText(null);
		telField.setText(null);
	}

	public String getNoField() {
		return noField.getText();
	}
	public void setNoField(String noField) {
		this.noField.setText(noField);
	}
	public String getNameField() {
		return nameField.getText();
	}
	public void setNameField(String nameField) {
		this.nameField.setText(nameField);
	}
	public String getGenderField() {
		return genderField.getText();
	}
	public void setGenderField(String genderField) {
		this.genderField.setText(genderField);
	}
	public String getBirthField() {
		return birthField.getText();
	}
	public void setBirthField(String birthField) {
		this.birthField.setText(birthField);
	}
	public String getAddressField() {
		return addressField.getText();
	}
	public void setAddressField(String addressField) {
		this.addressField.setText(addressField);
	}
	public String getTelField() {
		return telField.getText();
	}
	public void setTelField(String telField) {
		this.telField.setText(telField);
	}
}
