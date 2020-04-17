/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author HCARACH
 */
public class MyAccount {

    private BorderPane profilePane = new BorderPane();
    private BorderPane changePane = new BorderPane();

    private File user = new File("src/data/user.dat");
    private ReadWriteFile file = new ReadWriteFile();

    private Account myAccount;
    private ArrayList<Account> listAccount = new ArrayList<>();

    private LocalDate dOB;

    private VBox editBox = new VBox(30);
    private VBox changeBox = new VBox(10);
    private VBox leftBox = new VBox(10);
    private HBox bottomBox = new HBox();

    //TextFiled
    private TextField username = new TextField();
    private TextField firstname = new TextField();
    private TextField lastname = new TextField();
    private TextField email = new TextField();
    private TextField currentPassword = new PasswordField();
    private TextField currentPasswordInGrid = new PasswordField();
    private TextField password = new PasswordField();
    private TextField passwordConfirm = new PasswordField();

    private ToggleGroup sexToggle = new ToggleGroup(); //create radio button group
    private RadioButton male;
    private RadioButton female;
    private RadioButton otherRadio;

    boolean changeStatus = false;

    private FileChooser fileChooser;
    private File filePath;
    private ImageView photo;
    private Image image;

    public MyAccount(Account myAccount) {
        this.showAccount(myAccount);
    }

    public BorderPane getProfilePane() {
        return profilePane;
    }

    public void showAccount(Account myAccount) {
        this.myAccount = myAccount;
        editBox.getChildren().clear();
        
        Label titleHead = new Label("My Account / บัญชีของฉัน");
        titleHead.getStyleClass().add("titleMyAccount");
        
        Label firstnameLabel = new Label(myAccount.getName());
        Label lastnameLabel = new Label(myAccount.getSurname());
        firstnameLabel.getStyleClass().add("fistNameAndSurname");
        lastnameLabel.getStyleClass().add("fistNameAndSurname");
        
        HBox hboxNameSurname = new HBox(30);
        hboxNameSurname.setAlignment(Pos.CENTER);
        hboxNameSurname.getChildren().addAll(firstnameLabel,lastnameLabel);

        Label usernameTitleLabel = new Label("Username");
        Label usernameLabel = new Label(myAccount.getUsername());
        Label emailTitleLabel = new Label("E-mail");
        Label emailLabel = new Label(myAccount.getEmail());
        Label dateOfBirthLabelTitle = new Label("Date of Birth");
        
        DatePicker date = new DatePicker();
        dOB = myAccount.getDateOfBirth();
        date.setValue(dOB);
        Label dateOfBirthLabel = new Label(dOB.format(DateTimeFormatter.ISO_DATE));
        Label genderTitleLabel = new Label("Gender");
        Label genderLabel = new Label(myAccount.getGender());       
        
        Label colon1 = new Label(":");
        Label colon2 = new Label(":");
        Label colon3 = new Label(":");
        Label colon4 = new Label(":");  
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.getStyleClass().add("detailMyAccount");
        gridPane.setVgap(20);
        gridPane.setHgap(10);
        GridPane.setHalignment(usernameTitleLabel, HPos.LEFT);
        GridPane.setHalignment(emailTitleLabel, HPos.LEFT);
        GridPane.setHalignment(dateOfBirthLabelTitle, HPos.LEFT);
        GridPane.setHalignment(genderTitleLabel, HPos.LEFT);
        GridPane.setConstraints(usernameTitleLabel, 0, 0);
        GridPane.setConstraints(emailTitleLabel, 0, 1);
        GridPane.setConstraints(genderTitleLabel, 0, 2);
        GridPane.setConstraints(dateOfBirthLabelTitle, 0, 3);
        GridPane.setConstraints(colon1, 1, 0);
        GridPane.setConstraints(colon2, 1, 1);
        GridPane.setConstraints(colon3, 1, 2);
        GridPane.setConstraints(colon4, 1, 3);
        GridPane.setConstraints(usernameLabel, 2, 0);
        GridPane.setConstraints(emailLabel, 2, 1);
        GridPane.setConstraints(genderLabel, 2, 2);
        GridPane.setConstraints(dateOfBirthLabel, 2, 3);
        gridPane.getChildren().addAll(usernameTitleLabel,emailTitleLabel,dateOfBirthLabelTitle,genderTitleLabel,
                                      colon1,colon2,colon3,colon4,
                                      usernameLabel,emailLabel,genderLabel,dateOfBirthLabel);       
        
        image = myAccount.getPhoto();
        photo = new ImageView(image);
        photo.setFitHeight(200);
        photo.setFitWidth(200);
        photo.setPreserveRatio(true);

        VBox profilePictureBox = new VBox(10);
        //profilePictureBox.setPadding(new Insets(30));
        profilePictureBox.getChildren().addAll(photo);
        profilePictureBox.setAlignment(Pos.CENTER_RIGHT);
        profilePictureBox.setMinWidth(300);
        
        editBox.setAlignment(Pos.CENTER);
        editBox.getChildren().addAll(titleHead, hboxNameSurname, gridPane);
        //editBox.setPadding(new Insets(10));
        profilePane.setPadding(new Insets(30));
        profilePane.getStyleClass().add("innerPane");
        profilePane.setLeft(profilePictureBox);
        profilePane.setCenter(editBox);
    }

