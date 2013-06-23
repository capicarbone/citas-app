package capicp.test.sherlockdrawer;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

import capicp.test.sherlockdrawer.R;

/**
 * Created by capi on 22/06/13.
 */
public class FragmentListaPensamientos extends SherlockListFragment {

    String[] pensamientos;
    boolean pantalla_compartida = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pensamientos = getResources().getStringArray(R.array.pensamientos_emprendedores);

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                pensamientos));

        View fragment_pensamiento = getActivity().findViewById(R.id.pensamiento_detalle);
        pantalla_compartida = fragment_pensamiento != null && fragment_pensamiento.getVisibility() == View.VISIBLE;

        if (pantalla_compartida){
            FragmentPensamiento inicio = new FragmentPensamiento("Seleciona un pensamiento");

            FragmentTransaction ft = getSherlockActivity().getSupportFragmentManager().beginTransaction();
            ft.add(R.id.pensamiento_detalle, inicio);
            ft.commit();
        }

    }
}
