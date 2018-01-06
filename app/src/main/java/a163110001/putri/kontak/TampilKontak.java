package a163110001.putri.kontak;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TampilKontak extends AppCompatActivity implements View.OnClickListener{

    private EditText eid,enama,etelp;
    private Button bedit,bhapus;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_kontak);


        enama = (EditText) findViewById(R.id.editTextName);
        etelp = (EditText) findViewById(R.id.editTextTelp);


        bedit = (Button) findViewById(R.id.buttonUpdate);
        bhapus = (Button) findViewById(R.id.buttonDelete);

        bedit.setOnClickListener(this);
        bhapus.setOnClickListener(this);

        id = getIntent().getStringExtra(Konfigurasi.EMP_ID);

        getEmployee();
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        TampilKontak.this,"Mengambil Data",
                        "Tunggu Sebentar...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }
            //method yang digunakn untuk negambil data dari metod showEmploye

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(Konfigurasi.URL_GET_EMP,id);
            }
        }
        new GetEmployee().execute();
    }

    private void showEmployee(String json){
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            enama.setText(c.getString(Konfigurasi.TAG_NAMA));
            etelp.setText(c.getString(Konfigurasi.TAG_TELP));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(){
        final String nama_kontak = enama.getText().toString().trim();
        final String no_telp = etelp.getText().toString().trim();


        class UpdateEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        TampilKontak.this,"Mengubah",
                        "Tunggu Sebentar...",false,false
                );

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilKontak.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                HashMap<String,String> map = new HashMap<>();
                map.put(Konfigurasi.KEY_EMP_ID,id);
                map.put(Konfigurasi.KEY_EMP_NAMA,nama_kontak);
                map.put(Konfigurasi.KEY_EMP_TELP,no_telp);
                RequestHandler rh = new RequestHandler();
                return rh.sendPostRequest(Konfigurasi.URL_UPDATE_EMP,map);

            }
        }

        new UpdateEmployee().execute();
    }

    private void deleteEmployee(){
        class DeleteEmpolyee extends AsyncTask<Void,Void,String>{

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(
                        TampilKontak.this,"Menghapus Data",
                        "Tunggu Sebentar",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(TampilKontak.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //sintak yang digunkan untuk menghapus data di program
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(Konfigurasi.URL_DELETE_EMP,id);
            }
        }

        new DeleteEmpolyee().execute();
    }

    private void confirmDelete(){
        new AlertDialog.Builder(TampilKontak.this)
                .setMessage("Hapus data kontak ini ?")
                .setTitle("KONFIRMASI")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteEmployee();
                        startActivity(new Intent(TampilKontak.this,MainActivity.class));
                    }
                })
                .setNegativeButton("Tidak",null)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (view == bedit){
            updateEmployee();
            Intent intent = new Intent(TampilKontak.this, MainActivity.class);
            startActivity(intent);
        }else if (view == bhapus){
            confirmDelete();

        }
    }
}
