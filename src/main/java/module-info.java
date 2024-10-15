module networkstwo.capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires jakarta.validation;
    requires jackson.dataformat.msgpack;


    opens networkstwo.capstone to javafx.fxml;
    exports networkstwo.capstone;
    exports networkstwo.capstone.utils;
    exports networkstwo.capstone.domain.messages;
    exports networkstwo.capstone.services;
    exports networkstwo.capstone.config;
    exports networkstwo.capstone.domain.models;


    exports networkstwo.capstone.application.pages;
    opens networkstwo.capstone.application.pages to javafx.fxml;
    exports networkstwo.capstone.application.views;
    opens networkstwo.capstone.application.views to javafx.fxml;
    exports networkstwo.capstone.application.stages;
    opens networkstwo.capstone.application.stages to javafx.fxml;

}