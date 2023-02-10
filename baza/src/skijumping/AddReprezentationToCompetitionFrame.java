package skijumping;

import java.util.ArrayList;

public class AddReprezentationToCompetitionFrame extends AppJFrameAddToBase{
    String id;
    String place;
    static ArrayList<AddField> makeList(String id){
        ArrayList<AddField> list = new ArrayList<>();
        //list.add(new AddField("Data", "data", 'e'));
        //list.add(new AddField("Termin zgloszen", "termin_zgloszen", 'e'));
        String select = "SELECT id_rep, nazwa FROM REPREZENTACJA ORDER BY nazwa;";
        list.add(new AddField("Reprezentacja", "id_rep", select));
        list.add(new AddField("id_kon", id));
        list.add(new AddField("Kwota bazowa", "kwota_bazowa", 'i'));

        //list.add(new AddField("kwota_bazowa", 'i'));
        return list;
    }
    AddReprezentationToCompetitionFrame(String id, String place){
        super(makeList(id), "reprezentacja_konkurs", "Dodawanie reprezentacji do konkursu");
        this.id = id;
        this.place = place;
    }

}
/*
extends AppJFrameAddToBase {
    static ArrayList<AddField> makeList(){
        ArrayList<AddField> list = new ArrayList<>();
        list.add(new AddField("Data", "data", 'e'));
        list.add(new AddField("Termin zgloszen", "termin_zgloszen", 'e'));
        String select = "SELECT id_rep, nazwa FROM REPREZENTACJA ORDER BY nazwa;";
        list.add(new AddField("Organizator", "id_org", select));
        list.add(new AddField("Miejsce", "miejsce", 's'));

        //list.add(new AddField("kwota_bazowa", 'i'));
        return list;
    }
    CompetitionAddFrame(){
        super(makeList(), "konkurs");
        //FrameAddToBase frameAddToBase = new FrameAddToBase(c);

        //frameAddToBase.add("Dodaj reprezentacje", "reprezentacja", list);
    }
}
 */