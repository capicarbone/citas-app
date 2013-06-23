package capicp.test.sherlockdrawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Created by capi on 22/06/13.
 */
public class FragmentPensamiento extends SherlockFragment {

    private String pensamiento;
    private String autor;

    public FragmentPensamiento(String mensaje) {

        pensamiento = mensaje;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View pensamiento_view = inflater.inflate(R.layout.fragment_pensamiento, container, false);

        TextView texto = (TextView) pensamiento_view.findViewById(R.id.pensamiento_cuerpo);
        texto.setText(pensamiento);

        return texto;

    }
}