    public void editAccount() {
        
        this.changeStatus = false;
        
        Label usernameTitleLabel = new Label("Username");
        Label firstnameTitleLabel = new Label("First Name ");
        Label lastnameTitleLabel = new Label("Last Name");
        Label emailTitleLabel = new Label("E-mail");
        Label dateOfBirthLabelTitle = new Label("Date of Birth");
        Label genderTitleLabel = new Label("Gender");
        
        username.setPromptText("Usrname");
        username.setText(this.myAccount.getUsername());
        username.setMaxWidth(280);
                
        firstname.setPromptText("First Name");
        firstname.setText(this.myAccount.getName());
        firstname.setMaxWidth(280);
        
        lastname.setPromptText("Last Name");
        lastname.setText(this.myAccount.getSurname());
        lastname.setMaxWidth(280);
        
        email.setPromptText("Email e.g. Spookify@gmail.com");
        email.setText(this.myAccount.getEmail());
        email.setMaxWidth(280);
        
        username.getStyleClass().add("detailMyAccountFillText");
        firstname.getStyleClass().add("detailMyAccountFillText");
        lastname.getStyleClass().add("detailMyAccountFillText");
        email.getStyleClass().add("detailMyAccountFillText");
        
        // create a date picker 
        DatePicker date = new DatePicker();
        date.setMaxWidth(280);
        // show week numbers 
        date.setShowWeekNumbers(false);
        // when datePicker is pressed 
        dOB = myAccount.getDateOfBirth();
        date.setValue(dOB);
        date.setOnAction(e -> {
            dOB = date.getValue();
        });
        
        date.getStyleClass().add("detailMyAccountFillText");
        
        //Select Gender
        ToggleGroup sexToggle = new ToggleGroup(); //create radio button group
        male = new RadioButton("Male"); //create radio button
        female = new RadioButton("Female");
        otherRadio = new RadioButton("Not specify");
        male.setToggleGroup(sexToggle); //Set radio button group
        female.setToggleGroup(sexToggle);
        otherRadio.setToggleGroup(sexToggle);

        if (myAccount.getGender().equals("Male")) {
            sexToggle.selectToggle(male);
        } else if (myAccount.getGender().equals("Female")) {
            sexToggle.selectToggle(female);
        } else {
            sexToggle.selectToggle(otherRadio);
        }

        male.getStyleClass().add("detailMyAccount2");
        female.getStyleClass().add("detailMyAccount2");
        otherRadio.getStyleClass().add("detailMyAccount2");
        HBox rowGender = new HBox(10);
        rowGender.getChildren().addAll(male, female, otherRadio);
        
        Label colon1 = new Label(":");
        Label colon2 = new Label(":");
        Label colon3 = new Label(":");
        Label colon4 = new Label(":");
        Label colon5 = new Label(":");
        Label colon6 = new Label(":");     
        
        GridPane gridPaneEdit = new GridPane();
        gridPaneEdit.getStyleClass().add("detailMyAccount");
        gridPaneEdit.setVgap(20);
        gridPaneEdit.setHgap(10);
        gridPaneEdit.setAlignment(Pos.CENTER_LEFT);
        GridPane.setHalignment(usernameTitleLabel, HPos.RIGHT);
        GridPane.setHalignment(firstnameTitleLabel, HPos.RIGHT);
        GridPane.setHalignment(lastnameTitleLabel, HPos.RIGHT);
        GridPane.setHalignment(emailTitleLabel, HPos.RIGHT);
        GridPane.setHalignment(dateOfBirthLabelTitle, HPos.RIGHT);
        GridPane.setHalignment(genderTitleLabel, HPos.RIGHT);
        GridPane.setConstraints(firstnameTitleLabel, 0, 0);
        GridPane.setConstraints(lastnameTitleLabel, 0, 1);
        GridPane.setConstraints(usernameTitleLabel, 0, 2);
        GridPane.setConstraints(emailTitleLabel, 0, 3);
        GridPane.setConstraints(dateOfBirthLabelTitle, 0, 4);
        GridPane.setConstraints(genderTitleLabel, 0, 5);
        GridPane.setConstraints(colon1, 1, 0);
        GridPane.setConstraints(colon2, 1, 1);
        GridPane.setConstraints(colon3, 1, 2);
        GridPane.setConstraints(colon4, 1, 3);
        GridPane.setConstraints(colon5, 1, 4);
        GridPane.setConstraints(colon6, 1, 5);
        GridPane.setConstraints(firstname, 2, 0);
        GridPane.setConstraints(lastname, 2, 1);
        GridPane.setConstraints(username, 2, 2);
        GridPane.setConstraints(email, 2, 3);
        GridPane.setConstraints(date, 2, 4);
        GridPane.setConstraints(rowGender, 2, 5);
        gridPaneEdit.getChildren().addAll(usernameTitleLabel,firstnameTitleLabel,lastnameTitleLabel,emailTitleLabel,dateOfBirthLabelTitle,genderTitleLabel,
                                      colon1,colon2,colon3,colon4,colon5,colon6,
                                      username,firstname,lastname,email,date,rowGender);
        


        Label currentPasswordTitle = new Label("Current Password");
        currentPasswordTitle.getStyleClass().add("detailMyAccount2");
        currentPassword.setMaxWidth(180);
        currentPasswordInGrid.setMaxWidth(180);
        currentPassword.getStyleClass().add("detailMyAccountFillText");
        currentPasswordInGrid.getStyleClass().add("detailMyAccountFillText");
        
        
        Label changePassword = new Label("Change Password?");
        changePassword.getStyleClass().add("detailMyAccount2");
        changePassword.setStyle("-fx-underline: true");
       
        HBox rowCurrentPass = new HBox(10);
        rowCurrentPass.getChildren().addAll(currentPasswordTitle,currentPassword);
        
        Label currentPasswordTitleInGrid = new Label("Current Password");
        Label passwordTitle = new Label("New Password");
        Label passwordConfirmTitle = new Label("Confirm New Password");
        currentPassword.getStyleClass().add("detailMyAccountFillText");
        password.getStyleClass().add("detailMyAccountFillText");
        passwordConfirm.getStyleClass().add("detailMyAccountFillText");
        password.setMaxWidth(180);
        passwordConfirm.setMaxWidth(180);
        
        Label colonA = new Label(":");
        Label colonB = new Label(":");
        Label colonC = new Label(":");
        
        GridPane gridPanePass = new GridPane();
        gridPanePass.setVgap(20);
        gridPanePass.setHgap(10);
        gridPanePass.getStyleClass().add("detailMyAccount");
        GridPane.setHalignment(currentPasswordTitleInGrid, HPos.RIGHT);
        GridPane.setHalignment(passwordTitle, HPos.RIGHT);
        GridPane.setHalignment(passwordConfirmTitle, HPos.RIGHT);
        GridPane.setConstraints(currentPasswordTitle, 0, 0);
        GridPane.setConstraints(passwordTitle, 0, 1);
        GridPane.setConstraints(passwordConfirmTitle, 0, 2);
        GridPane.setConstraints(colonA, 1, 0);
        GridPane.setConstraints(colonB, 1, 1);
        GridPane.setConstraints(colonC, 1, 2);
        GridPane.setConstraints(currentPasswordInGrid, 2, 0);
        GridPane.setConstraints(password, 2, 1);
        GridPane.setConstraints(passwordConfirm, 2, 2);
        gridPanePass.getChildren().addAll(currentPasswordTitleInGrid,passwordTitle,passwordConfirmTitle,
                                        colonA,colonB,colonC,
                                        currentPasswordInGrid,password,passwordConfirm);
        
        changePassword.setOnMouseClicked(event -> {
            currentPassword.clear();
            currentPasswordInGrid.clear();
            editBox.getChildren().removeAll(rowCurrentPass,changePassword);
            editBox.getChildren().addAll(gridPanePass);
            changeStatus = true;
        });

        editBox.getChildren().clear();
        editBox.getChildren().addAll(gridPaneEdit,rowCurrentPass,changePassword);
        editBox.setPadding(new Insets(50, 50, 50, 50));
        
        Button uploadBtn = new Button("Change Image");
        //Upload Picture 
        uploadBtn.getStyleClass().add("detailbtn");
        uploadBtn.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");

            //Set to user's directory or go to the default C drvie if cannot access
            String userDirectoryString = System.getProperty("user.home") + "\\Pictures";
            File userDirectory = new File(userDirectoryString);

            if (!userDirectory.canRead()) {
                userDirectory = new File("user.home");
            }

            fileChooser.setInitialDirectory(userDirectory);

            this.filePath = fileChooser.showOpenDialog(stage);

            //Try to update the image by loading the new image
            try {
                BufferedImage bufferedImage = ImageIO.read(filePath);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                this.photo.setImage(image);
                this.image = image;
            } catch (IOException e) {
                System.out.println("MyAccount : IOExeption upload picture in editAccount");
            }
        });
        //
        
        VBox profilePictureBox = new VBox(10);
        profilePictureBox.setPadding(new Insets(40));
        profilePictureBox.getChildren().addAll(photo, uploadBtn);
        profilePictureBox.setAlignment(Pos.TOP_CENTER);
        profilePictureBox.setMinWidth(300);

        profilePane.getStyleClass().add("innerPane");
        profilePane.setLeft(profilePictureBox);
        profilePane.setCenter(editBox);
    }

    public boolean saveAccount() {

        if(currentPassword.getText().equals("")){
            currentPassword.setText(currentPasswordInGrid.getText());   //By Pop Solve UI Grid Problems
        }
        
        
        boolean editSave = false;

        String userGender;

        if (male.isSelected()) {
            userGender = "Male";
        } else if (female.isSelected()) {
            userGender = "Female";
        } else {
            userGender = "N/A";
        }

        try {
            listAccount = file.readFile(user);
        } catch (Exception e) {
            System.out.println("MyAcoount : Exeption read file in saveAccount");
        }

        boolean uniqueID = true;
        for (Account account : listAccount) {
            String userId = username.getText(), emailID = email.getText();
            String chkUser = account.getUsername(), chkEmail = account.getEmail();
            if (((userId.equals(chkUser) && !userId.equals(myAccount.getUsername())) || (emailID.equals(chkEmail) && !emailID.equals(myAccount.getEmail())))) {
                uniqueID = false;
                break;
            }
        }
        
        System.out.println(uniqueID);
        
        if (uniqueID == false) {
            AlertBox.displayAlert("Something went wrong!", "Email / username is already exists.");
        } else if (currentPassword.getText().equals(myAccount.getPassword())) {
            if(this.changeStatus && !password.getText().equals(passwordConfirm.getText())) {
                AlertBox.displayAlert("Something went wrong!", "Confirm password is not as same as password.");
            }
            else if (firstname.getText().isBlank() || lastname.getText().isBlank() || username.getText().isBlank()
                    || email.getText().isBlank()) {
                AlertBox.displayAlert("Something went wrong!", "Please check all the form.\nAnd make sure it was filled.");
            } else {
                if (dOB.isAfter(LocalDate.now())) {
                    System.out.println("User is come from the future.");
                    AlertBox.displayAlert("Something went wrong!", "Please check a date field again.\n"
                            + "Make sure that's your date of birth.");
                } else if (!isEmail(email.getText())) {
                    AlertBox.displayAlert("Something went wrong!", "Please use another email.");
                } else {

                    ArrayList<Account> changeAccount = new ArrayList<>();

                    if (changeStatus) {
                        myAccount.setPassword(this.password.getText());
                    }

                    for (Account account : listAccount) {
                        if (account.getUsername().equals(myAccount.getUsername())) {
                            myAccount.setPhoto(image);
                            this.photo.setImage(myAccount.getPhoto());
                            Account newAccount = new Account(firstname.getText(), lastname.getText(), username.getText(), email.getText(),
                                    myAccount.getPassword(), userGender, dOB, myAccount.getQuestion(), myAccount.getAnswer(), myAccount.getUserRole(), myAccount.getPhoto());
                            changeAccount.add(newAccount);
                            myAccount = newAccount;

                        } else {
                            changeAccount.add(account);
                        }
                    }

                    try {
                        file.writeFile(user, changeAccount);
                        System.out.println("Saving account.");
                    } catch (IOException ex) {
                        System.out.println("MyAcoount : IOExeption write file in saveAccount");
                    }
                    try {
                        listAccount = file.readFile(user);
                    } catch (Exception ex) {
                        System.out.println("MyAcoount : IOExeption read file in saveAccount");
                    }

                    System.out.println("Edit Profile Complete!\n");

                    editSave = true;
                }
            }

            this.Clear();

        } else if (!currentPassword.getText().equals(myAccount.getPassword())) {
            AlertBox.displayAlert("Something went wrong!", "Current password is not correct.");
        }

        changeStatus = false;
        return editSave;
    }

    public Account getMyAccount() {
        return myAccount;
    }

    public void Clear() {
        currentPassword.clear();
        password.clear();
        passwordConfirm.clear();
    }

    private boolean isEmail(String email) {
        return email.contains("@") && email.contains(".") && !(email.contains(" ") || email.contains(";") || email.contains(",") || email.contains("..")
                || email.length() <= 12 || email.length() > 64);
    }

}
