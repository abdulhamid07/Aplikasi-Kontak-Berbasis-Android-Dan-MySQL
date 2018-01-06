package a163110001.putri.kontak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{
    //class Main dengan turunan AppCompatActivity menggunkaan interface ListView

    private  ListView listView;
    private String JSON_STRING;
    //digunakan untuk mendeklarasikan variabel beserta tipe

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //untuk mengatur tampilan layout menggunakan activity main
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //untuk menambahkan toolbar.(berada diatas aplikasi)
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TambahKontak.class);
                startActivity(intent);
                //digunakan untuk membuat tombol tambah kontak dan membuat action ketika tombol
                //di klik maka akan muncul ke halaman tambah kontak
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
        //digunakan untuk menbahakan listView ke halaman activity (daftar Kontak)
    }
    // 34-53 method onCreate  yang pertama kali dijalanka ketika halaman aplikasi dibuka

    private void showEmployee(){

        JSONObject jsonObject;
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        //digunakan untuk mendeklarasikan variabel lst dengan tipe Array
        try {
            jsonObject = new JSONObject(JSON_STRING);
            //membuat objek baru dengan parameter JSON_STRING
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
//digunakan untuk membuat perintah yang berfungsi untuk megmabil data dlm bentuk JSON
//dari class Konfigurasi

            for (int i = 0; i < result.length();i++){
                JSONObject object = result.getJSONObject(i);
                //digunakan untuk mengambil data dlam bentuk array dengan index ke i
                //dimasukan ke variabel objek

                String id=object.getString(Konfigurasi.TAG_ID);
                String nama_kontak = object.getString(Konfigurasi.TAG_NAMA);
                String no_telp = object.getString(Konfigurasi.TAG_TELP);
//77-79 digunakna untuk mengambil data dari masing-masing TAG dimasukan ke masing-masing variabel

                HashMap<String,String> employees = new HashMap<>();
//digunak utuk membuat objek employes dengn tipe hasMap
                employees.put(Konfigurasi.TAG_ID,id);
                employees.put(Konfigurasi.TAG_NAMA,nama_kontak);
                employees.put(Konfigurasi.TAG_TELP,no_telp);
                //diguanakn untuk menambahkan nilai dari masing-masing varibale di petakan dalam sebuah tag yang ad di
                //class konfigurasi
                list.add(employees);
                //menambahkan data dari objek employes ke dalam list.
            }
        } catch (JSONException e) {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage(e.getMessage())
                    .show();
        }catch (NullPointerException e){
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage(e.getMessage())
                    .show();
        }
        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this,list,R.layout.list_item,
                new String[] {Konfigurasi.TAG_NAMA, Konfigurasi.TAG_TELP},
                new int[] {R.id.nama_kontak,R.id.no_telp});
        listView.setAdapter(adapter);
    //diguankan untuk menambahkan nilai" yang akan ditampilkan di listView dalam bentuk array
    }

    private void getJSON(){

        class GetJSON extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        MainActivity.this,
                        "Mengambil Data",
                        "Mohon tunggu...",
                        false,true);
            }
            //115-122 methid onPreExecute di jalankan pada saat proses
            //pengambilan data dlm bentuk JSON dari databases (loading)

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }
           //127-131 di jalankan  pada saat proses pengambilan data sudah selesai,
            //dan menjalnkan method showEmploye untuk menampilkan data

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s =rh.sendGetRequest(Konfigurasi.URL_GET_ALL,MainActivity.this);
                return s;
            }
            //method senGetRequest digunakna untuk menjalankan query dari file tampil_semua.php dari class Konfigurasi
            //untuk mengambil data lalu disimpan dalam variabel s.
        }

        GetJSON js = new GetJSON();
        js.execute();
        //digunakan untuk mengambil data dalam bentuk json
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, TampilKontak.class);
        HashMap<String,String> map = (HashMap)adapterView.getItemAtPosition(i);
        intent.putExtra(Konfigurasi.EMP_ID,map.get(Konfigurasi.TAG_ID).toString());
        startActivity(intent);
        //digunakan untuk membuat action ketika salah satu listview dipilih, maka akan
        //menampilkan tampilkontak dengan mengirimkan data EMP_ID
        //yang didapat dari TAG_ID dari kelas KOnfigurasi
    }
}
