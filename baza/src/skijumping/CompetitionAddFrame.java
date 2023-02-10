package skijumping;

import java.util.ArrayList;

public class CompetitionAddFrame  extends AppJFrameAddToBase {
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
        super(makeList(), "konkurs", "Dodawanie konkursu");
        //FrameAddToBase frameAddToBase = new FrameAddToBase(c);

        //frameAddToBase.add("Dodaj reprezentacje", "reprezentacja", list);
    }
}
