package ui;

import businessLogic.BlFacade;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import uicontrollers.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUI {

  private Window portal, adminPortal, userPortal, createQuestion, browseQuestions, setFee, removeBet,
          userLogin, userRegister, createEvent, placeAbet, depositMoney, showMovements,removeEvent,
          myProfile, editProfile;

  private BlFacade businessLogic;
  private Stage stage;
  private Scene scene;

  public BlFacade getBusinessLogic() {
    return businessLogic;
  }

  public void setBusinessLogic(BlFacade afi) {
    businessLogic = afi;
  }

  public MainGUI(BlFacade bl) {
    Platform.startup(() -> {
      try {
        setBusinessLogic(bl);
        init(new Stage());
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }


  static class Window {
    Controller c;
    Parent ui;
  }

  private Window load(String fxmlfile) throws IOException {
    Window window = new Window();
    FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource(fxmlfile), ResourceBundle.getBundle("Etiquetas", Locale.getDefault()));
    loader.setControllerFactory(controllerClass -> {

      if (controllerClass == PortalController.class)    // For both Portal and AdminPortal
        return new PortalController(businessLogic);

      if (controllerClass == BrowseQuestionsController.class)
        return new BrowseQuestionsController(businessLogic);

      if (controllerClass == CreateQuestionController.class)
        return new CreateQuestionController(businessLogic);

      if(controllerClass== SetFeeController.class)
        return new SetFeeController(businessLogic);

      if(controllerClass== UserLoginController.class)
        return new UserLoginController(businessLogic);

      if(controllerClass== UserRegisterController.class)
        return new UserRegisterController(businessLogic);

      if (controllerClass == CreateNewEventController.class)
        return new CreateNewEventController(businessLogic);

      if (controllerClass == DepositMoneyController.class)
        return new DepositMoneyController(businessLogic);

      if (controllerClass == PlaceABetController.class)
        return new PlaceABetController(businessLogic);

      if (controllerClass == RemoveBetController.class)
        return new RemoveBetController(businessLogic);

      if (controllerClass == MyProfileController.class)
        return new MyProfileController(businessLogic);

      if (controllerClass == EditProfileController.class)
        return new EditProfileController(businessLogic);

      if (controllerClass == ShowMovementsController.class)
        return new ShowMovementsController(businessLogic);

      if (controllerClass == RemoveEventController.class)
        return new RemoveEventController(businessLogic);



      else {
        // default behavior for controllerFactory:
        try {
          return controllerClass.getDeclaredConstructor().newInstance();
        } catch (Exception exc) {
          exc.printStackTrace();
          throw new RuntimeException(exc); // fatal, just bail...
        }
      }


    });
    window.ui = loader.load();
    ((Controller) loader.getController()).setMainApp(this);
    window.c = loader.getController();
    return window;
  }

  public void init(Stage stage) throws IOException {

    this.stage = stage;

    portal = load("/src/main/resources/Portal.fxml");
    adminPortal = load("/src/main/resources/AdminPortal.fxml");
    userPortal = load("/src/main/resources/UserPortal.fxml");
    browseQuestions = load("/src/main/resources/BrowseQuestions.fxml");
    createQuestion = load("/src/main/resources/CreateQuestion.fxml");
    setFee = load("/src/main/resources/SetFee.fxml");
    createEvent = load("/src/main/resources/CreateNewEvent.fxml");
    userLogin = load("/src/main/resources/UserLogin.fxml");
    userRegister = load("/src/main/resources/UserRegister.fxml");
    depositMoney = load("/src/main/resources/DepositMoney.fxml");
    placeAbet = load("/src/main/resources/PlaceABetv2.fxml");
    removeBet = load("/src/main/resources/RemoveaBetv2.fxml");
    myProfile = load("/src/main/resources/MyProfile.fxml");
    editProfile = load("/src/main/resources/EditProfile.fxml");
    showMovements = load("/src/main/resources/ShowMovements.fxml");
    removeEvent = load("/src/main/resources/RemoveEvent.fxml");


    showPortal();

  }

  public void showPortal(){setupScene(portal.ui, "Portal", 395, 315);}

  public void showAdminPortal(){setupScene(adminPortal.ui, "AdminPortal", 395, 520);}

  public void showUserPortal(){setupScene(userPortal.ui, "UserPortal", 395, 375);}

  public void showBrowseQuestions() {
    setupScene(browseQuestions.ui, "BrowseQuestions", 1000, 500);
  }

  public void showCreateQuestion() {
    setupScene(createQuestion.ui, "CreateQuestion", 550, 420);
  }

  public void showSetFee() {
    setupScene(setFee.ui, "SetFee", 1050, 500);
  }

  public void showCreateEvent() {
    setupScene(createEvent.ui, "CreateEvent", 650, 520);
  }

  public void showUserLogin() {
    setupScene(userLogin.ui, "Login", 565, 305);
  }

  public void showUserRegister() {
    setupScene(userRegister.ui, "Register", 600, 420);
  }

  public void showDepositMoney() { setupScene(depositMoney.ui,  "Deposit", 600, 450);}

  public void showPlaceABet(){setupScene(placeAbet.ui, "PlaceABet", 900, 680);}

  public void showRemoveABet(){setupScene(removeBet.ui, "RemoveBet", 940, 520);}

  public void showRemoveEvent(){setupScene(removeEvent.ui, "RemoveEvent", 680, 475);}

  public void showShowMovements(){setupScene(showMovements.ui, "ShowMovements", 395, 470);}

  public void showMyProfile(){setupScene(myProfile.ui, "MyProfile", 750, 300);}

  public void showEditProfile(){setupScene(myProfile.ui, "EditProfile", 600, 300);}



  private void setupScene(Parent ui, String title, int width, int height) {
    if (scene == null){
      scene = new Scene(ui, width, height);
      scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
      //scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/hover.css").toString());
      stage.setScene(scene);
    }
    stage.setWidth(width);
    stage.setHeight(height);
    stage.setTitle(ResourceBundle.getBundle("Etiquetas",Locale.getDefault()).getString(title));
    scene.setRoot(ui);
    stage.show();
  }
}