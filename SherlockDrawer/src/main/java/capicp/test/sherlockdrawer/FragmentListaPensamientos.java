package capicp.test.sherlockdrawer;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

import java.util.ArrayList;
import java.util.List;

import capicp.test.sherlockdrawer.R;
import capicp.test.sherlockdrawer.data.Pensamiento;
import capicp.test.sherlockdrawer.data.PensamientoBDHelper;
import capicp.test.sherlockdrawer.data.PensamientosLoader;

/**
 * Created by capi on 22/06/13.
 */
public class FragmentListaPensamientos extends SherlockListFragment implements LoaderManager.LoaderCallbacks<List<Pensamiento>> {

    private static final String _ID = "FragmentListaPensamientos";
    private static final int PENSAMIENTOS_LOADER_ID = 256;

    private static final String SELECCIONADO_KEY = "seleccionado";
    private static final String CITA_KEY = "cita";
    private static final String AUTOR_KEY = "autor";
    private static final String DESCRIPCION_AUTOR_KEY = "descripcion_autor";
    private static final String FOTO_AUTOR_KEY = "foto";
    private static final String CATEGORIA_KEY = "categoria";

    private List<Pensamiento> pensamientos;
    private boolean pantalla_compartida = false;
    private PensamientosAdapter mAdapter;
    private int seleccionado = -1;
    private int categoria = 0;

    public FragmentListaPensamientos(int categoria){
        this.categoria = categoria;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Pensamiento> list_view_elementos = new ArrayList<Pensamiento>();

        getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        getListView().setBackgroundColor(getResources().getColor(R.color.default_color));

        mAdapter = new PensamientosAdapter(getActivity(),list_view_elementos);

        setListAdapter(mAdapter);

        View fragment_pensamiento = getActivity().findViewById(R.id.detalle);
        pantalla_compartida = fragment_pensamiento != null && fragment_pensamiento.getVisibility() == View.VISIBLE;

        Pensamiento p = null;

        if (savedInstanceState != null){

            p = new Pensamiento();

            seleccionado = savedInstanceState.getInt(SELECCIONADO_KEY);
            p.setCita(savedInstanceState.getString(CITA_KEY));
            p.setAutor_nombre(savedInstanceState.getString(AUTOR_KEY));
            p.setAutor_descripcion(savedInstanceState.getString(DESCRIPCION_AUTOR_KEY));
            p.setAutor_foto(savedInstanceState.getString(FOTO_AUTOR_KEY));

        }

        if (pantalla_compartida){
            FragmentPensamiento inicio;

            if ( p == null )
                inicio = new FragmentPensamiento("Selecciona algún pensamiento de la lista");
            else
                inicio = new FragmentPensamiento(p);

            FragmentTransaction ft = getSherlockActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.detalle, inicio);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }else{
            if (savedInstanceState != null ){
                Intent intent = new Intent(getSherlockActivity(), PensamientoActivity.class);
                intent.putExtra(PensamientoActivity.PENSAMIENTO_MENSAJE, p.getCita());
                intent.putExtra(PensamientoActivity.PENSAMIENTO_AUTOR, p.getAutor_nombre());
                intent.putExtra(PensamientoActivity.PENSAMIENTO_AUTOR_DESCRIPCION, p.getAutor_descripcion());
                intent.putExtra(PensamientoActivity.PENSAMIENTO_AUTOR_FOTO, p.getAutor_foto());
                startActivity(intent);
            }

        }

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle b = new Bundle();
        b.putInt(CATEGORIA_KEY, this.categoria);

        LoaderManager lm = getLoaderManager();

        lm.initLoader(PENSAMIENTOS_LOADER_ID, b, this).forceLoad();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (pantalla_compartida)
            v.setSelected(true);

        cambiar_pensamiento(position);

    }

    @Override
    public Loader<List<Pensamiento>> onCreateLoader(int i, Bundle b) {

        Log.d(_ID, "Se creo el Loader");

        return (Loader<List<Pensamiento>>) new PensamientosLoader(getActivity(), b.getInt(CATEGORIA_KEY));
    }

    @Override
    public void onLoadFinished(Loader<List<Pensamiento>> listLoader, List<Pensamiento> pensamientos) {

        Log.d(_ID, "Se recibieron pensamiento " + pensamientos.size());

        mAdapter.clear();
        for (Pensamiento pensamiento : pensamientos) {
            mAdapter.add(pensamiento);
        }

        mAdapter.notifyDataSetChanged();

        this.pensamientos =  pensamientos;

    }

    @Override
    public void onLoaderReset(Loader<List<Pensamiento>> listLoader) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (seleccionado != -1 ){

            Pensamiento pensamiento = pensamientos.get(seleccionado);

            outState.putInt(SELECCIONADO_KEY, seleccionado);
            outState.putString(CITA_KEY, pensamiento.getCita());
            outState.putString(AUTOR_KEY, pensamiento.getAutor_nombre());
            outState.putString(DESCRIPCION_AUTOR_KEY, pensamiento.getAutor_descripcion());
            outState.putString(FOTO_AUTOR_KEY, pensamiento.getAutor_foto());
        }

    }

    private void cambiar_pensamiento(int pos){

        if (pantalla_compartida){
            FragmentPensamiento inicio = new FragmentPensamiento(pensamientos.get(pos));

            FragmentTransaction ft = getSherlockActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.detalle, inicio);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }else{
            Intent intent = new Intent(getSherlockActivity(), PensamientoActivity.class);
            intent.putExtra(PensamientoActivity.PENSAMIENTO_MENSAJE, pensamientos.get(pos).getCita());
            intent.putExtra(PensamientoActivity.PENSAMIENTO_AUTOR, pensamientos.get(pos).getAutor_nombre());
            intent.putExtra(PensamientoActivity.PENSAMIENTO_AUTOR_DESCRIPCION, pensamientos.get(pos).getAutor_descripcion());
            intent.putExtra(PensamientoActivity.PENSAMIENTO_AUTOR_FOTO, pensamientos.get(pos).getAutor_foto());
            startActivity(intent);
        }

        seleccionado = pos;
    }
}
