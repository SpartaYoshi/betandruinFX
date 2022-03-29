package uicontrollers;

import businessLogic.BlFacade;
import domain.Event;
import domain.Question;
import exceptions.FeeAlreadyExists;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.MouseEvent;
import javafx.util.*;
import ui.MainGUI;
import utils.Dates;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;


public class SetFeeController implements Controller {
    private MainGUI mainGUI;

    @FXML
    private Label warningLbl;

    @FXML
    private Button btnClose;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TableColumn<Event, Integer> ec1;

    @FXML
    private TableColumn<Event, String> ec2;

    @FXML
    private TableColumn<Event, Integer> qc1;

    @FXML
    private TableColumn<Event, Integer>qc2;
    @FXML
    private TextField feeField;
    @FXML
    private TextField resultField;

    @FXML
    private Button setBtn;

    @FXML
    private TableView<Event> tblEvents;

    @FXML
    private TableView<Question> tblQuestions;

    private BlFacade businessLogic;
    private List<LocalDate> holidays = new ArrayList<>();

    public SetFeeController(BlFacade bl) {
        businessLogic = bl;
        warningLbl=new Label();
        resultField=new TextField();
        feeField=new TextField();
    }

    @FXML
    void closeClick(ActionEvent event) {
        mainGUI.showPortal();
    }

    @FXML
    void setFee(MouseEvent event) {

        warningLbl.setText(" ");
        domain.Question quest = ((domain.Question) tblQuestions.getSelectionModel().getSelectedItem());

            try {
                warningLbl.setText("");
                String result=resultField.getText();
                System.out.println(feeField.getText());
                float feeAmount=Float.parseFloat(feeField.getText());


                if(feeAmount<=0) {
                    warningLbl.setText("Sorry, fee amount should be a numeric positive value");
                    warningLbl.getStyleClass().setAll("lbl","lbl-danger");


                }else {
                    businessLogic.createFee(quest, result, feeAmount);
                    warningLbl.setText("Fee has been set correctly");
                    warningLbl.getStyleClass().setAll("lbl","lbl-success");


                }

            }
            catch (FeeAlreadyExists e1) {
                warningLbl.setText("Sorry, the fee for that result already exists");
                warningLbl.getStyleClass().setAll("lbl","lbl-danger");

            }
            catch (java.lang.NumberFormatException e1) {
                warningLbl.setText("Error in numeric value of Fee");
                warningLbl.getStyleClass().setAll("lbl","lbl-danger");


            }

        }



    private void setEvents(int year, int month) {
        Date date = Dates.toDate(year,month);

        for (Date day : businessLogic.getEventsMonth(date)) {
            holidays.add(Dates.convertToLocalDateViaInstant(day));
        }
    }

    private void setEventsPrePost(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        setEvents(date.getYear(), date.getMonth().getValue());
        setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
        setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
    }

    private void setupEventSelection() {
        tblEvents.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                tblQuestions.getItems().clear();
                for (Question q : tblEvents.getSelectionModel().getSelectedItem().getQuestions()) {
                    tblQuestions.getItems().add(q);
                }
            }
        });
    }

    @FXML
    void initialize() {

        setupEventSelection();

        setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());

        datepicker.setOnMouseClicked(e -> {
            // get a reference to datepicker inner content
            // attach a listener to the  << and >> buttons
            // mark events for the (prev, current, next) month and year shown
            DatePickerSkin skin = (DatePickerSkin) datepicker.getSkin();
            skin.getPopupContent().lookupAll(".button").forEach(node -> {
                node.setOnMouseClicked(event -> {
                    List<Node> labels = skin.getPopupContent().lookupAll(".label").stream().toList();
                    String month = ((Label) (labels.get(0))).getText();
                    String year =  ((Label) (labels.get(1))).getText();
                    YearMonth ym = Dates.getYearMonth(month + " " + year);
                    setEventsPrePost(ym.getYear(), ym.getMonthValue());
                });
            });


        });

        datepicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty && item != null) {
                            if (holidays.contains(item)) {
                                this.setStyle("-fx-background-color: pink");
                            }
                        }
                    }
                };
            }
        });

        // a date has been chosen, update the combobox of Events
        datepicker.setOnAction(actionEvent -> {
            tblEvents.getItems().clear();
            Vector<domain.Event> events = businessLogic.getEvents(Dates.convertToDate(datepicker.getValue()));
            for (domain.Event ev : events) {
                tblEvents.getItems().add(ev);
            }
        });

        // Bind columns to Event attributes
        ec1.setCellValueFactory(new PropertyValueFactory<>("eventNumber"));
        ec2.setCellValueFactory(new PropertyValueFactory<>("description"));
        // Bind columns to Question attributes
        qc1.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
        qc2.setCellValueFactory(new PropertyValueFactory<>("question"));



    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
