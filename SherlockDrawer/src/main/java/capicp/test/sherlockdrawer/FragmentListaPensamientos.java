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

    private List<Pensamiento> pensamientos;
    private boolean pantalla_compartida = false;
    private ArrayAdapter<String> mAdapter;
    private int seleccionado = -1;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> list_view_elementos = new ArrayList<String>();

        mAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.pensamiento_list_view, R.id.pensamiento_preview,
                list_view_elementos);

        setListAdapter(mAdapter);

        View fragment_pensamiento = getActivity().findViewById(R.id.detalle);
        pantalla_compartida = fragment_pensamiento != null && fragment_pensamiento.getVisibility() == View.VISIBLE;

        String cita = "Selecciona algún pensamiento de la lista";

        if (savedInstanceState != null){
            seleccionado = savedInstanceState.getInt(SELECCIONADO_KEY);
            cita = savedInstanceState.getString(CITA_KEY);
        }

        if (pantalla_compartida){
            FragmentPensamiento inicio = new FragmentPensamiento(cita);

            FragmentTransaction ft = getSherlockActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.detalle, inicio);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }else{
            if (savedInstanceState != null ){
                Intent intent = new Intent(getSherlockActivity(), PensamientoActivity.class);
                intent.putExtra(PensamientoActivity.PENSAMIENTO_MENSAJE, cita);
                startActivity(intent);
            }

        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        LoaderManager lm = getLoaderManager();
        lm.initLoader(PENSAMIENTOS_LOADER_ID, null, this).forceLoad();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        cambiar_pensamiento(position);
        v.setSelected(true);

    }

    @Override
    public Loader<List<Pensamiento>> onCreateLoader(int i, Bundle bundle) {

        Log.d(_ID, "Se creo el Loader");

        return (Loader<List<Pensamiento>>) new PensamientosLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Pensamiento>> listLoader, List<Pensamiento> pensamientos) {

        Log.d(_ID, "Ser finalizo la carga de datos con " + pensamientos.size());

        mAdapter.clear();
        for (Pensamiento pensamiento : pensamientos) {
            mAdapter.add(pensamiento.getCita().substring(0,25) + "...");
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

        outState.putInt(SELECCIONADO_KEY, seleccionado);
        outState.putString(CITA_KEY, pensamientos.get(seleccionado).getCita());
    }

    private void cambiar_pensamiento(int pos){

        if (pantalla_compartida){
            FragmentPensamiento inicio = new FragmentPensamiento(pensamientos.get(pos).getCita());

            FragmentTransaction ft = getSherlockActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.detalle, inicio);
            ft.commit();
        }else{
            Intent intent = new Intent(getSherlockActivity(), PensamientoActivity.class);
            intent.putExtra(PensamientoActivity.PENSAMIENTO_MENSAJE, pensamientos.get(pos).getCita());
            startActivity(intent);
        }

        seleccionado = pos;
    }
}
