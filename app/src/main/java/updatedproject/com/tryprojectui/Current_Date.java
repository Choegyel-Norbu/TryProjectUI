package updatedproject.com.tryprojectui;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Current_Date {

    //String dateUpdate;

    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String dateUpdate = simpleDateFormat.format(date);

    public String getCurrentdDate() {
        return dateUpdate;
    }
}
