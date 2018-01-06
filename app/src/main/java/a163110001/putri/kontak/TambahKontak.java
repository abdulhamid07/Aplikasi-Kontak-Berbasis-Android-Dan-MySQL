package a163110001.putri.kontak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class TambahKontak extends AppCompatActivity implements View.OnClickListener {
    private EditText enama,etelp;
    private Button bsimpan,blihat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kontak);

            enama   = (EditText) findViewById(R.id.editTextName);
            etelp    = (EditText) findViewById(R.id.editTextTelp);

            bsimpan = (Button) findViewById(R.id.buttonAdd);
            blihat  = (Button) findViewById(R.id.buttonView);

            bsimpan.setOnClickListener(this);
            blihat.setOnClickListener(this);

        }


    private void reset(){

    }
    private void AddEmployee(){
        final String name = enama.getText().toString().trim();
        final String telp = etelp.getText().toString().trim();
        //digunakan untuk mengambil data yang ada divariabel enama dan etelp kemudian dimasukkan kedalam variabel name dan telp

        class AddEmployee extends AsyncTask<Void,Void,String > {

            ProgressDialog loading;
            //digunakan untuk mendeklarasikan variabel loading dengan tipe progressdialog
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        TambahKontak.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false,false);
            }
            //methid onPreExecute di jalankan pada saat proses
            //penyimpanan data dlm bentuk JSON ke databases (loading)

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                reset();
                Toast.makeText(TambahKontak.this, s, Toast.LENGTH_LONG).show();
            }
            //method onPostExecute di jalankan  pada saat proses penyimpanan data sudah selesai,
            //setelah selesai maka akan menuju halaman tambahKontak

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_EMP_NAMA,name);
                params.put(Konfigurasi.KEY_EMP_TELP,telp);
                RequestHandler requestHandler = new RequestHandler();
                return requestHandler.sendPostRequest(Konfigurasi.URL_ADD,params);
            }
            //method senGetRequest digunakna untuk menjalankan query dari file tambah.php dari class Konfigurasi
            //untuk menambah data ke dalam databases.
        }

        new AddEmployee().execute();
        //digunakan utuk menjalankan method AddEmpolyed yang bertujuan untuk menjalankan atau mengeksekusi query yang ada di
        //tambah.php
    }

    @Override
    public void onClick(View view) {

        if (view == bsimpan){
            AddEmployee();
            enama.setText(null);
            etelp.setText(null);
        }
        //jika yang di klik button simpan maka jalankan method AddEmployed untuk menambhkan data
        //dan komponene enama dan etlfn di beri nilai null
        if (view == blihat){
            startActivity(new Intent(TambahKontak.this,MainActivity.class));
        }
        //ketika button lihat di klik maka akan menmapilkan halaman mainActivity
    }
}
