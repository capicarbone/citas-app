package capicp.test.sherlockdrawer;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * Created by capi on 27/06/13.
 */
public class DrawerActivity extends SherlockFragmentActivity {

    ArrayAdapter mAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    ListView opcionesDrawer;
    String[] opciones;
    DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        opciones = getResources().getStringArray(R.array.opciones);

        mAdapter = new ArrayAdapter(this, R.layout.opcion_view, opciones );

        opcionesDrawer = (ListView) findViewById(R.id.nav_drawer);
        opcionesDrawer.setAdapter(mAdapter);
        opcionesDrawer.setOnItemClickListener(new DrawerItemSeleccionadoEscucha());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_abierto,
                R.string.drawer_cerrado){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                getSupportActionBar().setTitle("Opciones");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(opciones[opcionesDrawer.getCheckedItemPosition()]);

            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if ( savedInstanceState == null )
            cambiarContenido(0);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(opcionesDrawer))
                    mDrawerLayout.closeDrawer(opcionesDrawer);
                else
                    mDrawerLayout.openDrawer(opcionesDrawer);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cambiarContenido(int seleccionado){



    }

    private class DrawerItemSeleccionadoEscucha implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
            cambiarContenido(pos);
        }
    }
}
