module no.ntnu.idatg.wargames {
  requires javafx.controls;
  requires javafx.fxml;

  opens no.ntnu.idatg2001.wargames;
  exports no.ntnu.idatg2001.wargames;
  exports no.ntnu.idatg2001.wargames.controller;
  opens no.ntnu.idatg2001.wargames.controller;
}