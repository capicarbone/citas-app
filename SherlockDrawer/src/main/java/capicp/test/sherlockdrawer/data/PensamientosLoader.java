package capicp.test.sherlockdrawer.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by capi on 28/06/13.
 */
public class PensamientosLoader extends AsyncTaskLoader<List<Pensamiento>> {


    List<Pensamiento> mPensamientos;

    public PensamientosLoader(Context ctx){
        super(ctx);

    }

    @Override
    public List<Pensamiento> loadInBackground() {

        Log.d("PensamientoLoader", "Se procede a cargar la información");

        SQLiteDatabase db = new PensamientoBDHelper(getContext()).getReadableDatabase();
        ArrayList<Pensamiento> pensamientos = new ArrayList<Pensamiento>();
        Pensamiento nuevo;

        Cursor c = db.rawQuery("SELECT cita, nombre, descripcion, foto FROM pensamientos, autores WHERE autores._id=autor_id", null);

        c.moveToFirst();

        while (!c.isAfterLast()){

            nuevo = new Pensamiento();

            nuevo.setCita(c.getString(0));
            nuevo.setAutor_nombre(c.getString(1));
            nuevo.setAutor_descripcion(c.getString(2));
            nuevo.setAutor_foto(c.getString(3));

            pensamientos.add(nuevo);

            c.moveToNext();

        }

        db.close();

        return pensamientos;
    }

    @Override
    public void deliverResult(List<Pensamiento> data) {
        super.deliverResult(data);
    }
}
