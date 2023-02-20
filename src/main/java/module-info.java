module com.example.multiplechoiceexam {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.multiplechoiceexam to javafx.fxml;
    exports com.example.multiplechoiceexam;
}