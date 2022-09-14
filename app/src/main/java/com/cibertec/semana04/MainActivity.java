package com.cibertec.semana04;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.semana04.entity.Pais;
import com.cibertec.semana04.service.ServiceEditorial;
import com.cibertec.semana04.service.ServicePais;
import com.cibertec.semana04.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText txtRazonSocial;
    private EditText txtDireccion;
    private EditText txtRUC;
    private EditText txtFechaCreacion;
    private Button btnRegistrar;


    private Spinner spnPais;
    private ArrayAdapter<String> adapter;
    private List<String> lstPaises = new ArrayList<String>();

    //servicios REST
    private ServicePais servicePais;
    private ServiceEditorial serviceEditorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRazonSocial = findViewById(R.id.txtRegEdiRazonSocial);
        txtDireccion = findViewById(R.id.txtRegEdiDirecccion);
        txtRUC = findViewById(R.id.txtRegEdiRuc);
        txtFechaCreacion = findViewById(R.id.txtRegEdiFechaCreacion);
        btnRegistrar = findViewById(R.id.btnRegEdiEnviar);

        spnPais = findViewById(R.id.spnRegEdiPais);
        adapter = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstPaises);
        spnPais.setAdapter(adapter);

        servicePais = ConnectionRest.getConnecion().create(ServicePais.class);
        serviceEditorial = ConnectionRest.getConnecion().create(ServiceEditorial.class);

        cargaPais();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void cargaPais(){
        Call<List<Pais>>  call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                    List<Pais> lista = response.body();
                    lstPaises.add("[Seleccione País]");
                    for(Pais obj:lista){
                        lstPaises.add( obj.getIdPais() + ": " + obj.getNombre());
                    }
                    adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {

            }
        });
    }

}