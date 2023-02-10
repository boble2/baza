package skijumping;

import java.util.ArrayList;

public class RepresentationAddFrame extends AppJFrameAddToBase {
    static ArrayList<AddField> makeList(){
        ArrayList<AddField> list = new ArrayList<>();
        list.add(new AddField("nazwa", "nazwa", 's'));
        //list.add(new AddField("kwota_bazowa", 'i'));
        return list;
    }
    RepresentationAddFrame(){
        super(makeList(), "reprezentacja", "Dodawanie reprezentacji");
        //FrameAddToBase frameAddToBase = new FrameAddToBase(c);

        //frameAddToBase.add("Dodaj reprezentacje", "reprezentacja", list);
    }
}
