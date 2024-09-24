module networkstwo.capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires jakarta.validation;
    requires jackson.dataformat.msgpack;


    opens networkstwo.capstone to javafx.fxml;
    exports networkstwo.capstone;
    exports networkstwo.capstone.utils;
    exports networkstwo.capstone.messages;
    exports networkstwo.capstone.services;
    exports networkstwo.capstone.config;
    exports networkstwo.capstone.models;


    exports networkstwo.capstone.controllers.pages;
    opens networkstwo.capstone.controllers.pages to javafx.fxml;
    exports networkstwo.capstone.controllers.views;
    opens networkstwo.capstone.controllers.views to javafx.fxml;

}