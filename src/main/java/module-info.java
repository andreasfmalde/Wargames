module no.ntnu.idatg.wargames {
  requires javafx.controls;
  requires javafx.fxml;

  opens no.ntnu.idatg2001.wargames;
  opens no.ntnu.idatg2001.wargames.model.army;
  opens no.ntnu.idatg2001.wargames.model.battle;
  opens no.ntnu.idatg2001.wargames.model.unit;
  opens no.ntnu.idatg2001.wargames.utility;
  exports no.ntnu.idatg2001.wargames;
  exports no.ntnu.idatg2001.wargames.controller;
  opens no.ntnu.idatg2001.wargames.controller;
  opens no.ntnu.idatg2001.wargames.model;
}