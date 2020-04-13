/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author HCARACH
 */
public class Profile {
    
    Button savebt = new Button("Save");
    Button cancelbt = new Button("Cancel");
    Button editbt = new Button("Edit");
    Account userAccount;
    
    BorderPane accountPane = new BorderPane();
    BorderPane mainPane = new BorderPane();
    
    public Profile(Account userAccount) {
        
        this.userAccount = userAccount;
        MyAccount myAccount = new MyAccount(this.userAccount);
                
        VBox head = new VBox(10);
        head.setPadding(new Insets(0,10,20,20));
        head.getChildren().add(new Text("MY ACCOUNT"));
        
         HBox bottom = new HBox(10);
        bottom.setPadding(new Insets(20,20,0,0));
        bottom.setAlignment(Pos.CENTER_RIGHT);
        
        savebt.setOnAction(event -> {
            if(myAccount.saveAccount()) {
                AlertBox.displayAlert("Edit Profile", "Saved.");
                this.userAccount = myAccount.getMyAccount();
                myAccount.showAccount(this.userAccount);
                accountPane.setCenter(myAccount.getProfilePane());
                bottom.getChildren().clear();
                bottom.getChildren().addAll(editbt);
            } else 
                AlertBox.displayAlert("Edit Profile", "Failed.");
        });
        
        cancelbt.setOnAction(event -> {
            myAccount.showAccount(userAccount);
            accountPane.setCenter(myAccount.getProfilePane());
            myAccount.Clear();
            bottom.getChildren().clear();
            bottom.getChildren().addAll(editbt);
        });
        
        VBox right = new VBox(10);
        right.setPadding(new Insets(20,20,20,20));
        editbt.setOnAction(event -> {
            myAccount.editAccount();
            accountPane.setCenter(myAccount.getProfilePane());
            bottom.getChildren().clear();
            bottom.getChildren().addAll(cancelbt, savebt);
        });
        
                bottom.getChildren().add(editbt);
        
        accountPane.setTop(head);
        accountPane.setCenter(myAccount.getProfilePane());
        accountPane.setPadding(new Insets(50, 50, 50, 50));
        accountPane.setStyle("-fx-background-color: white");
        accountPane.setBottom(bottom);
        
        mainPane.setCenter(accountPane);
        mainPane.setPadding(new Insets(50, 100, 0, 100));
        
    }

    public BorderPane getMainPane() {
        return mainPane;
    }
    
    
    
    
}