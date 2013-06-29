package capicp.test.sherlockdrawer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentTransaction;


import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;

public class PensamientoActivity extends DrawerActivity {

    public static final String PENSAMIENTO_MENSAJE = "capicp.test.sherlockdrawer.PENSAMIENTO_MENSAJE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
        }

        if (savedInstanceState == null){
            Intent i = getIntent();
            String pensamiento = i.getStringExtra(PENSAMIENTO_MENSAJE);
            FragmentPensamiento pensamientoFr = new FragmentPensamiento(pensamiento);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.listado, pensamientoFr);
            ft.commit();
        }


    }

    
}
