module no.ntnu.idatg.wargames {
  requires javafx.controls;
  requires javafx.fxml;

  opens no.ntnu.idatg.wargames;
  exports no.ntnu.idatg.wargames;
  exports no.ntnu.idatg.wargames.controller;
  opens no.ntnu.idatg.wargames.controller;
}