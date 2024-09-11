module networkstwo.capstone {
    requires javafx.controls;
    requires javafx.fxml;


    opens networkstwo.capstone to javafx.fxml;
    exports networkstwo.capstone;
    exports networkstwo.capstone.controllers;
    exports networkstwo.capstone.utils;
    opens networkstwo.capstone.controllers to javafx.fxml;
}