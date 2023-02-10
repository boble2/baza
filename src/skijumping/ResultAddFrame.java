package skijumping;

import java.util.ArrayList;

public class ResultAddFrame extends AppJFrameAddToBase {
    int id_kon;
    static ArrayList<AddField> makeList(String id_kon, String id_serii, String id_zgl){
        //try {
            //String id_serii;
            ArrayList<AddField> list = new ArrayList<>();

            list.add(new AddField("id_serii", id_serii));
            list.add(new AddField("id_zgl", id_zgl));
            list.add(new AddField("Ocena_zbiorcza", "ocena_zb", 'd'));
            list.add(new AddField("Dlugosc", "dlugosc", 'd'));
            list.add(new AddField("Czy dyskwalifikacja? (t/f)", "czy_dsq", 's'));
            list.add(new AddField("Punkty", "punkty", 'd'));


            /*list.add(new AddField("Imie", "imie", 's'));
            list.add(new AddField("Nazwisko", "nazwisko", 's'));
            String select = "SELECT id_rep, nazwa FROM REPREZENTACJA ORDER BY nazwa;";
            list.add(new AddField("Reprezentacja", "id_rep", select));*/
            //list.add(new AddField("kwota_bazowa", 'i'));
            return list;
        //}
        //catch (Exception e){ //

            //throw new RuntimeException(e);
        //}
    }
    ResultAddFrame(String id_kon, String id_serii, String id_zgl, String dane){
        super(makeList(id_kon, id_serii, id_zgl), "wynik", "Wpisywanie wyniku zawodnika " + dane);
        //FrameAddToBase frameAddToBase = new FrameAddToBase(c);

        //frameAddToBase.add("Dodaj reprezentacje", "reprezentacja", list);
    }
}
