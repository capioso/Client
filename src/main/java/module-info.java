module networkstwo.capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens networkstwo.capstone to javafx.fxml;
    exports networkstwo.capstone;
    exports networkstwo.capstone.controllers;
    exports networkstwo.capstone.utils;
    exports networkstwo.capstone.messages;
    exports networkstwo.capstone.services;
    opens networkstwo.capstone.controllers to javafx.fxml;
}