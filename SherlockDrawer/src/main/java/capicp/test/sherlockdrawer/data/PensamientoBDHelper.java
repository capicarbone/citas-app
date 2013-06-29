package capicp.test.sherlockdrawer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by capi on 27/06/13.
 */
public class PensamientoBDHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NOMBRE = "pensamientos.db";

    public static final String ID_COLUMNA = "_id";

    public static final String PENSAMIENTOS_NOMBRE_TABLA = "pensamientos";
    public static final String PENSAMIENTOS_COLUMNA_CITA = "cita";
    public static final String PENSAMIENTOS_COLUMNA_AUTOR = "autor_id";

    public static final String AUTORES_NOMBRE_TABLA = "autores";
    public static final String AUTORES_COLUMNA_NOMBRE = "nombre";
    public static final String AUTORES_COLUMNA_DESCRIPCION = "descripcion";

    public PensamientoBDHelper(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String autores_tabla_create = "CREATE TABLE " + AUTORES_NOMBRE_TABLA + "( " +
                ID_COLUMNA + " INTEGER PRIMARY KEY, " +
                AUTORES_COLUMNA_NOMBRE + " TEXT, " +
                AUTORES_COLUMNA_DESCRIPCION + " TEXT " +
                ");";

        String pensamientos_tabla_create = "CREATE TABLE " + PENSAMIENTOS_NOMBRE_TABLA + "( " +
                ID_COLUMNA + " INTEGER PRIMARY KEY, " +
                PENSAMIENTOS_COLUMNA_CITA + " TEXT, " +
                PENSAMIENTOS_COLUMNA_AUTOR + " INTEGER, " +
                "FOREIGN KEY(" + PENSAMIENTOS_COLUMNA_AUTOR + ") REFERENCES " + AUTORES_NOMBRE_TABLA + "(" + ID_COLUMNA + "));";

        db.execSQL(autores_tabla_create);
        db.execSQL(pensamientos_tabla_create);

        // Insertando autores

        long id_autor;

        ContentValues content = new ContentValues();
        content.put(AUTORES_COLUMNA_NOMBRE, "Steve Jobs");
        content.put(AUTORES_COLUMNA_DESCRIPCION, "Fundador de Apple");


        id_autor = db.insert(AUTORES_NOMBRE_TABLA, null, content);

        content.clear();
        content.put(PENSAMIENTOS_COLUMNA_AUTOR, id_autor);
        content.put(PENSAMIENTOS_COLUMNA_CITA, "Tu tiempo es limitado, así que no lo malgastes viviendo la vida de otra persona [...] No dejes que el ruido de las opiniones de otros apague tu propia voz interior.");
        db.insert(PENSAMIENTOS_NOMBRE_TABLA, null, content);

        content.clear();
        content.put(PENSAMIENTOS_COLUMNA_AUTOR, id_autor);
        content.put(PENSAMIENTOS_COLUMNA_CITA, "No puedes preguntarle a los consumidores qué quieren y luego pretender dárselo. En el tiempo que has estado fabricándolo, ellos querrán una cosa nueva.");
        db.insert(PENSAMIENTOS_NOMBRE_TABLA, null, content);

        content.clear();
        content.put(PENSAMIENTOS_COLUMNA_AUTOR, id_autor);
        content.put(PENSAMIENTOS_COLUMNA_CITA, "No hemos sido los primeros, pero seremos los mejores.");
        db.insert(PENSAMIENTOS_NOMBRE_TABLA, null, content);

        content.clear();
        content.put(PENSAMIENTOS_COLUMNA_AUTOR, id_autor);
        content.put(PENSAMIENTOS_COLUMNA_CITA, "Estoy convencido de que la mitad de lo que separa a los emprendedores exitosos de los que no triunfan es la perseverancia");
        db.insert(PENSAMIENTOS_NOMBRE_TABLA, null, content);


        content.clear();
        content.put(AUTORES_COLUMNA_NOMBRE, "Jack Dorsey");
        content.put(AUTORES_COLUMNA_DESCRIPCION, "Fundador de Twitter");



    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if (!db.isReadOnly())
            // Se activa el funcionamiento de claves foraneas.
            db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
