package skijumping;

import java.util.ArrayList;

public class CompetitorAddFrame  extends AppJFrameAddToBase {
    static ArrayList<AddField> makeList(){
        ArrayList<AddField> list = new ArrayList<>();
        list.add(new AddField("Imie", "imie", 's'));
        list.add(new AddField("Nazwisko", "nazwisko", 's'));
        String select = "SELECT id_rep, nazwa FROM REPREZENTACJA ORDER BY nazwa;";
        list.add(new AddField("Reprezentacja", "id_rep", select));
        //list.add(new AddField("kwota_bazowa", 'i'));
        return list;
    }
    CompetitorAddFrame(){
        super(makeList(), "zawodnik", "Dodawanie zawodnika");
        //FrameAddToBase frameAddToBase = new FrameAddToBase(c);

        //frameAddToBase.add("Dodaj reprezentacje", "reprezentacja", list);
    }
}
